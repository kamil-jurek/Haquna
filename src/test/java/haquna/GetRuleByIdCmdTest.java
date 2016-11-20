package haquna;

import static haquna.TestUtils.getErrorStringFormat;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.get.GetRuleByIdCmd;
import haquna.utils.HaqunaUtils;

public class GetRuleByIdCmdTest {
	public static void setup() {
		HaqunaUtils.clearMemory();
		TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");
		TestUtils.createAndExecCmd("Table = Model.getTableByName('Threats')");
	}
		
	@Test
	public void testGetRuleByIdCmdNoModel() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "Rule = NoExistingTab.getRuleById('Threats/1')";
		GetRuleByIdCmd sal = (GetRuleByIdCmd) TestUtils.createCmd(cmd);
		sal.execute();
		String expectedOutput = getErrorStringFormat("No '" + sal.getTableName() + "' Table object in memory");
				
		assertEquals(Haquna.ruleMap.containsKey("Rule"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetRuleByIdCmdNoName() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		
		String cmd = "Rule = Table.getRuleById('NotExisting')";
		GetRuleByIdCmd sal = (GetRuleByIdCmd) TestUtils.createCmd(cmd);
		sal.execute();
		String expectedOutput = getErrorStringFormat("No rule with '" + sal.getRuleId() + "' id in '" + sal.getTableName() + "' table");
		
		assertEquals(Haquna.tableMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetRuleByIdCmdNoVar() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		TestUtils.createAndExecCmd("Tab = Model.getTableByName('Today')");
		
		String cmd = "Tab = Table.getRuleById('integer')";
		GetRuleByIdCmd sal = (GetRuleByIdCmd) TestUtils.createCmd(cmd);
		sal.execute();
		String expectedOutput = getErrorStringFormat("Variable name '" + sal.getVarName() + "' already in use");
		
		assertEquals(Haquna.ruleMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
}
