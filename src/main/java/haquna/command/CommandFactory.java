package haquna.command;

import haquna.command.get.GetTableByIdCmd;
import haquna.command.get.GetTableByNameCmd;
import haquna.command.io.XloadCmd;
import haquna.command.show.ShowAttributesListCmd;
import haquna.command.show.ShowCmd;
import haquna.command.show.ShowTablesListCmd;
import haquna.command.show.ShowTypesListCmd;
import haquna.command.show.ShowVarsCmd;

public class CommandFactory {
	
	public Command createCommand(String commandStr) {
		//M = xload('/home/kamil/MyHeartDroid/MyHeart/src/res/threat-monitor.hmr')
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
		
	}
}
