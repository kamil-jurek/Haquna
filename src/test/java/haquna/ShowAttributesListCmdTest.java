package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.show.ShowAttributesListCmd;

public class ShowAttributesListCmdTest {
	
	CommandFactory cp = new CommandFactory();
	
	@Test
	public void testShowAttribiutesListCmd() {
		String cmd;
		cp.createCommand("Model = xload('threat-monitor.hmr')");
		
		cmd = "Model.showAttributesList()";
		ShowAttributesListCmd sal = (ShowAttributesListCmd) cp.createCommand(cmd);
		System.out.println(sal.getVarName());
		assertEquals(sal.getVarName(), "Model");
				
	}
	
	@Test
	public void testShowAttribiutesListCmdNoModel() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "NoExistingModel.showAttributesList()";
		ShowAttributesListCmd sal = (ShowAttributesListCmd) cp.createCommand(cmd);
		String expectedOutput = "No '" + sal.getVarName() + "' XTTModel object in memory\n";
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
}
