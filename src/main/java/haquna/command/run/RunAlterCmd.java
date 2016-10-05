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
import heart.xtt.XTTModel;

public class RunAlterCmd implements Command {		
	// run(Model, Wm, mode=gdi, ['tab1', 'tab2'])
	public static final String pattern = "^run[(]" + "(\\s*)" + Haquna.varName + "(\\s*)[,](\\s*)" + 	// Model
																Haquna.varName + "(\\s*)[,](\\s*)" + 	// WorkingMemory
																"mode=(gdi|ddi|foi)" + "(\\s*)[,](\\s*)" +
																"[\\[]"+ "(.*)" + "[\\]]" + "(\\s*)" +
																"(.*)[)](\\s*)";		// mode
		
	private String commandStr;
	private String modelName;
	private String wmName;
	private String mode;
	private String[] tableNames;
	
	private String token = "none";
	private String uncertanity = "none";
	private String conflictStrategy = "none";

	
	public RunAlterCmd() {
		
	}
	
	public RunAlterCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
				
		String[] commandParts = this.commandStr.split("[(|)=|,|']");		
		this.modelName = commandParts[1];
		this.wmName = commandParts[2];				
		this.mode = commandParts[4];
		
		setupTableNames(commandParts);
		setupNotMandatoryArgs(commandParts);
						
	}
	
	@Override
	public void execute() {					
		if(Haquna.modelMap.containsKey(modelName)) {
			if(Haquna.wmMap.containsKey(wmName)) {
				XTTModel model = Haquna.modelMap.get(modelName);				
				WorkingMemory wm = Haquna.wmMap.get(wmName);
							    
			    Configuration.Builder confBuilder = new Configuration.Builder();			 
				confBuilder.setInitialState(wm.getCurrentState());
				
				confBuilder.setCsr(new ConflictSetFirstWin());
			    
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
					//confBuilder.setUte(new ProbabilityEvaluator());
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
			       			     
			    } catch(UnsupportedOperationException e){
			    	e.printStackTrace();				
				} catch(BuilderException e) {
					e.printStackTrace();
				}
			
			  } else {
				  System.out.println("No " + wmName + " WorkingMemory object in memory");
			  }
			} else {
				System.out.println("No " + modelName + " Model in memory");
			}
		
		
		

	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new RunAlterCmd(cmdStr);
	}
	
	private void setupTableNames(String[] commandParts) {
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
		}
	}
	
	private void setupNotMandatoryArgs(String[] commandParts) {
		for(int i = 0; i < commandParts.length; i++) {
			switch(commandParts[i]) {
			case "token": {
				this.token = commandParts[i+1];
				break;
			}
			
			case "uncertanity": {
				this.uncertanity = commandParts[i+1];
				break;
			}
			
			case "conflict_strategy": {
				this.conflictStrategy = commandParts[i+1];
				break;
			}
			
			}
		}
	}

}
	
