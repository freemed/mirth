package com.webreach.mirth.client.ui.browsers.message;

import com.Ostermiller.Syntax.HighlightedDocument;
import com.webreach.mirth.client.core.ClientException;
import com.webreach.mirth.client.ui.Frame;
import com.webreach.mirth.client.ui.HL7TreePanel;
import com.webreach.mirth.client.ui.PlatformUI;
import com.webreach.mirth.client.ui.UIConstants;
import com.webreach.mirth.model.MessageEvent;
import com.webreach.mirth.model.converters.ER7Serializer;
import com.webreach.mirth.model.filters.MessageEventFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;
import org.jdesktop.swingx.decorator.HighlighterPipeline;

public class MessageBrowser extends javax.swing.JPanel
{
    public static final String MESSAGE_ID_TABLE_NAME = "Message ID";
    public static final String CHANNEL_ID_TABLE_NAME = "Channel ID";
    public static final String DATE_TABLE_NAME = "Date";
    public static final String SENDING_FACILITY_TABLE_NAME = "Sending Facility";
    public static final String EVENT_TABLE_NAME = "Event";
    public static final String CONTROL_ID_TABLE_NAME = "Control ID";
    public static final String STATUS_TABLE_NAME = "Status";
    
    private JScrollPane eventPane;
    private JXTable eventTable;
    private Frame parent;
    private List<MessageEvent> messageEventList;
    private HL7TreePanel HL7Panel;
    private JScrollPane HL7ScrollPane;
    private HighlightedDocument mappingDoc;
    private Document normalDoc;
    
    public MessageBrowser()
    {
        this.parent = PlatformUI.MIRTH_FRAME;
        initComponents();
        
        mappingDoc = new HighlightedDocument();
        mappingDoc.setHighlightStyle(HighlightedDocument.HTML_STYLE);
        normalDoc = XMLTextPane.getDocument();
        
        HL7Panel = new HL7TreePanel();
        HL7Panel.setBackground(Color.white);
        HL7ScrollPane = new JScrollPane();
        HL7ScrollPane.setViewportView(HL7Panel);
        descriptionTabbedPane.addTab("HL7", HL7ScrollPane);
        
        String[] values = new String[MessageEvent.Status.values().length + 1];
        values[0] = "ALL";
        for (int i = 1; i < values.length; i++)
            values[i] = MessageEvent.Status.values()[i-1].toString();
        
        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel(values));
        
        eventPane = new JScrollPane();
        
