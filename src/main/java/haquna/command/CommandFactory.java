package haquna.command;

import java.util.LinkedList;

import haquna.RunCmd;
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
import haquna.command.show.ShowAttributesListCmd;
import haquna.command.show.ShowCmd;
import haquna.command.show.ShowCurrentStateCmd;
import haquna.command.show.ShowRulesListCmd;
import haquna.command.show.ShowTablesListCmd;
import haquna.command.show.ShowTypesListCmd;
import haquna.command.show.ShowValueOfCmd;
import haquna.command.show.ShowVarsCmd;

public class CommandFactory {
	
	public static LinkedList<Command> commandTypes = new LinkedList<Command>() {
		{
			add(new XloadCmd());
			add(new ShowVarsCmd());
			add(new ShowTablesListCmd());
			add(new ShowTypesListCmd());
			add(new ShowAttributesListCmd());
			add(new ShowRulesListCmd());
			add(new ShowCmd());
			add(new GetAttributeByIdCmd());
			add(new GetAttributeByNameCmd());
			add(new GetTableByIdCmd());
			add(new GetTableByNameCmd());
			add(new GetTypeByIdCmd());
			add(new GetTypeByNameCmd());
			add(new GetTypeCmd());
			add(new GetRuleByIdCmd());
			add(new GetRuleByNameCmd());
			add(new GetCallbackCmd());
			add(new RunCmd());
			add(new ShowCurrentStateCmd());
			add(new ShowValueOfCmd());
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
