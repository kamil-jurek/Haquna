package haquna.completer;

import java.util.Arrays;
import java.util.List;

import haquna.Haquna;
import heart.xtt.Table;

public class RunCompleter2 extends CompleterAbstract {

	@Override
	protected void setupStringCandidates() {
		stringCandidates.clear();
				
        Haquna.LOGGER.info("argPos = " + argPos);
        Haquna.LOGGER.info("arguments = " + Arrays.toString(arguments));
		if(argPos == 2) {
			stringCandidates.addAll(Haquna.wmMap.keySet());
		}
		
		String prevArg = arguments[argPos-1];
		if(prevArg.equals("mode")){
			stringCandidates.add("gdi");
			stringCandidates.add("foi");
			stringCandidates.add("ddi");
		}
		
		else if(prevArg.equals("token")){
			stringCandidates.add("true");
			stringCandidates.add("false");
		}
		
		else if(prevArg.equals("uncertainty")){
			stringCandidates.add("true");
			stringCandidates.add("false");
		}
		
		else if(prevArg.equals("conflict_strategy")){
			stringCandidates.add("first");
			stringCandidates.add("last");
			stringCandidates.add("all");
		}
				
		else if(Arrays.asList(arguments).contains("[")) {
			Haquna.LOGGER.info("modelName = " + arguments[0]);
			for(Table t : Haquna.modelMap.get(arguments[0]).getTables()) {
				Haquna.LOGGER.info("adding table name = " + t.getName());
				stringCandidates.add(t.getName());
			}			
		}
		
		else {
			List<String> args = Arrays.asList(arguments);
			if(!args.contains("mode")) stringCandidates.add("mode");
			if(!args.contains("token")) stringCandidates.add("token");
			if(!args.contains("unceratinty")) stringCandidates.add("uncertainty");
			if(!args.contains("conflict_strategy")) stringCandidates.add("conflict_strategy");
			if(!args.contains("[")) stringCandidates.add("[");
		}
		
	}

}
