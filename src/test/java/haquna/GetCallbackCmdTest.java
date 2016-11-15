package haquna;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;

public class GetCallbackCmdTest {
	
public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = new Model('threat-monitor.hmr')");
		cp.createCommand("Attr = Model.getAttributeByName('day')");
	}
		
	@Test
	public void testGetTypeCmd() {
		setup();
				
		String cmd = "Callback = Attr.getCallback()";
		cp.createCommand(cmd);
						
		assertEquals(Haquna.callbackMap.containsKey("Callback"), true);
		assertEquals(Haquna.callbackMap.get("Callback"), null);
				
	}		
}
