package util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Aes128 {
	private final String key;
	private final Charset charset = StandardCharsets.UTF_8;

	public Aes128(String key) {
		this.key = checkValidKey(key);
	}

	// AES 방식의 암호화
	public String encrypt(String message) {
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(charset), "AES");
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(message.getBytes(charset));
			return byteArrayToHex(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String byteArrayToHex(byte[] ba) {
		if (ba == null || ba.length == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder(ba.length << 1);
		String hexNumber;
		for (byte b : ba) {
			hexNumber = "0" + Integer.toHexString(0xff & b);
			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}

	// AES 방식의 복호화
	public String decrypt(String encrypted) {
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(charset), "AES");
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(hexToByteArray(encrypted));
			return new String(original);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}
		byte[] ba = new byte[hex.length() >> 1];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.
					parseInt(hex.substring(i << 1, (i << 1) + 2), 16);
		}
		return ba;
	}

	private String checkValidKey(String key) {
		int len = key.length();
		if (len == 16) {
			return key;
		}
		if (len > 16) {
			return key.substring(0, 16);
		} else {
			char[] t = key.toCharArray();
			char[] c = new char[16];
			System.arraycopy(t, 0, c, 0, t.length);
			for (int i = len; i < 16; i++) {
				c[i] = 'a';
			}
			return new String(c);
		}
	}
}
