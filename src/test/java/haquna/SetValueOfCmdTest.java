package haquna;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;

public class SetValueOfCmdTest {
	public static void setup() {
		HaqunaUtils.clearMemory();
		TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");
		TestUtils.createAndExecCmd("Wm = new WorkingMemory(Model)");
		TestUtils.createAndExecCmd("Wm.setValueOf('hour','16')");
		TestUtils.createAndExecCmd("Wm.setValueOf('location','work')");
		TestUtils.createAndExecCmd("Wm.setValueOf('activity','walking')");
		TestUtils.createAndExecCmd("Wm.setValueOf('day','mon/1')");
	}
		
	@Test
	public void testRunCmd1() {		
		setup();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('hour','22#0.4')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.containsKey("Wm"), true);
			
	}
	
	@Test
	public void testRunCmd2() {		
		setup();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('hour','20.4')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.containsKey("Wm"), true);
			
	}
}
