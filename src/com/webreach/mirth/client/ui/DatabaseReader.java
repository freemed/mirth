package com.webreach.mirth.client.ui;

import com.Ostermiller.Syntax.HighlightedDocument;
import java.util.Properties;

public class DatabaseReader extends ConnectorClass
{
    Frame parent;
    /** Creates new form DatabaseWriter */
    public final String DATATYPE = "DataType";
    public final String DATABASE_DRIVER = "Driver";
    public final String SUN_JDBC_ODBC_BRIDGE = "Sun JDBC-ODBC Bridge";
    public final String ODBC_MYSQL = "ODBC - MySQL";
    public final String ODBC_POSTGRESQL = "ODBC - PostgresSQL";
    public final String ODBC_SQL_SERVER_SYBASE = "ODBC - SQL Server/Sybase";
    public final String ODBC_ORACLE_10G_RELEASE_2 = "ODBC - Oracle 10g Release 2";
    public final String SUN_JDBC_ODBC_JDBCODBCDRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
    public final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public final String ORG_POSTGRESQL_DRIVER = "org.postgresql.Driver";
    public final String NET_SOURCEFORGE_JTDS_JDBC_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    public final String ORACLE_JDBC_ORACLEDRIVER = "oracle.jdbc.OracleDriver";
    public final String DATABASE_URL = "URL";
    public final String DATABASE_USERNAME = "username";
    public final String DATABASE_PASSWORD = "password";
    public final String DATABASE_POLLING_FREQUENCY = "pollingFrequency";
    public final String DATABASE_SQL_STATEMENT = "query";
    public final String DATABASE_USE_ACK = "useAck";
    public final String DATABASE_ACK = "ack";
    
    private HighlightedDocument mappingDoc;
    private HighlightedDocument mappingDoc2;
    
    public DatabaseReader()
    {
        this.parent = PlatformUI.MIRTH_FRAME;
        name = "Database Reader";
        initComponents();
        mappingDoc = new HighlightedDocument();
        mappingDoc.setHighlightStyle(HighlightedDocument.SQL_STYLE);
        mappingDoc2 = new HighlightedDocument();
        mappingDoc2.setHighlightStyle(HighlightedDocument.SQL_STYLE);
        databaseSQLTextPane.setDocument(mappingDoc);
        databaseUpdateSQLTextPane.setDocument(mappingDoc2);
    }

    public Properties getProperties()
    {
        Properties properties = new Properties();
        properties.put(DATATYPE, name);
        
        if(databaseDriverCombobox.getSelectedItem().equals(SUN_JDBC_ODBC_BRIDGE))
            properties.put(DATABASE_DRIVER, SUN_JDBC_ODBC_JDBCODBCDRIVER);
        else if(databaseDriverCombobox.getSelectedItem().equals(ODBC_MYSQL))
            properties.put(DATABASE_DRIVER, COM_MYSQL_JDBC_DRIVER);
        else if(databaseDriverCombobox.getSelectedItem().equals(ODBC_POSTGRESQL))
            properties.put(DATABASE_DRIVER, ORG_POSTGRESQL_DRIVER);
        else if(databaseDriverCombobox.getSelectedItem().equals(ODBC_SQL_SERVER_SYBASE))
            properties.put(DATABASE_DRIVER, NET_SOURCEFORGE_JTDS_JDBC_DRIVER);
        else if(databaseDriverCombobox.getSelectedItem().equals(ODBC_ORACLE_10G_RELEASE_2))
            properties.put(DATABASE_DRIVER, ORACLE_JDBC_ORACLEDRIVER);
        
        properties.put(DATABASE_URL, databaseURLField.getText());
        properties.put(DATABASE_USERNAME, databaseUsernameField.getText());
        properties.put(DATABASE_PASSWORD, new String(databasePasswordField.getPassword()));
        properties.put(DATABASE_POLLING_FREQUENCY, pollingFreq.getText());
        properties.put(DATABASE_SQL_STATEMENT, databaseSQLTextPane.getText());
        
        if (readOnUpdateYes.isSelected())
            properties.put(DATABASE_USE_ACK, "YES");
        else
            properties.put(DATABASE_USE_ACK, "NO");
        
        properties.put(DATABASE_ACK, databaseUpdateSQLTextPane.getText());
        return properties;
    }

    public void setProperties(Properties props)
    {
        boolean visible = parent.channelEditTasks.getContentPane().getComponent(0).isVisible();
        
        if(props.get(DATABASE_DRIVER).equals(SUN_JDBC_ODBC_JDBCODBCDRIVER))
            databaseDriverCombobox.setSelectedItem(SUN_JDBC_ODBC_BRIDGE);
        else if(props.get(DATABASE_DRIVER).equals(COM_MYSQL_JDBC_DRIVER))
            databaseDriverCombobox.setSelectedItem(ODBC_MYSQL);
        else if(props.get(DATABASE_DRIVER).equals(ORG_POSTGRESQL_DRIVER))
            databaseDriverCombobox.setSelectedItem(ODBC_POSTGRESQL);
        else if(props.get(DATABASE_DRIVER).equals(NET_SOURCEFORGE_JTDS_JDBC_DRIVER))
            databaseDriverCombobox.setSelectedItem(ODBC_SQL_SERVER_SYBASE);
        else if(props.get(DATABASE_DRIVER).equals(ORACLE_JDBC_ORACLEDRIVER))
            databaseDriverCombobox.setSelectedItem(ODBC_ORACLE_10G_RELEASE_2);
        
        parent.channelEditTasks.getContentPane().getComponent(0).setVisible(visible);
        databaseURLField.setText((String)props.get(DATABASE_URL));
        databaseUsernameField.setText((String)props.get(DATABASE_USERNAME));
        databasePasswordField.setText((String)props.get(DATABASE_PASSWORD));
        pollingFreq.setText((String)props.get(DATABASE_POLLING_FREQUENCY));
        databaseSQLTextPane.setText((String)props.get(DATABASE_SQL_STATEMENT));
        
        if(((String)props.get(DATABASE_USE_ACK)).equalsIgnoreCase("YES"))
            readOnUpdateYes.setSelected(true);
        else
            readOnUpdateNo.setSelected(true);
        
        databaseUpdateSQLTextPane.setText((String)props.get(DATABASE_ACK));
    }

