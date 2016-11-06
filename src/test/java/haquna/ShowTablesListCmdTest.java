package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.show.ShowTablesListCmd;
import haquna.utils.HaqunaUtils;

public class ShowTablesListCmdTest {
	
	public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = xload('threat-monitor.hmr')");
	}
	
	@Test
	public void testShowTablesListCmd() {
		setup();
		
		String cmd = "Model.showTablesList()";
		ShowTablesListCmd sal = (ShowTablesListCmd) cp.createCommand(cmd);
		System.out.println(sal.getVarName());
		assertEquals(sal.getVarName(), "Model");
				
	}
	
	@Test
	public void testShowTablesListCmdNoModel() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "NoExistingModel.showTablesList()";
		ShowTablesListCmd sal = (ShowTablesListCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("No '" + sal.getVarName() + "' XTTModel object in memory");
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
	
	private String getErrorStringFormat(String str) {
		return "\u001B[31m======>" + str + "\"\u001B[0m\n";
	}
}
