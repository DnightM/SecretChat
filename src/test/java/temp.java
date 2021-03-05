import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

public class temp {
	@Test
	public void test() {
		String temp = "!@#$%^&*()fhwsoafjaewqwertyuiop[]\\  ♡ ofjwioqj4r09v23u40c32,i4092,i04923i0-";
		System.out.println(temp);
		System.out.println(temp.replaceAll("[^!-~ㄱ-힣]", ""));
	}


}
