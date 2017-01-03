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
		assertEquals("eating", Haquna.wmMap.get("Wm").getAttributeValue("user_profile").toString());
		assertEquals(0.2, Haquna.wmMap.get("Wm").getAttributeValue("user_profile").getCertaintyFactor(), 0.001);
			
	}
	
	@Test
	public void testRunCmd5() {		
		setup2();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('user_profile','[eating,sport]')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
		assertEquals("[eating ,sport]", Haquna.wmMap.get("Wm").getAttributeValue("user_profile").toString());
		assertEquals(1.0, Haquna.wmMap.get("Wm").getAttributeValue("user_profile").getCertaintyFactor(), 0.001);
			
	}
	
	@Test
	public void testRunCmd6() {		
		setup2();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('user_profile','[eating#0.2]#0.4')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
		assertEquals("[eating]", Haquna.wmMap.get("Wm").getAttributeValue("user_profile").toString());
		assertEquals(0.4, Haquna.wmMap.get("Wm").getAttributeValue("user_profile").getCertaintyFactor(), 0.001);
			
	}
	
	@Test
	public void testRunCmd7() {		
		setup2();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('user_profile','[eating#0.5, sport#0.2,culture]#0.7')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
		assertEquals("[eating ,sport ,culture]", Haquna.wmMap.get("Wm").getAttributeValue("user_profile").toString());
		assertEquals(0.7, Haquna.wmMap.get("Wm").getAttributeValue("user_profile").getCertaintyFactor(), 0.001);	
			
	}
	
	@Test
	public void testRunCmd8() {		
		setup2();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('hour','[[1 to 10], 12, [2 to 5]]')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
		assertEquals("[<1.0 ; 10.0> ,12.0 ,<2.0 ; 5.0>]", Haquna.wmMap.get("Wm").getAttributeValue("hour").toString());
		assertEquals(1.0, Haquna.wmMap.get("Wm").getAttributeValue("day").getCertaintyFactor(), 0.001);	
			
	}
	
	@Test
	public void testRunCmd9() {		
		setup();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('day','[[mon to wed], fri]')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
		assertEquals("[<mon ; wed> ,fri]", Haquna.wmMap.get("Wm").getAttributeValue("day").toString());
		assertEquals(1.0, Haquna.wmMap.get("Wm").getAttributeValue("day").getCertaintyFactor(), 0.001);	
			
	}
	
	@Test
	public void testRunCmd10() {		
		setup();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('day','[[mon/1 to wed]#0.3, fri/5]#0.4')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
		assertEquals("[<mon/1 ; wed> ,fri/5]", Haquna.wmMap.get("Wm").getAttributeValue("day").toString());
		assertEquals(0.4, Haquna.wmMap.get("Wm").getAttributeValue("day").getCertaintyFactor(), 0.001);	
	}
	
	@Test
	public void testRunCmd11() {		
		setup();				
		
		TestUtils.createAndExecCmd("Wm.setValueOf('day','[fri to sun]#0.4')");
		TestUtils.createAndExecCmd("Wm.showCurrentState()");
		assertEquals(Haquna.wmMap.containsKey("Wm"), true);
		assertEquals("[<fri ; sun>]", Haquna.wmMap.get("Wm").getAttributeValue("day").toString());
		assertEquals(0.4, Haquna.wmMap.get("Wm").getAttributeValue("day").getCertaintyFactor(), 0.001);
			
	}

}
