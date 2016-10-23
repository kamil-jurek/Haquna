package haquna.command;

import java.util.LinkedList;

import haquna.command.add.AddCmd;
import haquna.command.add.RemoveCmd;
import haquna.command.create.NewAttributeCmd;
import haquna.command.create.NewTypeCmd;
import haquna.command.get.GetAttributeByIdCmd;
import haquna.command.get.GetAttributeByNameCmd;
import haquna.command.get.GetCallbackCmd;
import haquna.command.get.GetRuleByIdCmd;
import haquna.command.get.GetRuleByNameCmd;
import haquna.command.get.GetTableByIdCmd;
import haquna.command.get.GetTableByNameCmd;
import haquna.command.get.GetTypeByIdCmd;
import haquna.command.get.GetTypeByNameCmd;
import haquna.command.get.GetTypeCmd;
import haquna.command.io.XloadCmd;
import haquna.command.io.XsaveCmd;
import haquna.command.run.DetermineValuesCmd;
import haquna.command.run.RunAlterCmd;
import haquna.command.run.RunCmd;
import haquna.command.show.ShowAttributesListCmd;
import haquna.command.show.ShowCmd;
import haquna.command.show.ShowCurrentStateCmd;
import haquna.command.show.ShowHistoryValueOfCmd;
import haquna.command.show.ShowRulesListCmd;
import haquna.command.show.ShowTablesListCmd;
import haquna.command.show.ShowTypesListCmd;
import haquna.command.show.ShowValueOfCmd;
import haquna.command.show.PrintVarsCmd;
import haquna.command.wm.NewWorkingMemoryCmd;
import haquna.command.wm.SetValueOfCmd;

public class CommandFactory {
	
	@SuppressWarnings("serial")
	public static LinkedList<Command> commandTypes = new LinkedList<Command>() {
		{	
			// IO methods
			add(new XloadCmd());
			add(new XsaveCmd());
			
			// Common methods
			add(new ShowCmd());
			add(new AddCmd());
			add(new RemoveCmd());
						
			// Model methods
			add(new ShowTablesListCmd());
			add(new ShowTypesListCmd());
			add(new ShowAttributesListCmd());
			add(new GetAttributeByIdCmd());
			add(new GetAttributeByNameCmd());
			add(new GetTableByIdCmd());
			add(new GetTableByNameCmd());
			add(new GetTypeByIdCmd());
			add(new GetTypeByNameCmd());
						
			// Table methods
			add(new ShowRulesListCmd());
			add(new GetRuleByIdCmd());
			add(new GetRuleByNameCmd());
			
			// Attribute methods
			add(new GetTypeCmd());			
			add(new GetCallbackCmd());
			
			// Running inference methods
			add(new RunCmd());
			add(new RunAlterCmd());
			add(new DetermineValuesCmd());
						
			// Working memory methods
			add(new NewWorkingMemoryCmd());
			add(new ShowValueOfCmd());
			add(new SetValueOfCmd());
			add(new ShowCurrentStateCmd());
			add(new ShowHistoryValueOfCmd());
			
			
			add(new NewTypeCmd());	
			add(new NewAttributeCmd());

			
			// utils
			add(new PrintVarsCmd());
			
		}
	};
	
	public Command createCommand(String commandStr) {
		for(Command cmdType : commandTypes) {
			if(cmdType.matches(commandStr)) {
				Command cmd = cmdType.getNewCommand(commandStr);
				cmd.execute();
				
				return cmd;
			}
		}
		return null;				
	}
}
