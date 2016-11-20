package haquna;

import static haquna.TestUtils.getErrorStringFormat;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.CommandFactory;
import haquna.command.show.ShowTypesListCmd;
import haquna.utils.HaqunaUtils;

public class ShowTypesListCmdTest {
	public static void setup() {
		HaqunaUtils.clearMemory();
		TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");
	}
	
	@Test
	public void testShowTypesListCmd() {
		setup();
		
		String cmd = "Model.showTypesList()";
		ShowTypesListCmd sal = (ShowTypesListCmd) TestUtils.createCmd(cmd);
		sal.execute();

		assertEquals(sal.getVarName(), "Model");
	}
	
	@Test
	public void testShowTypesListCmdNoModel() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		String cmd = "NoExistingModel.showTypesList()";
		ShowTypesListCmd sal = (ShowTypesListCmd) TestUtils.createCmd(cmd);
		sal.execute();

		String expectedOutput = getErrorStringFormat("No '" + sal.getVarName() + "' XTTModel object in memory");
		
		assertEquals(outContent.toString(), expectedOutput);
				
	}
	

}
