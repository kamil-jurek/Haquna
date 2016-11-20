package haquna;

import static haquna.TestUtils.getErrorStringFormat;
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
		TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");
	}
	
	@Test
	public void testGetTableByNameCmd() {
		setup();
		String cmdStr = "Tab = Model.getTableByName('Today')";
		TestUtils.createAndExecCmd(cmdStr);

		assertEquals(Haquna.tableMap.containsKey("Tab"), true);
		assertEquals(Haquna.tableMap.get("Tab").getName(), "Today");
	}
	
	@Test
	public void testGetTableByNameCmdNoModel() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "Tab = NoExistingModel.getTableByName('Today')";
		GetTableByNameCmd sal = (GetTableByNameCmd) TestUtils.createCmd(cmd);
		sal.execute();
		String expectedOutput = getErrorStringFormat("No '" + sal.getModelName() + "' XTTModel object in memory");
				
		assertEquals(Haquna.tableMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetTableByNameCmdNoName() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		
		String cmd = "Tab = Model.getTableByName('NotExisting')";
		GetTableByNameCmd sal = (GetTableByNameCmd) TestUtils.createCmd(cmd);
		sal.execute();
		String expectedOutput = getErrorStringFormat("No table with '" + sal.getTableName() + "' name in '" + sal.getModelName() + "' model");
		
		assertEquals(Haquna.tableMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetTableByNameCmdNoVar() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		TestUtils.createAndExecCmd("Tab = Model.getTableByName('Today')");
		
		String cmd = "Tab = Model.getTableByName('integer')";
		GetTableByNameCmd sal = (GetTableByNameCmd) TestUtils.createCmd(cmd);
		sal.execute();
		String expectedOutput = getErrorStringFormat("Variable name '" + sal.getVarName() + "' already in use");
		
		assertEquals(Haquna.tableMap.containsKey("Tab"), true);
		assertEquals(outContent.toString(), expectedOutput);				
	}
}
