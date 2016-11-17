package haquna;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import haquna.command.CommandFactory;
import haquna.completer.CompleterMenager;
import haquna.utils.HaqunaUtils;
import haquna.utils.MyLogger;
import heart.WorkingMemory;
import heart.xtt.Attribute;
import heart.xtt.Rule;
import heart.xtt.Table;
import heart.xtt.Type;
import heart.xtt.XTTModel;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;


public class Haquna {
	public static final String varName = "[a-zA-Z_$]([a-zA-Z_$0-9])*";
	public static final String attrNamePattern = "[a-z|_]+";
	public static final String attrValuePattern = "[a-z|A-Z|0-9|_|.]+[/]?[0-9]*";
	
	public static boolean wasSucces = false;
	
	public static Map<String, XTTModel> modelMap = new HashMap<String, XTTModel>();
	public static Map<String, Table> tableMap = new HashMap<String, Table>();
	public static Map<String, Attribute> attrMap = new HashMap<String, Attribute>();
	public static Map<String, Type> typeMap = new HashMap<String, Type>();
	public static Map<String, Rule> ruleMap = new HashMap<String, Rule>();
	public static Map<String, String> callbackMap = new HashMap<String, String>();
	public static Map<String, WorkingMemory> wmMap = new HashMap<String, WorkingMemory>();
	
	public static Map<String, Type.Builder> typeBuilderMap = new HashMap<String, Type.Builder>();
	public static Map<String, Attribute.Builder> attrBuilderMap = new HashMap<String, Attribute.Builder>();
	public static Map<String, Table.Builder> tableBuilderMap = new HashMap<String, Table.Builder>();
	public static Map<String, Rule.Builder> ruleBuilderMap = new HashMap<String, Rule.Builder>();
	
	public static List<Completer> completers = new LinkedList<Completer>();
	public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	
	
	public static void main(String[] args) throws IOException {
		try {
            MyLogger.setup();
            Haquna.LOGGER.setLevel(Level.INFO);
            
		} catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
	    }
		
		List<String> scriptCmds = null;
		if(args.length == 1 && args[0].matches("^.*[.]hqn$")) {
			HaqunaScript hs = new HaqunaScript(args[0]);
			scriptCmds = hs.getScriptCommands(); 
		}
		
		
		try {
            ConsoleReader reader = new ConsoleReader();
            reader.setPrompt("\u001B[35;1m" + "HaQuNa> " + "\u001B[0m");
                    
            String line;
            PrintWriter out = new PrintWriter(reader.getOutput());
            	
            CommandFactory cmdFactory = new CommandFactory();
            new CompleterMenager().setupCompleter(reader);
            
            if(scriptCmds != null) {
	            for(String cmd : scriptCmds) {
	            	cmdFactory.createCommand(cmd);
	            	if(wasSucces) {
	                	HaqunaUtils.printGreen(cmd);               
	                } else {
	                	HaqunaUtils.printRed(cmd);
	                }
	                wasSucces = false;
	                out.flush();	                               	            	
	            }
            }
            reader.setBellEnabled(true);
            while ((line = reader.readLine()) != null) {          	           	                            
                cmdFactory.createCommand(line);
                
                if(wasSucces) {
                	HaqunaUtils.printGreen(line);               
                } else {
                	HaqunaUtils.printRed(line);
                	reader.beep();
                }
                wasSucces = false;
                out.flush();
                               
                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    break;
                }
                if (line.equalsIgnoreCase("cls")) {
                    reader.clearScreen();
                }
                            
                //myCompleter(reader);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
	
	public static boolean isVarUsed(String varName) {
		if(modelMap.containsKey(varName) ||
		   tableMap.containsKey(varName) ||
		   attrMap.containsKey(varName) || 
		   typeMap.containsKey(varName) ||
		   ruleMap.containsKey(varName) ||
		   callbackMap.containsKey(varName) ||
		   wmMap.containsKey(varName) ||
		   tableBuilderMap.containsKey(varName) ||
		   attrBuilderMap.containsKey(varName) || 
		   typeBuilderMap.containsKey(varName) ||
		   ruleBuilderMap.containsKey(varName)) {
			
			return true;
		
		} else {
			return false;
		}		
	}
	
	public static void log(String sourceClass, String sourceMethod, String msg) {
		Haquna.LOGGER.info(sourceClass + "\t" + sourceMethod + "\t" + msg);
	}
}
	

