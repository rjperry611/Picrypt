package com.lezorte.picrypt.transform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by lezorte on 12/3/16.
 */
public class Hider {


    public static void hide(String filePath, String imagePath, String savePath, String password) {
        File file = new File(filePath);
        File imageFile = new File(imagePath);
        if(!imageFile.exists()){
            System.err.println("Image file does not exist");
        }
        try(FileInputStream inputStream = new FileInputStream(file)) {
            int next;
            while((next = inputStream.read()) != -1) {

            }
        } catch (FileNotFoundException e) {
            System.err.println("File to hide does not exist");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
