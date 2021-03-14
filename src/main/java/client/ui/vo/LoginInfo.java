package client.ui.vo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginInfo {
	private final String ip;
	private final int port;
	private final String id;
	private final String pw;

	public LoginInfo(String ip, int port, String id, String pw) {
		this.ip = ip;
		this.port = port;
		this.id = id;
		this.pw = pw;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getId() {
		return id;
	}

	public String getPw() {
		return pw;
	}

	@Override
	public String toString() {
		return "LoginInfo{" +
				"ip='" + ip + '\'' +
				", port=" + port +
				", id='" + id + '\'' +
				", pw='" + pw + '\'' +
				'}';
	}
}
