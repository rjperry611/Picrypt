package com.lezorte.picrypt.transform;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
            ImageDataInjectorOutputStream outputStream = new ImageDataInjectorOutputStream(image);
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
    public void imageDataExtractionLoadTest() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/2by2.png"));
            ImageDataInjectorOutputStream outputStream = new ImageDataInjectorOutputStream(image);
            outputStream.write(new byte[]{'r','g','b'});
            outputStream.close();
            ImageDataExtractorInputStream inputStream = new ImageDataExtractorInputStream(image);
            inputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
            fail("unknown IOException");
        }
    }

}
