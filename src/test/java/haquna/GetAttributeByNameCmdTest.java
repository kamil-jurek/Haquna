package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.get.GetAttributeByNameCmd;
import haquna.utils.HaqunaUtils;

public class GetAttributeByNameCmdTest {
	
	public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = new Model('threat-monitor.hmr')");
	}
	
	@Test
	public void testGetAttributeByNameCmd() {
		setup();
		String cmdStr = "Att = Model.getAttributeByName('location')";
		cp.createCommand(cmdStr);

		assertEquals(HaqunaSingleton.attrMap.containsKey("Att"), true);
		assertEquals(HaqunaSingleton.attrMap.get("Att").getAttributeName(), "location");
	}
	
	@Test
	public void testGetAttributeByNameCmdNoModel() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "Att = NoExistingModel.getAttributeByName('location')";
		GetAttributeByNameCmd sal = (GetAttributeByNameCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("No '" + sal.getModelName() + "' XTTModel object in memory");
				
		assertEquals(HaqunaSingleton.attrMap.containsKey("Att"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetAttributeByNameCmdNoName() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		
		String cmd = "Att = Model.getAttributeByName('always')";
		GetAttributeByNameCmd sal = (GetAttributeByNameCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("No attribute with '" + sal.getAttribiuteName() + "' name in '" + sal.getModelName() + "' model");
		
		assertEquals(HaqunaSingleton.attrMap.containsKey("Att"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetAttributeByNameCmdNoVar() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		cp.createCommand("Att = Model.getAttributeByName('location')");
		
		String cmd = "Att = Model.getAttributeByName('integer')";
		GetAttributeByNameCmd sal = (GetAttributeByNameCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("Variable name '" + sal.getVarName() + "' already in use");
		
		assertEquals(HaqunaSingleton.attrMap.containsKey("Att"), true);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	private String getErrorStringFormat(String str) {
		return "\u001B[31m======>" + str + "\"\u001B[0m\n";
	}
}
