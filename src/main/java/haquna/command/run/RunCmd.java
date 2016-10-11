package haquna.command.run;

import haquna.Haquna;
import haquna.command.Command;
import heart.Configuration;
import heart.HeaRT;
import heart.State;
import heart.StateElement;
import heart.WorkingMemory;
import heart.alsvfd.SimpleNumeric;
import heart.alsvfd.SimpleSymbolic;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.BuilderException;
import heart.exceptions.NotInTheDomainException;
import heart.inference.GoalDrivenInference;
import heart.inference.InferenceAlgorithm;
import heart.uncertainty.ConflictSetFireAll;
import heart.xtt.XTTModel;

public class RunCmd implements Command {		
	//WorkingMemory = run(Model, Wm, ['tabName1','tabName2'], mode=gdi)
	public static final String pattern = "^[A-Z].*=(\\s*)runnn[(][A-Z](.*)[,](\\s*)[A-Z](.*)[,](\\s*)[\\[](.*)[\\]][,](\\s*)mode=(gdi|ddi|foi)[)](\\s*)";
		
	private String commandStr;
	private String varName;
	private String modelName;
	private String[] tableNames;
	private String mode;
	private String wmName;
	
	public RunCmd() {
		
	}
	
	public RunCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");

		String[] commandParts = this.commandStr.split("[(|)=|,|']");		
		this.varName = commandParts[0];
		this.modelName = commandParts[2];
		this.wmName = commandParts[3];
		
		int tabCount = commandParts.length - 8;
		
		if(tabCount > 0) {
			this.tableNames = new String[tabCount];
			for(int i = 0; i < tabCount; i++) {
				int index = i + 5;
				this.tableNames[i] = commandParts[index];
				System.out.println(tableNames[i]);
			}
		}	
		
		this.mode = commandParts[commandParts.length-1];
				
	}
	
	@Override
	public void execute() {			
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
			  if(Haquna.wmMap.containsKey(wmName)) {
				XTTModel model = Haquna.modelMap.get(modelName);				
				WorkingMemory wm = Haquna.wmMap.get(wmName);
				// Creating StateElements objects, one for each attribute
				StateElement hourE = new StateElement();
			    StateElement dayE = new StateElement();
			    StateElement locationE = new StateElement();
			    StateElement activityE = new StateElement();

			  // Setting the values of the state elements
			    hourE.setAttributeName("hour");
			    hourE.setValue(new SimpleNumeric(16d));
			    dayE.setAttributeName("day");
			    dayE.setValue(new SimpleSymbolic("mon",1));
			    
			    locationE.setAttributeName("location");
			    locationE.setValue(new SimpleSymbolic("work"));
			    
			    activityE.setAttributeName("activity");
			    activityE.setValue(new SimpleSymbolic("walking"));

			  //Creating a XTTState object that agregates all the StateElements
			    State XTTstate = new State();
			    XTTstate.addStateElement(hourE);
			    XTTstate.addStateElement(dayE);
			    XTTstate.addStateElement(locationE);
			    //XTTstate.addStateElement(activityE);
			    
			    Configuration.Builder confBuilder = new Configuration.Builder();
			 
			    /*switch(token) {
			    case "true": {			    	
			    	confBuilder.setTokenPassingEnabled(true);
			    	break;
			    }
			    
			    case "false": {
			    	confBuilder.setTokenPassingEnabled(false);
			    	break;
			    }
			    }
			    
			    switch(conflictStrategy) {
			    case "first": {			    	
			    	confBuilder.setCsr(new ConflictSetFirstWin());
			    	break;
			    }
			    
			    case "last": {
			    	confBuilder.setCsr(new ConflictSetFireAll());
			    	break;
			    }
			    
			    case "all": {
			    	confBuilder.setCsr(new ConflictSetFireAll());
			    	break;
			    }
			    }*/
			    
			    
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
			    	switch(mode) {
			    	case "foi": {
			    		 HeaRT.fixedOrderInference(model, tableNames,
					              new Configuration.Builder().setCsr(new ConflictSetFireAll())
					                      .setInitialState(XTTstate)
					                      .build());

					        System.out.println("Printing current state (after inference FOI)");
					        State current = HeaRT.getWm().getCurrentState(model);
					        for(StateElement se : current){
					            System.out.println("Attribute "+se.getAttributeName()+" = "+se.getValue());
					        }

					        System.out.println("\n\n");
			    		
			    		break;
			    	}
			    	 
			    	case "ddi": {
			    		 HeaRT.dataDrivenInference(model, tableNames,
					                new Configuration.Builder().setCsr(new ConflictSetFireAll())
					                        .setInitialState(XTTstate)
					                        .build());

					        System.out.println("Printing current state (after inference DDI)");
					        State current = HeaRT.getWm().getCurrentState(model);
					        for(StateElement se : current){
					            System.out.println("Attribute "+se.getAttributeName()+" = "+se.getValue());
					        }

					        System.out.println("\n\n");
			    		
			    		break;
			    	}
			    	
			    	case "gdi": {
			    		Configuration cs = confBuilder.build();
			    		new GoalDrivenInference(newWm, model, cs).start(new InferenceAlgorithm.TableParameters(tableNames));
			    		//new GoalDrivenInference(wm,model,cs).start(new InferenceAlgorithm.TableParameters(tablesNames));

			    		System.out.println("Printing current state (after inference GDI)");
				        State current = newWm.getCurrentState(model);
				        for(StateElement se : current){
				            System.out.println("Attribute "+se.getAttributeName()+" = "+se.getValue());
				        }

				        System.out.println("\n\n");
			    		
			    		break;
			    	}
			    }
			       
			    Haquna.wmMap.put(varName, newWm);
			     
			    } catch(UnsupportedOperationException e){
			    	e.printStackTrace();
			    } catch(AttributeNotRegisteredException e) {
					e.printStackTrace();				
				} catch(BuilderException e) {
					e.printStackTrace();
				} catch(NotInTheDomainException e) {
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
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new RunCmd(cmdStr);
	}


}
	
