package haquna;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import haquna.utils.HaqunaUtils;

public class ShowHistoricalValueCmd {
	public static void setup() {
		try {
			HaqunaUtils.clearMemory();
			TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");		
			TestUtils.createAndExecCmd("Wm = new WorkingMemory(Model)");
			TestUtils.createAndExecCmd("Wm.setValueOf('hour','1')");
			TimeUnit.SECONDS.sleep(1);
			TestUtils.createAndExecCmd("Wm.setValueOf('hour','2')");
			TimeUnit.SECONDS.sleep(1);
			TestUtils.createAndExecCmd("Wm.setValueOf('hour','3')");
			TimeUnit.SECONDS.sleep(1);
			TestUtils.createAndExecCmd("Wm.setValueOf('hour','4')");
			TimeUnit.SECONDS.sleep(1);
			TestUtils.createAndExecCmd("Wm.setValueOf('hour','5')");
			TimeUnit.SECONDS.sleep(1);
			TestUtils.createAndExecCmd("Wm.setValueOf('hour','6')");
			TimeUnit.SECONDS.sleep(1);
			TestUtils.createAndExecCmd("Wm.setValueOf('hour','7')");
			TimeUnit.SECONDS.sleep(1);
			TestUtils.createAndExecCmd("Wm.setValueOf('hour','8')");
			TimeUnit.SECONDS.sleep(1);
			TestUtils.createAndExecCmd("Wm.setValueOf('hour','9')");
			TimeUnit.SECONDS.sleep(1);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	@Test
	public void testAddCmd1() {		
		setup();				
		
		TestUtils.createAndExecCmd("Wm.showHistoricalValueOf('hour','5')");
		//TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		TestUtils.createAndExecCmd("Wm.showHistoricalValueOf('hour','3')");
		//TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		TestUtils.createAndExecCmd("Wm.showHistoricalValueOf('hour','1')");
		//TestUtils.createAndExecCmd("Wm.showCurrentState()");
		//assertEquals(Haquna.modelMap.containsKey("M4"), true);
		System.out.println("=======================================");			
	}
}
