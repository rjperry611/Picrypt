package com.lezorte.picrypt.transform;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Main logic for hiding data into an image.
 */
public class DataHider {

    /**
     * Call hide to hide data into an image and save it as a new image. It will encrypt the data as well as hide it.
     * @param dataFilePath Path to the file which contains the data to hide
     * @param imagePath Image which the data will be saved into
     * @param savePath Path which the new image will be saved to
     * @param password Password for encrypting the data
     * @throws IOException
     */
    public static void hide(String dataFilePath, String imagePath, String savePath, String password) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(new File(dataFilePath));
             OutputStream imageDataHiderOutputStream = new ImageDataHiderOutputStream(imagePath, savePath);
             OutputStream encrypterOutputStream = EncrypterOutputStream.getInstance(password, imageDataHiderOutputStream)) {

            encrypterOutputStream.write(FilenameUtils.getName(dataFilePath).getBytes("UTF-8"));
            encrypterOutputStream.write(0);
            IOUtils.copy(inputStream, encrypterOutputStream);

        }
    }


}
