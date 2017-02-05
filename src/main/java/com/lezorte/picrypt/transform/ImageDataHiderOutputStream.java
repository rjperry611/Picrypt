package com.lezorte.picrypt.transform;

import com.lezorte.picrypt.exceptions.ImageFullException;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * This class takes individual bytes and hides them into an image. It accomplishes this by breaking down the image data
 * into individual pixels and each pixel down to 3 bytes: red, green, and blue. It replaces the bits starting from the
 * least significant and moving towards the most significant until all color data has been replaced by data. As long as
 * 2 or 3 bits are left for all colors, there shouldn't be a noticable difference to the human eye in the new image.
 */
public class ImageDataHiderOutputStream extends OutputStream {

    private BufferedImage image;

    private int x = 0;
    private int y = 0;
    private int currentColor = 0;
    private int currentBitPosition = 0;
    private long messageSize = 0;
    private long totalAllowedMessageSize = 0;

    private String outputImagePath = "";

    private int mode;
    private static final int MEMORY_MODE = 0;
    private static final int DISK_MODE = 1;

    private boolean closed = false;

    private static final int CURRENT_VERSION = 1;

    /**
     * Loads an image from the input path provided. Once close is called on the stream, the new image will be saved
     * to the output path provided
     * @param inputImagePath
     * @param outputImagePath
     * @throws IOException
     */
    public ImageDataHiderOutputStream(String inputImagePath, String outputImagePath) throws IOException {
        this.mode = DISK_MODE;
        this.outputImagePath = outputImagePath;

        // Load image and check for issues
        File imageFile = new File(inputImagePath);
        if(!imageFile.exists()){
            throw new FileNotFoundException("Image file does not exist");
        }
        if(!FilenameUtils.getExtension(outputImagePath).toLowerCase().equals("png")) {
            throw new IOException("Only PNG image files are supported for output");
        }
        image = ImageIO.read(imageFile);
        if(image==null) {
            throw new IOException("Image did not properly load");
        }

        init();
    }

    /**
     * The image provided will be used for hiding data
     * @param image
     * @throws IOException
     */
    public ImageDataHiderOutputStream(BufferedImage image) throws IOException {
        this.mode = MEMORY_MODE;
        this.image = image;

        init();
    }

    private void init() throws IOException {
        //This is needed so that the message size can be written to the beginning
        write(ByteBuffer.allocate(8).array());

        // Write version of data hiding process into the image. This will allow future updates to the
        // encryption/decryption process to be backwards compatible with images produced with older versions.
        write((byte)CURRENT_VERSION);

        // Compute total allowed bytes based on image size
        totalAllowedMessageSize = this.image.getWidth()*this.image.getHeight()*3 - messageSize;

        // Reset message size variable
        messageSize = 0;
    }

    /**
     * Returns the available space in the image for data
     * @return
     */
    public long availableSpace() {
        return totalAllowedMessageSize - messageSize;
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
    public void close() throws IOException {
        if(!closed) {
            // Go back to beginning
            x = 0;
            y = 0;
            currentColor = 0;
            currentBitPosition = 0;
            // Write size of data written to image
            write(ByteBuffer.allocate(8).putLong(messageSize).array());
            if (mode == DISK_MODE) {
                try {
                    ImageIO.write(image, "png", new File(outputImagePath));
                } catch (IOException e) {
                    throw new IOException("Unable to write image");
                }
            }
            image = null;
        }
        closed = true;
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
