package com.webreach.mirth.client.ui.editors;

import com.webreach.mirth.client.ui.PlatformUI;
import com.webreach.mirth.model.Channel;

public class HL7MessageBuilder extends MapperPanel {
	public HL7MessageBuilder(MirthEditorPane p) {
		super();
		parent = p;		
		initComponents();
	}
	public void update(){
		parent.update();
		mappingLabel.setText( "   HL7 Message Segment: " );
		parent.setDroppedTextPrefix("hl7");
	
	}
}
