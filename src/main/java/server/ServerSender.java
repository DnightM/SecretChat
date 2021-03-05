package server;

import util.Aes128;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ServerSender extends Thread {
	private final Aes128 aes;

	private final BlockingQueue<ClientMsgVo> MSG_QUEUE = new ArrayBlockingQueue<>(10000);
	private final ArrayList<ClientThread> clientList = new ArrayList<>();
	private final ReentrantLock lock = new ReentrantLock();

	public ServerSender(String key) {
		aes = new Aes128(key);
	}

	@SuppressWarnings("InfiniteLoopStatement")
	@Override
	public void run() {
		super.run();
		while (true) {
			if (!MSG_QUEUE.isEmpty()) {
				ClientMsgVo vo = MSG_QUEUE.poll();
				System.out.println(vo.getMsg()); // 콘솔
				String tMsg = aes.encrypt(vo.getMsg()); // 최종적으로 보내는 곳에서 암호화
				ClientThread tCt = vo.getCt();

				sendToAllClients(tCt, tMsg);
			}
		}
	}

	public void add(ClientThread ct) {
		try {
			lock.lock();
			clientList.add(ct);
			ct.start();
		} finally {
			lock.unlock();
		}
	}

	public void remove(ClientThread ct) {
		try {
			lock.lock();
			clientList.remove(ct);
		} finally {
			lock.unlock();
		}
	}

	public void sendDirect(String msg) {
		MSG_QUEUE.add(new ClientMsgVo(null, msg));
	}

	public void send(ClientThread ct, String msg) {
		MSG_QUEUE.add(new ClientMsgVo(ct, ct.getClientName() + " : " + aes.decrypt(msg))); // 여기서 복호화
	}

	private void sendToAllClients(ClientThread tCt, String tMsg) {
		try {
			lock.lock();
			for (ClientThread ct : clientList) {
				if (ct == tCt) {
					continue;
				}
				ct.write(tMsg);
			}
		} finally {
			lock.unlock();
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
