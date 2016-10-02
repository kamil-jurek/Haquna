package haquna.command.run;

import haquna.Haquna;
import haquna.command.Command;
import heart.Configuration;
import heart.State;
import heart.StateElement;
import heart.WorkingMemory;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.BuilderException;
import heart.exceptions.NotInTheDomainException;
import heart.inference.GoalDrivenInference;
import heart.inference.InferenceAlgorithm;
import heart.xtt.XTTModel;

public class DetermineValuesCmd implements Command {		
	//WorkingMemory = determineValues(Model, Wm, ['tabName1','tabName2'])
	public static final String pattern = "^[A-Z].*=(\\s*)determineValues[(][A-Z](.*)[,](\\s*)([A-Z](.*)[,])*(\\s*)[\\[](.*)[\\]](\\s*)[)](\\s*)";			// )
														
		
	private String commandStr;
	private String varName;
	private String modelName;
	private String[] attrNames;
	private String wmName;
	private boolean usingWm;
	
	public DetermineValuesCmd() {
		
	}
	
	public DetermineValuesCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");

		String[] commandParts = this.commandStr.split("[(|)=|,|']");		
		this.varName = commandParts[0];
		this.modelName = commandParts[2];
		
		int tabCount = 0;
		int attrBegin = 0;
		if(!commandParts[3].equals("[")) { //  WM in command
			this.wmName = commandParts[3];
			tabCount = commandParts.length - 6;
			attrBegin = 5;
			this.usingWm = true;
		
		} else {
			tabCount = commandParts.length - 5;
			attrBegin = 4;
		}
				
		if(tabCount > 0) {
			this.attrNames = new String[tabCount];
			for(int i = 0; i < tabCount; i++) {
				int index = i + attrBegin;
				this.attrNames[i] = commandParts[index];
				System.out.println(attrNames[i]);
			}
		}						
	}
	
	@Override
	public void execute() {			
		if(usingWm) {
			executeUsingWm();
			
		} else {
			executeNoWm();
		}
		

	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new DetermineValuesCmd(cmdStr);
	}

	private void executeUsingWm() {
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
				if(Haquna.wmMap.containsKey(wmName)) {
					XTTModel model = Haquna.modelMap.get(modelName);				
					WorkingMemory wm = Haquna.wmMap.get(wmName);		    
				    Configuration.Builder confBuilder = new Configuration.Builder();
			 				
				    //////// WM concept in progress ///////
				    WorkingMemory newWm = new WorkingMemory();
				    try {
						newWm.setCurrentState(wm.getCurrentState(), model, true);
						confBuilder.setInitialState(wm.getCurrentState());
					} catch (NotInTheDomainException | AttributeNotRegisteredException e1) {					
						e1.printStackTrace();
					}			   				    
				    //////////////////////////////////////
				    
				    try {
				    	Configuration cs = confBuilder.build();
				    	new GoalDrivenInference(newWm, model, cs).start(new InferenceAlgorithm.AttributeParameters(attrNames));
				    		
				    	System.out.println("Printing current state");
					  	State current = newWm.getCurrentState(model);
					    for(StateElement se : current){
					      	System.out.println("Attribute "+se.getAttributeName()+" = "+se.getValue());
					    }
	
					  	System.out.println("\n\n");
				    				       
					  	Haquna.wmMap.put(varName, newWm);
				     
				    } catch(UnsupportedOperationException e){
				    	e.printStackTrace();
				    } catch(BuilderException e) {
						e.printStackTrace();
					}
				
				  } else {
					  System.out.println("No " + wmName + " WorkingMemory object in memory");
				  }
			} else {
				System.out.println("No " + modelName + " model in memory");
			}
		
		} else {
			System.out.println("Variable name: " + varName + " already in use");
		}	
		
	}		
	
	private void executeNoWm() {
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.modelMap.containsKey(modelName)) {			 
				XTTModel model = Haquna.modelMap.get(modelName);						    
				Configuration.Builder confBuilder = new Configuration.Builder();
			 
				//////// WM concept in progress ///////
				WorkingMemory newWm = new WorkingMemory();
				confBuilder.setInitialState(newWm.getCurrentState());
				/////////////////////////////////////
				    
				try {
					Configuration cs = confBuilder.build();
					new GoalDrivenInference(newWm, model, cs).start(new InferenceAlgorithm.AttributeParameters(attrNames));
				    		
					System.out.println("Printing current state");
					State current = newWm.getCurrentState(model);
					for(StateElement se : current){
						System.out.println("Attribute "+se.getAttributeName()+" = "+se.getValue());
					}
	
					  System.out.println("\n\n");
				      Haquna.wmMap.put(varName, newWm);
				     
				} catch(UnsupportedOperationException e){
				  	e.printStackTrace();
				} catch(BuilderException e) {
					e.printStackTrace();
				}				
			 				
			} else {
				System.out.println("No " + modelName + " model in memory");
			}
		
		} else {
			System.out.println("Variable name: " + varName + " already in use");
		}	
	}
}
	
