package abo.crypt;

public class Main
{
	public static void main(String[] args)
	{
		try{
			if(args.length == 3)
			{
				if(args[0].equals("-enc"))
				{
					Encrypter.encrypt(args[1], args[2]);
					System.out.println("Successfully encrypted to "+args[2]);
					return;
				}
				else if(args[0].equals("-dec"))
				{
					Decrypter.decrypt(args[1], args[2]);
					return;
				}
			}
			System.out.println("-enc <input file> <output file>\t\tEncrypts a file.\n" +
					"-dec <input file> <output folder>\tDecrypts a file.");
		}catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
}