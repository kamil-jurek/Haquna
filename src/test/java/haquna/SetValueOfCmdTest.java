package haquna;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
	
	public static void setup2() {
		HaqunaUtils.clearMemory();
		TestUtils.createAndExecCmd("Model = new Model('poi-recommender.hmr')");
		TestUtils.createAndExecCmd("Wm = new WorkingMemory(Model)");		
	}
		
	@Test
	public void testRunCmd1() {		
		setup();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('hour','22#0.4')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
			
	}
	
	@Test
	public void testRunCmd2() {		
		setup();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('hour','20.4')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
			
	}
	
	@Test
	public void testRunCmd3() {		
		setup2();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('user_profile','eating')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
			
	}
	
	@Test
	public void testRunCmd4() {		
		setup2();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('user_profile','eating#0.2')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
			
	}
	
	@Test
	public void testRunCmd5() {		
		setup2();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('user_profile','[eating,sport]')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
			
	}
	
	@Test
	public void testRunCmd6() {		
		setup2();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('user_profile','[eating#0.2]')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
			
	}
	
	@Test
	public void testRunCmd7() {		
		setup2();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('user_profile','[eating#0.5, sport#0.2,culture]]')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
			
	}
}
