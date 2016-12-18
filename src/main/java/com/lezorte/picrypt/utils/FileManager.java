package com.lezorte.picrypt.utils;

import com.lezorte.picrypt.exceptions.FileTooBigException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystem;

import javax.imageio.ImageIO;


/**
 * Created by lezorte on 12/3/16.
 */
public class FileManager {

	public FileManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static byte[] loadFile(String path) throws FileTooBigException {
		File file = new File(path);
		try {
			FileInputStream fin = new FileInputStream(file);
			long length = file.length();
			if (/*length > (long)Constants.MAXIMUM_ARRAY_SIZE*/false) {
				fin.close();
				throw new FileTooBigException();
			}
			byte[] data = new byte[(int)file.length()];
			fin.read(data);
			fin.close();
			
			return data;
		} catch (IOException e) {
			
		}
		return null;		
	}
	
	public static byte[] loadFileWithName(String path) throws FileTooBigException {
		File file = new File(path);
		byte[] fileData = loadFile(path);
		byte[] fileName = (byte[])file.getName().getBytes();
		byte[] combine = new byte[fileData.length + fileName.length + 1];
		for(int i=0;i<fileName.length;i++) {
			combine[i]=fileName[i];
		}
		combine[fileName.length] = 0;
		for(int i=0;i<fileData.length;i++) {
			combine[i+fileName.length+1] = fileData[i];
		}
		return combine;
	}
	
	public static void saveFile(byte[] data, String folderPath) {
		int zeroIndex = 0;
		while(data[zeroIndex]!=0)zeroIndex++;
		String path = new String(data,0,zeroIndex);
		try {
			FileOutputStream fout = new FileOutputStream(new File(folderPath + File.separator + path));
			fout.write(data,zeroIndex+1,data.length-zeroIndex-1);
			fout.close();
		} catch (IOException e) {
			
		}
	}
	
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
		}
		return null;
	}
	
	public static void saveImage(BufferedImage image, String path) {
		try {
			ImageIO.write(image, "png", new File(path));
		} catch (IOException e) {
			
		}
	}

}