    public void setDefaults()
    {
        databaseDriverCombobox.setSelectedItem(0);
        databaseURLField.setText("");
        databaseUsernameField.setText("");
        databasePasswordField.setText("");
        pollingFreq.setText("");
        databaseSQLTextPane.setText("");
        readOnUpdateNo.setSelected(true);
        databaseUpdateSQLTextPane.setText("");
    }
    
    public Properties getDefaults()
    {
        Properties properties = new Properties();
        properties.put(DATATYPE, name);
        properties.put(DATABASE_DRIVER, COM_MYSQL_JDBC_DRIVER);
        properties.put(DATABASE_URL, "");
        properties.put(DATABASE_USERNAME, "");
        properties.put(DATABASE_PASSWORD, "");
        properties.put(DATABASE_POLLING_FREQUENCY, "5000");
        properties.put(DATABASE_SQL_STATEMENT, "SELECT FROM");
        properties.put(DATABASE_USE_ACK, "NO");
        properties.put(DATABASE_ACK, "UPDATE");
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
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        databaseDriverCombobox = new com.webreach.mirth.client.ui.MirthComboBox();
        databaseURLField = new com.webreach.mirth.client.ui.MirthTextField();
        databaseUsernameField = new com.webreach.mirth.client.ui.MirthTextField();
        databasePasswordField = new com.webreach.mirth.client.ui.MirthPasswordField();
        jScrollPane2 = new javax.swing.JScrollPane();
        databaseSQLTextPane = new com.webreach.mirth.client.ui.MirthTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        databaseUpdateSQLTextPane = new com.webreach.mirth.client.ui.MirthTextPane();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pollingFreq = new com.webreach.mirth.client.ui.MirthTextField();
        readOnUpdateYes = new com.webreach.mirth.client.ui.MirthRadioButton();
        readOnUpdateNo = new com.webreach.mirth.client.ui.MirthRadioButton();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Database Reader", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
        jLabel1.setText("Driver:");

        jLabel2.setText("Database URL:");

        jLabel3.setText("Username:");

        jLabel4.setText("Password:");

        jLabel5.setText("SQL Statement:");

        databaseDriverCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sun JDBC-ODBC Bridge", "ODBC - MySQL", "ODBC - PostgresSQL", "ODBC - SQL Server/Sybase", "ODBC - Oracle 10g Release 2" }));

        databasePasswordField.setFont(new java.awt.Font("Tahoma", 0, 11));

        jScrollPane2.setViewportView(databaseSQLTextPane);

        jScrollPane3.setViewportView(databaseUpdateSQLTextPane);

        jLabel6.setText("On-Update Statement:");

        jLabel7.setText("Polling Frequency:");

        readOnUpdateYes.setBackground(new java.awt.Color(255, 255, 255));
        readOnUpdateYes.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        buttonGroup1.add(readOnUpdateYes);
        readOnUpdateYes.setText("Yes");
        readOnUpdateYes.setMargin(new java.awt.Insets(0, 0, 0, 0));

        readOnUpdateNo.setBackground(new java.awt.Color(255, 255, 255));
        readOnUpdateNo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        buttonGroup1.add(readOnUpdateNo);
        readOnUpdateNo.setSelected(true);
        readOnUpdateNo.setText("No");
        readOnUpdateNo.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel8.setText("Read On-Update:");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(48, 48, 48)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel2)
                            .add(jLabel1)
                            .add(jLabel3)
                            .add(jLabel4))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(databaseURLField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 145, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(databaseDriverCombobox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 175, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, databasePasswordField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, databaseUsernameField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel7)
                            .add(jLabel5)
                            .add(jLabel8)
                            .add(jLabel6))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 396, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 396, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(pollingFreq, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(layout.createSequentialGroup()
                                .add(readOnUpdateYes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(readOnUpdateNo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        layout.linkSize(new java.awt.Component[] {databaseDriverCombobox, databaseURLField}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(databaseDriverCombobox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(databaseURLField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(databaseUsernameField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel3)))
                    .add(layout.createSequentialGroup()
                        .add(50, 50, 50)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(databasePasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel4))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel7)
                    .add(pollingFreq, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel5)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 145, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(readOnUpdateYes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(readOnUpdateNo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 145, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(85, 85, 85))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private com.webreach.mirth.client.ui.MirthComboBox databaseDriverCombobox;
    private com.webreach.mirth.client.ui.MirthPasswordField databasePasswordField;
    private com.webreach.mirth.client.ui.MirthTextPane databaseSQLTextPane;
    private com.webreach.mirth.client.ui.MirthTextField databaseURLField;
    private com.webreach.mirth.client.ui.MirthTextPane databaseUpdateSQLTextPane;
    private com.webreach.mirth.client.ui.MirthTextField databaseUsernameField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.webreach.mirth.client.ui.MirthTextField pollingFreq;
    private com.webreach.mirth.client.ui.MirthRadioButton readOnUpdateNo;
    private com.webreach.mirth.client.ui.MirthRadioButton readOnUpdateYes;
    // End of variables declaration//GEN-END:variables

}
