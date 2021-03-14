package main;

import client.ui.UiMain;
import client.ui.component.UiStarter;
import client.ui.vo.LoginInfo;

import java.net.Socket;

public class ClientMain {
	public static void main(String[] args) throws Exception {
		UiStarter starter = new UiStarter();
		// 창이 켜져있으면 사용자가 재대로된 정보를 입력하지 않았다는 의미이므로, 대기
		while (starter.isVisible()) {
			Thread.sleep(100);
		}

		LoginInfo info = starter.getLoginInfo();
		String key = info.getPw();
		Socket c_socket = new Socket(info.getIp(), info.getPort());
		UiMain ui = new UiMain(c_socket, key, info.getId());
		ui.open();
	}
}
