package util.io;

import util.Aes128;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AesReader extends BufferedReader {
	private final Aes128 aes;

	public AesReader(InputStream is, String key) {
		super(new InputStreamReader(is, StandardCharsets.UTF_8));
		this.aes = new Aes128(key);
	}

	public String aesReadLine() throws IOException {
		String t = readLine();
		if (t == null) {
			return null;
		}
		return aes.decrypt(t);
	}
}
