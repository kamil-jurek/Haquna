package haquna.command.run;

import java.util.ArrayList;
import java.util.List;

import haquna.command.Command;
import heart.Configuration;
import heart.uncertainty.ConflictSetFireAll;
import heart.uncertainty.ConflictSetFirstWin;
import heart.uncertainty.ProbabilityEvaluator;

abstract public class RunAbstactCmd implements Command {					
	
	protected String[] tableNames = new String[]{};	
	protected String mode = "ddi";
	protected String token = "false";
	protected String uncertanity = "false";
	protected String conflictStrategy = "first";		
	protected Configuration.Builder confBuilder;
	
	@Override
	abstract public void execute();		
	
	abstract public boolean matches(String commandStr);
	
	abstract public Command getNewCommand(String cmdStr);
	
	protected void setupToken() {
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
	
	protected void setupUncertainty() {
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
	
	protected void setupConflictStrategy() {
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
	
	protected void setupTableNames(String[] commandParts){
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
			tableNames = new String[]{};
			System.out.println("Tables names were not initialized");
		}
	}
	
	protected void setupNotMandatoryArgs(String[] commandParts) throws Exception{				
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
	
