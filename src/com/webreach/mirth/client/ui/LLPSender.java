package com.webreach.mirth.client.ui;

import java.util.Properties;
import java.util.StringTokenizer;

public class LLPSender extends ConnectorClass
{
    Frame parent;
    /** Creates new form LLPSender */
    public final String DATATYPE = "DataType";
    public final String LLP_ADDRESS = "host";
    public final String LLP_PORT = "port";
    public final String LLP_SERVER_TIMEOUT = "sendTimeout";
    public final String LLP_BUFFER_SIZE = "bufferSize";
    public final String LLP_KEEP_CONNECTION_OPEN = "keepSendSocketOpen";
    public final String LLP_MAX_RETRY_COUNT = "maxRetryCount";
    public final String LLP_START_OF_MESSAGE_CHARACTER = "messageStart";
    public final String LLP_END_OF_MESSAGE_CHARACTER = "messageEnd";
    public final String LLP_FIELD_SEPARATOR = "fieldSeparator";
    public final String LLP_RECORD_SEPARATOR = "recordSeparator";
    
    public LLPSender()
    {
        this.parent = PlatformUI.MIRTH_FRAME;
        name = "LLP Sender";
        initComponents();
    }

    public Properties getProperties()
    {        
        Properties properties = new Properties();
        properties.put(DATATYPE, name);
        String hostIPAddress = hostIPAddressField.getText() + "." + hostIPAddressField1.getText() + "." + hostIPAddressField2.getText() + "." + hostIPAddressField3.getText();
        properties.put(LLP_ADDRESS, hostIPAddress);
        properties.put(LLP_PORT, hostPortField.getText());
        properties.put(LLP_SERVER_TIMEOUT, serverTimeoutField.getText());
        properties.put(LLP_BUFFER_SIZE, bufferSizeField.getText());

        if (keepConnectionOpenYesRadio.isSelected())
            properties.put(LLP_KEEP_CONNECTION_OPEN, "YES");
        else
            properties.put(LLP_KEEP_CONNECTION_OPEN, "NO");

        properties.put(LLP_MAX_RETRY_COUNT, maximumRetryCountField.getText());
        properties.put(LLP_START_OF_MESSAGE_CHARACTER, startOfMessageCharacterField.getText());
        properties.put(LLP_END_OF_MESSAGE_CHARACTER, endOfMessageCharacterField.getText());
        properties.put(LLP_RECORD_SEPARATOR, recordSeparatorField.getText());
        properties.put(LLP_FIELD_SEPARATOR, fieldSeparatorField.getText());
        return properties;
    }

    public void setProperties(Properties props)
    {
        String hostIPAddress = (String)props.get(LLP_ADDRESS);
        StringTokenizer IP = new StringTokenizer(hostIPAddress, ".");
        if (IP.hasMoreTokens())
            hostIPAddressField.setText(IP.nextToken());
        else
            hostIPAddressField.setText("");
        if (IP.hasMoreTokens())
            hostIPAddressField1.setText(IP.nextToken());
        else
            hostIPAddressField1.setText("");
        if (IP.hasMoreTokens())
            hostIPAddressField2.setText(IP.nextToken());
        else
            hostIPAddressField2.setText("");
        if (IP.hasMoreTokens())
            hostIPAddressField3.setText(IP.nextToken());
        else
            hostIPAddressField3.setText("");
        
        hostPortField.setText((String)props.get(LLP_PORT));
        serverTimeoutField.setText((String)props.get(LLP_SERVER_TIMEOUT));
        bufferSizeField.setText((String)props.get(LLP_BUFFER_SIZE));

        if(((String)props.get(LLP_KEEP_CONNECTION_OPEN)).equals("YES"))
            keepConnectionOpenYesRadio.setSelected(true);
        else
            keepConnectionOpenNoRadio.setSelected(true);

        maximumRetryCountField.setText((String)props.get(LLP_MAX_RETRY_COUNT));
        startOfMessageCharacterField.setText((String)props.get(LLP_START_OF_MESSAGE_CHARACTER));
        endOfMessageCharacterField.setText((String)props.get(LLP_END_OF_MESSAGE_CHARACTER));
        recordSeparatorField.setText((String)props.get(LLP_RECORD_SEPARATOR));
        fieldSeparatorField.setText((String)props.get(LLP_FIELD_SEPARATOR));
    }

    public void setDefaults()
    {
        hostIPAddressField.setText("127");
        hostIPAddressField1.setText("0");
        hostIPAddressField2.setText("0");
        hostIPAddressField3.setText("1");
        hostPortField.setText("3601");
        serverTimeoutField.setText("");
        bufferSizeField.setText("");
        keepConnectionOpenYesRadio.setSelected(true);
        maximumRetryCountField.setText("");
        startOfMessageCharacterField.setText("");
        endOfMessageCharacterField.setText("");
        recordSeparatorField.setText("");
        fieldSeparatorField.setText("");
    }
    
