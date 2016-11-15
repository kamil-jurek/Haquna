package haquna;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;

public class AddCmdTest {
public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = new Model('threat-monitor.hmr')");
		cp.createCommand("Model_bis = new Model('threat-monitor.hmr')");
		cp.createCommand("Typ = new Type('xtype [name: weather_type,base: symbolic,desc: 'Wheater type',domain: [sunny,rainy,cloudy]]')");
		cp.createCommand("Att = new Attribute('xattr [name: weather,abbrev: weat1,class: simple,type: weather_type,comm: inter].')");
		cp.createCommand("Tab = new Table('xschm 'Recommendations': [weather] ==> [day].')");
		cp.createCommand("Rul= new Rule('xrule 'Recommendations'/1: [weather in [sunny,cloudy]]==>[day set [sun]].')");
		
		cp.createCommand("M0 = new Model('poi-recommender.hmr ')");
		cp.createCommand("TypObj = Model.getTypeByName('day_type')");
		cp.createCommand("TypObj_today = Model.getTypeByName('today_type')");
		cp.createCommand("AttrObj_day = Model.getAttributeByName('day')");
		cp.createCommand("AttrObj_today = Model.getAttributeByName('today')");
		cp.createCommand("TabObj = Model.getTableByName('Today')");
		cp.createCommand("RulObj1 = TabObj.getRuleByName('Today/1')");
		cp.createCommand("RulObj2 = TabObj.getRuleByName('Today/2')");
	}
	
	@Test
	public void testAddCmd1() {		
		setup();				
		
		cp.createCommand("M1 = Model.add(Typ)");
		cp.createCommand("M1.showTypesList()");
		
		cp.createCommand("M2 = M1.add(Att)");
		cp.createCommand("M2.showAttributesList()");
		
		cp.createCommand("M3 = M2.add(Tab)");
		cp.createCommand("M3.showTablesList()");
		
		cp.createCommand("M4 = M3.add(Rul)");
		//cp.createCommand("M4.show()");
		
		assertEquals(Haquna.modelMap.containsKey("M4"), true);
		System.out.println("=======================================");			
	}
		
	
	@Test
	public void testAddCmd2() {		
		setup();				
		
		cp.createCommand("M1 = M0.add(TypObj)");
		cp.createCommand("M1.showTypesList()");
		
		assertEquals(Haquna.modelMap.containsKey("M1"), true);
		System.out.println("=======================================");			
	}
	
	@Test
	public void testAddCmd3() {		
		setup();				
		
		cp.createCommand("M1 = M0.add(TypObj)");
		cp.createCommand("M1.showTypesList()");
		
		cp.createCommand("M2 = M1.add(AttrObj_day)");
		cp.createCommand("M2.showTypesList()");
		cp.createCommand("M2.showAttributesList()");
				
		assertEquals(Haquna.modelMap.containsKey("M2"), true);
		System.out.println("=======================================");
	}
	
	@Test
	public void testAddCmd4() {		
		setup();				
		
		cp.createCommand("M1 = M0.add(TypObj)");	
		cp.createCommand("M2 = M1.add(TypObj_today)");
		cp.createCommand("M3 = M2.add(AttrObj_day)");
		cp.createCommand("M4 = M3.add(AttrObj_today)");
		cp.createCommand("M5 = M4.add(TabObj)");
		
		cp.createCommand("M5.showTypesList()");
		cp.createCommand("M5.showAttributesList()");
		
		cp.createCommand("printVars()");
				
		assertEquals(Haquna.modelMap.containsKey("M5"), true);
		System.out.println("=======================================");
	}
	
	@Test
	public void testAddCmd5() {		
		setup();				
		
		cp.createCommand("M1 = M0.add(TypObj)");	
		cp.createCommand("M2 = M1.add(TypObj_today)");
		cp.createCommand("M3 = M2.add(AttrObj_day)");
		cp.createCommand("M4 = M3.add(AttrObj_today)");
		cp.createCommand("M5 = M4.add(TabObj)");
		cp.createCommand("M6 = M5.add(RulObj1)");
		cp.createCommand("M7 = M6.add(RulObj2)");
		
		cp.createCommand("M7.showTypesList()");
		cp.createCommand("M7.showAttributesList()");
		
		cp.createCommand("printVars()");
				
		assertEquals(Haquna.modelMap.containsKey("M7"), true);
		System.out.println("=======================================");
	}
}
