package com.video.core.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author xuexianbin
 *
 * @date 2014年8月22日 下午10:58:24
 */
public class Utils {

	private static final double EARTH_RADIUS = 6378.137;// 赤道半径(单位km)

	/**
	 * 生成V2版本中的消息序列号
	 * 
	 * num 上次生成的消息序列号
	 * 
	 * @return
	 */
	public static String generateMessageSerialNumber(String num) {
		int hexNumber = Integer.valueOf(num, 16);
		if (hexNumber < 255) {
			hexNumber = hexNumber + 1;
		} else {
			hexNumber = 0;
		}
		String serialNumber = Integer.toHexString(hexNumber);
		if (serialNumber.length() == 1) {
			serialNumber = "0" + serialNumber;
		}
		return serialNumber.toUpperCase();
	}

	/**
	 * 4.1.4.5. 校验 校验是从第一个字节（0xFF）到“应答类型”，每个字节的累加和，用1个字节表示，丢弃溢出的高字节数据。
	 * 
	 * btMDN 手机号 btSerial 消息序列号 replyType 应答类型
	 * 
	 * @return
	 */
	public static byte getCheckSum(byte[] btMDN, byte btSerial, byte replyType) {
		short ckSum = (short) 0xFF;
		for (int i = 0; i < btMDN.length; i++) {
			ckSum += (short) (btMDN[i] & 0xff);
		}
		ckSum += (short) (btSerial & 0xff);
		ckSum += (short) (replyType & 0xff);
		byte btSum = (byte) (ckSum % 256);
		return btSum;
	}

	public static byte getCheckSum(byte[] btLen, byte[] btMDN, byte btSerial,
			byte property, byte[] btMsg) {
		short ckSum = 0;
		ckSum += (short) (btLen[0] & 0xff);
		ckSum += (short) (btLen[1] & 0xff);
		for (int i = 0; i < btMDN.length; i++) {
			byte bt = (byte) (btMDN[i] & 0xff);
			ckSum += (short) bt;
		}
		ckSum += (short) (btSerial & 0xff);
		ckSum += (short) (property & 0xff);
		for (int i = 0; i < btMsg.length; i++) {
			byte bt = (byte) (btMsg[i] & 0xff);
			ckSum += (short) bt;
		}
		byte btSum = (byte) (ckSum % 256);
		// char ctest=(char)(btSum&0xff);
		// byte test=(byte)ctest;
		// btSum&=0xff;
		return btSum;
	}

	public static String toHexString1(byte[] b) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < b.length; ++i) {
			buffer.append(toHexString1(b[i]));
		}
		return buffer.toString();
	}

	public static String toHexString1(byte b) {
		String s = Integer.toHexString(b & 0xFF);
		if (s.length() == 1) {
			return "0" + s;
		} else {
			return s;
		}
	}

	public static String toHexString2(byte[] b) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < b.length; ++i) {
			buffer.append("0x");
			buffer.append(toHexString2(b[i]));
			buffer.append(" ");
		}
		return buffer.toString();
	}

	public static String toHexString2(byte b) {
		char[] buffer = new char[2];
		buffer[0] = Character.forDigit((b >>> 4) & 0x0F, 16);
		buffer[1] = Character.forDigit(b & 0x0F, 16);
		return new String(buffer);
	}

	/**
	 * 数据通道消息格式中，把7E替换为7D02、7D替换为7D01(初始与结束标识符外) 示例: 0x7E -> 0x7D02 0x7D ->
	 * 0x7D01 本方法与decodeByte配套使用
	 */
	public static byte[] encodeByte(byte[] rawArray) {
		int length = rawArray.length;
		if (rawArray == null || length <= 0) {
			return null;
		}

		List<Byte> list = new LinkedList<Byte>();

		for (int i = 0; i < length; i++) {
			byte rawByte = rawArray[i];

			if (i == 0 || i == length - 1) {// 忽略初始与结束标识符
				list.add(rawByte);
				continue;
			}
			if (rawByte == 0x7E) {
				list.add((byte) 0x7D);
				list.add((byte) 0x02);
			} else if (rawByte == 0x7D) {
				list.add((byte) 0x7D);
				list.add((byte) 0x01);
			} else {
				list.add(rawByte);
			}
		}

		// 转为byte基本类型数组
		byte[] encodedArray = new byte[list.size()];
		for (int i = 0; i < list.size(); i++) {
			encodedArray[i] = list.get(i);
		}

		return encodedArray;
	}

	/**
	 * 把encodeByte方法转义后的消息再还原 示例: 0x7D02 -> 0x7E 0x7D01 -> 0x7D
	 * 
	 */
	public static byte[] decodeByte(byte[] encodedArray) {
		int length = encodedArray.length;
		if (encodedArray == null || length <= 0)
			return null;

		List<Byte> list = new LinkedList<Byte>();

		list.add(encodedArray[0]);
		for (int i = 1; i < length - 1; i++) {// 忽略初始与结束标识符
			byte firstByte = encodedArray[i];
			byte secondByte = encodedArray[i + 1];

			if (firstByte == 0x7D) {
				if (secondByte == 0x02) {
					list.add((byte) 0x7E);
					i++;// 需要跳过下一个byte
				} else if (secondByte == 0x01) {
					list.add((byte) 0x7D);
					i++;// 需要跳过下一个byte
				}
			} else {
				list.add(firstByte);
			}
		}
		list.add(encodedArray[length - 1]);

		// 转为byte基本类型数组
		byte[] decodeArray = new byte[list.size()];
		for (int i = 0; i < list.size(); i++) {
			decodeArray[i] = list.get(i);
		}

		return decodeArray;
	}


