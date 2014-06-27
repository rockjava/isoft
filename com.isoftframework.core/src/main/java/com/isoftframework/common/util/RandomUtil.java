
package com.isoftframework.common.util;

import java.util.Random;

/**
 * 随机数工具类
 */

public class RandomUtil
{
	private RandomUtil()
	{

	}

	/**
	 * 得到width长度的随机数字组成的字符串
	 * 
	 * @param width
	 * @return
	 */
	public static String getRand(int width)
	{
		Random random = new Random();
		String strRand = "";
		for (int i = 0; i < width; i++)
		{
			String rand = String.valueOf(random.nextInt(10));
			strRand += rand;
		}
		return strRand;
	}

}
