package haquna;

import static org.junit.Assert.*;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;


public class RemoveCmdTest {
	
	public static CommandFactory cp = new CommandFactory();
	

	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = new Model('threat-monitor.hmr')");

	}
		
	@Test
	public void testRemoveType() {		
		setup();				
		
		cp.createCommand("M2 = Model.remove('day_type')");
		
		assertEquals(Haquna.modelMap.containsKey("M2"), false);
			
	}
	
	@Test
	public void testRemoveAttr() {		
		setup();				
		
		cp.createCommand("M2 = Model.remove('today')");
		
		assertEquals(Haquna.modelMap.containsKey("M2"), false);
		cp.createCommand("Model.showAttributesList()");
		cp.createCommand("M2.showAttributesList()");
			
	}
	
	@Test
	public void testRemoveTable() {		
		setup();				
		
		cp.createCommand("M2 = Model.remove('Today')");
		
		assertEquals(Haquna.modelMap.containsKey("M2"), false);
		
			
	}
	
	
	@Test
	public void testRemoveRule() {		
		setup();				
		
		cp.createCommand("M2 = Model.remove('Today/1')");
		
		assertEquals(Haquna.modelMap.containsKey("M2"), true);
		
			
	}
	
	@Test
	public void testRemoveAll() {		
		setup();				
		
		cp.createCommand("M2 = Model.remove('Today/1')");
		cp.createCommand("M3 = M2.remove('Today/2')");
		cp.createCommand("M4 = M3.remove('Today')");
		cp.createCommand("M4.showAttributesList()");
		
		//cp.createCommand("M5 = M4.remove('today')");
		//cp.createCommand("M5.showAttributesList()");
	
		cp.createCommand("M6 = M4.remove('day')");
		cp.createCommand("M6.showAttributesList()");
		cp.createCommand("M7 = M6.remove('day_type')");
		cp.createCommand("M7.showTypesList()");
		
		assertEquals(Haquna.modelMap.containsKey("M7"), true);
		
		
		
			
	}

	@Test
	public void testRemoveNewAddedType() {		
		setup();				
		
		cp.createCommand("PoiModel = new Model('/home/kamil/Pulpit/poi-recommender.hmr')");
		cp.createCommand("DayType = Model.getTypeByName('day_type')");
		
		cp.createCommand("NewPoiModel = PoiModel.add(DayType)");
		cp.createCommand("NewPoiModel.showTypesList()");
		
		cp.createCommand("NewPoiWithoutDay = NewPoiModel.remove('day_type')");
		cp.createCommand("NewPoiWithoutDay.showTypesList()");
		
		assertEquals(Haquna.modelMap.containsKey("NewPoiWithoutDay"), true);							
	}
	
	@Test
	public void testRemoveNewAddedAttr() {		
		setup();				
		
		cp.createCommand("PoiModel = new Model('/home/kamil/Pulpit/poi-recommender.hmr')");
		cp.createCommand("DayType = Model.getTypeByName('day_type')");
		cp.createCommand("Attr = Model.getAttributeByName('day')");
		
		cp.createCommand("NewPoiModel = PoiModel.add(DayType)");
		cp.createCommand("NewPoiModel.showTypesList()");
		
		cp.createCommand("AttrAdded = NewPoiModel.add(Attr)");
		cp.createCommand("AttrAdded.showAttributesList()");
		
		cp.createCommand("NewPoiWithoutDay = AttrAdded.remove('day')");
		cp.createCommand("NewPoiWithoutDay.showAttributesList()");
		
		
		cp.createCommand("NewPoiWithoutDay2 = NewPoiWithoutDay.remove('day_type')");
		cp.createCommand("NewPoiWithoutDay2.showTypesList()");
		assertEquals(Haquna.modelMap.containsKey("NewPoiWithoutDay2"), true);							
	}
}
