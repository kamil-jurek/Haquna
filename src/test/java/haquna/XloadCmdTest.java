package haquna;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.io.XloadCmd;

public class XloadCmdTest {
	
	CommandFactory cp = new CommandFactory();
	
	@Test
	public void testXlaodCmd() {
		String cmd;
		XloadCmd xc;
		
		cmd = "M = xload('/home/kamil/MyHeartDroid/MyHeart/src/res/threat-monitor.hmr')";		
		xc = (XloadCmd)cp.createCommand(cmd);
		assertEquals(xc.getVarName(), "M");
		
		cmd = "M1 =xload('/home/kamil/MyHeartDroid/MyHeart/src/res/threat-monitor.hmr')";
		xc = (XloadCmd)cp.createCommand(cmd);
		assertEquals(xc.getVarName(), "M1");
		
		cmd = "Model2 =xload('threat-monitor.hmr')";
		xc = (XloadCmd)cp.createCommand(cmd);
		assertEquals(xc.getVarName(), "Model2");
				
		cmd = "M3 =xload('threat-monitor.hmr')";
		xc = (XloadCmd)cp.createCommand(cmd);
		assertEquals(xc.getVarName(), "M3");
		
		cmd = "M_4 = xload('threat-monitor.hmr')";
		xc = (XloadCmd)cp.createCommand(cmd);
		assertEquals(xc.getVarName(), "M_4");		
	}
	
	@Test
	public void testXloadCmdNoModel() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
						
		String cmd = "Model2 = xload('threat-monitor2.hmr')";
		XloadCmd xc = (XloadCmd) cp.createCommand(cmd);
		String expectedOutput = "File '" + xc.getModelPath() + "' was found\n";
		
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testXloadCmdModelVar() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
						
		String cmd = "Model_1 = xload('threat-monitor.hmr')";
		cp.createCommand(cmd);
		
		XloadCmd xc = (XloadCmd) cp.createCommand(cmd);
		String expectedOutput = "Variable name: " + xc.getVarName() + " already in use\n";
		
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
/*	@Test
	public void testShowTableListCmd() {
		String cmd;
		cp.createCommand("M = xload('threat-monitor.hmr')");
		
		cmd = "M.showTablesList()";
		assertTrue(((ShowTablesListCmd)(cp.createCommand(cmd))).getVarName().equals("M"));
		
		cmd = "M1.showTablesList()";
		assertTrue(((ShowTablesListCmd)(cp.createCommand(cmd))).getVarName().equals("M1"));
	}*/
	/*@Test
	public void testException(){	    
		exception.expect(Exception.class);
	    //exception.expectMessage("Error parsing the hmr file");
	    
	    String cmd = "M=xload('threat-monitor.hmr2')";
		cp.createCommand(cmd);
	}*/

}
