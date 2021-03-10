package obj;

public class Message {
	private static final String SEP = " : ";
	private final String id;
	private final StringBuilder msg;

	public Message(String id, String msg) {
		this.id = id;
		this.msg = new StringBuilder(msg);
	}

	public Message(String msg, boolean onlyMsg) {
		int idx;
		if (onlyMsg || (idx = msg.indexOf(SEP)) < 0) {
			this.id = null;
			this.msg = new StringBuilder(msg);
		} else {
			this.id = msg.substring(0, idx);
			this.msg = new StringBuilder(msg.substring(idx + SEP.length()));
		}
	}

	public String getId() {
		return id;
	}

	public String send() {
		if (id == null) {
			return msg.toString();
		}
		return id + SEP + msg;
	}
}
