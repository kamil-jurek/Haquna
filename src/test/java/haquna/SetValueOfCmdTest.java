package haquna;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;

public class SetValueOfCmdTest {
public static CommandFactory cp = new CommandFactory();
	

	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = new Model('threat-monitor.hmr')");
		cp.createCommand("Wm = new WorkingMemory(Model)");
		cp.createCommand("Wm.setValueOf('hour','16')");
		cp.createCommand("Wm.setValueOf('location','work')");
		cp.createCommand("Wm.setValueOf('activity','walking')");
		cp.createCommand("Wm.setValueOf('day','mon/1')");
	}
		
	@Test
	public void testRunCmd1() {		
		setup();				
		
		cp.createCommand("Wm.setValueOf('hour','22#0.4')");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
			
	}
	
	@Test
	public void testRunCmd2() {		
		setup();				
		
		cp.createCommand("Wm.setValueOf('hour','20.4')");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
			
	}
}
