package com.lezorte.picrypt.transform;

import com.lezorte.picrypt.exceptions.ImageFullException;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.Assert.fail;

/**
 * Created by lezorte on 12/29/16.
 */
public class ImageDataHiderOutputStreamTest {

    @Test
    public void testImageFullException() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/2by2.png"));
            ImageDataHiderOutputStream outputStream = new ImageDataHiderOutputStream(image);
            while(outputStream.availableSpace()>0) {
                outputStream.write((byte)'a');
            }
            outputStream.close();
        } catch(ImageFullException e) {
            fail("Image should not throw exception if every bit is filled");
        } catch(IOException e) {
            e.printStackTrace();
            fail("unknown IOException");
        }

        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/2by2.png"));
            ImageDataHiderOutputStream outputStream = new ImageDataHiderOutputStream(image);
            while(outputStream.availableSpace()>-1) {
                outputStream.write((byte)'a');
            }
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
