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

import heart.xtt.Table;
import heart.xtt.XTTModel;
import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.FileNameCompleter;
import jline.console.completer.StringsCompleter;

public class Haquna {
	public static Map<String, XTTModel> modelMap = new HashMap<String, XTTModel>();
	public static Map<String, Table> tableMap = new HashMap<String, Table>();
	
	public static void main(String[] args) throws IOException {
        try {
            ConsoleReader reader = new ConsoleReader();
            reader.setPrompt("prompt> ");
            
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
            
            
            
            
            List<Completer> completers = new LinkedList<Completer>();
            completers.add(new FileNameCompleter());
            completers.add(new StringsCompleter(
                    "xload('",
                    "')",
                    //".showTablesList()",
                    "showTablesList()",
                    //".showAttributesList()",
                    "showAttributesList()",
                    "showTypesList()",
                    "show()",
                    "getTableByName('"
       		));
            
            AggregateCompleter aggComp = new AggregateCompleter(completers);
            //ArgumentCompleter argComp = new ArgumentCompleter(aggComp);
            ArgumentCompleter argComp = new ArgumentCompleter(new ArgumentCompleter.AbstractArgumentDelimiter() {

				@Override
				public boolean isDelimiterChar(CharSequence buffer, int pos) {
					char c = buffer.charAt(pos);
					return !Character.isLetter(c) && c != '_' && c != '=';
				}
			},aggComp);
            /*
            reader.addCompleter(
                    new AggregateCompleter(
                            new FileNameCompleter() ,
                            new StringsCompleter(
                                    "xload('/home/kamil/MyHeartDroid/MyHeart/src/res/threat-monitor.hmr')",
                                    "xload",
                                    ".showTablesList()"
                                    
                            )));*/
            argComp.setStrict(false);
            reader.addCompleter(argComp);
            String line;
            PrintWriter out = new PrintWriter(reader.getOutput());
            	
            CommandFactory inv = new CommandFactory();
            
            
            while ((line = reader.readLine()) != null) {
                reader.addCompleter(argComp);
            	if(line.equals("pwd")) {
            		System.out.println("Working directory: " + System.getProperty("user.dir"));
            	}
            	
            	if(line.equals("ls")) {
            		Path dir = Paths.get(System.getProperty("user.dir"));
            		try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*")) {
            			for(Path file : stream) {
            				System.out.println(file.getFileName());
            			}
            		}
            	}
            	
                out.println("======>\"" + line + "\"");
                inv.createCommand(line);
                
                out.flush();

                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    break;
                }
                if (line.equalsIgnoreCase("cls")) {
                    reader.clearScreen();
                }
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
	
	public static boolean isVarUsed(String varName) {
		if(modelMap.containsKey(varName) ||
		   tableMap.containsKey(varName)) {
			
			return true;
		
		} else {
			return false;
		}		
	}
}
	

