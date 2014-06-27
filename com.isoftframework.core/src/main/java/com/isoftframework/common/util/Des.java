package com.isoftframework.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <ul>
 * <li> <b>目的:</b> <br />
 * <p>
 * 包含三重DES加密算法
 * </p>
 * </li>
 * <li><b>采用的不变量：</b></li>
 * <li><b>并行策略：</b></li>
 * <li> <b>修改历史：</b><br />
 * <p>
 * 创建: Jan 30, 2008 2:16:55 PM<br />
 * 作者:liulibin@sinovatech.com
 * </p>
 * </li>
 * <li><b>已知问题：</b></li>
 * </ul>
 */
public class Des
{

	public static final String DES = "DES";

	// 3位
	public static final String DESede = "DESede";

	// 2重
	public static final String Blowfish = "Blowfish";

	public static void main(String[] args)
	{
		String key = "123456781234567812345678123456781234567812345678";

		String str = "5GLdlihlArMbnfDo4IaAkPjdmBQWKo%2FyOx7oqw%2BB5aFE2QhE3H%2FUWUjUp2FYyCTsZ8Lq4oLV6%2B%2BT%0D%0A2OpV63fSHTsMfFRSyjQ1KbP28WG7odS6XIDNtW2U4MMckTUzE4KOYQsBD0K1XpderKp4TFsVfmXQ%0D%0AX0jqkB2yKeflOxoq06d5m20FP09L1ukkRAKgw9PKKOfTH0tKGmGI2UR5u3VfniBqZWhoVJluPJT1%0D%0AOogwoKLxpz%2BEfgE3mdp69H%2Fd4PQnsvB0FoWjyxUcjOmSWpCWL%2BagWWlNd10E%2BDrc93vOTWoNYL1M%0D%0ApyoeCKGb%2BaUJFCClsoNiMbVka4sqUiGy4Yl%2BN2PiIzB4cPysq0tiiZfpkCQajMMhTjvylImH8PiZ%0D%0A07uaRJmq1Vl3Uq7qykNbvznxBF%2Bfth3sqN6NMQYasC2%2F8ma2nZQiBijbpZfrSt4ApTVHcocx%2BF4m%0D%0A64nJGkeQQuoC9yJtmKsj1mBeIv%2FC%2BPiLO9u7mspO04fI5oB8KTcoJbQCaYdQeID%2FBFiaIlQmfmr0%0D%0Arv3rag1edzj2nU94IwbOsdUMTvEUiIFpmMnCAU0DFfqFw6X73pG1zgBdsQKKUKNLtBvBQ%2BvRcJTf%0D%0AFUooAHFLuU5yseRqIlJINiD5IGG3GH%2Fl7U9v850wI6TMqGh0RM4f70fKlnv5WT5vZOvPQPNe6%2FP1%0D%0AcUBiXKvjUw%3D%3D";
		try
		{
			str = new URLDecoder().decode(str,"utf-8");
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String c = Des
//				.encrytWithBase64(
//						Des.DESede,
//						str, key);
//		System.out.println("...." + c);
		System.out.println(Des.deEncrytWithBase64(Des.DESede, str, key));
	}

	/**
	 * <p>
	 * <ul>
	 * <li>解密</li>
	 * </ul>
	 * </p>
	 * 
	 * @param Algorithm
	 *            算法
	 * @param myinfo
	 *            待加密的字符串
	 * @param key
	 *            1：Des.DES密钥必须是16位长度的字符串，即16进制编码后必须为8位<br />
	 *            2：Des.Blowfish密钥必须是32位长度的字符串，即16进制编码后必须为16位<br />
	 *            3：Des.DESede密钥必须是48位长度的字符串，即16进制编码后必须为32位<br />
	 * 
	 * @return
	 */
	public static String encrytWithBase64(String Algorithm, String myinfo,
			String key)
	{
		byte[] keybyte = HexString2Bytes(key);

		byte[] str = Des.encryt(Algorithm, myinfo, keybyte);

		return new BASE64Encoder().encode(str);
	}

	/**
	 * <p>
	 * <ul>
	 * <li>解密</li>
	 * </ul>
	 * </p>
	 * 
	 * @param Algorithm
	 *            算法
	 * @param myinfo
	 *            待解密的秘文
	 * @param key
	 *            1：Des.DES密钥必须是16位长度的字符串，即16进制编码后必须为8位<br />
	 *            2：Des.Blowfish密钥必须是32位长度的字符串，即16进制编码后必须为16位<br />
	 *            3：Des.DESede密钥必须是48位长度的字符串，即16进制编码后必须为32位<br />
	 * @return
	 */
	public static String deEncrytWithBase64(String Algorithm, String myinfo,
			String key)
	{
		byte[] keybyte = HexString2Bytes(key);
		byte[] info = null;
		try
		{
			info = new BASE64Decoder().decodeBuffer(myinfo);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		String str = deEncryt(Algorithm, info, keybyte);
		return str;
	}

	/**
	 * <p>
	 * <ul>
	 * <li>解密</li>
	 * </ul>
	 * </p>
	 * 
	 * @param Algorithm
	 *            算法
	 * @param myinfo
	 *            待加密的字符串
	 * @param key
	 *            1：Des.DES密钥必须是16位长度的字符串，即16进制编码后必须为8位<br />
	 *            2：Des.Blowfish密钥必须是32位长度的字符串，即16进制编码后必须为16位<br />
	 *            3：Des.DESede密钥必须是48位长度的字符串，即16进制编码后必须为32位<br />
	 * @return
	 */
	public static byte[] encryt(String Algorithm, String myinfo, String key)
	{
		byte[] keybyte = HexString2Bytes(key);

		byte[] str = Des.encryt(Algorithm, myinfo, keybyte);

		return str;
	}

	/**
	 * <p>
	 * <ul>
	 * <li>解密</li>
	 * </ul>
	 * </p>
	 * 
	 * @param Algorithm
	 *            算法
	 * @param myinfo
	 *            待解密的秘文
	 * @param key
	 *            1：Des.DES密钥必须是16位长度的字符串，即16进制编码后必须为8位<br />
	 *            2：Des.Blowfish密钥必须是32位长度的字符串，即16进制编码后必须为16位<br />
	 *            3：Des.DESede密钥必须是48位长度的字符串，即16进制编码后必须为32位<br />
	 * @return
	 */
	public static String deEncryt(String Algorithm, byte[] myinfo, String key)
	{
		byte[] keybyte = HexString2Bytes(key);

		String str = deEncryt(Algorithm, myinfo, keybyte);
		return str;
	}

	/*
	 * 加密 Algorithm 定义 加密算法,可用 DES,DESede,Blowfish myinfo 要加密的信息
	 */
	private static byte[] encryt(String Algorithm, String myinfo, byte[] keybyte)
	{

		byte[] cipherByte = null;
		try
		{
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// 加密

			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			cipherByte = c1.doFinal(myinfo.getBytes());

		} catch (java.security.NoSuchAlgorithmException e1)
		{
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2)
		{
			e2.printStackTrace();
		} catch (java.lang.Exception e3)
		{
			e3.printStackTrace();
		}
		return cipherByte;

	}

	/*
	 * 解密 Algorithm 定义 加密算法,可用 DES,DESede,Blowfish cipherByte 要解密的信息
	 */
	private static String deEncryt(String Algorithm, byte[] cipherByte,
			byte[] keybyte)
	{
		byte[] clearByte = null;
		try
		{
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			clearByte = c1.doFinal(cipherByte);

		} catch (java.security.NoSuchAlgorithmException e1)
		{
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2)
		{
			e2.printStackTrace();
		} catch (java.lang.Exception e3)
		{
			e3.printStackTrace();
		}
		return new String(clearByte);

	}

	private static int parse(char c)
	{
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	// 从十六进制字符串到字节数组转换
	public static byte[] HexString2Bytes(String hexstr)
	{
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++)
		{
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	// 从字节数组到十六进制字符串转换
	public static final String encodeHex(byte[] bytes)
	{
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		int i;

		for (i = 0; i < bytes.length; i++)
		{
			if (((int) bytes[i] & 0xff) < 0x10)
			{
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}

}