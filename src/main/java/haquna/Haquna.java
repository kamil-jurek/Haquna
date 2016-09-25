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
	public static Map<String, XTTModel> modelMap = new HashMap<String, XTTModel>();
	public static Map<String, Table> tableMap = new HashMap<String, Table>();
	public static Map<String, Attribute> attribiuteMap = new HashMap<String, Attribute>();
	public static Map<String, Type> typeMap = new HashMap<String, Type>();
	public static Map<String, Rule> ruleMap = new HashMap<String, Rule>();
	
	public static List<Completer> completers = new LinkedList<Completer>();
	
	public static void main(String[] args) throws IOException {
        try {
            ConsoleReader reader = new ConsoleReader();
            reader.setPrompt("prompt> ");
            
            /*Completer completer;
            completer = new ArgumentCompleter(
                    new StringsCompleter("xload", "show()", "showTablesList()"),
                    new FileNameCompleter(),
                    new StringsCompleter("foo21", "foo22", "foo23"));
            
            
            CandidateListCompletionHandler handler = new CandidateListCompletionHandler();
            handler.setPrintSpaceAfterFullCompletion(false);
            reader.setCompletionHandler(handler);
            reader.addCompleter(completer);*/
            /*List<Completer> completors = new LinkedList<Completer>();
            completors.add(
                    new AggregateCompleter(
                            new ArgumentCompleter(new StringsCompleter("xload('"), new FileNameCompleter(), new StringsCompleter("')"), new NullCompleter()),
                            new ArgumentCompleter(new NullCompleter(), new StringsCompleter("xload('"), new FileNameCompleter(), new StringsCompleter("')"), new NullCompleter()),
                            new ArgumentCompleter(new StringsCompleter(map.keySet()), new StringsCompleter("showTablesList()", "showAttributesList()"), new NullCompleter()),
                            new ArgumentCompleter(new NullCompleter(), new StringsCompleter(map.keySet()), new StringsCompleter("showTablesList()", "showAttributesList()"), new NullCompleter())
                            //new ArgumentCompleter(new StringsCompleter("show"), new StringsCompleter("aaa", "access-expression", "access-lists", "accounting", "adjancey"), new NullCompleter()),
                            //new ArgumentCompleter(new StringsCompleter("show"), new StringsCompleter("ip"), new StringsCompleter("access-lists", "accounting", "admission", "aliases", "arp"), new NullCompleter()),
                            //new ArgumentCompleter(new StringsCompleter("show"), new StringsCompleter("ip"), new StringsCompleter("interface"), new StringsCompleter("ATM", "Async", "BVI"), new NullCompleter())
                            )
                    );
		    for (Completer c : completors) {
		        reader.addCompleter(c);
		    }*/
            
            
            
            
           /* List<Completer> completers = new LinkedList<Completer>();
            completers.add(new FileNameCompleter());
            completers.add(new StringsCompleter(
                    "xload('",
                    ")",
                    "showTablesList()",
                    "showAttributesList()",
                    "showTypesList()",
                    "show()",
                    "getTableByName('",
                    "showVars()"
       		));
            
            AggregateCompleter aggComp = new AggregateCompleter(completers);
            ArgumentCompleter argComp = new ArgumentCompleter(new MyArgumentDelimiter(), aggComp);
            
            
            argComp.setStrict(false);
            CandidateListCompletionHandler handler = new CandidateListCompletionHandler();
            handler.setPrintSpaceAfterFullCompletion(false);
            
            reader.setCompletionHandler(handler);
            reader.addCompleter(argComp);*/
          
            /*ArgumentCompleter argComp = new ArgumentCompleter(new ArgumentCompleter.AbstractArgumentDelimiter() {

				@Override
				public boolean isDelimiterChar(CharSequence buffer, int pos) {
					char c = buffer.charAt(pos);
					return !Character.isLetter(c) && c != '_' && c != '=';
				}
			},aggComp);
            argComp.setStrict(false);
            reader.addCompleter(argComp);
            
            
            
            reader.addCompleter(
                    new AggregateCompleter(
                            new FileNameCompleter() ,
                            new StringsCompleter(
                                    "xload('/home/kamil/MyHeartDroid/MyHeart/src/res/threat-monitor.hmr')",
                                    "xload",
                                    ".showTablesList()"
                                    
                            )));*/
           
            //------------------
           /* List<Completer> completors = new LinkedList<Completer>();
            List<Completer> ac = new LinkedList<Completer>();
            ArgumentCompleter ag1 = new ArgumentCompleter(new MyArgumentDelimiter(), new StringsCompleter("ModelName"),new StringsCompleter(" ="), new StringsCompleter("xload('"), new FileNameCompleter(), new StringsCompleter("')"));
            //ag1.setStrict(false);
            
            ac.add( new ArgumentCompleter(new MyArgumentDelimiter(), new StringsCompleter("ModelName"), new StringsCompleter(" ="), new StringsCompleter("xload('"), new FileNameCompleter(), new StringsCompleter(")")));
            ac.add( new ArgumentCompleter(new MyArgumentDelimiter(), new StringsCompleter("ModelName"), new StringsCompleter("xload('"), new FileNameCompleter(), new StringsCompleter(")")));
            ac.add( new ArgumentCompleter(new MyArgumentDelimiter(), new StringsCompleter("ModelName"), new StringsCompleter("showTablesList()", "showAttributesList()", "showTypesLust()")));
            //ac.add( new ArgumentCompleter(new MyArgumentDelimiter(), new StringsCompleter("show"), new StringsCompleter("ip"), new StringsCompleter("interface"), new StringsCompleter("ATM", "Async", "BVI"), new NullCompleter()));
            
            completors.add(new AggregateCompleter(ac));
            completors.add(
                    new AggregateCompleter(
                            new ArgumentCompleter(new MyArgumentDelimiter(), new StringsCompleter("show"), new NullCompleter());,
                            new ArgumentCompleter(new MyArgumentDelimiter(), new StringsCompleter("aaa", "access-expression", "access-lists", "accounting", "adjancey"), new NullCompleter()),
                            new ArgumentCompleter(new MyArgumentDelimiter(), new StringsCompleter("show"), new StringsCompleter("ip"), new StringsCompleter("access-lists", "accounting", "admission", "aliases", "arp"), new NullCompleter()),
                            new ArgumentCompleter(new MyArgumentDelimiter(), new StringsCompleter("show"), new StringsCompleter("ip"), new StringsCompleter("interface"), new StringsCompleter("ATM", "Async", "BVI"), new NullCompleter())
                            )
                    );
		    for (Completer c : completors) {
		        reader.addCompleter(c);
		    }*/
            /*List<Completer> completers = new LinkedList<Completer>();
            ArgumentCompleter commandCompleter1 = new ArgumentCompleter(new MyArgumentDelimiter(), new StringsCompleter(""), new StringsCompleter("xload('"), new FileNameCompleter(), new StringsCompleter(")"));
            commandCompleter1.setStrict(false);
            completers.add(commandCompleter1);
            
            ArgumentCompleter commandCompleter2 = new ArgumentCompleter(new MyArgumentDelimiter(),  new StringsCompleter(""), new StringsCompleter("showTablesList()", "showAttributesList()", "showTypesLust()"));
            commandCompleter2.setStrict(false);
            completers.add(commandCompleter2);
        

            completers.add(new StringsCompleter("exit", "clear"));
            
            CandidateListCompletionHandler handler = new CandidateListCompletionHandler();
            handler.setPrintSpaceAfterFullCompletion(false);
            reader.setCompletionHandler(handler);
            
            Completer completer = new AggregateCompleter(completers);
            reader.addCompleter(completer);
            //------------------
*/            
            
            //////////////////
            /*StringsCompleter loadCommand = new StringsCompleter("load ", "create ");
            ArgumentCompleter fileOption = new ArgumentCompleter(
                    new StringsCompleter("--file"),
                    new FileNameCompleter()
            ); fileOption.setStrict(false);
            ArgumentCompleter formatOption = new ArgumentCompleter(
                    new StringsCompleter("--format"),
                    new StringsCompleter("xml", "csv", "json")
            ); formatOption.setStrict(false);

            AggregateCompleter optionsCompleter = new AggregateCompleter(fileOption, formatOption);
            //AggregateCompleter t = new AggregateCompleter(loadCommand, optionsCompleter);
            ArgumentCompleter topCompleter = new ArgumentCompleter(loadCommand, optionsCompleter);

            //ConsoleReader reader = new ConsoleReader();
            CandidateListCompletionHandler handler = new CandidateListCompletionHandler();
            handler.setPrintSpaceAfterFullCompletion(false);         
            reader.setCompletionHandler(handler);
            reader.addCompleter(topCompleter);
            ////////////////////
*/            
            String line;
            PrintWriter out = new PrintWriter(reader.getOutput());
            	
            CommandFactory cmdFactory = new CommandFactory();
            
            updateCompleter(reader);
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
		   ruleMap.containsKey(varName)) {
			
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
                "getAttribiuteById(",
                "getAttribiuteByName(",
                "getTypeById('",
                "getTypeByName('",
                "getRuleById('",
                "getRuleByName('",               
                "showVars()"
   		));
        completers.add(new StringsCompleter(modelMap.keySet()));
        completers.add(new StringsCompleter(tableMap.keySet()));
        completers.add(new StringsCompleter(attribiuteMap.keySet()));
        completers.add(new StringsCompleter(typeMap.keySet()));
        completers.add(new StringsCompleter(ruleMap.keySet()));
        
        AggregateCompleter aggComp = new AggregateCompleter(completers);
        ArgumentCompleter argComp = new ArgumentCompleter(new MyArgumentDelimiter(), aggComp);
        
        
        argComp.setStrict(false);
        CandidateListCompletionHandler handler = new CandidateListCompletionHandler();
        handler.setPrintSpaceAfterFullCompletion(false);
        
        reader.setCompletionHandler(handler);
        reader.addCompleter(argComp);
	}
}
	

