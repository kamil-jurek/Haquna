package haquna.utils;

import haquna.Haquna;
import haquna.HaqunaException;
import heart.WorkingMemory;
import heart.xtt.Table;
import heart.xtt.XTTModel;

public class HaqunaUtils {
	public static XTTModel getModel(String name) throws HaqunaException {
		if(Haquna.modelMap.containsKey(name)) {
			return Haquna.modelMap.get(name);
		}	
		else {
			throw new HaqunaException("No '" + name + "' XTTModel object in memory");
		}
	}
	
	public static Table getTable(String name) throws HaqunaException {
		if(Haquna.tableMap.containsKey(name)) {
			return Haquna.tableMap.get(name);
		}	
		else {
			throw new HaqunaException("No '" + name + "' Table object in memory");
		}
	}
	
	public static WorkingMemory getWorkingMemory(String name) throws HaqunaException {
		if(Haquna.wmMap.containsKey(name)) {
			return Haquna.wmMap.get(name);
		}	
		else {
			throw new HaqunaException("No '" + name + "' WorkingMemory object in memory");
		}
	}
	
	public static void checkVarName(String varName) throws HaqunaException {
		if(Haquna.isVarUsed(varName)) {
			throw new HaqunaException("Variable name '" + varName + "' already in use");
		}
	}
	
	public static void printGreeen(String line) {
		System.out.println("\u001B[32m======>" + line + "\"\u001B[0m");
	}
	
	public static void printRed(String line) {
		System.out.println("\u001B[31m======>" + line + "\"\u001B[0m");
	}

	public static void clearMemory() {
		Haquna.modelMap.clear();
		Haquna.wmMap.clear();
		Haquna.modelMap.clear();
		Haquna.attribiuteMap.clear();
		Haquna.tableMap.clear();
		Haquna.ruleMap.clear();
		Haquna.typeMap.clear();
		Haquna.callbackMap.clear();
	}
}
