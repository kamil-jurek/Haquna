package haquna;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.get.GetRuleByIdCmd;
import haquna.utils.HaqunaUtils;

public class GetRuleByIdCmdTest {
	
public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = new Model('threat-monitor.hmr')");
		cp.createCommand("Table = Model.getTableByName('Threats')");
	}
		
	@Test
	public void testGetRuleByIdCmdNoModel() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "Rule = NoExistingTab.getRuleById('Threats/1')";
		GetRuleByIdCmd sal = (GetRuleByIdCmd) cp.createCommand(cmd);
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
		GetRuleByIdCmd sal = (GetRuleByIdCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("No rule with '" + sal.getRuleId() + "' id in '" + sal.getTableName() + "' table");
		
		assertEquals(Haquna.tableMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	@Test
	public void testGetRuleByIdCmdNoVar() {
		setup();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		cp.createCommand("Tab = Model.getTableByName('Today')");
		
		String cmd = "Tab = Table.getRuleById('integer')";
		GetRuleByIdCmd sal = (GetRuleByIdCmd) cp.createCommand(cmd);
		String expectedOutput = getErrorStringFormat("Variable name '" + sal.getVarName() + "' already in use");
		
		assertEquals(Haquna.ruleMap.containsKey("Tab"), false);
		assertEquals(outContent.toString(), expectedOutput);				
	}
	
	private String getErrorStringFormat(String str) {
		return "\u001B[31m======>" + str + "\"\u001B[0m\n";
	}
}
