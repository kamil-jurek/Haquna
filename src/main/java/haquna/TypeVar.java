package haquna;

import heart.xtt.Type;

public class TypeVar {
	public Type type;
	public Type.Builder typeBuilder;
	
	public TypeVar(Type type, Type.Builder typeBuilder) {
		this.type = type;
		this.typeBuilder = typeBuilder;
	}
}
