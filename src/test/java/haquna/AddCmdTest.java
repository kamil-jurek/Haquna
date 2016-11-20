package haquna;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import haquna.utils.HaqunaUtils;

public class AddCmdTest {
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");
		TestUtils.createAndExecCmd("Model_bis = new Model('threat-monitor.hmr')");
		TestUtils.createAndExecCmd("Typ = new Type('xtype [name: weather_type,base: symbolic,desc: 'Wheater type',domain: [sunny,rainy,cloudy]]')");
		TestUtils.createAndExecCmd("Att = new Attribute('xattr [name: weather,abbrev: weat1,class: simple,type: weather_type,comm: inter].')");
		TestUtils.createAndExecCmd("Tab = new Table('xschm 'Recommendations': [weather] ==> [day].')");
		TestUtils.createAndExecCmd("Rul= new Rule('xrule 'Recommendations'/1: [weather in [sunny,cloudy]]==>[day set [sun]].')");
		
		TestUtils.createAndExecCmd("M0 = new Model('poi-recommender.hmr ')");
		TestUtils.createAndExecCmd("TypObj = Model.getTypeByName('day_type')");
		TestUtils.createAndExecCmd("TypObj_today = Model.getTypeByName('today_type')");
		TestUtils.createAndExecCmd("AttrObj_day = Model.getAttributeByName('day')");
		TestUtils.createAndExecCmd("AttrObj_today = Model.getAttributeByName('today')");
		TestUtils.createAndExecCmd("TabObj = Model.getTableByName('Today')");
		TestUtils.createAndExecCmd("RulObj1 = TabObj.getRuleByName('Today/1')");
		TestUtils.createAndExecCmd("RulObj2 = TabObj.getRuleByName('Today/2')");
	}
	
	@Test
	public void testAddCmd1() {		
		setup();				
		
		TestUtils.createAndExecCmd("M1 = Model.add(Typ)");
		TestUtils.createAndExecCmd("M1.showTypesList()");
		
		TestUtils.createAndExecCmd("M2 = M1.add(Att)");
		TestUtils.createAndExecCmd("M2.showAttributesList()");
		
		TestUtils.createAndExecCmd("M3 = M2.add(Tab)");
		TestUtils.createAndExecCmd("M3.showTablesList()");
		
		TestUtils.createAndExecCmd("M4 = M3.add(Rul)");
		//TestUtils.createAndExecCmd("M4.show()");
		
		assertEquals(HaqunaSingleton.modelMap.containsKey("M4"), true);
		System.out.println("=======================================");			
	}
		
	
	@Test
	public void testAddCmd2() {		
		setup();				
		
		TestUtils.createAndExecCmd("M1 = M0.add(TypObj)");
		TestUtils.createAndExecCmd("M1.showTypesList()");
		
		assertEquals(HaqunaSingleton.modelMap.containsKey("M1"), true);
		System.out.println("=======================================");			
	}
	
	@Test
	public void testAddCmd3() {		
		setup();				
		
		TestUtils.createAndExecCmd("M1 = M0.add(TypObj)");
		TestUtils.createAndExecCmd("M1.showTypesList()");
		
		TestUtils.createAndExecCmd("M2 = M1.add(AttrObj_day)");
		TestUtils.createAndExecCmd("M2.showTypesList()");
		TestUtils.createAndExecCmd("M2.showAttributesList()");
				
		assertEquals(HaqunaSingleton.modelMap.containsKey("M2"), true);
		System.out.println("=======================================");
	}
	
	@Test
	public void testAddCmd4() {		
		setup();				
		
		TestUtils.createAndExecCmd("M1 = M0.add(TypObj)");
		TestUtils.createAndExecCmd("M2 = M1.add(TypObj_today)");
		TestUtils.createAndExecCmd("M3 = M2.add(AttrObj_day)");
		TestUtils.createAndExecCmd("M4 = M3.add(AttrObj_today)");
		TestUtils.createAndExecCmd("M5 = M4.add(TabObj)");
		
		TestUtils.createAndExecCmd("M5.showTypesList()");
		TestUtils.createAndExecCmd("M5.showAttributesList()");
		
		TestUtils.createAndExecCmd("printVars()");
				
		assertEquals(HaqunaSingleton.modelMap.containsKey("M5"), true);
		System.out.println("=======================================");
	}
	
	@Test
	public void testAddCmd5() {		
		setup();				
		
		TestUtils.createAndExecCmd("M1 = M0.add(TypObj)");
		TestUtils.createAndExecCmd("M2 = M1.add(TypObj_today)");
		TestUtils.createAndExecCmd("M3 = M2.add(AttrObj_day)");
		TestUtils.createAndExecCmd("M4 = M3.add(AttrObj_today)");
		TestUtils.createAndExecCmd("M5 = M4.add(TabObj)");
		TestUtils.createAndExecCmd("M6 = M5.add(RulObj1)");
		TestUtils.createAndExecCmd("M7 = M6.add(RulObj2)");
		
		TestUtils.createAndExecCmd("M7.showTypesList()");
		TestUtils.createAndExecCmd("M7.showAttributesList()");
		
		TestUtils.createAndExecCmd("printVars()");
				
		assertEquals(HaqunaSingleton.modelMap.containsKey("M7"), true);
		System.out.println("=======================================");
	}
}
