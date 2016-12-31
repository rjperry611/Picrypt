package com.lezorte.picrypt.transform;

import com.lezorte.picrypt.exceptions.ImageFullException;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by lezorte on 12/29/16.
 */
public class ImageDataInjectorOutputStreamTest {

    private static final int[][] expectedPixels = new int[][]{
            {
                    0b1111_1111__1111_0000__0000_0000__0000_0000, //0,0
                    0b1111_1111__0010_0000__0000_0000__0000_0000  //0,1
            },
            {
                    0b1111_1111__0010_0000__1110_0000__0000_0000, //1,0
                    0b1111_1111__1100_0000__1100_0000__1100_0000  //1,1
            }
    };

    @Test
    public void testImageData() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/2by2.png"));
            ImageDataInjectorOutputStream outputStream = new ImageDataInjectorOutputStream(image);
            outputStream.write('r');
            outputStream.close();
            assertEquals(image.getRGB(0,0),expectedPixels[0][0]);
            assertEquals(image.getRGB(0,1),expectedPixels[0][1]);
            assertEquals(image.getRGB(1,0),expectedPixels[1][0]);
            assertEquals(image.getRGB(1,1),expectedPixels[1][1]);
        } catch(IOException e) {
            e.printStackTrace();
            fail("unknown IOException");
        }
    }

    @Test
    public void testImageFullException() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/2by2.png"));
            ImageDataInjectorOutputStream outputStream = new ImageDataInjectorOutputStream(image);
            outputStream.write(new byte[]{'a', 'b', 'c', 'd'});
            outputStream.close();
        } catch(ImageFullException e) {
            fail("Image should not throw exception if every bit is filled");
        } catch(IOException e) {
            e.printStackTrace();
            fail("unknown IOException");
        }

        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/2by2.png"));
            ImageDataInjectorOutputStream outputStream = new ImageDataInjectorOutputStream(image);
            outputStream.write(new byte[]{'a', 'b', 'c', 'd', 'e'});
            fail("Image should throw exception if too much data is provided");
            outputStream.close();
            throw new IOException();
        } catch(ImageFullException e) {
        } catch(IOException e) {
            e.printStackTrace();
            fail("unknown IOException");
        }
    }

}
