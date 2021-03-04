package server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerThread extends Thread {
	public static final BlockingQueue<ClientMsgVo> MSG_QUEUE = new ArrayBlockingQueue<>(10000);

	@Override
	public void run() {
		super.run();
		while (true) {
			if (!MSG_QUEUE.isEmpty()) {
				ClientMsgVo vo = MSG_QUEUE.poll();
				String tMsg = vo.getMsg();
				ClientThread tCt = vo.getCt();

				System.out.println(tMsg);
				for (ClientThread ct : ServerMain.CLIENT_LIST) {
					if (ct == tCt) {
						continue;
					}
					ct.write(tMsg);
				}
			}
		}
	}


}
