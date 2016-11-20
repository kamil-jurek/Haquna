package haquna;

import static org.junit.Assert.*;

import org.junit.Test;

import haquna.utils.HaqunaUtils;


public class RemoveCmdTest {
	public static void setup() {
		HaqunaUtils.clearMemory();
		TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");

	}
		
	@Test
	public void testRemoveType() {		
		setup();				
		
		TestUtils.createAndExecCmd("M2 = Model.remove('day_type')");
		
		assertEquals(Haquna.modelMap.containsKey("M2"), false);
			
	}
	
	@Test
	public void testRemoveAttr() {		
		setup();				
		
		TestUtils.createAndExecCmd("M2 = Model.remove('today')");
		
		assertEquals(Haquna.modelMap.containsKey("M2"), false);
		TestUtils.createAndExecCmd("Model.showAttributesList()");
		TestUtils.createAndExecCmd("M2.showAttributesList()");
			
	}
	
	@Test
	public void testRemoveTable() {		
		setup();				
		
		TestUtils.createAndExecCmd("M2 = Model.remove('Today')");
		
		assertEquals(Haquna.modelMap.containsKey("M2"), false);
		
			
	}
	
	
	@Test
	public void testRemoveRule() {		
		setup();				
		
		TestUtils.createAndExecCmd("M2 = Model.remove('Today/1')");

		assertEquals(Haquna.modelMap.containsKey("M2"), true);
		
			
	}
	
	@Test
	public void testRemoveAll() {		
		setup();				
		
		TestUtils.createAndExecCmd("M2 = Model.remove('Today/1')");
		TestUtils.createAndExecCmd("M3 = M2.remove('Today/2')");
		TestUtils.createAndExecCmd("M4 = M3.remove('Today')");
		TestUtils.createAndExecCmd("M4.showAttributesList()");
		
		//TestUtils.createAndExecCmd("M5 = M4.remove('today')");
		//TestUtils.createAndExecCmd("M5.showAttributesList()");
	
		TestUtils.createAndExecCmd("M6 = M4.remove('day')");
		TestUtils.createAndExecCmd("M6.showAttributesList()");
		TestUtils.createAndExecCmd("M7 = M6.remove('day_type')");
		TestUtils.createAndExecCmd("M7.showTypesList()");
		
		assertEquals(Haquna.modelMap.containsKey("M7"), true);
		
		
		
			
	}

	@Test
	public void testRemoveNewAddedType() {		
		setup();				
		
		TestUtils.createAndExecCmd("PoiModel = new Model('/home/kamil/Pulpit/poi-recommender.hmr')");
		TestUtils.createAndExecCmd("DayType = Model.getTypeByName('day_type')");
		
		TestUtils.createAndExecCmd("NewPoiModel = PoiModel.add(DayType)");
		TestUtils.createAndExecCmd("NewPoiModel.showTypesList()");
		
		TestUtils.createAndExecCmd("NewPoiWithoutDay = NewPoiModel.remove('day_type')");
		TestUtils.createAndExecCmd("NewPoiWithoutDay.showTypesList()");
		
		assertEquals(Haquna.modelMap.containsKey("NewPoiWithoutDay"), true);
	}
	
	@Test
	public void testRemoveNewAddedAttr() {		
		setup();				
		
		TestUtils.createAndExecCmd("PoiModel = new Model('/home/kamil/Pulpit/poi-recommender.hmr')");
		TestUtils.createAndExecCmd("DayType = Model.getTypeByName('day_type')");
		TestUtils.createAndExecCmd("Attr = Model.getAttributeByName('day')");
		
		TestUtils.createAndExecCmd("NewPoiModel = PoiModel.add(DayType)");
		TestUtils.createAndExecCmd("NewPoiModel.showTypesList()");
		
		TestUtils.createAndExecCmd("AttrAdded = NewPoiModel.add(Attr)");
		TestUtils.createAndExecCmd("AttrAdded.showAttributesList()");
		
		TestUtils.createAndExecCmd("NewPoiWithoutDay = AttrAdded.remove('day')");
		TestUtils.createAndExecCmd("NewPoiWithoutDay.showAttributesList()");
		
		
		TestUtils.createAndExecCmd("NewPoiWithoutDay2 = NewPoiWithoutDay.remove('day_type')");
		TestUtils.createAndExecCmd("NewPoiWithoutDay2.showTypesList()");
		assertEquals(Haquna.modelMap.containsKey("NewPoiWithoutDay2"), true);
	}
}
