package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.show.ShowAttributesListCmd;
import haquna.utils.HaqunaUtils;

public class ShowAttributesListCmdTest {
	
	public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = xload('threat-monitor.hmr')");
	}
	
	@Test
	public void testShowAttribiutesListCmd() {
		setup();
		
		String cmd = "Model.showAttributesList()";
		ShowAttributesListCmd sal = (ShowAttributesListCmd) cp.createCommand(cmd);
		
		assertEquals(sal.getVarName(), "Model");
				
	}
		
	@Test
	public void testShowAttribiutesListCmdNoModel() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "NoExistingModel.showAttributesList()";
		ShowAttributesListCmd sal = (ShowAttributesListCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("No '" + sal.getVarName() + "' XTTModel object in memory");
	
		assertEquals(outContent.toString(), expectedOutput);
				
	}
	
	private String getErrorStringFormat(String str) {
		return "\u001B[31m======>" + str + "\"\u001B[0m\n";
	}
}
