package com.webreach.mirth.client.ui.transformeditor;

public class MapperData implements StepData {
	
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
