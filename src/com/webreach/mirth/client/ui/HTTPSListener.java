package com.webreach.mirth.client.ui;

import java.util.Properties;
import java.util.StringTokenizer;

public class HTTPSListener extends ConnectorClass
{
    Frame parent;
    /** Creates new form HTTPSListener */
    public HTTPSListener()
    {
        this.parent = PlatformUI.MIRTH_FRAME;
        name = "HTTPS Listener";
        initComponents();
    }

    public Properties getProperties()
    {
        Properties properties = new Properties();
        properties.put("DataType", name);
        String listenerIPAddress = listenerIPAddressField.getText() + "." + listenerIPAddressField1.getText() + "." + listenerIPAddressField2.getText() + "." + listenerIPAddressField3.getText();
        properties.put("address", listenerIPAddress);
        properties.put("port", listenerPortField.getText());
        properties.put("ReceiveTimeout", receiveTimeoutField.getText());
        properties.put("BufferSize", bufferSizeField.getText());

        if (keepConnectionOpenYesRadio.isSelected())
            properties.put("KeepConnectionOpen", "YES");
        else
            properties.put("KeepConnectionOpen", "NO");

        properties.put("KeyStore", keyStoreField.getText());
        properties.put("KeyStorePassword", keyStorePasswordField.getText());
        properties.put("KeyStoreType", keyStoreTypeField.getText());
        properties.put("KeyManagerAlgorithm", keyManagerAlgorithmField.getText());
        properties.put("ProtocolHandler", protocolHandlerField.getText());

        if (requireClientAuthenticationYesRadio.isSelected())
            properties.put("RequireClientAuthentication", "YES");
        else
            properties.put("RequireClientAuthentication", "NO");

        properties.put("SecurityProvider", securityProviderField.getText());
        properties.put("ClientKeystore", clientKeystoreField.getText());
        properties.put("ClientKeystorePassword", clientKeystorePasswordField.getText());
        properties.put("TrustKeystore", trustKeystoreField.getText());
        properties.put("TrustKeystorePassword", trustKeystorePasswordField.getText());

        if (explicitTrustStoreOnlyYesRadio.isSelected())
            properties.put("ExplicitTrustStoreOnly", "YES");
        else
            properties.put("ExplicitTrustStoreOnly", "NO");

        properties.put("StartOfMessageCharacter", startOfMessageCharacterField.getText());
        properties.put("EndOfMessageCharacter", endOfMessageCharacterField.getText());
        properties.put("FieldSeparator", fieldSeparatorField.getText());
        properties.put("RecordSeparator", recordSeparatorField.getText());
        properties.put("SendACK", sendACKCombobox.getSelectedItem());
        return properties;
    }

