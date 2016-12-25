package com.lezorte.picrypt.transform;

import com.lezorte.picrypt.exceptions.ImageFullException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by rjper on 12/24/2016.
 */
public class ImageHider extends OutputStream {

    private BufferedImage image;

    private long totalPixels;
    private int mask = 1;
    private long currentPixel = 0;
    private int x = 0, y=0;
    private int currentColor = 0;
    private int currentBitPosition = 0;

    public ImageHider(BufferedImage image) {
        this.image = image;
        totalPixels = ((long)image.getHeight())*((long)image.getWidth());
    }

    @Override
    public void write(int b) throws IOException {
        for(int i=0;i<8;i++) {
            writeBit((b&(1<<i))>>i);
        }
    }

    private void writeBit(int b) throws ImageFullException {
        if(currentPixel==totalPixels) {
            currentPixel = 0;
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
        image.setRGB(x, y, image.getRGB(x, y) & (b<<(currentColor*8+currentBitPosition)));
        currentPixel++;
        x++;
    }
}
