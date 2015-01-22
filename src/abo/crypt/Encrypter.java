package abo.crypt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class Encrypter
{
	public static void encrypt(String input, String output)throws IOException
	{
		int[] data = byteToInt(header(Files.readAllBytes(Paths.get(input)), input));
		int width = nearestSquareDivisor(data.length);
		int height = data.length / width;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				image.setRGB(x, y, data[x+y*width]);
		ImageIO.write(image, "png", new File(output));
	}

	private static byte[] header(byte[] bytes, String input)
	{
		char[] charray = new File(input).getName().toCharArray();
		byte[] head = new byte[charray.length];
		for(int i = 0; i < charray.length; i++)
			head[i] = (byte)charray[i];
		byte[] header = new byte[head.length + bytes.length + 1];
		System.arraycopy(head, 0, header, 0, head.length);
		System.arraycopy(bytes, 0, header, head.length + 1, bytes.length);
		return header;
	}

	private static int nearestSquareDivisor(int num)
	{
		int divisor = (int) Math.sqrt(num);
		int divided = num/divisor;
		while(divisor*divided != num)
		{
			divisor--;
			divided = num/divisor;
		}
		return divisor;
	}

	private static int[] byteToInt(byte[] bytes)
	{
		int length = bytes.length;
		if(bytes.length/4*4 != bytes.length)
		{
			byte[] bys = new byte[bytes.length/4*4+4];
			System.arraycopy(bytes, 0, bys, 0, bytes.length);
			bytes = bys;
		}
		int[] ints = new int[bytes.length/4+1];
		ints[0] = length;
		for(int i = 0; i < bytes.length; i+=4)
			ints[i/4+1] = ((bytes[i]&0xff)<<24)|((bytes[i+1]&0xff)<<16)|((bytes[i+2]&0xff)<<8)|((bytes[i+3]&0xff)<<0);
		return ints;
	}
}