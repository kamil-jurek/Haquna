package haquna.command.run;

import haquna.Haquna;
import haquna.command.Command;
import heart.Configuration;
import heart.State;
import heart.StateElement;
import heart.WorkingMemory;
import heart.exceptions.BuilderException;
import heart.inference.DataDrivenInference;
import heart.inference.FixedOrderInference;
import heart.inference.GoalDrivenInference;
import heart.inference.InferenceAlgorithm;
import heart.uncertainty.ConflictSetFireAll;
import heart.uncertainty.ConflictSetFirstWin;
import heart.uncertainty.ProbabilityEvaluator;
import heart.xtt.XTTModel;

public class RunAlterCmd implements Command {		
	// run(Model, Wm, mode=gdi, ['tab1', 'tab2'])
	public static final String pattern = "^" + Haquna.varName + "(\\s*)=(\\s*)" + "run[(]" + "(\\s*)" +
											   Haquna.varName + "(\\s*)" + 	// Model
																"(.*)[)](\\s*)";
		
	private String commandStr;
	private String varName;
	private String modelName;
	private String wmName = null;	
	private String[] tableNames;
	
	private String mode = "ddi";
	private String token = "false";
	private String uncertanity = "false";
	private String conflictStrategy = "first";
	
	private String[] commandParts;
	
	public RunAlterCmd() {
		
	}
	
	public RunAlterCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
				
		commandParts = this.commandStr.split("[(|)=|,|']");	
		this.varName = commandParts[0];
		this.modelName = commandParts[2];
		//this.wmName = commandParts[2];				
		//this.mode = commandParts[4];
		
		try {
			//setupTableNames(commandParts);				
			//setupNotMandatoryArgs(commandParts);
		
		} catch(Exception e) {
			e.printStackTrace();
			
		}
						
	}
	
	@Override
	public void execute() {		
		try {
			setupNotMandatoryArgs(commandParts);
			setupTableNames(commandParts);
			
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		if(!Haquna.isVarUsed(varName)) {
		  if(Haquna.modelMap.containsKey(modelName)) {
			XTTModel model = Haquna.modelMap.get(modelName);
			Configuration.Builder confBuilder = new Configuration.Builder();
			WorkingMemory wm;
			
			if(wmName == null) {
				wm = new WorkingMemory();
				wm.registerAllAttributes(model);
			
			} else {
				wm = Haquna.wmMap.get(wmName);
				confBuilder.setInitialState(wm.getCurrentState());
			}
			
						 
			
				
				//confBuilder.setCsr(new ConflictSetFirstWin());
			
				
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
				
			switch(uncertanity) {
				case "true": {
					confBuilder.setUte(new ProbabilityEvaluator());
					break;
				}
				case "false": {
					//confBuilder.setTokenPassingEnabled(false);
					break;
				}
			}
				
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
				
			try {
			    switch(mode) {
			    	case "foi": {
			    		Configuration cs = confBuilder.build();
			    		new FixedOrderInference(wm, model, cs).start(new InferenceAlgorithm.TableParameters(tableNames));
			    		
			    		System.out.println("Printing current state (after inference FOI)");				        
			    		State current = wm.getCurrentState(model);
				        for(StateElement se : current){
				            System.out.println("Attribute "+se.getAttributeName()+" = "+se.getValue());
				        }

				        System.out.println("\n\n");
			    		
			    		break;			    		
			    	}
			    	 
			    	case "ddi": {
			    		Configuration cs = confBuilder.build();
			    		new DataDrivenInference(wm, model, cs).start(new InferenceAlgorithm.TableParameters(tableNames));
			    		
			    		System.out.println("Printing current state (after inference DDI)");				        
			    		State current = wm.getCurrentState(model);
				        for(StateElement se : current){
				            System.out.println("Attribute "+se.getAttributeName()+" = "+se.getValue());
				        }

				        System.out.println("\n\n");
			    		
			    		break;			    		
			    	}
			    	
			    	case "gdi": {
			    		Configuration cs = confBuilder.build();
			    		new GoalDrivenInference(wm, model, cs).start(new InferenceAlgorithm.TableParameters(tableNames));
			    		
			    		System.out.println("Printing current state (after inference GDI)");				        
			    		State current = wm.getCurrentState(model);
				        for(StateElement se : current){
				            System.out.println("Attribute "+se.getAttributeName()+" = "+se.getValue());
				        }

				        System.out.println("\n\n");
			    		
			    		break;
			    	}
			    }
			      
			    if(wmName == null) {
			    	Haquna.wmMap.put(varName, wm);
			    }
			    
				} catch (BuilderException e) {
					e.printStackTrace();
					return;
				}
						  
			} else {
				System.out.println("No " + modelName + " Model in memory");
			}
		} else {
			System.out.println("Variable name: " + varName + " already in use");
		}
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new RunAlterCmd(cmdStr);
	}
	
	private void setupTableNames(String[] commandParts) throws Exception{
		int tabBegin = 0;
		int tabEnd = 0;
		for(int i = 0; i < commandParts.length; i++) {
			if(commandParts[i].equals("[")) {
				tabBegin = i;
			}
			
			if(commandParts[i].equals("]")) {
				tabEnd = i;
			}
		}
		try {
			tableNames = new String[tabEnd - (tabBegin+1)];
			for(int i = tabBegin+1; i < tabEnd; i++) {
				tableNames[i-(tabBegin+1)] = commandParts[i];
				System.out.println(tableNames[i-(tabBegin+1)]);
			}
		} catch(Exception e) {
			System.out.println("Table names problem. Should be ['tabName1', 'tabName2']");
			throw new Exception();
		}
	}
	
	private void setupNotMandatoryArgs(String[] commandParts) throws Exception{
			System.out.println(commandParts[3]);
			if(commandParts[3].matches(Haquna.varName)) {
				if(Haquna.wmMap.containsKey(commandParts[3])) {
					this.wmName = commandParts[3];
				
				} else {
					  System.out.println("No " + commandParts[3] + " WorkingMemory object in memory");
				}
			}
							
			for(int i = 0; i < commandParts.length-1; i++) {
				String nextArgument = commandParts[i+1];
				switch(commandParts[i]) {
				case "mode" : {
					if(nextArgument.matches("gdi|fdi|foi")) {					
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
	