    public Properties getDefaults()
    {        
        Properties properties = new Properties();
        properties.put(DATATYPE, name);
        properties.put(LLP_ADDRESS, "127.0.0.1");
        properties.put(LLP_PORT, "6660");
        properties.put(LLP_SERVER_TIMEOUT, "5000");
        properties.put(LLP_BUFFER_SIZE, "65536");
        properties.put(LLP_KEEP_CONNECTION_OPEN, "NO");
        properties.put(LLP_MAX_RETRY_COUNT, "50");
        properties.put(LLP_START_OF_MESSAGE_CHARACTER, "0x0B");
        properties.put(LLP_END_OF_MESSAGE_CHARACTER, "0x1C");
        properties.put(LLP_RECORD_SEPARATOR, "0x0D");
        properties.put(LLP_FIELD_SEPARATOR, "0x7C");
        return properties;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        keepConnectionOpenGroup = new javax.swing.ButtonGroup();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        hostPortField = new com.webreach.mirth.client.ui.MirthTextField();
        serverTimeoutField = new com.webreach.mirth.client.ui.MirthTextField();
        bufferSizeField = new com.webreach.mirth.client.ui.MirthTextField();
        maximumRetryCountField = new com.webreach.mirth.client.ui.MirthTextField();
        startOfMessageCharacterField = new com.webreach.mirth.client.ui.MirthTextField();
        endOfMessageCharacterField = new com.webreach.mirth.client.ui.MirthTextField();
        recordSeparatorField = new com.webreach.mirth.client.ui.MirthTextField();
        fieldSeparatorField = new com.webreach.mirth.client.ui.MirthTextField();
        keepConnectionOpenYesRadio = new com.webreach.mirth.client.ui.MirthRadioButton();
        keepConnectionOpenNoRadio = new com.webreach.mirth.client.ui.MirthRadioButton();
        hostIPAddressField3 = new com.webreach.mirth.client.ui.MirthTextField();
        hostIPAddressField2 = new com.webreach.mirth.client.ui.MirthTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        hostIPAddressField1 = new com.webreach.mirth.client.ui.MirthTextField();
        jLabel9 = new javax.swing.JLabel();
        hostIPAddressField = new com.webreach.mirth.client.ui.MirthTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LLP Sender", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
        jLabel13.setText("Keep Connection Open:");

        jLabel15.setText("Buffer Size:");

        jLabel16.setText("Send Timeout (ms):");

        jLabel17.setText("Host Port:");

        jLabel18.setText("Host IP Address:");

        jLabel8.setText("Maximum Retry Count:");

        jLabel10.setText("Start of Message Character:");

        jLabel11.setText("End of Message Character:");

        jLabel12.setText("Record Sparator:");

        jLabel21.setText("Field Separator:");

        keepConnectionOpenYesRadio.setBackground(new java.awt.Color(255, 255, 255));
        keepConnectionOpenYesRadio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        keepConnectionOpenGroup.add(keepConnectionOpenYesRadio);
        keepConnectionOpenYesRadio.setText("Yes");
        keepConnectionOpenYesRadio.setMargin(new java.awt.Insets(0, 0, 0, 0));

        keepConnectionOpenNoRadio.setBackground(new java.awt.Color(255, 255, 255));
        keepConnectionOpenNoRadio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        keepConnectionOpenGroup.add(keepConnectionOpenNoRadio);
        keepConnectionOpenNoRadio.setText("No");
        keepConnectionOpenNoRadio.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel25.setText(".");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel26.setText(".");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel9.setText(".");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel17)
                    .add(jLabel18)
                    .add(jLabel16)
                    .add(jLabel15)
                    .add(jLabel13)
                    .add(jLabel8)
                    .add(jLabel10)
                    .add(jLabel11)
                    .add(jLabel12)
                    .add(jLabel21))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(hostPortField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(serverTimeoutField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(bufferSizeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(keepConnectionOpenYesRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(keepConnectionOpenNoRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, maximumRetryCountField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, startOfMessageCharacterField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, endOfMessageCharacterField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, recordSeparatorField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, fieldSeparatorField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(hostIPAddressField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel9)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(hostIPAddressField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel26)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(hostIPAddressField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel25)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(hostIPAddressField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(hostIPAddressField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(hostIPAddressField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(hostIPAddressField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(hostIPAddressField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel18))
                    .add(jLabel9)
                    .add(jLabel26)
                    .add(jLabel25))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(hostPortField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel17))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(serverTimeoutField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel16))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(bufferSizeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel15))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(keepConnectionOpenYesRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(keepConnectionOpenNoRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel13))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maximumRetryCountField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(startOfMessageCharacterField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel10))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(endOfMessageCharacterField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel11))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(recordSeparatorField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel12))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(fieldSeparatorField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel21))
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.webreach.mirth.client.ui.MirthTextField bufferSizeField;
    private com.webreach.mirth.client.ui.MirthTextField endOfMessageCharacterField;
    private com.webreach.mirth.client.ui.MirthTextField fieldSeparatorField;
    private com.webreach.mirth.client.ui.MirthTextField hostIPAddressField;
    private com.webreach.mirth.client.ui.MirthTextField hostIPAddressField1;
    private com.webreach.mirth.client.ui.MirthTextField hostIPAddressField2;
    private com.webreach.mirth.client.ui.MirthTextField hostIPAddressField3;
    private com.webreach.mirth.client.ui.MirthTextField hostPortField;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.ButtonGroup keepConnectionOpenGroup;
    private com.webreach.mirth.client.ui.MirthRadioButton keepConnectionOpenNoRadio;
    private com.webreach.mirth.client.ui.MirthRadioButton keepConnectionOpenYesRadio;
    private com.webreach.mirth.client.ui.MirthTextField maximumRetryCountField;
    private com.webreach.mirth.client.ui.MirthTextField recordSeparatorField;
    private com.webreach.mirth.client.ui.MirthTextField serverTimeoutField;
    private com.webreach.mirth.client.ui.MirthTextField startOfMessageCharacterField;
    // End of variables declaration//GEN-END:variables

}
