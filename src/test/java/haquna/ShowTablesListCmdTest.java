package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.show.ShowTablesListCmd;

public class ShowTablesListCmdTest {
	
	CommandFactory cp = new CommandFactory();
	
	@Test
	public void testShowTablesListCmd() {
		String cmd;
		cp.createCommand("Model = xload('threat-monitor.hmr')");
		
		cmd = "Model.showTablesList()";
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
		String expectedOutput = "No " + sal.getVarName() + " model in memory\n";
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
}
