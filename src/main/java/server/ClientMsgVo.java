package server;

public class ClientMsgVo {
	private final ClientThread ct;
	private final String msg;

	public ClientMsgVo(ClientThread ct, String msg) {
		this.ct = ct;
		this.msg = msg;
	}

	public ClientThread getCt() {
		return ct;
	}
	public String getMsg(){
		return msg;
	}
}