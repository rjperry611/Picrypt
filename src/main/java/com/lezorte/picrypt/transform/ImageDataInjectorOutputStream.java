package com.lezorte.picrypt.transform;

import com.lezorte.picrypt.exceptions.ImageFullException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created by rjper on 12/24/2016.
 */
public class ImageDataInjectorOutputStream extends OutputStream {

    private BufferedImage image;

    private int x = 0;
    private int y = 0;
    private int currentColor = 0;
    private int currentBitPosition = 0;
    private long messageSize = -8;

    public ImageDataInjectorOutputStream(BufferedImage image) {
        this.image = image;
        try {
            //This is needed so that the message size can be written to the beginning
            write(ByteBuffer.allocate(8).array());
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
        // Go back to beginning
        x = 0;
        y = 0;
        currentColor = 0;
        currentBitPosition = 0;
        // Write size of data written to image
        try {
            write(ByteBuffer.allocate(8).putLong(messageSize).array());
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = null;
    }

    private void writeBit(int b) throws ImageFullException {
        // Check if any variables need to be changed
        if(x==image.getWidth()) {
            x = 0;
            y++;
            if(y==image.getHeight()) {
                y = 0;
                currentColor++;
                if (currentColor == 3) {
                    currentColor = 0;
                    currentBitPosition++;
                    if (currentBitPosition == 8) {
                        throw new ImageFullException("All available image data has been used");
                    }
                }
            }
        }

        // write data
        int rgb = image.getRGB(x, y);
        int shift = currentColor*8+currentBitPosition;
        rgb &= (~(1<<shift));
        rgb |= (b<<shift);
        image.setRGB(x, y, rgb);
        x++;
    }

}
