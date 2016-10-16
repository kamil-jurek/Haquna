package haquna;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.BeforeClass;
import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;


public class RunCmdTest {
	
	public static CommandFactory cp = new CommandFactory();
	

	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = xload('threat-monitor.hmr')");
		cp.createCommand("Wm = new WorkingMemory(Model)");
		cp.createCommand("Wm.setValueOf('hour','16')");
		cp.createCommand("Wm.setValueOf('location','work')");
		cp.createCommand("Wm.setValueOf('activity','walking')");
		cp.createCommand("Wm.setValueOf('day','mon/1')");
	}
		
	@Test
	public void testRunCmd1() {		
		setup();				
		
		cp.createCommand("Wm2 = run(Model, Wm, ['DayTime','Today'])");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
			
	}
	
	@Test
	public void testRunCmd2() {
		setup();	
		
		cp.createCommand("Wm2 = run(Model, Wm, mode=gdi)");		
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);			
	}
	
	@Test
	public void testRunCmd3() {
		setup();	
			
		cp.createCommand("Wm3 = run(Model,mode=gdi, ['Threats'])");
		cp.createCommand("Wm3.setValueOf('hour','6')");
		cp.createCommand("Wm3.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm3"), true);
			
	}
	
	
	/*@Test
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
	}*/
	


}
