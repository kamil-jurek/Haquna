package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.show.ShowRulesListCmd;

public class ShowRulesListCmdTest {
	
	CommandFactory cp = new CommandFactory();
	
	@Test
	public void testShowRulesListCmd() {
		String cmd;
		cp.createCommand("Model = xload('threat-monitor.hmr')");
		
		cmd = "Table.showRulesList()";
		ShowRulesListCmd sal = (ShowRulesListCmd) cp.createCommand(cmd);
		System.out.println(sal.getVarName());
		assertEquals(sal.getVarName(), "Table");
				
	}
	
	@Test
	public void testShowRulesListCmdNoModel() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "NoExistingTable.showRulesList()";
		ShowRulesListCmd sal = (ShowRulesListCmd) cp.createCommand(cmd);
		String expectedOutput = "No " + sal.getVarName() + " table in memory\n";
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
}
