package com.lezorte.picrypt.transform;

import com.lezorte.picrypt.exceptions.ImageCorruptException;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * ImageDataExtractorInputStream will extract the data from an image that an ImageDataHiderOutputStream created.
 */
public class ImageDataExtractorInputStream extends InputStream {

    private BufferedImage image;

    private int x = 0;
    private int y = 0;
    private int currentColor = 0;
    private int currentBitPosition = 0;
    private int version = 0;
    private long messageSize = 8;
    private long totalBytesRead = 0;

    /**
     * Loads the image to extract from using the path provided
     * @param imagePath
     * @throws IOException
     */
    public ImageDataExtractorInputStream(String imagePath) throws IOException {
        // Load image and check for issues
        File imageFile = new File(imagePath);
        if(!imageFile.exists()) {
            throw new FileNotFoundException("Image file does not exist");
        }
        if(!FilenameUtils.getExtension(imagePath).toLowerCase().equals("png")) {
            throw new IOException("Only PNG image files are supported for extraction");
        }
        this.image = ImageIO.read(imageFile);

        init();
    }

    /**
     * Extracts the data from the image provided
     * @param image
     * @throws IOException
     */
    public ImageDataExtractorInputStream(BufferedImage image) throws IOException {
        this.image = image;

        init();
    }

    private void init() throws IOException {
        // Read the length of the data
        byte[] messageSizeAsArray = new byte[8];
        read(messageSizeAsArray);
        messageSize = ByteBuffer.wrap(messageSizeAsArray).getLong();

        // Read the version
        this.version = read();
        if(version!=1) {
            throw new ImageCorruptException("Data in image is corrupt");
        }

        // Reset bytes read variable
        totalBytesRead = 0;
    }

    /**
     * Returns the available number of bytes left in the image
     * @return
     */
    public long availableData() {
       return messageSize - totalBytesRead;
    }

    @Override
    public int read() throws IOException{
        if(totalBytesRead == messageSize) {
            return -1;
        }
        int byteValue = 0;
        for(int i=0;i<8;i++) {
            byteValue |= readBit()<<i;
        }
        totalBytesRead++;
        return byteValue;
    }

    @Override
    public void close() {
        image = null;
    }

    private int readBit() throws ImageCorruptException {
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
                        throw new ImageCorruptException("Message size is larger than image can store. Image is corrupt or not a Picrypt image.");
                    }
                }
            }
        }

        // Read the bit
        int rgb = image.getRGB(x, y);
        int shift = currentColor*8+currentBitPosition;
        x++;
        return (rgb & (1<<shift))>>>shift;
    }
}
