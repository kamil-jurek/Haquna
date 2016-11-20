package haquna;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Lists;

import haquna.utils.MyLogger;

public class HaqunaMain {
	
	public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static boolean startConsole = true;


	public static void main(String[] args) throws IOException {
		setupLogger();
			
		Haquna haquna = Haquna.getInstance();

		checkCommandline(haquna, args);
		checkScripts(haquna, args);

		if(startConsole == true) {
			haquna.loop();
		}
	}
	
	public static void log(String sourceClass, String sourceMethod, String msg) {
		HaqunaMain.LOGGER.info(sourceClass + "\t" + sourceMethod + "\t" + msg);
	}
	
	private static void checkScripts(Haquna h, String[] args) {
		if(args.length == 1 && args[0].matches("^.*[.]hqn$")) {
			HaqunaScript hs = new HaqunaScript(args[0]);
			hs.executeScriptCmds(h);
			startConsole = false;
		}
	}

	private static void checkCommandline(Haquna h, String[] args) {
		if(args != null && (Arrays.asList(args).contains("--model") ||
							Arrays.asList(args).contains("--console") ||
							Arrays.asList(args).contains("--help"))) {
			HaqunaJCommander.commandline(args, h);
		}
    }

	private static void setupLogger() {
		try {
            MyLogger.setup();
            HaqunaMain.LOGGER.setLevel(Level.INFO);
            
		} catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
	    }
	}
	

	
}
