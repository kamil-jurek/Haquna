package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import org.junit.Test;

import haquna.command.io.SaveCmd;
import haquna.utils.HaqunaUtils;

public class XsaveCmdTest {
	public static void setup() {
		HaqunaUtils.clearMemory();
		TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testXsaveCmd() {
		setup();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Model.save('test");
		stringBuilder.append(new Date().getHours());
		stringBuilder.append(new Date().getMinutes());
		stringBuilder.append(new Date().getSeconds());
		stringBuilder.append(".hmr");
		stringBuilder.append("')");
		
		String cmdStr = stringBuilder.toString();
		TestUtils.createAndExecCmd(cmdStr);
		
		assert(true);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testXsaveCmdN() {
		setup();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Mod.save('test");
		stringBuilder.append(new Date().getHours());
		stringBuilder.append(new Date().getMinutes());
		stringBuilder.append(new Date().getSeconds());
		stringBuilder.append(".hmr");
		stringBuilder.append("')");
				
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
						
		String cmdStr = stringBuilder.toString();
		SaveCmd xc = (SaveCmd) TestUtils.createCmd(cmdStr);
		xc.execute();
		String expectedOutput = TestUtils.getErrorStringFormat("No '" + xc.getModelName() + "' XTTModel object in memory");
		
		assertEquals(outContent.toString(), expectedOutput);						
	}
		

}
