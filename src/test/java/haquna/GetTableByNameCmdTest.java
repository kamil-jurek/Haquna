package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.get.GetTableByNameCmd;
import haquna.utils.HaqunaUtils;

public class GetTableByNameCmdTest {
	
public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = new Model('threat-monitor.hmr')");
	}
	
	@Test
	public void testGetTableByNameCmd() {
		setup();
		String cmdStr = "Tab = Model.getTableByName('Today')";
		cp.createCommand(cmdStr);

		assertEquals(HaqunaSingleton.tableMap.containsKey("Tab"), true);
		assertEquals(HaqunaSingleton.tableMap.get("Tab").getName(), "Today");
	}
	
	@Test
	public void testGetTableByNameCmdNoModel() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "Tab = NoExistingModel.getTableByName('Today')";
		GetTableByNameCmd sal = (GetTableByNameCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("No '" + sal.getModelName() + "' XTTModel object in memory");
				
		assertEquals(HaqunaSingleton.tableMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetTableByNameCmdNoName() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		
		String cmd = "Tab = Model.getTableByName('NotExisting')";
		GetTableByNameCmd sal = (GetTableByNameCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("No table with '" + sal.getTableName() + "' name in '" + sal.getModelName() + "' model");
		
		assertEquals(HaqunaSingleton.tableMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetTableByNameCmdNoVar() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		cp.createCommand("Tab = Model.getTableByName('Today')");
		
		String cmd = "Tab = Model.getTableByName('integer')";
		GetTableByNameCmd sal = (GetTableByNameCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("Variable name '" + sal.getVarName() + "' already in use");
		
		assertEquals(HaqunaSingleton.tableMap.containsKey("Tab"), true);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	private String getErrorStringFormat(String str) {
		return "\u001B[31m======>" + str + "\"\u001B[0m\n";
	}
}
