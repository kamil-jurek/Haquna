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
						
		String cmd = "Model1234 = xload('threat-monitor2.hmr')";
		cp.createCommand(cmd);
		//XloadCmd xc = (XloadCmd) cp.createCommand(cmd);
		//String expectedOutput = getErrorStringFormat("File '" + xc.getModelPath() + "' was not found");
		
		assertEquals(Haquna.modelMap.containsKey("Model1234"), false);				
	}
	
	@Test
	public void testXloadCmdModelVar() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
						
		String cmd = "Model_1 = xload('threat-monitor.hmr')";
		cp.createCommand(cmd);
		
		XloadCmd xc = (XloadCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("Variable name '" + xc.getVarName() + "' already in use");
		
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testXloadCmdURL() {						
		String cmd = "ModelUrl = xload('http://student.agh.edu.pl/~jurek/threat_monitor.hmr')";
		cp.createCommand(cmd);
		
		
		assertEquals(Haquna.modelMap.containsKey("ModelUrl"), true);				
	}
		
	private String getErrorStringFormat(String str) {
		return "\u001B[31m======>" + str + "\"\u001B[0m\n";
	}
}
