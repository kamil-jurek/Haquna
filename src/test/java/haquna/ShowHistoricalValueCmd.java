package haquna;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.utils.HaqunaUtils;

public class ShowHistoricalValueCmd {
public static CommandFactory cp = new CommandFactory();
	
	public static void setup() {
		try {
			HaqunaUtils.clearMemory();
			cp.createCommand("Model = new Model('threat-monitor.hmr')");		
			cp.createCommand("Wm = new WorkingMemory(Model)");
			cp.createCommand("Wm.setValueOf('hour','1')");
			TimeUnit.SECONDS.sleep(1);
			cp.createCommand("Wm.setValueOf('hour','2')");
			TimeUnit.SECONDS.sleep(1);
			cp.createCommand("Wm.setValueOf('hour','3')");
			TimeUnit.SECONDS.sleep(1);
			cp.createCommand("Wm.setValueOf('hour','4')");
			TimeUnit.SECONDS.sleep(1);
			cp.createCommand("Wm.setValueOf('hour','5')");
			TimeUnit.SECONDS.sleep(1);
			cp.createCommand("Wm.setValueOf('hour','6')");
			TimeUnit.SECONDS.sleep(1);
			cp.createCommand("Wm.setValueOf('hour','7')");
			TimeUnit.SECONDS.sleep(1);
			cp.createCommand("Wm.setValueOf('hour','8')");
			TimeUnit.SECONDS.sleep(1);
			cp.createCommand("Wm.setValueOf('hour','9')");
			TimeUnit.SECONDS.sleep(1);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	@Test
	public void testAddCmd1() {		
		setup();				
		
		cp.createCommand("Wm.showHistoricalValueOf('hour','5')");
		//cp.createCommand("Wm.showCurrentState()");
		
		cp.createCommand("Wm.showHistoricalValueOf('hour','3')");
		//cp.createCommand("Wm.showCurrentState()");
		
		cp.createCommand("Wm.showHistoricalValueOf('hour','1')");
		//cp.createCommand("Wm.showCurrentState()");
		//assertEquals(Haquna.modelMap.containsKey("M4"), true);
		System.out.println("=======================================");			
	}
}
