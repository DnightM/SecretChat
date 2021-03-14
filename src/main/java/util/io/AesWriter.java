package util.io;

import util.Aes128;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class AesWriter extends BufferedWriter {
	private final Aes128 aes;

	public AesWriter(OutputStream os, String key) {
		super(new OutputStreamWriter(os, StandardCharsets.UTF_8));
		this.aes = new Aes128(key);
	}

	public void aesWrite(String str) throws IOException {
		super.write(aes.encrypt(str));
		super.newLine();
		super.flush();
	}
}
