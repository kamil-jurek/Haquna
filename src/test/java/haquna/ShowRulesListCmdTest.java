package haquna;

import static haquna.TestUtils.getErrorStringFormat;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.show.ShowRulesListCmd;
import haquna.utils.HaqunaUtils;

public class ShowRulesListCmdTest {
	public static void setup() {
		HaqunaUtils.clearMemory();
		TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");
		TestUtils.createAndExecCmd("Table = Model.getTableByName('Threats')");
	}
	
	@Test
	public void testShowRulesListCmd() {
		setup();
		
		String cmd = "Table.showRulesList()";
		ShowRulesListCmd sal = (ShowRulesListCmd) TestUtils.createCmd(cmd);
		sal.execute();

		assertEquals(sal.getVarName(), "Table");
	}
	
	@Test
	public void testShowRulesListCmdNoModel() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "NoExistingTable.showRulesList()";
		ShowRulesListCmd sal = (ShowRulesListCmd) TestUtils.createCmd(cmd);
		sal.execute();

		String expectedOutput = getErrorStringFormat("No '" + sal.getVarName() + "' Table object in memory");
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
}
