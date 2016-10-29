package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.show.ShowTypesListCmd;

public class ShowTypesListCmdTest {
	
	CommandFactory cp = new CommandFactory();
	
	@Test
	public void testShowTypesListCmd() {
		String cmd;
		cp.createCommand("Model = xload('threat-monitor.hmr')");
		
		cmd = "Model.showTypesList()";
		ShowTypesListCmd sal = (ShowTypesListCmd) cp.createCommand(cmd);
		System.out.println(sal.getVarName());
		assertEquals(sal.getVarName(), "Model");
				
	}
	
	@Test
	public void testShowTypesListCmdNoModel() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "NoExistingModel.showTypesList()";
		ShowTypesListCmd sal = (ShowTypesListCmd) cp.createCommand(cmd);
		String expectedOutput = "No '" + sal.getVarName() + "' XTTModel object in memory\n";
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
}
