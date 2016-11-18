package haquna;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import haquna.utils.MyLogger;

public class HaqunaMain {
	
	public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static void main(String[] args) throws IOException {
		setupLogger();
			
		HaqunaSingleton haquna = HaqunaSingleton.getInstance();
		
		checkScripts(haquna, args);
				
		haquna.loop();
	}
	
	public static void log(String sourceClass, String sourceMethod, String msg) {
		HaqunaMain.LOGGER.info(sourceClass + "\t" + sourceMethod + "\t" + msg);
	}
	
	private static void checkScripts(HaqunaSingleton h, String[] args) {
		List<String> scriptCmds = null;
		if(args.length == 1 && args[0].matches("^.*[.]hqn$")) {
			HaqunaScript hs = new HaqunaScript(args[0]);
			scriptCmds = hs.getScriptCommands(); 
		}
		
		if(scriptCmds != null) {
            for(String cmd : scriptCmds) {
            	h.executeCmd(cmd);              	            	
            }
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
