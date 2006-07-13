package com.webreach.mirth.client.ui;

import com.webreach.mirth.model.Channel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/** Creates the new channel wizard dialog */
public class ChannelWizard extends javax.swing.JDialog
{
    private Frame parent;
    private Channel channel;
    
    /** Creates new form WizardDialog */
    public ChannelWizard()
    {
        super(PlatformUI.MIRTH_FRAME);
        this.parent = PlatformUI.MIRTH_FRAME;
        initComponents();
        channelName.setText("");
        nextButton.setEnabled(false);
        channelPattern.setVisible(false);
        channelOverview.setVisible(true);
        broadcastActionPerformed(null);
        inboundActionPerformed(null);
        jLabel2.setForeground(UIConstants.TITLE_TEXT_COLOR);
        jLabel5.setForeground(UIConstants.TITLE_TEXT_COLOR);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        channelOverview = new javax.swing.JPanel();
        nextButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        channelName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        outbound = new javax.swing.JRadioButton();
        inbound = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        mirthHeadingPanel2 = new com.webreach.mirth.client.ui.MirthHeadingPanel();
        jLabel2 = new javax.swing.JLabel();
        helpTextPane1 = new javax.swing.JScrollPane();
        helpText1 = new javax.swing.JTextPane();
        channelPattern = new javax.swing.JPanel();
        finishButton = new javax.swing.JButton();
        cancelButton2 = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        broadcast = new javax.swing.JRadioButton();
        router = new javax.swing.JRadioButton();
        applicationIntegration = new javax.swing.JRadioButton();
        mirthHeadingPanel1 = new com.webreach.mirth.client.ui.MirthHeadingPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        helpTextPane2 = new javax.swing.JScrollPane();
        helpText2 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create Channel Wizard");
        channelOverview.setBackground(new java.awt.Color(255, 255, 255));
        channelOverview.setName("");
        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                nextButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cancelButtonActionPerformed(evt);
            }
        });

        channelName.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                channelNameKeyReleased(evt);
            }
        });

        jLabel1.setText("Channel Name:");

        outbound.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(outbound);
        outbound.setText("Outbound");
        outbound.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        outbound.setMargin(new java.awt.Insets(0, 0, 0, 0));
        outbound.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                outboundActionPerformed(evt);
            }
        });

        inbound.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(inbound);
        inbound.setSelected(true);
        inbound.setText("Inbound");
        inbound.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        inbound.setMargin(new java.awt.Insets(0, 0, 0, 0));
        inbound.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                inboundActionPerformed(evt);
            }
        });

        jLabel3.setText("Direction:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Channel Overview");

        org.jdesktop.layout.GroupLayout mirthHeadingPanel2Layout = new org.jdesktop.layout.GroupLayout(mirthHeadingPanel2);
        mirthHeadingPanel2.setLayout(mirthHeadingPanel2Layout);
        mirthHeadingPanel2Layout.setHorizontalGroup(
            mirthHeadingPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mirthHeadingPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 165, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(196, Short.MAX_VALUE))
        );
        mirthHeadingPanel2Layout.setVerticalGroup(
            mirthHeadingPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mirthHeadingPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addContainerGap())
        );

        helpText1.setEditable(false);
        helpTextPane1.setViewportView(helpText1);

        org.jdesktop.layout.GroupLayout channelOverviewLayout = new org.jdesktop.layout.GroupLayout(channelOverview);
        channelOverview.setLayout(channelOverviewLayout);
        channelOverviewLayout.setHorizontalGroup(
            channelOverviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(channelOverviewLayout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .add(channelOverviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, channelOverviewLayout.createSequentialGroup()
                        .add(cancelButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(nextButton)
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, channelOverviewLayout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(channelName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 167, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(53, 53, 53))))
            .add(channelOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 351, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(mirthHeadingPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
            .add(channelOverviewLayout.createSequentialGroup()
                .add(99, 99, 99)
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(channelOverviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(inbound)
                    .add(outbound))
                .addContainerGap(157, Short.MAX_VALUE))
            .add(channelOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .add(helpTextPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addContainerGap())
        );

        channelOverviewLayout.linkSize(new java.awt.Component[] {cancelButton, nextButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        channelOverviewLayout.setVerticalGroup(
            channelOverviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, channelOverviewLayout.createSequentialGroup()
                .add(mirthHeadingPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 49, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(21, 21, 21)
                .add(channelOverviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(channelName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(channelOverviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(inbound)
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(outbound)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 14, Short.MAX_VALUE)
                .add(helpTextPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(14, 14, 14)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(channelOverviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(nextButton)
                    .add(cancelButton))
                .addContainerGap())
        );

        channelPattern.setBackground(new java.awt.Color(255, 255, 255));
        finishButton.setText("Finish");
        finishButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                finishButtonActionPerformed(evt);
            }
        });

        cancelButton2.setText("Cancel");
        cancelButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cancelButton2ActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                backButtonActionPerformed(evt);
            }
        });

        broadcast.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(broadcast);
        broadcast.setSelected(true);
        broadcast.setText("Broadcast");
        broadcast.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        broadcast.setMargin(new java.awt.Insets(0, 0, 0, 0));
        broadcast.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                broadcastActionPerformed(evt);
            }
        });

        router.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(router);
        router.setText("Router");
        router.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        router.setMargin(new java.awt.Insets(0, 0, 0, 0));
        router.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                routerActionPerformed(evt);
            }
        });

        applicationIntegration.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(applicationIntegration);
        applicationIntegration.setText("Application Integration");
        applicationIntegration.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        applicationIntegration.setEnabled(false);
        applicationIntegration.setMargin(new java.awt.Insets(0, 0, 0, 0));
        applicationIntegration.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                applicationIntegrationActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Channel Type");

        org.jdesktop.layout.GroupLayout mirthHeadingPanel1Layout = new org.jdesktop.layout.GroupLayout(mirthHeadingPanel1);
        mirthHeadingPanel1.setLayout(mirthHeadingPanel1Layout);
        mirthHeadingPanel1Layout.setHorizontalGroup(
            mirthHeadingPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mirthHeadingPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 182, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
        );
        mirthHeadingPanel1Layout.setVerticalGroup(
            mirthHeadingPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mirthHeadingPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel4.setText("Type:");

        helpText2.setBorder(null);
        helpText2.setEditable(false);
        helpTextPane2.setViewportView(helpText2);

        org.jdesktop.layout.GroupLayout channelPatternLayout = new org.jdesktop.layout.GroupLayout(channelPattern);
        channelPattern.setLayout(channelPatternLayout);
        channelPatternLayout.setHorizontalGroup(
            channelPatternLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, channelPatternLayout.createSequentialGroup()
                .addContainerGap(154, Short.MAX_VALUE)
                .add(backButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cancelButton2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(finishButton)
                .addContainerGap())
            .add(channelPatternLayout.createSequentialGroup()
                .addContainerGap()
                .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 351, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(mirthHeadingPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
            .add(channelPatternLayout.createSequentialGroup()
                .addContainerGap()
                .add(helpTextPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, channelPatternLayout.createSequentialGroup()
                .addContainerGap(119, Short.MAX_VALUE)
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(channelPatternLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(broadcast)
                    .add(router)
                    .add(applicationIntegration))
                .add(93, 93, 93))
        );

        channelPatternLayout.linkSize(new java.awt.Component[] {backButton, cancelButton2, finishButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        channelPatternLayout.setVerticalGroup(
            channelPatternLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, channelPatternLayout.createSequentialGroup()
                .add(mirthHeadingPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 49, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 23, Short.MAX_VALUE)
                .add(channelPatternLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(broadcast)
                    .add(jLabel4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(router)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(applicationIntegration)
                .add(16, 16, 16)
                .add(helpTextPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(14, 14, 14)
                .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(channelPatternLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(finishButton)
                    .add(cancelButton2)
                    .add(backButton))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(channelOverview, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(channelPattern, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(channelOverview, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(channelPattern, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void applicationIntegrationActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_applicationIntegrationActionPerformed
    {//GEN-HEADEREND:event_applicationIntegrationActionPerformed
        helpText2.setText("Single HL7 data feed that will be filtered and transformed into another data type to be used by an application. The application response is sent back as a formatted and validated HL7 message. This pattern would be used with synchronous responses such as web services or remote procedure calls.");
    }//GEN-LAST:event_applicationIntegrationActionPerformed

    private void routerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_routerActionPerformed
    {//GEN-HEADEREND:event_routerActionPerformed
        helpText2.setText("Single HL7 data feed that will be routed to several systems which require their own unique filtering and transformation. Data may also be also transformed and written to a data source such as a database or a file system.");
    }//GEN-LAST:event_routerActionPerformed

    private void broadcastActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_broadcastActionPerformed
    {//GEN-HEADEREND:event_broadcastActionPerformed
        helpText2.setText("Single HL7 data feed that will be filtered, transformed, and broadcast to several systems.");
    }//GEN-LAST:event_broadcastActionPerformed

    private void channelNameKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_channelNameKeyReleased
    {//GEN-HEADEREND:event_channelNameKeyReleased
        if(channelName.getText().trim().equals(""))
            nextButton.setEnabled(false);      
        else
            nextButton.setEnabled(true);
        
    }//GEN-LAST:event_channelNameKeyReleased

    private void outboundActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_outboundActionPerformed
    {//GEN-HEADEREND:event_outboundActionPerformed
        helpText1.setText("The server will read data from a database and create HL7 messages.");
        this.nextButton.setText("Finish");
    }//GEN-LAST:event_outboundActionPerformed

    private void finishButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_finishButtonActionPerformed
    {//GEN-HEADEREND:event_finishButtonActionPerformed
        createChannel();
    }//GEN-LAST:event_finishButtonActionPerformed

    /** Is called after the wizard is complete in order to create the
     *  new channel and load it up.
     */
    private void createChannel()
    {
        for (int i = 0; i < parent.channels.size(); i++)
        {
            if (!parent.checkChannelName(channelName.getText()))
                return;
        }
        channel = new Channel();
        channel.setName(channelName.getText());
        if(inbound.isSelected())
            channel.setDirection(Channel.Direction.INBOUND);
        else
            channel.setDirection(Channel.Direction.OUTBOUND);
        
        if(inbound.isSelected())
        {
            if(broadcast.isSelected())
                channel.setMode(Channel.Mode.BROADCAST);
            else if(router.isSelected())
                channel.setMode(Channel.Mode.ROUTER);
            else if(applicationIntegration.isSelected())
                channel.setMode(Channel.Mode.APPLICATION);
        }
        else
            channel.setMode(Channel.Mode.BROADCAST);
        parent.setupChannel(channel);
        this.dispose();
    }
    
    private void inboundActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_inboundActionPerformed
    {//GEN-HEADEREND:event_inboundActionPerformed
        helpText1.setText("The server will listen for HL7 messages or read them from a file and transform them for use by other systems, or into different HL7 messages.");
        this.nextButton.setText("Next");
    }//GEN-LAST:event_inboundActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
    {//GEN-HEADEREND:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void cancelButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButton2ActionPerformed
    {//GEN-HEADEREND:event_cancelButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButton2ActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_backButtonActionPerformed
    {//GEN-HEADEREND:event_backButtonActionPerformed
        channelOverview.setVisible(true);
        channelPattern.setVisible(false);
    }//GEN-LAST:event_backButtonActionPerformed

    /** If the button is "Next" instead of "Finish" then it moves on to the
     *  next options.  Otherwise, it creates the new channel.
     */
    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nextButtonActionPerformed
    {//GEN-HEADEREND:event_nextButtonActionPerformed
        parent.doRefreshChannels();
        for (int i = 0; i < parent.channels.size(); i++)
        {
            if (!parent.checkChannelName(channelName.getText()))
                return;
        }
        
        if(this.nextButton.getText().equals("Next"))
        {
            channelOverview.setVisible(false);
            channelPattern.setVisible(true);
        }
        else
        {
            createChannel();
        }
    }//GEN-LAST:event_nextButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton applicationIntegration;
    private javax.swing.JButton backButton;
    private javax.swing.JRadioButton broadcast;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton cancelButton2;
    private javax.swing.JTextField channelName;
    private javax.swing.JPanel channelOverview;
    private javax.swing.JPanel channelPattern;
    private javax.swing.JButton finishButton;
    private javax.swing.JTextPane helpText1;
    private javax.swing.JTextPane helpText2;
    private javax.swing.JScrollPane helpTextPane1;
    private javax.swing.JScrollPane helpTextPane2;
    private javax.swing.JRadioButton inbound;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private com.webreach.mirth.client.ui.MirthHeadingPanel mirthHeadingPanel1;
    private com.webreach.mirth.client.ui.MirthHeadingPanel mirthHeadingPanel2;
    private javax.swing.JButton nextButton;
    private javax.swing.JRadioButton outbound;
    private javax.swing.JRadioButton router;
    // End of variables declaration//GEN-END:variables
    
}
