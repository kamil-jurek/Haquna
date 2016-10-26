package haquna;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;

public class addCmdTest {
public static CommandFactory cp = new CommandFactory();
	

	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = xload('threat-monitor.hmr')");
		cp.createCommand("Typ1 = Model.getTypeByName('day_type')");
		cp.createCommand("Typ2 = Model.getTypeByName('today_type')");
		cp.createCommand("Attr1 = Model.getAttributeByName('day')");
		cp.createCommand("Attr2 = Model.getAttributeByName('today')");
		cp.createCommand("Tab = Model.getTableByName('Today')");
		
		cp.createCommand("R1 = Tab.getRuleByName('Today/1')");
		cp.createCommand("R2 = Tab.getRuleByName('Today/2')");
		
		cp.createCommand("M00 = xload('poi-recommender.hmr ')");
		
	}
		
	@Test
	public void testAddCmd1() {		
		setup();				
		
		cp.createCommand("M01 = M00.add(Typ1)");
		cp.createCommand("M01.showTypesList()");
		
		assertEquals(Haquna.modelMap.containsKey("M01"), true);
		System.out.println("=======================================");			
	}
	
	@Test
	public void testAddCmd2() {		
		setup();				
		
		cp.createCommand("M01 = M00.add(Typ1)");
		cp.createCommand("M01.showTypesList()");
		
		cp.createCommand("M02 = M01.add(Attr1)");
		cp.createCommand("M02.showTypesList()");
		cp.createCommand("M02.showAttributesList()");
				
		assertEquals(Haquna.modelMap.containsKey("M02"), true);
		System.out.println("=======================================");
	}
	
	@Test
	public void testAddCmd3() {		
		setup();				
		
		cp.createCommand("M01 = M00.add(Typ1)");
		cp.createCommand("M01.showTypesList()");
		
		cp.createCommand("M02 = M01.add(Attr1)");
		cp.createCommand("M02.showTypesList()");
		cp.createCommand("M02.showAttributesList()");
		
		cp.createCommand("M03 = M02.add(Typ2)");
		cp.createCommand("M04 = M03.add(Attr2)");
		cp.createCommand("M04.showTypesList()");
		cp.createCommand("M04.showAttributesList()");
				
		assertEquals(Haquna.modelMap.containsKey("M04"), true);
		System.out.println("=======================================");			
	}
	
	@Test
	public void testAddCmd4() {		
		setup();				
		
		cp.createCommand("M01 = M00.add(Typ1)");
		cp.createCommand("M01.showTypesList()");
		
		cp.createCommand("M02 = M01.add(Attr1)");
		cp.createCommand("M02.showTypesList()");
		cp.createCommand("M02.showAttributesList()");
		
		cp.createCommand("M03 = M02.add(Typ2)");
		cp.createCommand("M04 = M03.add(Attr2)");
		cp.createCommand("M04.showTypesList()");
		cp.createCommand("M04.showAttributesList()");
				
		cp.createCommand("M05 = M04.add(Tab)");
		cp.createCommand("M05.showTypesList()");
		cp.createCommand("M05.showAttributesList()");
		cp.createCommand("M05.showTablesList()");
		
		assertEquals(Haquna.modelMap.containsKey("M05"), true);
		System.out.println("=======================================");			
	}
	
	@Test
	public void testAddCmd5() {		
		setup();				
		
		cp.createCommand("M01 = M00.add(Typ1)");
		cp.createCommand("M01.showTypesList()");
		
		cp.createCommand("M02 = M01.add(Attr1)");
		cp.createCommand("M02.showTypesList()");
		cp.createCommand("M02.showAttributesList()");
		
		cp.createCommand("M03 = M02.add(Typ2)");
		cp.createCommand("M04 = M03.add(Attr2)");
		cp.createCommand("M04.showTypesList()");
		cp.createCommand("M04.showAttributesList()");
				
		cp.createCommand("M05 = M04.add(Tab)");
		cp.createCommand("M05.showTypesList()");
		cp.createCommand("M05.showAttributesList()");
		cp.createCommand("M05.showTablesList()");
		
		cp.createCommand("M06 = M05.add(R1)");
		cp.createCommand("M07 = M06.add(R2)");
		cp.createCommand("M07.showTablesList()");
		cp.createCommand("M07.show()");
		assertEquals(Haquna.modelMap.containsKey("M05"), true);
		System.out.println("=======================================");			
	}
}