/*	*//**
	 * 
	 * @param telephoneNumber
	 * @return
	 *//*
	public static String[] locateMobileInfo(String telephoneNumber) {
		HttpClient httpclient = new DefaultHttpClient();
		String[] telephoneLocationInfo = new String[2];
		try {

			String postUrl = "http://www.youdao.com/smartresult-xml/search.s?jsFlag=true&type=mobile&q="
					+ telephoneNumber;

			HttpGet httpGet = new HttpGet(postUrl);

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httpGet, responseHandler);
			responseBody = responseBody.replaceAll("\'", "");
			responseBody = responseBody.replaceAll("location:", "location;");
			responseBody = responseBody.replaceAll("\\} \\, \\);", "");

			String[] result = StringUtils.split(responseBody, ";");
			String locationInfo = result[1];
			String province = StringUtils.split(locationInfo, " ")[0].trim();
			String city = StringUtils.split(locationInfo, " ")[1].trim();
			if (StringUtils.isBlank(city)) {
				city = province;
			}
			if (StringUtils.equals(province, "上海")
					|| StringUtils.equals(province, "北京")
					|| StringUtils.equals(province, "天津")
					|| StringUtils.equals(province, "重庆")) {
				province = province + "市";
			} else {
				province = province + "省";
			}
			telephoneLocationInfo[0] = province;

			city = city + "市";
			telephoneLocationInfo[1] = city;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return telephoneLocationInfo;
	}

	public static String encrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		SecureRandom sr = new SecureRandom();
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
		return new String(Base64.encodeBase64(cipher.doFinal(message
				.getBytes("UTF-8"))), "UTF-8");
	}

	public static String decrypt(String message, String key) throws Exception {
		byte[] bytesrc = Base64.decodeBase64(message.getBytes("UTF-8"));
		Cipher cipher = Cipher.getInstance("DES");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		SecureRandom sr = new SecureRandom();
		cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte, "UTF8");
	}*/

	/**
	 * 转化为弧度(rad)
	 * */
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下
	 * 
	 * @param lon1
	 *            第一点的精度
	 * @param lat1
	 *            第一点的纬度
	 * @param lon2
	 *            第二点的精度
	 * @param lat2
	 *            第二点的纬度
	 * @return 返回的距离，单位km
	 * */
	public static double getDistance(double lon1, double lat1, double lon2,
			double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lon1) - rad(lon2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		// s = Math.round(s * 10000) / 10000;
		return s;
	}
}
