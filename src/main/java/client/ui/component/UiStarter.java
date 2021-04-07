
package client.ui.component;

import client.ui.vo.LoginInfo;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class UiStarter extends JFrame {
	private final static File SETTING_FILE = new File(System.getProperty("user.home") + "/chat/setting.properties");
	private final static int WINDOW_WIDTH = 200;
	private final static int WINDOW_HEIGHT = 400;
	private LoginInfo info;

	public UiStarter() {
		super();
		LoginInfo oldSetting = loadSavedSetting();

		JPanel background = new JPanel(new GridLayout(6, 1));
		InfoPanel ipPanel = new InfoPanel("ip", oldSetting.getIp());
		InfoPanel portPanel = new InfoPanel("port", String.valueOf(oldSetting.getPort()));
		InfoPanel idPanel = new InfoPanel("id", oldSetting.getId());
		InfoPanel pwPanel = new InfoPanel("pw", oldSetting.getPw());
		JCheckBox box = new JCheckBox("save this setting");
		box.setSelected(true);
		JButton button = new JButton("OK");
		button.addActionListener(e -> {
			String ip = ipPanel.getInfo().trim();
			int port = -1;
			try {
				port = Integer.parseInt(portPanel.getInfo().trim());
			} catch (Exception ignore) {
			}
			String id = idPanel.getInfo().trim();
			String pw = pwPanel.getInfo().trim();

			int errorMsgCode;
			if ((errorMsgCode = checkData(ip, port, id, pw)) > -1) {
				JOptionPane.showMessageDialog(null, errorMsg[errorMsgCode]);
				return;
			}
			info = new LoginInfo(ip, port, id, pw);
			if (box.isSelected()) {
				saveSetting(info);
			} else {
				// 사용자가 저장을 하지 않으면 이전 저장 항목도 삭제함.
				if (SETTING_FILE.isFile()) {
					SETTING_FILE.delete();
				}
			}

			this.setVisible(false);
			this.dispose();
		});

		background.add(ipPanel);
		background.add(portPanel);
		background.add(idPanel);
		background.add(pwPanel);
		background.add(box);
		background.add(button);

		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();

		this.setBounds(new Rectangle(
				(int) dimen.getWidth() / 2 - WINDOW_WIDTH / 2,
				(int) dimen.getHeight() / 2 - WINDOW_HEIGHT / 2,
				WINDOW_WIDTH,
				WINDOW_HEIGHT));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(background);
		this.setVisible(true);
	}

	private static class InfoPanel extends JPanel {
		private final JTextField jtf;

		public InfoPanel(String name, String defaultValue) {
			super(new GridLayout(1, 2));
			JLabel jl = new JLabel("  " + name + " : ");
			jl.setFont(new Font("굴림", Font.BOLD, 15));
			jtf = new JTextField();
			jtf.setText(defaultValue);

			this.add(jl);
			this.add(jtf);
		}

		public String getInfo() {
			return new String(jtf.getText().getBytes(), StandardCharsets.UTF_8);
		}
	}

	private LoginInfo loadSavedSetting() {
		if (!SETTING_FILE.isFile()) {
			return new LoginInfo("", -1, "", "");
		}
		try {
			Properties p = new Properties();
			p.load(new InputStreamReader(new FileInputStream(SETTING_FILE), StandardCharsets.UTF_8));
			return new LoginInfo(
					p.getProperty("ip"),
					Integer.parseInt(p.getProperty("port")),
					p.getProperty("id"),
					p.getProperty("pw")
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new LoginInfo("", -1, "", "");
	}

	private void saveSetting(LoginInfo info) {
		File dir = SETTING_FILE.getParentFile();
		if (!dir.isDirectory()) {
			if (!dir.mkdirs()) {
				// 폴더 생성 실패 로그 작성
				return;
			}
		}
		try {
			Properties p = new Properties();
			p.put("ip", info.getIp());
			p.put("port", String.valueOf(info.getPort()));
			p.put("id", info.getId());
			p.put("pw", info.getPw());
			p.store(new OutputStreamWriter(new FileOutputStream(SETTING_FILE), StandardCharsets.UTF_8), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LoginInfo getLoginInfo() {
		return info;
	}

	private final String[] errorMsg = new String[]{
			"잘못된 형식의 ip adress 입니다.",
			"잘못된 형식의 port 입니다.",
			"잘못된 형식의 id 입니다.",
			"잘못된 형식의 password 입니다."
	};

	private int checkData(String ip, int port, String id, String pw) {
		// ip 확인
		String[] t = ip.split("\\.");
		if (t.length != 4) {
			return 0;
		}
		for (String a : t) {
			try {
				int b = Integer.parseInt(a);
				if (!(0 <= b && b < 256)) {
					return 0;
				}
			} catch (Exception e) {
				return 0;
			}
		}

		// port 확인
		if (!(0 <= port && port <= 65535)) {
			return 1;
		}

		// id 확인
		// 한글 영어 및 아스키 코드에 등장하는 특수문자만 허용
		if (id.matches("[^!-~ㄱ-힣A-Za-z ]")) {
			return 2;
		}

		// pw 확인
		// 영어 및 아스키 코드에 등장하는 특수문자만 허용
		if (pw.matches("[^!-~A-Za-z ]")) {
			return 3;
		}

		return -1;
	}
}
