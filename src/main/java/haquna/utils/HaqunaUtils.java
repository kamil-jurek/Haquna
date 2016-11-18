package haquna.utils;

import haquna.HaqunaSingleton;
import haquna.HaqunaException;
import heart.WorkingMemory;
import heart.xtt.Attribute;
import heart.xtt.Table;
import heart.xtt.XTTModel;

public class HaqunaUtils {
	public static XTTModel getModel(String name) throws HaqunaException {
		if(HaqunaSingleton.modelMap.containsKey(name)) {
			return HaqunaSingleton.modelMap.get(name);
		
		} else {
			throw new HaqunaException("No '" + name + "' XTTModel object in memory");
		}
	}
	
	public static Attribute getAttribute(String name) throws HaqunaException {
		if(HaqunaSingleton.attrMap.containsKey(name)) {
			return HaqunaSingleton.attrMap.get(name);
		
		} else {
			throw new HaqunaException("No " + name + " attribute in memory");
		}
	}
	
	public static Table getTable(String name) throws HaqunaException {
		if(HaqunaSingleton.tableMap.containsKey(name)) {
			return HaqunaSingleton.tableMap.get(name);
		
		} else {
			throw new HaqunaException("No '" + name + "' Table object in memory");
		}
	}
	
	public static WorkingMemory getWorkingMemory(String name) throws HaqunaException {
		if(HaqunaSingleton.wmMap.containsKey(name)) {
			return HaqunaSingleton.wmMap.get(name);
		
		} else {
			throw new HaqunaException("No '" + name + "' WorkingMemory object in memory");
		}
	}
	
	public static void checkVarName(String varName) throws HaqunaException {
		if(HaqunaSingleton.isVarUsed(varName)) {
			throw new HaqunaException("Variable name '" + varName + "' already in use");
		}
	}
	
	public static void printGreen(String line) {
		System.out.println("\u001B[32m======>" + line + "\u001B[0m");
	}
	
	public static void printRed(String line) {
		System.out.println("\u001B[31m======>" + line + "\u001B[0m");
	}

	public static void clearMemory() {
		HaqunaSingleton.modelMap.clear();
		HaqunaSingleton.wmMap.clear();
		HaqunaSingleton.attrMap.clear();
		HaqunaSingleton.tableMap.clear();
		HaqunaSingleton.ruleMap.clear();
		HaqunaSingleton.typeMap.clear();
		HaqunaSingleton.callbackMap.clear();
		HaqunaSingleton.attrBuilderMap.clear();
		HaqunaSingleton.tableBuilderMap.clear();
		HaqunaSingleton.ruleBuilderMap.clear();
		HaqunaSingleton.typeBuilderMap.clear();
	}
}
