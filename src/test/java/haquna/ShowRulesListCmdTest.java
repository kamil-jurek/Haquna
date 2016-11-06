package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.show.ShowRulesListCmd;
import haquna.utils.HaqunaUtils;

public class ShowRulesListCmdTest {
	
	public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = xload('threat-monitor.hmr')");
	}
	
	@Test
	public void testShowRulesListCmd() {
		setup();
		
		String cmd = "Table.showRulesList()";
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
		String expectedOutput = getErrorStringFormat("No '" + sal.getVarName() + "' Table object in memory");
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
	
	private String getErrorStringFormat(String str) {
		return "\u001B[31m======>" + str + "\"\u001B[0m\n";
	}
}
