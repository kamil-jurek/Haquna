package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.get.GetTableByIdCmd;
import haquna.utils.HaqunaUtils;

public class GetTableByIdCmdTest {
	
public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = xload('threat-monitor.hmr')");
	}
		
	@Test
	public void testGetTableByIdCmdNoModel() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "Tab = NoExistingModel.getTableById('Today')";
		GetTableByIdCmd sal = (GetTableByIdCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("No '" + sal.getModelName() + "' XTTModel object in memory");
				
		assertEquals(Haquna.tableMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetTableByIdCmdNoName() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		
		String cmd = "Tab = Model.getTableById('NotExisting')";
		GetTableByIdCmd sal = (GetTableByIdCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("No table with '" + sal.getTableId() + "' id in '" + sal.getModelName() + "' model");
		
		assertEquals(Haquna.tableMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetTableByIdCmdNoVar() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		cp.createCommand("Tab = Model.getTableByName('Today')");
		
		String cmd = "Tab = Model.getTableById('integer')";
		GetTableByIdCmd sal = (GetTableByIdCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("Variable name '" + sal.getVarName() + "' already in use");
		
		assertEquals(Haquna.tableMap.containsKey("Tab"), true);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	private String getErrorStringFormat(String str) {
		return "\u001B[31m======>" + str + "\"\u001B[0m\n";
	}
}
