package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.get.GetTableByNameCmd;

public class GetTableByNameCmdTest {
	
	CommandFactory cp = new CommandFactory();
	
	@Test
	public void testGetTableByNameCmdParse() {
		String cmd;
		cp.createCommand("Model = xload('threat-monitor.hmr')");
		
		cmd = "Tab = Model.getTableByName('Today')";
		GetTableByNameCmd sal = (GetTableByNameCmd) cp.createCommand(cmd);

		assertEquals(sal.getVarName(), "Tab");
		assertEquals(sal.getModelName(), "Model");
		assertEquals(sal.getTableName(), "Today");
				
	}
	
	@Test
	public void testGetTableByNameCmdNoModel() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "Tab_1 = NoExistingModel.getTableByName('Today')";
		GetTableByNameCmd sal = (GetTableByNameCmd) cp.createCommand(cmd);
		String expectedOutput = "No " + sal.getModelName() + " model in memory\n";
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
	
	@Test
	public void testGetTableByNameCmdNoName() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		cp.createCommand("Model_1 = xload('threat-monitor.hmr')");
		
		String cmd = "Tab_2 = Model_1.getTableByName('Tommorow')";
		GetTableByNameCmd sal = (GetTableByNameCmd) cp.createCommand(cmd);
		String expectedOutput = "No table with '" + sal.getTableName() + "' name in '" + sal.getModelName() + "' model\n";
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
	
	@Test
	public void testGetTableByNameCmdNoVar() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		cp.createCommand("Model_2 = xload('threat-monitor.hmr')");
		cp.createCommand("Tab_3 = Model_2.getTableByName('Today')");
		
		String cmd = "Tab_3 = Model_2.getTableByName('Today')";
		GetTableByNameCmd sal = (GetTableByNameCmd) cp.createCommand(cmd);
		String expectedOutput = "Variable name: " + sal.getVarName() + " already in use\n";
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
}
