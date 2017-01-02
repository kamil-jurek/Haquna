package haquna;

import static org.junit.Assert.*;

import org.junit.Test;

import haquna.utils.HaqunaUtils;

public class RunCmdTest {
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
		
		TestUtils.createAndExecCmd("Model.run(Wm, tables=['DayTime','Today'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
			
	}
	
	@Test
	public void testRunCmd21() {
		setup();	
		
		TestUtils.createAndExecCmd("Model.run(Wm, inference=gdi, tables=['Today'])");		
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");
	}
	
	@Test
	public void testRunCmd22() {
		setup();	
		
		TestUtils.createAndExecCmd("Model.run(Wm, inference=ddi, tables=['Today'])");		
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");
	}
	
	@Test
	public void testRunCmd23() {
		setup();	
		
		TestUtils.createAndExecCmd("Model.run(Wm, inference=foi, tables=['Today'])");		
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");
	}
	
	/*@Test
	public void testRunCmd3() {
		setup();	
			
		TestUtils.createAndExecCmd("Wm3 = Model.run(inference=gdi, tables=['Threats'])");
		TestUtils.createAndExecCmd("Wm3.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm3").getAttributeValue("daytime"), new Null());
			
	}*/
	
	@Test
	public void testRunCmd41() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, tokens=on, tables=['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd42() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, tokens=off, tables=['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd51() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, uncertainty=on, tables=['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd52() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, uncertainty=off, tables=['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd61() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, conflict_resolution=first, tables=['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd62() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, conflict_resolution=last, tables=['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd63() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, conflict_resolution=all, tables=['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd71() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, inference=ddi, tables=['DayTime', 'Today'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");
			
	}
	
	@Test
	public void testRunCmd81() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, inference=ddi, tokens=on, tables=['DayTime', 'Today'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");
			
	}
	
	@Test
	public void testRunCmdN1() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, tokens=on, tables=['DayTime', 'Today'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
		assertEquals(Haquna.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");
			
	}
}
