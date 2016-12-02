package haquna;

import static haquna.TestUtils.getErrorStringFormat;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import haquna.command.get.GetAttributeByNameCmd;
import haquna.utils.HaqunaUtils;

public class GetAttributeByNameCmdTest {
	public static void setup() {
		HaqunaUtils.clearMemory();
		TestUtils.createAndExecCmd("Model = new Model('threat-monitor.hmr')");
	}

	@Test
	public void testGetAttributeByNameCmd() {
		setup();
		String cmdStr = "Att = Model.getAttributeByName('location')";
		TestUtils.createAndExecCmd(cmdStr);

		assertEquals(Haquna.attrMap.containsKey("Att"), true);
		assertEquals(Haquna.attrMap.get("Att").getAttributeName(), "location");
	}

	@Test
	public void testGetAttributeByNameCmdNoModel() {
		setup();

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		String cmd = "Att = NoExistingModel.getAttributeByName('location')";
		GetAttributeByNameCmd sal = (GetAttributeByNameCmd) TestUtils.createCmd(cmd);
		sal.execute();
		String expectedOutput = getErrorStringFormat("No '" + sal.getModelName() + "' XTTModel object in memory");

		assertEquals(Haquna.attrMap.containsKey("Att"), false);
		assertEquals(outContent.toString(), expectedOutput);
	}

	@Test
	public void testGetAttributeByNameCmdNoName() {
		setup();

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));


		String cmd = "Att = Model.getAttributeByName('always')";
		GetAttributeByNameCmd sal = (GetAttributeByNameCmd) TestUtils.createCmd(cmd);
		sal.execute();
		String expectedOutput = getErrorStringFormat("No attribute with '" + sal.getAttribiuteName() + "' name in '" + sal.getModelName() + "' model");

		assertEquals(Haquna.attrMap.containsKey("Att"), false);
		assertEquals(outContent.toString(), expectedOutput);
	}

	/*@Test
	public void testGetAttributeByNameCmdNoVar() {
		setup();

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		TestUtils.createAndExecCmd("Att = Model.getAttributeByName('location')");

		String cmd = "Att = Model.getAttributeByName('integer')";
		GetAttributeByNameCmd sal = (GetAttributeByNameCmd) TestUtils.createCmd(cmd);
		sal.execute();
		String expectedOutput = getErrorStringFormat("Variable name '" + sal.getVarName() + "' already in use");

		assertEquals(Haquna.attrMap.containsKey("Att"), true);
		assertEquals(outContent.toString(), expectedOutput);
	}*/
}
