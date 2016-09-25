package haquna.command;

import java.util.LinkedList;

import haquna.command.get.GetAttribiuteByIdCmd;
import haquna.command.get.GetAttribiuteByNameCmd;
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
import haquna.command.show.ShowRulesListCmd;
import haquna.command.show.ShowTablesListCmd;
import haquna.command.show.ShowTypesListCmd;
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
			add(new GetAttribiuteByIdCmd());
			add(new GetAttribiuteByNameCmd());
			add(new GetTableByIdCmd());
			add(new GetTableByNameCmd());
			add(new GetTypeByIdCmd());
			add(new GetTypeByNameCmd());
			add(new GetTypeCmd());
			add(new GetRuleByIdCmd());
			add(new GetRuleByNameCmd());
			add(new GetCallbackCmd());
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
		
		/*//M = xload('/home/kamil/MyHeartDroid/MyHeart/src/res/threat-monitor.hmr')
		if(XloadCmd.matches(commandStr)) {
			Command xloadCmd = new XloadCmd(commandStr);
			xloadCmd.execute();
			
			return xloadCmd;
		
		//M.showTablesList()
		} else if(ShowTablesListCmd.matches(commandStr)) {
			Command showTablesListCmd = new ShowTablesListCmd(commandStr);
			showTablesListCmd.execute();
			
			return showTablesListCmd;		
		}
		
		//M.showTablesList()
		else if(ShowAttributesListCmd.matches(commandStr)) {
			Command showAttributesListCmd = new ShowAttributesListCmd(commandStr);
			showAttributesListCmd.execute();
			
			return showAttributesListCmd;
		}
		
		//M.showTypesList()
		else if(ShowTypesListCmd.matches(commandStr)) {
			Command showTypesListCmd = new ShowTypesListCmd(commandStr);
			showTypesListCmd.execute();
					
			return showTypesListCmd;
		}
		
		//T = M.getTableByName('tabName')
		else if(GetTableByNameCmd.matches(commandStr)) {
			Command getTableByNameCmd = new GetTableByNameCmd(commandStr);
			getTableByNameCmd.execute();
							
			return getTableByNameCmd;
		}
		
		//T = M.getTableById('tabId')
		else if(GetTableByIdCmd.matches(commandStr)) {
			Command getTableByIdCmd = new GetTableByIdCmd(commandStr);
			getTableByIdCmd.execute();
									
			return getTableByIdCmd;
		}
		
		//A = M.getAttribiuteById('tabId')
		else if(GetAttribiuteByIdCmd.matches(commandStr)) {
			Command getAttribiuteByIdCmd = new GetTableByIdCmd(commandStr);
			getTableByIdCmd.execute();
											
			return getTableByIdCmd;
		}
		
		//T.show()
		else if(ShowCmd.matches(commandStr)) {
			Command showCmd = new ShowCmd(commandStr);
			showCmd.execute();
									
			return showCmd;
		}
		
		else if(ShowVarsCmd.matches(commandStr)) {
			Command showVarsCmd = new ShowVarsCmd(commandStr);
			showVarsCmd.execute();
									
			return showVarsCmd;
		}
		
		else {
			return null;
		}
		*/
	}
}
