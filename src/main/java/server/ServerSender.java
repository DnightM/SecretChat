package server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerSender extends Thread {
	private static final BlockingQueue<ClientMsgVo> MSG_QUEUE = new ArrayBlockingQueue<>(10000);

	public static void sendMsg(ClientThread ct, String msg) {
		MSG_QUEUE.add(new ClientMsgVo(ct, msg));
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			if (!MSG_QUEUE.isEmpty()) {
				ClientMsgVo vo = MSG_QUEUE.poll();
				String tMsg = vo.getMsg();
				ClientThread tCt = vo.getCt();

				System.out.println(tMsg);
				ClientManager.sendMsg(tCt, tMsg);
			}
		}
	}

	private static class ClientMsgVo {
		private final ClientThread ct;
		private final String msg;

		public ClientMsgVo(ClientThread ct, String msg) {
			this.ct = ct;
			this.msg = msg;
		}

		public ClientThread getCt() {
			return ct;
		}

		public String getMsg() {
			return msg;
		}
	}

}
