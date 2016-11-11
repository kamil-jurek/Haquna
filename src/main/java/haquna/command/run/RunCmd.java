package haquna.command.run;

import java.util.ArrayList;
import java.util.List;

import haquna.Haquna;
import haquna.HaqunaException;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
import heart.Configuration;
import heart.WorkingMemory;
import heart.inference.DataDrivenInference;
import heart.inference.FixedOrderInference;
import heart.inference.GoalDrivenInference;
import heart.inference.InferenceAlgorithm;
import heart.uncertainty.ConflictSetFireAll;
import heart.uncertainty.ConflictSetFirstWin;
import heart.uncertainty.ProbabilityEvaluator;
import heart.xtt.XTTModel;

public class RunCmd implements Command {		
	// run(Model, Wm, mode=gdi, ['tab1', 'tab2'])
	public static final String pattern = "^" + Haquna.varName + "(\\s*)=(\\s*)" + "run[(]" + "(\\s*)" +
											   Haquna.varName + "(\\s*)" + 	// Model
											   "(" + Haquna.varName + ")?" + // WorkingMemory
											   //"([mode=[ddi|gdi|foi])?" +
											   "([\\[]([']" + Haquna.varName + "['][,]?)+[\\]])?" + // tables names
																"(.*)[)](\\s*)";
		
	private String commandStr;
	private String varName;
	private String modelName;
	private String wmName = null;	
	private String[] tableNames = new String[]{};
	
	private String mode = "ddi";
	private String token = "false";
	private String uncertanity = "false";
	private String conflictStrategy = "first";
	
	private String[] commandParts;
	
	private XTTModel model;
	private Configuration.Builder confBuilder;
	private WorkingMemory wm;
	
	public RunCmd() {
		
	}
	
	public RunCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
				
		commandParts = this.commandStr.split("[(|)=|,|']");	
		this.varName = commandParts[0];
		this.modelName = commandParts[2];								
	}
	
	@Override
	public void execute() {		
		try {									
			model = HaqunaUtils.getModel(modelName);
			confBuilder = new Configuration.Builder();
			
			setupNotMandatoryArgs(commandParts);
			setupTableNames(commandParts);
			setupWorkingMemory();
			setupToken();
			setupUncertainty();
			setupConflictStrategy();
									
			switch(mode) {
	    	  case "foi": {
	    		Configuration cs = confBuilder.build();
	    		new FixedOrderInference(wm, model, cs).start(new InferenceAlgorithm.TableParameters(tableNames));
	    			    		
	    		break;			    		
	    	  }
	    	 
	    	  case "ddi": {
	    		Configuration cs = confBuilder.build();
	    		new DataDrivenInference(wm, model, cs).start(new InferenceAlgorithm.TableParameters(tableNames));
	    			    		
	    		break;			    		
	    	  }
	    	
	    	  case "gdi": {
	    		Configuration cs = confBuilder.build();
	    		new GoalDrivenInference(wm, model, cs).start(new InferenceAlgorithm.TableParameters(tableNames));
	    			    		
	    		break;
	    	  }
			}
						  
		    if(wmName == null) {
		    	Haquna.wmMap.put(varName, wm);
		    }
		    
		    Haquna.wasSucces = true;
			
		} catch(HaqunaException e) {
			e.printStackTrace();
			HaqunaUtils.printRed(e.getMessage());
			
			return;
		
		} catch (Exception e) {
			HaqunaUtils.printRed(e.getMessage());
			e.printStackTrace();
			
			return;
		}		
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new RunCmd(cmdStr);
	}
	
	private void setupWorkingMemory() throws HaqunaException {
		if(wmName == null) {
			wm = new WorkingMemory();
			wm.registerAllAttributes(model);
			HaqunaUtils.checkVarName(varName);
		
		} else {
			wm = HaqunaUtils.getWorkingMemory(wmName);
			confBuilder.setInitialState(wm.getCurrentState());
		}
	}
	
	private void setupToken() {
		switch(token) {
		  case "true": {
			confBuilder.setTokenPassingEnabled(true);
			break;
		  }
		  case "false": {
			confBuilder.setTokenPassingEnabled(false);
			break;
		  }
		}
	}
	
	private void setupUncertainty() {
		switch(uncertanity) {
		  case "true": {
			confBuilder.setUte(new ProbabilityEvaluator());
			break;
		  }
		  case "false": {
			confBuilder.setUte(null);
			break;
		  }
		}
	}
	
	private void setupConflictStrategy() {
		switch(conflictStrategy) {
		  case "first": {
			confBuilder.setCsr(new ConflictSetFirstWin());
			break;
		  }
		  case "last": {
			confBuilder.setCsr(new ConflictSetFirstWin());
			break;
		  }
		  case "all": {
			confBuilder.setCsr(new ConflictSetFireAll());
			break;
		  }
		}
	}
	
	private void setupTableNames(String[] commandParts){
		int tabBegin = 0;
		int tabEnd = 0;
		
		String commandParts2[] = commandParts;
		
		List<String> list = new ArrayList<String>();

	    for(String s : commandParts2) {
	       if(s != null && s.length() > 0) {
	          list.add(s);
	       }
	    }
	    commandParts2 = list.toArray(new String[list.size()]);
		
		for(int i = 0; i < commandParts2.length; i++) {
			if(commandParts2[i].equals("[")) {
				tabBegin = i;
			}
			
			if(commandParts2[i].equals("]")) {
				tabEnd = i;
			}
		}
		try {			
			tableNames = new String[tabEnd - (tabBegin+1)];
			for(int i = tabBegin+1; i < tabEnd; i++) {
				tableNames[i-(tabBegin+1)] = commandParts2[i];
				
			}
		} catch(Exception e) {
			//e.printStackTrace();
			tableNames = new String[]{};
			System.out.println("Tables names were not initialized");
			//throw new Exception("Table names problem. Should be ['tabName1', 'tabName2']");
		}
	}
	
	private void setupNotMandatoryArgs(String[] commandParts) throws Exception{
		
		if(commandParts.length >= 3 && commandParts[3].matches(Haquna.varName)) {
			if(Haquna.wmMap.containsKey(commandParts[3])) {
				this.wmName = commandParts[3];
			
			} else {
				  throw new HaqunaException("No " + commandParts[3] + " WorkingMemory object in memory");
			}
		}
						
		for(int i = 0; i < commandParts.length-1; i++) {
			String nextArgument = commandParts[i+1];
			switch(commandParts[i]) {
			case "mode" : {
				if(nextArgument.matches("gdi|foi|ddi")) {					
					this.mode = nextArgument;
				
				} else {
					throw new Exception("Incorrect mode arg.");
				}
				break;
			}
			
			case "token": {
				if(nextArgument.matches("true|false")) {
					this.token = nextArgument;
				
				}else {
					throw new Exception("Incorrect token arg.");
				}
				
				break;
			}
			
			case "uncertanity": {
				if(nextArgument.matches("true|false")) {
					this.uncertanity = nextArgument;
				
				} else {
					throw new Exception("Incorrect uncertanity arg.");
				}
				
				break;
			}
			
			case "conflict_strategy": {
				if(nextArgument.matches("first|last|all")) {
					this.conflictStrategy = commandParts[i+1];
				
				} else {
					throw new Exception("Incorrect conflict_strategy arg");
				}
				
				break;
			}
			
			}
		}
		
	}

}
	
