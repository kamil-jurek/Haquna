package haquna;

import static org.junit.Assert.*;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;
import heart.alsvfd.Null;


public class RunCmdTest {
	
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
		
		cp.createCommand("Model.run(Wm, ['DayTime','Today'])");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.containsKey("Wm"), true);
			
	}
	
	@Test
	public void testRunCmd21() {
		setup();	
		
		cp.createCommand("Model.run(Wm, mode=gdi, ['Today'])");		
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");			
	}
	
	@Test
	public void testRunCmd22() {
		setup();	
		
		cp.createCommand("Model.run(Wm, mode=ddi, ['Today'])");		
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");			
	}
	
	@Test
	public void testRunCmd23() {
		setup();	
		
		cp.createCommand("Model.run(Wm, mode=foi, ['Today'])");		
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");			
	}
	
	@Test
	public void testRunCmd3() {
		setup();	
			
		cp.createCommand("Wm3 = Model.run(mode=gdi, ['Threats'])");
		cp.createCommand("Wm3.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm3").getAttributeValue("daytime"), new Null());
			
	}
	
	@Test
	public void testRunCmd41() {
		setup();	
			
		cp.createCommand("Model.run(Wm, token=true, ['DayTime'])");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd42() {
		setup();	
			
		cp.createCommand("Model.run(Wm, token=false, ['DayTime'])");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd51() {
		setup();	
			
		cp.createCommand("Model.run(Wm, uncertainty=true, ['DayTime'])");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd52() {
		setup();	
			
		cp.createCommand("Model.run(Wm, uncertainty=false, ['DayTime'])");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd61() {
		setup();	
			
		cp.createCommand("Model.run(Wm, conflict_strategy=first, ['DayTime'])");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd62() {
		setup();	
			
		cp.createCommand("Model.run(Wm, conflict_strategy=last, ['DayTime'])");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd63() {
		setup();	
			
		cp.createCommand("Model.run(Wm, conflict_strategy=all, ['DayTime'])");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
			
	}
	
	@Test
	public void testRunCmd71() {
		setup();	
			
		cp.createCommand("Model.run(Wm, mode=ddi, ['DayTime', 'Today'])");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");
			
	}
	
	@Test
	public void testRunCmd81() {
		setup();	
			
		cp.createCommand("Model.run(Wm, mode=ddi, token=true, ['DayTime', 'Today'])");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");
			
	}
	
	@Test
	public void testRunCmdN1() {
		setup();	
			
		cp.createCommand("Model.run(Wm, token=true, ['DayTime', 'Today'])");
		cp.createCommand("Wm.showCurrentState()");
		
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("daytime").toString(), "afternoon");
		assertEquals(HaqunaSingleton.wmMap.get("Wm").getAttributeValue("today").toString(), "workday");
			
	}
}
