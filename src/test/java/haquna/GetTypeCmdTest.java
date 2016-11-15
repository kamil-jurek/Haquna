package haquna;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;

public class GetTypeCmdTest {
	
public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = new Model('threat-monitor.hmr')");
		cp.createCommand("Attr = Model.getAttributeByName('day')");
	}
		
	@Test
	public void testGetTypeCmd() {
		setup();
				
		String cmd = "Type = Attr.getType()";
		cp.createCommand(cmd);
						
		assertEquals(Haquna.typeMap.containsKey("Type"), true);
		assertEquals(Haquna.typeMap.get("Type").getName(), "day_type");
				
	}		
}
