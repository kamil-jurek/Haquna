package haquna;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import haquna.command.CommandFactory;
import heart.WorkingMemory;
import heart.xtt.Attribute;
import heart.xtt.Rule;
import heart.xtt.Table;
import heart.xtt.Type;
import heart.xtt.XTTModel;
import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.CandidateListCompletionHandler;
import jline.console.completer.Completer;
import jline.console.completer.FileNameCompleter;
import jline.console.completer.StringsCompleter;

public class Haquna {
	public static final String varName = "[A-Z]([A-Z|a-z|0-9|_])*";
	
	public static Map<String, XTTModel> modelMap = new HashMap<String, XTTModel>();
	public static Map<String, Table> tableMap = new HashMap<String, Table>();
	public static Map<String, Attribute> attribiuteMap = new HashMap<String, Attribute>();
	public static Map<String, Type> typeMap = new HashMap<String, Type>();
	public static Map<String, Rule> ruleMap = new HashMap<String, Rule>();
	public static Map<String, String> callbackMap = new HashMap<String, String>();
	public static Map<String, WorkingMemory> wmMap = new HashMap<String, WorkingMemory>();
	
	public static List<Completer> completers = new LinkedList<Completer>();
	
	public static void main(String[] args) throws IOException {
        try {
            ConsoleReader reader = new ConsoleReader();
            reader.setPrompt("prompt> ");
                    
            String line;
            PrintWriter out = new PrintWriter(reader.getOutput());
            	
            CommandFactory cmdFactory = new CommandFactory();
            
            updateCompleter(reader);
            //myCompleter(reader);
            while ((line = reader.readLine()) != null) {
            	                 	
                out.println("======>\"" + line + "\"");
                cmdFactory.createCommand(line);
                
                out.flush();
                
                if(line.equalsIgnoreCase("pwd")) {
            		out.println("Working directory: " + System.getProperty("user.dir"));
            	}          	
            	if(line.equals("ls")) {
            		Path dir = Paths.get(System.getProperty("user.dir"));
            		try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*")) {
            			for(Path file : stream) {
            				out.println(file.getFileName());
            			}
            		}
            	}
                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    break;
                }
                if (line.equalsIgnoreCase("cls")) {
                    reader.clearScreen();
                }
                
                updateCompleter(reader);
                //myCompleter(reader);
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
	
	public static boolean isVarUsed(String varName) {
		if(modelMap.containsKey(varName) ||
		   tableMap.containsKey(varName) ||
		   attribiuteMap.containsKey(varName) || 
		   typeMap.containsKey(varName) ||
		   ruleMap.containsKey(varName) ||
		   callbackMap.containsKey(varName) ||
		   wmMap.containsKey(varName)) {
			
			return true;
		
		} else {
			return false;
		}		
	}
	
	public static void updateCompleter(ConsoleReader reader) {
		for(Completer c : reader.getCompleters()){
			reader.removeCompleter(c);
		}
		
		completers = new LinkedList<Completer>();
        completers.add(new FileNameCompleter());
        completers.add(new StringsCompleter(
                "xload('",
                ")",
                "showTablesList()",
                "showAttributesList()",
                "showTypesList()",
                "showRulesList()",
                "show()",
                "getTableByName('",
                "getTableById('",
                "getAttributeById(",
                "getAttributeByName(",
                "getTypeById('",
                "getTypeByName('",
                "getRuleById('",
                "getRuleByName('",
                "getType()",
                "getCallback()",
                "showVars()",
                "run(",
                "showCurrentState()",
                "showValueOf('",
                "new",
                "WorkingMemory(",
                "setValueOf('",
                "determineValues(",
                "add(",
                "Type(",
                "new",
                "xsave('"
                
                
                
   		));
        completers.add(new StringsCompleter(modelMap.keySet()));
        completers.add(new StringsCompleter(tableMap.keySet()));
        completers.add(new StringsCompleter(attribiuteMap.keySet()));
        completers.add(new StringsCompleter(typeMap.keySet()));
        completers.add(new StringsCompleter(ruleMap.keySet()));
        completers.add(new StringsCompleter(callbackMap.keySet()));
        completers.add(new StringsCompleter(wmMap.keySet()));
        
        AggregateCompleter aggComp = new AggregateCompleter(completers);
        ArgumentCompleter argComp = new ArgumentCompleter(new MyArgumentDelimiter(), aggComp);
                
        argComp.setStrict(false);
        CandidateListCompletionHandler handler = new CandidateListCompletionHandler();
        handler.setPrintSpaceAfterFullCompletion(false);
        
        reader.setCompletionHandler(handler);
        reader.addCompleter(argComp);
	}
	
	public static void myCompleter(ConsoleReader reader) {
		for(Completer c : reader.getCompleters()){
			reader.removeCompleter(c);
		}
		
		completers = new LinkedList<Completer>();
       /* completers.add(new FileNameCompleter());
        completers.add(new StringsCompleter(
                "xload('",
                ")",
                "showTablesList()",
                "showAttributesList()",
                "showTypesList()",
                "showRulesList()",
                "show()",
                "getTableByName('",
                "getTableById('",
                "getAttributeById(",
                "getAttributeByName(",
                "getTypeById('",
                "getTypeByName('",
                "getRuleById('",
                "getRuleByName('",
                "getType()",
                "getCallback()",
                "showVars()",
                "run(",
                "showCurrentState()",
                "showValueOf('",
                "new",
                "WorkingMemory(",
                "setValueOf('",
                "determineValues(",
                "add(",
                "Type(",
                "new",
                "xsave('"
                
                
                
   		));
        completers.add(new StringsCompleter(modelMap.keySet()));
        completers.add(new StringsCompleter(tableMap.keySet()));
        completers.add(new StringsCompleter(attribiuteMap.keySet()));
        completers.add(new StringsCompleter(typeMap.keySet()));
        completers.add(new StringsCompleter(ruleMap.keySet()));
        completers.add(new StringsCompleter(callbackMap.keySet()));
        completers.add(new StringsCompleter(wmMap.keySet()));*/
        completers.add(new HaqunaCompleter());
        AggregateCompleter aggComp = new AggregateCompleter(completers);
        //ArgumentCompleter argComp = new ArgumentCompleter(new MyArgumentDelimiter(), aggComp);
                
        //argComp.setStrict(false);
        CandidateListCompletionHandler handler = new CandidateListCompletionHandler();
        handler.setPrintSpaceAfterFullCompletion(false);
        
        reader.setCompletionHandler(handler);
        reader.addCompleter(aggComp);
	}
}
	

