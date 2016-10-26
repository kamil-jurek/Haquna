package haquna;


import static org.junit.Assert.*;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;

public class NewTableCmdTest {
	public static CommandFactory cp = new CommandFactory();
	

	public static void setup() {
		HaqunaUtils.clearMemory();
		cp.createCommand("Model = xload('threat-monitor.hmr')");
		
	}
		
	@Test
	public void testAddCmd1() {		
		setup();				
		
		cp.createCommand("Typ = new Type(xtype [name: weather_type,base: symbolic,desc: 'Wheater type',domain: [sunny,rainy,cloudy]].)");
		cp.createCommand("Att = new Attribute(xattr [name: weather,abbrev: weat1,class: simple,type: weather_type,comm: inter].)");
		cp.createCommand("Tab = new Table(xschm 'Recommendations': [weather,user_profile,activity] ==> [poi].)");
		cp.createCommand("Rul =new Rule(xrule 'Recommendations'/1:[weather in [sunny,cloudy],user_profile sim [eating],activity eq any]==>[poi set outdor_eating].)");
		cp.createCommand("printVars()");
				
		assertEquals(Haquna.tableBuMap.containsKey("Tab"), true);
		System.out.println("=======================================");			
	}	

}
