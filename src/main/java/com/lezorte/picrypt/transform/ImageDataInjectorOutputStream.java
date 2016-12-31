package com.lezorte.picrypt.transform;

import com.lezorte.picrypt.exceptions.ImageFullException;
import com.lezorte.picrypt.utils.Debug;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created by rjper on 12/24/2016.
 */
public class ImageDataInjectorOutputStream extends OutputStream {

    private BufferedImage image;

    private long totalPixels;
    private long currentPixel = 0;
    private int x = 0, y=0;
    private int currentColor = 0;
    private int currentBitPosition = 0;
    private long messageSize = 0;

    public ImageDataInjectorOutputStream(BufferedImage image) {
        this.image = image;
        totalPixels = ((long)image.getHeight())*((long)image.getWidth());
        try {
            //This is needed so that the message size can be written to the beginning
            write(ByteBuffer.allocate(8).array());
            messageSize = 0;
            System.out.println("-------------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    // splits a byte into 8 individual bits and calls writeBit with each one
    public void write(int b) throws IOException {
        for(int i=0;i<8;i++) {
            writeBit((b&(1<<i))>>>i);
        }
        messageSize++;
    }

    @Override
    public void close() {
        currentPixel = 0;
        currentColor = 0;
        currentBitPosition = 0;
        x = 0;
        y = 0;
        try {
            write(ByteBuffer.allocate(8).putLong(messageSize).array());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // In order to do the reverse...
        // ByteBuffer.wrap(arr).getLong();
    }

    private void writeBit(int b) throws ImageFullException {
        // Check if any variables need to be changed
        if(currentPixel==totalPixels) {
            System.out.println();
            currentPixel = 0;
            x = 0;
            y = 0;
            currentColor++;
            if(currentColor==3) {
                currentColor = 0;
                currentBitPosition++;
                if(currentBitPosition==8) {
                    throw new ImageFullException("All available image data has been used");
                }
            }
        }
        if(x==image.getWidth()) {
            x = 0;
            y++;
        }
        // write data
        int rgb = image.getRGB(x, y);
        Debug.printBinary(rgb, false);
        System.out.print("  ---  ");
        int shift = currentColor*8+currentBitPosition;
        rgb &= (~(1<<shift));
        rgb |= (b<<shift);
        Debug.printBinary(rgb, true);
        image.setRGB(x, y, rgb);
        // increment variables
        currentPixel++;
        x++;
    }

}
