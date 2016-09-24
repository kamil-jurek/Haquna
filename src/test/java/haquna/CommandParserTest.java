package haquna;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import haquna.command.CommandFactory;
import haquna.command.io.XloadCmd;
import haquna.command.show.ShowTablesListCmd;

public class CommandParserTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
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
		
		cmd = "M3=xload('threat-monitor2.hmr')";
		assertTrue("M2=xload ('threat-monitor2.hmr')", 
				((XloadCmd)(cp.createCommand(cmd))).getVarName().equals("M3"));
		
		cmd = "M2 = xload('threat-monitor.hmr')";
		assertTrue("M2=xload ('threat-monitor.hmr')", 
				((XloadCmd)(cp.createCommand(cmd))).getVarName().equals("M2"));
		
		cmd = "M_4 = xload('threat-monitor.hmr')";
		assertTrue("M2=xload('threat-monitor.hmr')", 
				((XloadCmd)(cp.createCommand(cmd))).getVarName().equals("M_4"));
		
	}
	
	@Test
	public void testShowTableListCmd() {
		String cmd;
		cp.createCommand("M = xload('threat-monitor.hmr')");
		
		cmd = "M.showTablesList()";
		assertTrue(((ShowTablesListCmd)(cp.createCommand(cmd))).getVarName().equals("M"));
		
		cmd = "M1.showTablesList()";
		assertTrue(((ShowTablesListCmd)(cp.createCommand(cmd))).getVarName().equals("M1"));
	}
	/*@Test
	public void testException(){	    
		exception.expect(Exception.class);
	    //exception.expectMessage("Error parsing the hmr file");
	    
	    String cmd = "M=xload('threat-monitor.hmr2')";
		cp.createCommand(cmd);
	}*/

}