    public void setProperties(Properties props)
    {
        String listenerIPAddress = (String)props.get("address");
        StringTokenizer IP = new StringTokenizer(listenerIPAddress, ".");
        if (IP.hasMoreTokens())
            listenerIPAddressField.setText(IP.nextToken());
        else
            listenerIPAddressField.setText("");
        if (IP.hasMoreTokens())
            listenerIPAddressField1.setText(IP.nextToken());
        else
            listenerIPAddressField1.setText("");
        if (IP.hasMoreTokens())
            listenerIPAddressField2.setText(IP.nextToken());
        else
            listenerIPAddressField2.setText("");
        if (IP.hasMoreTokens())
            listenerIPAddressField3.setText(IP.nextToken());
        else
            listenerIPAddressField3.setText("");      

        listenerPortField.setText((String)props.get("port"));
        receiveTimeoutField.setText((String)props.get("ReceiveTimeout"));
        bufferSizeField.setText((String)props.get("BufferSize"));

        if(((String)props.get("KeepConnectionOpen")).equals("YES"))
            keepConnectionOpenYesRadio.setSelected(true);
        else
            keepConnectionOpenNoRadio.setSelected(true);

        keyStoreField.setText((String)props.get("KeyStore"));
        keyStorePasswordField.setText((String)props.get("KeyStorePassword"));
        keyStoreTypeField.setText((String)props.get("KeyStoreType"));
        keyManagerAlgorithmField.setText((String)props.get("KeyManagerAlgorithm"));
        protocolHandlerField.setText((String)props.get("ProtocolHandler"));

        if(((String)props.get("RequireClientAuthentication")).equals("YES"))
            requireClientAuthenticationYesRadio.setSelected(true);
        else
            requireClientAuthenticationNoRadio.setSelected(true);

        securityProviderField.setText((String)props.get("SecurityProvider"));
        clientKeystoreField.setText((String)props.get("ClientKeystore"));
        clientKeystorePasswordField.setText((String)props.get("ClientKeystorePassword"));
        trustKeystoreField.setText((String)props.get("TrustKeystore"));
        trustKeystorePasswordField.setText((String)props.get("TrustKeystorePassword"));

        if(((String)props.get("ExplicitTrustStoreOnly")).equals("YES"))
            explicitTrustStoreOnlyYesRadio.setSelected(true);
        else
            explicitTrustStoreOnlyNoRadio.setSelected(true);

        startOfMessageCharacterField.setText((String)props.get("StartOfMessageCharacter"));
        endOfMessageCharacterField.setText((String)props.get("EndOfMessageCharacter"));
        fieldSeparatorField.setText((String)props.get("FieldSeparator"));
        recordSeparatorField.setText((String)props.get("RecordSeparator"));
        boolean visible = parent.channelEditTasks.getContentPane().getComponent(0).isVisible();
        sendACKCombobox.setSelectedItem(props.get("SendACK"));
        parent.channelEditTasks.getContentPane().getComponent(0).setVisible(visible);
    }

    public void setDefaults()
    {
        listenerIPAddressField.setText("127");
        listenerIPAddressField1.setText("0");
        listenerIPAddressField2.setText("0");
        listenerIPAddressField3.setText("1");        
        listenerPortField.setText("3800");
        receiveTimeoutField.setText("");
        bufferSizeField.setText("");
        keepConnectionOpenYesRadio.setSelected(true);
        keyStoreField.setText("");
        keyStorePasswordField.setText("");
        keyStoreTypeField.setText("");
        keyManagerAlgorithmField.setText("");
        protocolHandlerField.setText("");
        requireClientAuthenticationYesRadio.setSelected(true);
        securityProviderField.setText("");
        clientKeystoreField.setText("");
        clientKeystorePasswordField.setText("");
        trustKeystoreField.setText("");
        trustKeystorePasswordField.setText("");
        explicitTrustStoreOnlyYesRadio.setSelected(true);
        startOfMessageCharacterField.setText("");
        endOfMessageCharacterField.setText("");
        fieldSeparatorField.setText("");
        recordSeparatorField.setText("");
        sendACKCombobox.setSelectedIndex(0);
    }

