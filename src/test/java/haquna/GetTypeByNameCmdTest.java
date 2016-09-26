package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.get.GetTypeByNameCmd;

public class GetTypeByNameCmdTest {
	
	CommandFactory cp = new CommandFactory();
	
	@Test
	public void testGetTypeByNameCmdParse() {
		String cmd;
		cp.createCommand("Model = xload('threat-monitor.hmr')");
		
		cmd = "Type = Model.getTypeByName('integer')";
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
		String expectedOutput = "No " + sal.getModelName() + " model in memory\n";
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
	
	@Test
	public void testGetTypeByNameCmdNoName() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		cp.createCommand("Model_1 = xload('threat-monitor.hmr')");
		
		String cmd = "Type_2 = Model_1.getTypeByName('today')";
		GetTypeByNameCmd sal = (GetTypeByNameCmd) cp.createCommand(cmd);
		String expectedOutput = "No type with '" + sal.getTypeName() + "' name in '" + sal.getModelName() + "' model\n";
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
	
	@Test
	public void testGetTypeByNameCmdNoVar() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		cp.createCommand("Model_2 = xload('threat-monitor.hmr')");
		cp.createCommand("Type_3 = Model_2.getTypeByName('integer')");
		
		String cmd = "Type_3 = Model_2.getTypeByName('integer')";
		GetTypeByNameCmd sal = (GetTypeByNameCmd) cp.createCommand(cmd);
		String expectedOutput = "Variable name: " + sal.getVarName() + " already in use\n";
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
}
