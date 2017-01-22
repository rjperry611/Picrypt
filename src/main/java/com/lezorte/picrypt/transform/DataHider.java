package com.lezorte.picrypt.transform;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by lezorte on 12/3/16.
 */
public class DataHider {


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
