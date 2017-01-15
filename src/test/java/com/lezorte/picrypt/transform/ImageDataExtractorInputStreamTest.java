package com.lezorte.picrypt.transform;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by lezorte on 12/31/16.
 */
public class ImageDataExtractorInputStreamTest {

    @Test
    public void testProperImageDataExtraction() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/2by2.png"));
            ImageDataHiderOutputStream outputStream = new ImageDataHiderOutputStream(image);
            outputStream.write(new byte[]{'r','g','b'});
            outputStream.close();
            ImageDataExtractorInputStream inputStream = new ImageDataExtractorInputStream(image);
            assertEquals('r', inputStream.read());
            assertEquals('g', inputStream.read());
            assertEquals('b', inputStream.read());
            assertEquals(-1, inputStream.read());
            inputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
            fail("unknown IOException");
        }
    }


    @Test
    public void testProperImageDataExtractionOnLargeAmountOfData() {
        try {
            int dataSize = 1_000_000;
            BufferedImage image = new BufferedImage(10_000,10_000,BufferedImage.TYPE_INT_ARGB);
            ImageDataHiderOutputStream outputStream = new ImageDataHiderOutputStream(image);
            Random random = new Random();
            int[] data = new int[dataSize];
            for(int i=0;i<dataSize;i++) {
                data[i] = random.nextInt(256);
                outputStream.write(data[i]);
            }
            outputStream.close();
            ImageDataExtractorInputStream inputStream = new ImageDataExtractorInputStream(image);
            for(int i=0;i<dataSize;i++) {
                int b = inputStream.read();
                if(b!=data[i]) {
                    fail("Data extracted from image did not match data written to image.");
                }
            }
            inputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
            fail("unknown IOException");
        }

    }

}
