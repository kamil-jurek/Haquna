package haquna;

import static haquna.TestUtils.getErrorStringFormat;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.get.GetTableByIdCmd;
import haquna.utils.HaqunaUtils;

public class GetTableByIdCmdTest {
	public static void setup() {
		HaqunaUtils.clearMemory();
		TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");
	}
		
	@Test
	public void testGetTableByIdCmdNoModel() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "Tab = NoExistingModel.getTableById('Today')";
		GetTableByIdCmd sal = (GetTableByIdCmd) TestUtils.createCmd(cmd);
		sal.execute();
		String expectedOutput = getErrorStringFormat("No '" + sal.getModelName() + "' XTTModel object in memory");
				
		assertEquals(HaqunaSingleton.tableMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetTableByIdCmdNoName() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		
		String cmd = "Tab = Model.getTableById('NotExisting')";
		GetTableByIdCmd sal = (GetTableByIdCmd) TestUtils.createCmd(cmd);
		sal.execute();
		String expectedOutput = getErrorStringFormat("No table with '" + sal.getTableId() + "' id in '" + sal.getModelName() + "' model");
		
		assertEquals(HaqunaSingleton.tableMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetTableByIdCmdNoVar() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		TestUtils.createAndExecCmd("Tab = Model.getTableByName('Today')");
		
		String cmd = "Tab = Model.getTableById('integer')";
		GetTableByIdCmd sal = (GetTableByIdCmd) TestUtils.createCmd(cmd);
		sal.execute();
		String expectedOutput = getErrorStringFormat("Variable name '" + sal.getVarName() + "' already in use");
		
		assertEquals(HaqunaSingleton.tableMap.containsKey("Tab"), true);
		assertEquals(outContent.toString(), expectedOutput);				
	}
}
