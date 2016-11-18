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
	
	public static void main(String[] args) throws IOException {
		setupLogger();
			
		HaqunaSingleton haquna = HaqunaSingleton.getInstance();
		
		if(args != null && Arrays.asList(args).contains("-model")) {
			commandline(args);
		
		} else {
			checkScripts(haquna, args);					
			haquna.loop();
		}
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
	
	public static class JCommanderConfiguration {
		@Parameter
	    public List<String> parameters = Lists.newArrayList();
		
		@Parameter(
			names = {"-model"},
            description = "<path to the HMR file>",
            required = true
        )
        private String modelPath;
        
        @Parameter(
            names = {"-tables"},
            description = "Order of tables used in the inference",
            required = true
        )
        private String tables = "[]";
        
        @Parameter(
            names = {"-inference"},
            description = "inference",
            required = true
        )        
        private String inference = "ddi";
        
        @Parameter(
            names = {"-state"},
            description = "Initial values of attributes"
        )
        private String attributes = null;
        
        @Parameter(
            names = {"-conflist_strategy"},
            description = "conflict_strategy"
        )        
        private String conflictStrategy = "all";
        
        @Parameter(
            names = {"--help"},
            help = true,
            description = "Display help (this message)"
        )
        private boolean help;

        public JCommanderConfiguration() {
        }
    }
	
	private static void commandline(String[] args) {
	 	JCommanderConfiguration jcc = new HaqunaMain.JCommanderConfiguration();
        JCommander jcomm = new JCommander(jcc, args);
        HaqunaSingleton haquna = HaqunaSingleton.getInstance();
        ArrayList<String> cmds = new ArrayList<String>();
        
        if(jcc.help) {
            jcomm.usage();
        
        } else {
        	String cmd;
            cmd = "Mod = new Model('" + jcc.modelPath + "')";
            cmds.add(cmd);

            cmd ="Wm = new WorkingMemory(Mod)";
            cmds.add(cmd);
           
            if(jcc.attributes != null) {
	            String attrs = jcc.attributes;
	            attrs = attrs.replace("[", "");
	            attrs = attrs.replace("]", "");
	            attrs = attrs.replace(" ", "");
                 
	            String[] splited = attrs.split("[=|,]");
            	for(int i = 0; i < splited.length; i=i+2 ) {
            		String argName = splited[i];
                    String argVal = splited[i+1];
                    
                    cmd = "Wm.setValueOf('" + argName + "','" + argVal + "')";   
                    cmds.add(cmd);
            	}
            }
            
            String tabs = jcc.tables;
            tabs = tabs.replace("[", "['");
            tabs = tabs.replace("]", "']");
            tabs = tabs.replace(",", "','");

            
            cmd = "Mod.run(Wm, mode=" + jcc.inference + ", " + "conflict_strategy=" + jcc.conflictStrategy+ ", " + tabs + ")";
            cmds.add(cmd);
            
            cmd = "Wm.showCurrentState()";
            cmds.add(cmd);
            
            
            for(String c : cmds) {
            	haquna.executeCmd(c);
            	
            }
        }
	}
	
}
