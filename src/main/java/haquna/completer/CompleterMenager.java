package haquna.completer;

import java.util.LinkedList;

import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.CandidateListCompletionHandler;
import jline.console.completer.Completer;
import jline.console.completer.FileNameCompleter;
import jline.console.completer.StringsCompleter;

public class CompleterMenager {
	
	public void setupCompleter(ConsoleReader reader) {
		LinkedList<Completer> completers = new LinkedList<Completer>();
		
		for(Completer c : reader.getCompleters()){
			reader.removeCompleter(c);
		}
		
		// Model.showTableList()
		completers = new LinkedList<Completer>();
        completers.add(new VarNamesCompleter2());
        completers.add(new FunctionNamesCompleter2());
		HaqunaCompleter argComp1 = new HaqunaCompleter(new HaqunaDelimiter('.'), completers);
        
		// Type = Attr.getType()
		completers = new LinkedList<Completer>();
        completers.add(new UniversalCompleter());
        completers.add(new VarNamesCompleter2());
        completers.add(new FunctionNamesCompleter2());
        HaqunaCompleter argComp2 = new HaqunaCompleter(new HaqunaDelimiter('='), completers);
        
    	// Attr = Model.getAttributeByName('day')
        completers = new LinkedList<Completer>();
        completers.add(new UniversalCompleter());
        completers.add(new VarNamesCompleter2());
        completers.add(new FunctionNamesCompleter2());
        completers.add(new ParametersCompleter());
        HaqunaCompleter argComp3 = new HaqunaCompleter(new HaqunaDelimiter('=', '.','\'','('), completers);
        
        // Model = xload('file.hmr')
        completers = new LinkedList<Completer>();
        completers.add(new UniversalCompleter());
        completers.add(new StringsCompleter("new"));
        completers.add(new StringsCompleter("Model"));
        completers.add(new FileNameCompleter());
        HaqunaCompleter argComp4 = new HaqunaCompleter(new HaqunaDelimiter('=','(','\''), completers);
       
        // Wm = new WorkingMemory(Model) 
        completers = new LinkedList<Completer>();
        completers.add(new UniversalCompleter());
        completers.add(new StringsCompleter("new"));
        completers.add(new FunctionNamesCompleter2());
        completers.add(new ParametersCompleter());       
        HaqunaCompleter argComp5 = new HaqunaCompleter(new HaqunaDelimiter('=', '('), completers);
        
        // Wm.setValueOf('attr')
        completers = new LinkedList<Completer>();
        completers.add(new VarNamesCompleter2());
        completers.add(new FunctionNamesCompleter2());
        completers.add(new ParametersCompleter());         
		HaqunaCompleter argComp6 = new HaqunaCompleter(new HaqunaDelimiter('.', '(','\''), completers);
        
		  
        completers = new LinkedList<Completer>();
        completers.add(new ModelNamesCompleter());
        completers.add(new StringsCompleter("run"));
        completers.add(new RunCompleter2());
        completers.add(new RunCompleter2());
        completers.add(new RunCompleter2());
        completers.add(new RunCompleter2());
        completers.add(new RunCompleter2());
        completers.add(new RunCompleter2());
        completers.add(new RunCompleter2());
        completers.add(new RunCompleter2());
        completers.add(new RunCompleter2());
        HaqunaCompleter argComp7 = new HaqunaCompleter(new HaqunaDelimiter(',','=','.', '(','\''), completers);
        
        completers = new LinkedList<Completer>();
        completers.add(new StringsCompleter("ls", "pwd", "printVars()", "cls", "clearMemory()"));
        HaqunaCompleter argComp8 = new HaqunaCompleter(new HaqunaDelimiter(), completers);
		
        argComp1.setStrict(true);
        argComp2.setStrict(true);
        argComp3.setStrict(true);
        argComp4.setStrict(true);
        argComp5.setStrict(true);
        argComp6.setStrict(true);
        argComp7.setStrict(true);
        argComp8.setStrict(true);
        
        CandidateListCompletionHandler handler = new CandidateListCompletionHandler();        
        handler.setPrintSpaceAfterFullCompletion(false);
        
        AggregateCompleter aggComp = new AggregateCompleter(argComp1, 
        								argComp2, argComp3, argComp4, 
        								argComp5, argComp6, argComp7,
        								argComp8);
        reader.setCompletionHandler(handler);
        reader.addCompleter(aggComp);
	}
}
