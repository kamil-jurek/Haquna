package haquna;

import static org.junit.Assert.*;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;
import heart.alsvfd.Null;


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
		
		TestUtils.createAndExecCmd("Model.run(Wm, ['DayTime','Today'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.containsKey("Wm"), true);
			
	}
	
	@Test
	public void testRunCmd21() {
		setup();	
		
		TestUtils.createAndExecCmd("Model.run(Wm, mode=gdi, ['Today'])");		
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");			
	}
	
	@Test
	public void testRunCmd22() {
		setup();	
		
		TestUtils.createAndExecCmd("Model.run(Wm, mode=ddi, ['Today'])");		
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");			
	}
	
	@Test
	public void testRunCmd23() {
		setup();	
		
		TestUtils.createAndExecCmd("Model.run(Wm, mode=foi, ['Today'])");		
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");			
	}
	
	@Test
	public void testRunCmd3() {
		setup();	
			
		TestUtils.createAndExecCmd("Wm3 = Model.run(mode=gdi, ['Threats'])");
		TestUtils.createAndExecCmd("Wm3.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm3").getAttributeValue("daytime"), new Null());
			
	}
	
	@Test
	public void testRunCmd41() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, token=true, ['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd42() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, token=false, ['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd51() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, uncertainty=true, ['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd52() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, uncertainty=false, ['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd61() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, conflict_strategy=first, ['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd62() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, conflict_strategy=last, ['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd63() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, conflict_strategy=all, ['DayTime'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd71() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, mode=ddi, ['DayTime', 'Today'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");
			
	}
	
	@Test
	public void testRunCmd81() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, mode=ddi, token=true, ['DayTime', 'Today'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");
			
	}
	
	@Test
	public void testRunCmdN1() {
		setup();	
			
		TestUtils.createAndExecCmd("Model.run(Wm, token=true, ['DayTime', 'Today'])");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");
			
	}
}
