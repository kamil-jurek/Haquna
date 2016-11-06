package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.get.GetTypeByNameCmd;
import haquna.utils.HaqunaUtils;

public class GetTypeByNameCmdTest {
	
	public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = xload('threat-monitor.hmr')");
	}
	
	@Test
	public void testGetTypeByNameCmdParse() {
		setup();
		
		String cmd = "Type = Model.getTypeByName('integer')";
		GetTypeByNameCmd sal = (GetTypeByNameCmd) cp.createCommand(cmd);

		assertEquals(sal.getVarName(), "Type");
		assertEquals(sal.getModelName(), "Model");
		assertEquals(sal.getTypeName(), "integer");
				
	}
	
	@Test
	public void testGetTypeByNameCmdNoModel() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "Tab_1 = NoExistingModel.getTypeByName('integer')";
		GetTypeByNameCmd sal = (GetTypeByNameCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("No '" + sal.getModelName() + "' XTTModel object in memory");
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
	
	@Test
	public void testGetTypeByNameCmdNoName() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "Type = Model.getTypeByName('today')";
		GetTypeByNameCmd sal = (GetTypeByNameCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("No type with '" + sal.getTypeName() + "' name in '" + sal.getModelName() + "' model");
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
	
	@Test
	public void testGetTypeByNameCmdNoVar() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		cp.createCommand("Type = Model.getTypeByName('integer')");		
		String cmd = "Type = Model.getTypeByName('integer')";
		GetTypeByNameCmd sal = (GetTypeByNameCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("Variable name '" + sal.getVarName() + "' already in use");
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
	
	private String getErrorStringFormat(String str) {
		return "\u001B[31m======>" + str + "\"\u001B[0m\n";
	}
}