        eventPane.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                deselectRows();
            }
        });
        
        eventPane.setViewportView(eventTable);
        
        jPanel2.removeAll();  
        
        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(eventPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(eventPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );
        
        jPanel2.updateUI();
    }
    
    public void loadNew()
    {
        // use the start filters and make the table.
        sendingFacilityField.setText("");
        controlIDField.setText("");
        statusComboBox.setSelectedIndex(0);
        long currentTime = System.currentTimeMillis();
        mirthDatePicker1.setDateInMillis(currentTime);
        mirthDatePicker2.setDateInMillis(currentTime);
        
        filterButtonActionPerformed(null);
        clearDescription();
        descriptionTabbedPane.setSelectedIndex(0);
    }
    
    public void makeEventTable(MessageEventFilter filter) {
        eventTable = new JXTable();
        try 
        {
            messageEventList = parent.mirthClient.getMessageEvents(filter);
        } 
        catch (ClientException ex)
        {
            messageEventList = null;
            ex.printStackTrace();
        }
        
        if (messageEventList == null)
            return;
                
        Object[][] tableData = new Object[messageEventList.size()][7];
        
        for (int i=0; i < messageEventList.size(); i++)
        {
            MessageEvent messageEvent = messageEventList.get(i);
            
            tableData[i][0] = messageEvent.getId();
            tableData[i][1] = messageEvent.getChannelId();
            
            Calendar calendar = messageEvent.getDate();
            
            tableData[i][2] = String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", calendar);
            tableData[i][3] = messageEvent.getSendingFacility();
            tableData[i][4] = messageEvent.getEvent();
            tableData[i][5] = messageEvent.getControlId();
            tableData[i][6] = messageEvent.getStatus();
            
        }
                
        
        eventTable.setModel(new javax.swing.table.DefaultTableModel(
                tableData,
                new String []
        {
            MESSAGE_ID_TABLE_NAME, CHANNEL_ID_TABLE_NAME, DATE_TABLE_NAME, SENDING_FACILITY_TABLE_NAME, EVENT_TABLE_NAME, CONTROL_ID_TABLE_NAME, STATUS_TABLE_NAME
        }
        ) {
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false, false
            };
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        eventTable.setSelectionMode(0);        
        
        eventTable.getColumnExt(MESSAGE_ID_TABLE_NAME).setMaxWidth(90);
        eventTable.getColumnExt(MESSAGE_ID_TABLE_NAME).setMinWidth(90);
        eventTable.getColumnExt(CHANNEL_ID_TABLE_NAME).setMaxWidth(90);
        eventTable.getColumnExt(CHANNEL_ID_TABLE_NAME).setMinWidth(90);
        eventTable.getColumnExt(DATE_TABLE_NAME).setMaxWidth(120);
        eventTable.getColumnExt(DATE_TABLE_NAME).setMinWidth(120);
        
        eventTable.setRowHeight(20);
        eventTable.setColumnMargin(2);
        eventTable.setOpaque(true);
        eventTable.setRowSelectionAllowed(true);
        HighlighterPipeline highlighter = new HighlighterPipeline();
        highlighter.addHighlighter(AlternateRowHighlighter.beige);
        eventTable.setHighlighters(highlighter);
        
        eventPane.setViewportView(eventTable);
        
        eventTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent evt)
            {
                EventListSelected(evt);
            }
        });
    }
    
    public void deselectRows()
    {
        eventTable.clearSelection();
        clearDescription();
    }
    
    public void clearDescription()
    {
        ER7TextPane.setText("Select a message to view ER7-encoded HL7 message.");
        XMLTextPane.setDocument(normalDoc);
        XMLTextPane.setText("Select a message to view XML-encoded HL7 message.");
        HL7Panel.clearMessage();
    }
    
    private void EventListSelected(ListSelectionEvent evt)
    {
        if (!evt.getValueIsAdjusting())
        {
            int row = eventTable.getSelectedRow();

            if(row >= 0)
            {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                
                String message = messageEventList.get(row).getMessage();
                ER7TextPane.setText(message.replaceAll("\r", "\n"));
                ER7TextPane.setCaretPosition(0);
                
                ER7Serializer serializer = new ER7Serializer();
                XMLTextPane.setDocument(mappingDoc);
                XMLTextPane.setText(serializer.serialize(message));
                XMLTextPane.setCaretPosition(0);
                
                HL7Panel.setMessage(message);
                
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        filterPanel = new javax.swing.JPanel();
        sendingFacilityLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sendingFacilityField = new javax.swing.JTextField();
        filterButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        mirthDatePicker1 = new com.webreach.mirth.client.ui.MirthDatePicker();
        mirthDatePicker2 = new com.webreach.mirth.client.ui.MirthDatePicker();
        controlIDField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        statusComboBox = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        descriptionPanel = new javax.swing.JPanel();
        descriptionTabbedPane = new javax.swing.JTabbedPane();
        ER7Panel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ER7TextPane = new javax.swing.JTextPane();
        XMLPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        XMLTextPane = new javax.swing.JTextPane();

        setBackground(new java.awt.Color(255, 255, 255));
        filterPanel.setBackground(new java.awt.Color(255, 255, 255));
        filterPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filter By", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
        sendingFacilityLabel.setText("Sending Facility:");

        jLabel3.setText("Start Date:");

        filterButton.setText("Filter");
        filterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("End Date:");

        jLabel1.setText("Control ID:");

        jLabel5.setText("Status:");

        org.jdesktop.layout.GroupLayout filterPanelLayout = new org.jdesktop.layout.GroupLayout(filterPanel);
        filterPanel.setLayout(filterPanelLayout);
        filterPanelLayout.setHorizontalGroup(
            filterPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(filterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(filterPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel3)
                    .add(sendingFacilityLabel)
                    .add(jLabel1)
                    .add(jLabel5))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(filterPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(filterButton)
                    .add(filterPanelLayout.createSequentialGroup()
                        .add(mirthDatePicker1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(mirthDatePicker2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(filterPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, statusComboBox, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, controlIDField)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, sendingFacilityField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        filterPanelLayout.setVerticalGroup(
            filterPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(filterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(filterPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel2)
                    .add(filterPanelLayout.createSequentialGroup()
                        .add(filterPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(sendingFacilityLabel)
                            .add(sendingFacilityField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(filterPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(controlIDField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(filterPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel5)
                            .add(statusComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(filterPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel3)
                            .add(mirthDatePicker2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(mirthDatePicker1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 11, Short.MAX_VALUE)
                .add(filterButton)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        descriptionPanel.setBackground(new java.awt.Color(255, 255, 255));
        ER7Panel.setBackground(new java.awt.Color(255, 255, 255));
        ER7TextPane.setEditable(false);
        jScrollPane2.setViewportView(ER7TextPane);

        org.jdesktop.layout.GroupLayout ER7PanelLayout = new org.jdesktop.layout.GroupLayout(ER7Panel);
        ER7Panel.setLayout(ER7PanelLayout);
        ER7PanelLayout.setHorizontalGroup(
            ER7PanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(ER7PanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                .addContainerGap())
        );
        ER7PanelLayout.setVerticalGroup(
            ER7PanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(ER7PanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addContainerGap())
        );
        descriptionTabbedPane.addTab("ER7", ER7Panel);

        XMLPanel.setBackground(new java.awt.Color(255, 255, 255));
        XMLTextPane.setEditable(false);
        jScrollPane4.setViewportView(XMLTextPane);

        org.jdesktop.layout.GroupLayout XMLPanelLayout = new org.jdesktop.layout.GroupLayout(XMLPanel);
        XMLPanel.setLayout(XMLPanelLayout);
        XMLPanelLayout.setHorizontalGroup(
            XMLPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(XMLPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                .addContainerGap())
        );
        XMLPanelLayout.setVerticalGroup(
            XMLPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(XMLPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addContainerGap())
        );
        descriptionTabbedPane.addTab("XML", XMLPanel);

        org.jdesktop.layout.GroupLayout descriptionPanelLayout = new org.jdesktop.layout.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(descriptionTabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(descriptionTabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(filterPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(descriptionPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(filterPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(descriptionPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void filterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterButtonActionPerformed
        if (mirthDatePicker1.getDate() != null && mirthDatePicker2.getDate() != null)
        {
            if (mirthDatePicker1.getDateInMillis() > mirthDatePicker2.getDateInMillis())
            {
                JOptionPane.showMessageDialog(parent, "Start date cannot be after the end date.");
                return;
            }
        }
        
        MessageEventFilter filter = new MessageEventFilter();
        
        filter.setChannelId(parent.status.get(parent.statusListPage.getSelectedStatus()).getChannelId());
        
        if (!sendingFacilityField.getText().equals(""))
            filter.setSendingFacility(sendingFacilityField.getText());
        
        if (!controlIDField.getText().equals(""))
            filter.setControlId(controlIDField.getText());
        
        if (!((String)statusComboBox.getSelectedItem()).equalsIgnoreCase("ALL"))
        {
            for (int i = 0; i < MessageEvent.Status.values().length; i++)
            {
                if (((String)statusComboBox.getSelectedItem()).equalsIgnoreCase(MessageEvent.Status.values()[i].toString()))
                    filter.setStatus(MessageEvent.Status.values()[i]);
            }
        }
        
        if (mirthDatePicker1.getDate() != null)
        {
            Calendar calendarStart = Calendar.getInstance();
            calendarStart.setTimeInMillis(mirthDatePicker1.getDateInMillis());
            filter.setStartDate(calendarStart);
        }
        if (mirthDatePicker2.getDate() != null)
        {
            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTimeInMillis(mirthDatePicker2.getDateInMillis());
            filter.setEndDate(calendarEnd);
        }
        makeEventTable(filter);
    }//GEN-LAST:event_filterButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ER7Panel;
    private javax.swing.JTextPane ER7TextPane;
    private javax.swing.JPanel XMLPanel;
    private javax.swing.JTextPane XMLTextPane;
    private javax.swing.JTextField controlIDField;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JTabbedPane descriptionTabbedPane;
    private javax.swing.JButton filterButton;
    private javax.swing.JPanel filterPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private com.webreach.mirth.client.ui.MirthDatePicker mirthDatePicker1;
    private com.webreach.mirth.client.ui.MirthDatePicker mirthDatePicker2;
    private javax.swing.JTextField sendingFacilityField;
    private javax.swing.JLabel sendingFacilityLabel;
    private javax.swing.JComboBox statusComboBox;
    // End of variables declaration//GEN-END:variables
    
}
