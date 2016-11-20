package haquna;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;

public class GetTypeCmdTest {
	
public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");
		TestUtils.createAndExecCmd("Attr = Model.getAttributeByName('day')");
	}
		
	@Test
	public void testGetTypeCmd() {
		setup();
				
		String cmd = "Type = Attr.getType()";
		TestUtils.createAndExecCmd(cmd);
						
		assertEquals(HaqunaSingleton.typeMap.containsKey("Type"), true);
		assertEquals(HaqunaSingleton.typeMap.get("Type").getName(), "day_type");
				
	}		
}
