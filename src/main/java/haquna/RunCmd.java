package haquna;

import haquna.Haquna;
import haquna.command.Command;
import heart.Configuration;
import heart.HeaRT;
import heart.State;
import heart.StateElement;
import heart.alsvfd.SimpleNumeric;
import heart.alsvfd.SimpleSymbolic;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.BuilderException;
import heart.exceptions.NotInTheDomainException;
import heart.uncertainty.ConflictSetFireAll;
import heart.uncertainty.ConflictSetFirstWin;
import heart.xtt.XTTModel;

public class RunCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)run[(][A-Z](.*)[,](\\s*)mode=(gdi|ddi|foi)[)](\\s*)";
	
	private String varName;
	private String commandStr;
	private String modelName;
	private String mode;
	private String token;
	private String conflictStrategy;
	
	public RunCmd() {
		
	}
	
	public RunCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[(|)=|,]");		
		this.varName = commandParts[0];
		this.modelName = commandParts[2];
		this.mode = commandParts[4];
	}
	
	@Override
	public void execute() {			
		if(!Haquna.isVarUsed(varName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
				XTTModel model = Haquna.modelMap.get(modelName);				
				
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
			    XTTstate.addStateElement(activityE);
			    
			    Configuration.Builder confBuilder = new Configuration.Builder();
			    
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
			    }
			    
			    try {
			    	switch(mode) {
			    	case "foi": {
			    		 HeaRT.fixedOrderInference(model, new String[]{"DayTime", "Today", "Actions", "Threats"},
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
			    		 HeaRT.dataDrivenInference(model, new String[]{"DayTime", "Today"},
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
			    		HeaRT.goalDrivenInference(model, new String[]{"Threats"},
				                new Configuration.Builder().setCsr(new ConflictSetFireAll())
				                        .setInitialState(XTTstate)
				                        .build());

				        System.out.println("Printing current state (after inference GDI)");
				        State current = HeaRT.getWm().getCurrentState(model);
				        for(StateElement se : current){
				            System.out.println("Attribute "+se.getAttributeName()+" = "+se.getValue());
				        }

				        System.out.println("\n\n");
			    		
			    		break;
			    	}
			    }
			       
			    Haquna.wmMap.put(varName, HeaRT.getWm());
			     
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
	