    public Properties getDefaults()
    {
        Properties properties = new Properties();
        properties.put("DataType", name);
        properties.put("address", "127.0.0.1");
        properties.put("port", "3800");
        properties.put("ReceiveTimeout", "");
        properties.put("BufferSize", "");
        properties.put("KeepConnectionOpen", "YES");
        properties.put("KeyStore", "");
        properties.put("KeyStorePassword", "");
        properties.put("KeyStoreType", "");
        properties.put("KeyManagerAlgorithm", "");
        properties.put("ProtocolHandler", "");
        properties.put("RequireClientAuthentication", "YES");
        properties.put("SecurityProvider", "");
        properties.put("ClientKeystore", "");
        properties.put("ClientKeystorePassword", "");
        properties.put("TrustKeystore", "");
        properties.put("TrustKeystorePassword", "");
        properties.put("ExplicitTrustStoreOnly", "YES");
        properties.put("StartOfMessageCharacter", "");
        properties.put("EndOfMessageCharacter", "");
        properties.put("FieldSeparator", "");
        properties.put("RecordSeparator", "");
        properties.put("SendACK", sendACKCombobox.getItemAt(0));
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
        requireClientAuthenticationGroup = new javax.swing.ButtonGroup();
        explicitTrustStoreOnlyGroup = new javax.swing.ButtonGroup();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        listenerPortField = new com.webreach.mirth.client.ui.MirthTextField();
        receiveTimeoutField = new com.webreach.mirth.client.ui.MirthTextField();
        bufferSizeField = new com.webreach.mirth.client.ui.MirthTextField();
        keyStoreField = new com.webreach.mirth.client.ui.MirthTextField();
        keyStorePasswordField = new com.webreach.mirth.client.ui.MirthTextField();
        keyStoreTypeField = new com.webreach.mirth.client.ui.MirthTextField();
        keyManagerAlgorithmField = new com.webreach.mirth.client.ui.MirthTextField();
        protocolHandlerField = new com.webreach.mirth.client.ui.MirthTextField();
        securityProviderField = new com.webreach.mirth.client.ui.MirthTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        clientKeystoreField = new com.webreach.mirth.client.ui.MirthTextField();
        clientKeystorePasswordField = new com.webreach.mirth.client.ui.MirthTextField();
        trustKeystoreField = new com.webreach.mirth.client.ui.MirthTextField();
        trustKeystorePasswordField = new com.webreach.mirth.client.ui.MirthTextField();
        keyManagerFactoryField = new com.webreach.mirth.client.ui.MirthTextField();
        startOfMessageCharacterField = new com.webreach.mirth.client.ui.MirthTextField();
        endOfMessageCharacterField = new com.webreach.mirth.client.ui.MirthTextField();
        recordSeparatorField = new com.webreach.mirth.client.ui.MirthTextField();
        fieldSeparatorField = new com.webreach.mirth.client.ui.MirthTextField();
        sendACKCombobox = new com.webreach.mirth.client.ui.MirthComboBox();
        keepConnectionOpenYesRadio = new com.webreach.mirth.client.ui.MirthRadioButton();
        keepConnectionOpenNoRadio = new com.webreach.mirth.client.ui.MirthRadioButton();
        requireClientAuthenticationYesRadio = new com.webreach.mirth.client.ui.MirthRadioButton();
        requireClientAuthenticationNoRadio = new com.webreach.mirth.client.ui.MirthRadioButton();
        explicitTrustStoreOnlyYesRadio = new com.webreach.mirth.client.ui.MirthRadioButton();
        explicitTrustStoreOnlyNoRadio = new com.webreach.mirth.client.ui.MirthRadioButton();
        listenerIPAddressField2 = new com.webreach.mirth.client.ui.MirthTextField();
        jLabel9 = new javax.swing.JLabel();
        listenerIPAddressField3 = new com.webreach.mirth.client.ui.MirthTextField();
        jLabel25 = new javax.swing.JLabel();
        listenerIPAddressField1 = new com.webreach.mirth.client.ui.MirthTextField();
        jLabel26 = new javax.swing.JLabel();
        listenerIPAddressField = new com.webreach.mirth.client.ui.MirthTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HTTPS Listener", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
        jLabel13.setText("Keep Connection Open:");

        jLabel14.setText("Key Store Tye:");

        jLabel15.setText("Buffer Size:");

        jLabel16.setText("Receive Timeout (ms):");

        jLabel17.setText("Listener Port:");

        jLabel18.setText("Listener IP Address:");

        jLabel19.setText("Key Manager Algorithm:");

        jLabel20.setText("Protocol Handler:");

        jLabel1.setText("Require Client Authentication:");

        jLabel2.setText("Security Provider:");

        jLabel3.setText("Client Keystore:");

        jLabel4.setText("Client Keystore Password:");

        jLabel5.setText("Trust Keystore:");

        jLabel6.setText("Trust Keystore Password:");

        jLabel7.setText("Explicit Trust Store Only:");

        jLabel8.setText("Key Manager Factory:");

        jLabel10.setText("Start of Message Character:");

        jLabel11.setText("End of Message Character:");

        jLabel12.setText("Record Sparator:");

        jLabel21.setText("Field Separator:");

        jLabel22.setText("Send ACK:");

        jLabel23.setText("Key Store:");

        jLabel24.setText("Key Store Password:");

        sendACKCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Auto", "Yes", "No" }));

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

        requireClientAuthenticationYesRadio.setBackground(new java.awt.Color(255, 255, 255));
        requireClientAuthenticationYesRadio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        requireClientAuthenticationGroup.add(requireClientAuthenticationYesRadio);
        requireClientAuthenticationYesRadio.setText("Yes");
        requireClientAuthenticationYesRadio.setMargin(new java.awt.Insets(0, 0, 0, 0));

        requireClientAuthenticationNoRadio.setBackground(new java.awt.Color(255, 255, 255));
        requireClientAuthenticationNoRadio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        requireClientAuthenticationGroup.add(requireClientAuthenticationNoRadio);
        requireClientAuthenticationNoRadio.setText("No");
        requireClientAuthenticationNoRadio.setMargin(new java.awt.Insets(0, 0, 0, 0));

        explicitTrustStoreOnlyYesRadio.setBackground(new java.awt.Color(255, 255, 255));
        explicitTrustStoreOnlyYesRadio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        explicitTrustStoreOnlyGroup.add(explicitTrustStoreOnlyYesRadio);
        explicitTrustStoreOnlyYesRadio.setText("Yes");
        explicitTrustStoreOnlyYesRadio.setMargin(new java.awt.Insets(0, 0, 0, 0));

        explicitTrustStoreOnlyNoRadio.setBackground(new java.awt.Color(255, 255, 255));
        explicitTrustStoreOnlyNoRadio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        explicitTrustStoreOnlyGroup.add(explicitTrustStoreOnlyNoRadio);
        explicitTrustStoreOnlyNoRadio.setText("No");
        explicitTrustStoreOnlyNoRadio.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel9.setText(".");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel25.setText(".");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel26.setText(".");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel23)
                    .add(jLabel12)
                    .add(jLabel10)
                    .add(jLabel8)
                    .add(jLabel7)
                    .add(jLabel6)
                    .add(jLabel5)
                    .add(jLabel4)
                    .add(jLabel3)
                    .add(jLabel2)
                    .add(jLabel20)
                    .add(jLabel1)
                    .add(jLabel19)
                    .add(jLabel13)
                    .add(jLabel15)
                    .add(jLabel16)
                    .add(jLabel17)
                    .add(jLabel18)
                    .add(jLabel14)
                    .add(jLabel24)
                    .add(jLabel22)
                    .add(jLabel11)
                    .add(jLabel21))
                .add(17, 17, 17)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(explicitTrustStoreOnlyYesRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(explicitTrustStoreOnlyNoRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(listenerPortField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(receiveTimeoutField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(bufferSizeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(keyStoreField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(keyStorePasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(keyStoreTypeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(keyManagerAlgorithmField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(protocolHandlerField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(securityProviderField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(clientKeystoreField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(clientKeystorePasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(trustKeystoreField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(trustKeystorePasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(keyManagerFactoryField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(startOfMessageCharacterField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(endOfMessageCharacterField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(recordSeparatorField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(fieldSeparatorField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(sendACKCombobox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .add(keepConnectionOpenYesRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(keepConnectionOpenNoRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(requireClientAuthenticationYesRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(requireClientAuthenticationNoRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(listenerIPAddressField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel9)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(listenerIPAddressField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel26)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(listenerIPAddressField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel25)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(listenerIPAddressField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel18)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(listenerIPAddressField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(listenerIPAddressField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(listenerIPAddressField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(listenerIPAddressField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jLabel25)
                    .add(jLabel26)
                    .add(jLabel9))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel17)
                    .add(listenerPortField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel16)
                    .add(receiveTimeoutField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel15)
                    .add(bufferSizeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel13)
                    .add(keepConnectionOpenYesRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(keepConnectionOpenNoRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel23)
                    .add(keyStoreField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel24)
                    .add(keyStorePasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel14)
                    .add(keyStoreTypeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel19)
                    .add(keyManagerAlgorithmField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel20)
                    .add(protocolHandlerField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(requireClientAuthenticationYesRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(requireClientAuthenticationNoRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(securityProviderField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(clientKeystoreField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(clientKeystorePasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(trustKeystoreField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(trustKeystorePasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel7)
                    .add(explicitTrustStoreOnlyYesRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(explicitTrustStoreOnlyNoRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(7, 7, 7)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel8)
                    .add(keyManagerFactoryField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel10)
                    .add(startOfMessageCharacterField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(endOfMessageCharacterField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel11))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel12)
                    .add(recordSeparatorField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(fieldSeparatorField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel21))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(sendACKCombobox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel22))
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.webreach.mirth.client.ui.MirthTextField bufferSizeField;
    private com.webreach.mirth.client.ui.MirthTextField clientKeystoreField;
    private com.webreach.mirth.client.ui.MirthTextField clientKeystorePasswordField;
    private com.webreach.mirth.client.ui.MirthTextField endOfMessageCharacterField;
    private javax.swing.ButtonGroup explicitTrustStoreOnlyGroup;
    private com.webreach.mirth.client.ui.MirthRadioButton explicitTrustStoreOnlyNoRadio;
    private com.webreach.mirth.client.ui.MirthRadioButton explicitTrustStoreOnlyYesRadio;
    private com.webreach.mirth.client.ui.MirthTextField fieldSeparatorField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.ButtonGroup keepConnectionOpenGroup;
    private com.webreach.mirth.client.ui.MirthRadioButton keepConnectionOpenNoRadio;
    private com.webreach.mirth.client.ui.MirthRadioButton keepConnectionOpenYesRadio;
    private com.webreach.mirth.client.ui.MirthTextField keyManagerAlgorithmField;
    private com.webreach.mirth.client.ui.MirthTextField keyManagerFactoryField;
    private com.webreach.mirth.client.ui.MirthTextField keyStoreField;
    private com.webreach.mirth.client.ui.MirthTextField keyStorePasswordField;
    private com.webreach.mirth.client.ui.MirthTextField keyStoreTypeField;
    private com.webreach.mirth.client.ui.MirthTextField listenerIPAddressField;
    private com.webreach.mirth.client.ui.MirthTextField listenerIPAddressField1;
    private com.webreach.mirth.client.ui.MirthTextField listenerIPAddressField2;
    private com.webreach.mirth.client.ui.MirthTextField listenerIPAddressField3;
    private com.webreach.mirth.client.ui.MirthTextField listenerPortField;
    private com.webreach.mirth.client.ui.MirthTextField protocolHandlerField;
    private com.webreach.mirth.client.ui.MirthTextField receiveTimeoutField;
    private com.webreach.mirth.client.ui.MirthTextField recordSeparatorField;
    private javax.swing.ButtonGroup requireClientAuthenticationGroup;
    private com.webreach.mirth.client.ui.MirthRadioButton requireClientAuthenticationNoRadio;
    private com.webreach.mirth.client.ui.MirthRadioButton requireClientAuthenticationYesRadio;
    private com.webreach.mirth.client.ui.MirthTextField securityProviderField;
    private com.webreach.mirth.client.ui.MirthComboBox sendACKCombobox;
    private com.webreach.mirth.client.ui.MirthTextField startOfMessageCharacterField;
    private com.webreach.mirth.client.ui.MirthTextField trustKeystoreField;
    private com.webreach.mirth.client.ui.MirthTextField trustKeystorePasswordField;
    // End of variables declaration//GEN-END:variables
}