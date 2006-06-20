package com.webreach.mirth.applets.stepeditor;

public class MapperData {
	
	public MapperData() {
		variableName = "";
		variableMapping = "";
	}

	public void setVariableName( String name ) {
		variableName = name;
	}
	
	public void setVariableMapping( String mapping ) {
		variableMapping = mapping;
	}
	
	public String getVariableName() {
		return variableName;
	}
	
	public String getVariableMapping() {
		return variableMapping;
	}
	
	private String variableName;
	private String variableMapping;
}
