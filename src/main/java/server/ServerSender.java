package server;

import obj.Message;
import util.Aes128;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ServerSender extends Thread {
	private final Aes128 aes;

	private final BlockingQueue<Message> MSG_QUEUE = new ArrayBlockingQueue<>(10000);
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
				Message m = MSG_QUEUE.poll();
				System.out.println(m.send()); // 콘솔

				sendToAllClients(aes.encrypt(m.send())); // 최종적으로 보내는 곳에서 암호화
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

	public void sendLogin(String msg) {
		MSG_QUEUE.add(new Message("[" + aes.decrypt(msg) + "] connect", true));
	}

	public void sendLogout(String msg) {
		MSG_QUEUE.add(new Message("[" + aes.decrypt(msg) + "] disconnect", true));
	}

	public void sendClientMsg(String msg) {
		MSG_QUEUE.add(new Message(aes.decrypt(msg), false)); // 여기서 복호화
	}

	private void sendToAllClients(String msg) {
		try {
			lock.lock();
			for (ClientThread ct : clientList) {
				ct.write(msg);
			}
		} finally {
			lock.unlock();
		}
	}
}
