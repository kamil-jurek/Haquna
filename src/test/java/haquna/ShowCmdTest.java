package haquna;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;

public class ShowCmdTest {
	public static void setup() {
		HaqunaUtils.clearMemory();
		TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");
		TestUtils.createAndExecCmd("Attr = Model.getAttributeByName('day')");
		TestUtils.createAndExecCmd("Type = Model.getTypeByName('day_type')");
		TestUtils.createAndExecCmd("Table = Model.getTableByName('Threats')");
		TestUtils.createAndExecCmd("Rule = Table.getRuleByName('Threats/1')");
		TestUtils.createAndExecCmd("Type2 = Attr.getType()");
		TestUtils.createAndExecCmd("Callback = Attr.getCallback()");
		TestUtils.createAndExecCmd("Wm = new WorkingMemory(Model)");
	}
		
	@Test
	public void testGetTypeCmd() {
		setup();
				
		TestUtils.createAndExecCmd("Model.show()");
		TestUtils.createAndExecCmd("Attr.show()");
		TestUtils.createAndExecCmd("Type.show()");
		TestUtils.createAndExecCmd("Table.show()");
		TestUtils.createAndExecCmd("Rule.show()");
		TestUtils.createAndExecCmd("Type2.show()");
		TestUtils.createAndExecCmd("Callback.show()");
		TestUtils.createAndExecCmd("Wm.show()");
		
		assertEquals(HaqunaSingleton.typeMap.containsKey("Type"), true);
		assertEquals(HaqunaSingleton.typeMap.get("Type").getName(), "day_type");
				
	}		
}
