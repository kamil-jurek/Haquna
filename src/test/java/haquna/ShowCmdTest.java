package haquna;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;

public class ShowCmdTest {
	public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = new Model('threat-monitor.hmr')");
		cp.createCommand("Attr = Model.getAttributeByName('day')");
		cp.createCommand("Type = Model.getTypeByName('day_type')");
		cp.createCommand("Table = Model.getTableByName('Threats')");
		cp.createCommand("Rule = Table.getRuleByName('Threats/1')");
		cp.createCommand("Type2 = Attr.getType()");
		cp.createCommand("Callback = Attr.getCallback()");
		cp.createCommand("Wm = new WorkingMemory(Model)");
	}
		
	@Test
	public void testGetTypeCmd() {
		setup();
				
		cp.createCommand("Model.show()");
		cp.createCommand("Attr.show()");
		cp.createCommand("Type.show()");
		cp.createCommand("Table.show()");
		cp.createCommand("Rule.show()");
		cp.createCommand("Type2.show()");
		cp.createCommand("Callback.show()");
		cp.createCommand("Wm.show()");
		
		assertEquals(Haquna.typeMap.containsKey("Type"), true);
		assertEquals(Haquna.typeMap.get("Type").getName(), "day_type");
				
	}		
}
