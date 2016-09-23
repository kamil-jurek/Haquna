package haquna;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandParserTest {
	
	CommandFactory cp = new CommandFactory();
	@Test
	public void test() {
		String cmd;
		
		cmd = "M = xload('/home/kamil/MyHeartDroid/MyHeart/src/res/threat-monitor.hmr')";		
		assertTrue("M = xload('/home/kamil/MyHeartDroid/MyHeart/src/res/threat-monitor.hmr')", 
				((XloadCmd)(cp.createCommand(cmd))).getVarName().equals("M"));
		
		cmd = "M1 =xload('/home/kamil/MyHeartDroid/MyHeart/src/res/threat-monitor.hmr')";
		assertTrue("M2 =xload('/home/kamil/MyHeartDroid/MyHeart/src/res/threat-monitor.hmr')", 
				((XloadCmd)(cp.createCommand(cmd))).getVarName().equals("M1"));
		
		cmd = "M2=xload('threat-monitor.hmr')";
		assertTrue("M2=xload('/home/kamil/MyHeartDroid/MyHeart/src/res/threat-monitor.hmr')", 
				((XloadCmd)(cp.createCommand(cmd))).getVarName().equals("M2"));
	}

}
