package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.get.GetAttributeByNameCmd;

public class GetAttributeByNameCmdTest {
	
	CommandFactory cp = new CommandFactory();
	
	@Test
	public void testGetAttributeByNameCmdParse() {
		String cmd;
		cp.createCommand("Model = xload('threat-monitor.hmr')");
		
		cmd = "Att = Model.getAttributeByName('location')";
		GetAttributeByNameCmd sal = (GetAttributeByNameCmd) cp.createCommand(cmd);

		assertEquals(sal.getVarName(), "Att");
		assertEquals(sal.getModelName(), "Model");
		assertEquals(sal.getAttribiuteName(), "location");
				
	}
	
	@Test
	public void testGetAttributeByNameCmdNoModel() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "Att_1 = NoExistingModel.getAttributeByName('location')";
		GetAttributeByNameCmd sal = (GetAttributeByNameCmd) cp.createCommand(cmd);
		String expectedOutput = "No '" + sal.getModelName() + "' XTTModel object in memory\n";
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
	
	@Test
	public void testGetAttributeByNameCmdNoName() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		cp.createCommand("Model_1 = xload('threat-monitor.hmr')");
		
		String cmd = "Att_2 = Model_1.getAttributeByName('always')";
		GetAttributeByNameCmd sal = (GetAttributeByNameCmd) cp.createCommand(cmd);
		String expectedOutput = "No attribute with '" + sal.getAttribiuteName() + "' name in '" + sal.getModelName() + "' model\n";
		                       
		assertEquals(outContent.toString(), expectedOutput);
				
	}
	
	@Test
	public void testGetAttributeByNameCmdNoVar() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		cp.createCommand("Model_2 = xload('threat-monitor.hmr')");
		cp.createCommand("Att_3 = Model_2.getAttributeByName('location')");
		
		String cmd = "Att_3 = Model_2.getAttributeByName('integer')";
		GetAttributeByNameCmd sal = (GetAttributeByNameCmd) cp.createCommand(cmd);
		String expectedOutput = "Variable name '" + sal.getVarName() + "' already in use\n";
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
}
