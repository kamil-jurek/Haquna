package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.io.XsaveCmd;
import haquna.utils.HaqunaUtils;

public class XsaveCmdTest {
public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = xload('threat-monitor.hmr')");
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testXsaveCmd() {
		setup();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Model.xsave('test");
		stringBuilder.append(new Date().getHours());
		stringBuilder.append(new Date().getMinutes());
		stringBuilder.append(new Date().getSeconds());
		stringBuilder.append(".hmr");
		stringBuilder.append("')");
		
		String cmdStr = stringBuilder.toString();
		cp.createCommand(cmdStr);
		
		assert(true);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testXsaveCmdN() {
		setup();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Mod.xsave('test");
		stringBuilder.append(new Date().getHours());
		stringBuilder.append(new Date().getMinutes());
		stringBuilder.append(new Date().getSeconds());
		stringBuilder.append(".hmr");
		stringBuilder.append("')");
				
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
						
		String cmdStr = stringBuilder.toString();
		XsaveCmd xc = (XsaveCmd) cp.createCommand(cmdStr);
		String expectedOutput = getErrorStringFormat("No '" + xc.getModelName() + "' XTTModel object in memory");
		
		assertEquals(outContent.toString(), expectedOutput);						
	}
		
	private String getErrorStringFormat(String str) {
		return "\u001B[31m======>" + str + "\"\u001B[0m\n";
	}
}
