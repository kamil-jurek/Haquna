package haquna;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;

public class ShowValueOfCmdTest {
public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = new Model('threat-monitor.hmr')");
		cp.createCommand("Wm = new WorkingMemory(Model)");
	}
		
	@Test
	public void testGetTypeCmd() {
		setup();
				
		cp.createCommand("Wm.showValueOf('today')");
							
	}		
}