package com.bridgeit.utility;

import java.util.Random;

public class GenereateOtp 
{

	public static String OtpNumber()
	{
		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());
		  
		int num = generator.nextInt(99999) + 99999;
		if (num < 100000 || num > 999999) 
		{
		num = generator.nextInt(99999) + 99999;
		}
		return num+"";    
	}
}
