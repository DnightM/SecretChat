package server;

import obj.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ServerSender extends Thread {
	private final BlockingQueue<Message> MSG_QUEUE = new ArrayBlockingQueue<>(10000);
	private final ArrayList<ClientThread> clientList = new ArrayList<>();
	private final ReentrantLock lock = new ReentrantLock();

	public ServerSender() {

	}

	@SuppressWarnings("InfiniteLoopStatement")
	@Override
	public void run() {
		super.run();
		while (true) {
			if (!MSG_QUEUE.isEmpty()) {
				Message m = MSG_QUEUE.poll();
				System.out.println(m.send()); // 콘솔

				sendToAllClients(m.send()); // 최종적으로 보내는 곳에서 암호화
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
		MSG_QUEUE.add(new Message("[" + msg + "] connect", true));
	}

	public void sendLogout(String msg) {
		MSG_QUEUE.add(new Message("[" + msg + "] disconnect", true));
	}

	public void sendClientMsg(String msg) {
		MSG_QUEUE.add(new Message(msg, false));
	}

	private void sendToAllClients(String msg) {
		try {
			lock.lock();
			for (ClientThread ct : clientList) {
				ct.write(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
