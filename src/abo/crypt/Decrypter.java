package abo.crypt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Decrypter
{
	public static void decrypt(String input, String output)throws IOException
	{
		BufferedImage image = ImageIO.read(new File(input));
		int length = image.getRGB(0, 0);
		byte[] data = data(image, length);
		String header = header(data);
		byte[] raw = new byte[data.length-header.length()-1];
		System.arraycopy(data, header.length()+1, raw, 0, raw.length);
		data = raw;
		File file = new File(output+"\\"+header);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(data);
		fos.close();
		System.out.println("Successfully decrypted to "+file.getAbsolutePath());
	}

	private static String header(byte[] data)
	{
		int i = 0;
		String header = "";
		while(data[i] != 0)
			header += (char)data[i++];
		return header;
	}

	private static byte[] data(BufferedImage image, int length)
	{
		int width = image.getWidth();
		int height = image.getHeight();
		byte[] dataTmp = new byte[width*height*4-1];
		for(int y = 0; y < height; y++)
			for(int x = (y==0?1:0); x < width; x++)
			{
				int i = (x-1+y*width)*4;
				int rgb = image.getRGB(x, y);
				dataTmp[i+0] = (byte)((rgb>>24)&0xff);
				dataTmp[i+1] = (byte)((rgb>>16)&0xff);
				dataTmp[i+2] = (byte)((rgb>> 8)&0xff);
				dataTmp[i+3] = (byte)((rgb>> 0)&0xff);
			}
		byte[] data = new byte[length];
		System.arraycopy(dataTmp, 0, data, 0, length);
		return data;
	}
}