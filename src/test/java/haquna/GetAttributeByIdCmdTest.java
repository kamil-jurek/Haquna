package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.get.GetAttributeByIdCmd;
import haquna.utils.HaqunaUtils;

public class GetAttributeByIdCmdTest {
	
public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = new Model('threat-monitor.hmr')");
	}
		
	@Test
	public void testGetAttributeByIdCmdNoModel() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "Tab = NoExistingModel.getAttributeById('Today')";
		GetAttributeByIdCmd sal = (GetAttributeByIdCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("No '" + sal.getModelName() + "' XTTModel object in memory");
				
		assertEquals(HaqunaSingleton.tableMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetAttributeByIdCmdNoName() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		
		String cmd = "Tab = Model.getAttributeById('NotExisting')";
		GetAttributeByIdCmd sal = (GetAttributeByIdCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("No attribute with '" + sal.getAttribiuteId() + "' id in '" + sal.getModelName() + "' model");
		
		assertEquals(HaqunaSingleton.tableMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetAttributeByIdCmdNoVar() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		cp.createCommand("Tab = Model.getTableByName('Today')");
		
		String cmd = "Tab = Model.getAttributeById('integer')";
		GetAttributeByIdCmd sal = (GetAttributeByIdCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("Variable name '" + sal.getVarName() + "' already in use");
		
		assertEquals(HaqunaSingleton.tableMap.containsKey("Tab"), true);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	private String getErrorStringFormat(String str) {
		return "\u001B[31m======>" + str + "\"\u001B[0m\n";
	}
}
