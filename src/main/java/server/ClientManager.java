package server;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ClientManager {
	public static final ArrayList<ClientThread> CLIENT_LIST = new ArrayList<>();
	private static final ReentrantLock LOCK = new ReentrantLock();

	public static void add(ClientThread ct) {
		try {
			LOCK.lock();
			CLIENT_LIST.add(ct);
			ct.start();
		} finally {
			LOCK.unlock();
		}
	}

	public static void remove(ClientThread ct) {
		try {
			LOCK.lock();
			CLIENT_LIST.remove(ct);
		} finally {
			LOCK.unlock();
		}
	}

	public static void sendMsg(ClientThread tCt, String tMsg) {
		try {
			LOCK.lock();
			for (ClientThread ct : ClientManager.CLIENT_LIST) {
				if (ct == tCt) {
					continue;
				}
				ct.write(tMsg);
			}
		} finally {
			LOCK.unlock();
		}
	}
}
