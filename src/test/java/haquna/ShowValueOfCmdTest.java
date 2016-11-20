package haquna;

import haquna.command.Command;
import haquna.command.io.XsaveCmd;
import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class ShowValueOfCmdTest {
public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");
		TestUtils.createAndExecCmd("Wm = new WorkingMemory(Model)");
	}
		
	@Test
	public void testGetTypeCmd() {
		setup();


		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		Command xc = TestUtils.createCmd("Wm.showValueOf('today')");
		xc.execute();
		String expectedOutput = "today = null\n";

		assertEquals(outContent.toString(), expectedOutput);
	}		
}