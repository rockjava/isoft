package com.isoftframework.common.util;

import java.io.UnsupportedEncodingException;

public class ChineseTools {
	/**
	 * 存放国标一级汉字不同读音的起始区位码
	 */
	static final int[] secPosValueList = { 1601, 1637, 1833, 2078, 2274, 2302,
			2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
			4086, 4390, 4558, 4684, 4925, 5249, 9999 };

	/**
	 * 存放国标一级汉字不同读音的起始区位码对应读音
	 */
	static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
			'y', 'z' };

	/**
	 * 获取一个字符串的拼音码
	 * 
	 * @param oriStr
	 * @return
	 */
	public static String getFirstLetter(String oriStr) {
		String temp = "";
		try {
			temp = getFirstLetter(oriStr, 0);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 获取一个字符串的拼音码
	 * 
	 * @param oriStr
	 * @param scale
	 *            返回的拼音字头的位数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getFirstLetter(String oriStr, int scale)
			throws UnsupportedEncodingException {
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;
		for (int i = 0; i < str.length(); i++) {
			// 依次处理str中每个字符
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = new String(temp).getBytes("GBK");
			if (uniCode[0] < 128 && uniCode[0] > 0) {
				// 非汉字
				buffer.append(temp);
			} else {
				// flt002 如果是“晖”字直接输出H
				if ('晖' == ch) {
					buffer.append("H");
				} else {
					buffer.append(convert(uniCode));
				}
			}
		}
		if (buffer.toString().length() < scale || scale == 0) {
			return buffer.toString().toUpperCase();
		} else {
			return buffer.toString().substring(0, scale).toUpperCase();
		}
	}

	/**
	 * 获取一个汉字的拼音首字母
	 * 
	 * @param bytes
	 * @return
	 */
	static char convert(byte[] bytes) {
		char result = '-';
		int secPosValue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= 160;
		}
		secPosValue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosValue >= secPosValueList[i]
					&& secPosValue < secPosValueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// System.out.println(ChineseTools.getFirstLetter("I love u"));
		// System.out.println(ChineseTools.getFirstLetter(""));
		// System.out.println(ChineseTools.getFirstLetter(""));
		try {
			System.out.println(ChineseTools.getFirstLetter("反馈将反馈我好", 1));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
