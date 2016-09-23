package haquna;

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
		
		//T.show()
		else if(ShowCmd.matches(commandStr)) {
			Command showCmd = new ShowCmd(commandStr);
			showCmd.execute();
									
			return showCmd;
		}
		
		else {
			return null;
		}
		
	}
}
