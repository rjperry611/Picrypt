package com.lezorte.picrypt.transform;

import com.lezorte.picrypt.exceptions.ImageCorruptException;
import com.lezorte.picrypt.exceptions.VersionDoesNotExistException;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Main logic for extracting data from an image
 */
public class DataExtractor {

    /**
     * Seek will extract data from an image if data has been hidden in that image
     * @param imagePath Path to the image that has the data hidden in it
     * @param saveFolderPath Path to the folder which the extracted file will be saved to. The name of the file itself will have been hidden in the image with the data
     * @param password Password to decrypt the data
     * @throws IOException
     */
    public static void seek(String imagePath, String saveFolderPath, String password) throws IOException {
        try (InputStream imageDataExtractorInputStream = new ImageDataExtractorInputStream(imagePath);
             InputStream decrypterInputStream = DecrypterInputStream.getInstance(password, imageDataExtractorInputStream)) {

            // Extract the file name from the image
            ByteArrayOutputStream pathBuilder = new ByteArrayOutputStream();
            int next;
            while((next=decrypterInputStream.read())!=0) {
                if(next==-1) {
                    throw new ImageCorruptException("Could not read save path from image. Did you enter the wrong password?");
                }
                pathBuilder.write(next);
            }
            String path = pathBuilder.toString("UTF-8");
            if(saveFolderPath.charAt(saveFolderPath.length()-1) != '/') {
                saveFolderPath += "/";
            }
            path = saveFolderPath + path;

            // Extract the data from the image
            try (OutputStream fileOutputStream = new FileOutputStream(path)) {
                IOUtils.copy(decrypterInputStream, fileOutputStream);
            }

        } catch (VersionDoesNotExistException e) {
            throw new ImageCorruptException("Could not read version number from image. Did you enter the wrong password?");
        }
    }
}
