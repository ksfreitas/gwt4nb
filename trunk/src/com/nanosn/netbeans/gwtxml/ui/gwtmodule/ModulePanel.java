/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 * 
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package com.nanosn.netbeans.gwtxml.ui.gwtmodule;

import com.nanosn.netbeans.gwtxml.GwtXmlDataObject;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.netbeans.modules.xml.multiview.ui.SectionInnerPanel;
import org.netbeans.modules.xml.multiview.ui.SectionView;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

/**
 * 
 * @author selkhateeb
 */
public class ModulePanel extends SectionInnerPanel {
    private static final long serialVersionUID = 1;

    private GwtXmlDataObject dObj;
    private DefaultTableModel TableModuleInherits = new DefaultTableModel(
            new String[]{NbBundle.getMessage(ModulePanel.class, 
            "MODULES")}, 0); // NOI18N
    private DefaultTableModel TableModuleEntryPoints = new DefaultTableModel(
            new String[]{NbBundle.getMessage(ModulePanel.class, 
            "ENTRY POINT")}, 0); // NOI18N
    private DefaultTableModel TableModuleJavaScriptFiles =
            new DefaultTableModel(new String[]{
            NbBundle.getMessage(ModulePanel.class, 
            "JAVASCRIPT FILE")}, 0); // NOI18N
    private DefaultTableModel TableModuleStyleSheetFiles =
            new DefaultTableModel(new String[]{NbBundle.getMessage(ModulePanel.class, 
            "STYLESHEET FILE")}, 0); // NOI18N

    /** Creates new form ModulePanel */
    public ModulePanel(SectionView view, GwtXmlDataObject dObj) {
        super(view);
        this.dObj = dObj;
        initComponents();
        try {
            initCustomComponents();
        }
        catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void initCustomComponents() throws IOException {

        //Sets the UI values

        //<module rename-to="">
        jTextFieldRenameTo.setText(dObj.getModule().getRenameTo());

        //<inherits name="" />
        for (int i = 0; i < dObj.getModule().getInherits().length; i++) {
            String inheritsName = dObj.getModule().getInheritsName(i);
            TableModuleInherits.addRow(new String[]{inheritsName});
        }

        //<entry-point class="" />
        for (int i = 0; i < dObj.getModule().getEntryPoint().length; i++) {
            String entryPointClass = dObj.getModule().getEntryPointClass(i);
            TableModuleEntryPoints.addRow(new String[]{entryPointClass});
        }
        //<script src="" />
        for (int i = 0; i < dObj.getModule().getScript().length; i++) {
            String scriptSrc = dObj.getModule().getScriptSrc(i);
            TableModuleJavaScriptFiles.addRow(new String[]{scriptSrc});
        }
        //<stylesheet src="" />
        for (int i = 0; i < dObj.getModule().getStylesheet().length; i++) {
            String stylesheetSrc = dObj.getModule().getStylesheetSrc(i);
            TableModuleStyleSheetFiles.addRow(new String[]{stylesheetSrc});
        }

        //TableModuleInherits listener
        TableModuleInherits.addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                System.out.println("tableChanged"); // NOI18N
                switch (e.getType()) {
                    case TableModelEvent.INSERT:
                        String insertedValue = (String) TableModuleInherits.getValueAt(e.getFirstRow(), 0);
                        try {
                            int index = dObj.getModule().addInherits(true);
                            dObj.getModule().setInheritsName(index, insertedValue);
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }

                        break;
                    case TableModelEvent.UPDATE:
                        String updatedValue = (String) TableModuleInherits.getValueAt(e.getFirstRow(), 0);
                        if (updatedValue.trim().equals("")) { // NOI18N
                            //delete it -- not intrested in empty values
                            TableModuleInherits.removeRow(e.getFirstRow());
                            try {
                                dObj.getModule().removeInherits(e.getFirstRow());
                            }
                            catch (IOException ex) {
                                Exceptions.printStackTrace(ex);
                            }
                            break;
                        }

                        try {
                            dObj.getModule().setInheritsName(e.getFirstRow(), updatedValue);
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        break;

                    case TableModelEvent.DELETE:
                        try {
                            for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
                                dObj.getModule().removeInherits(i);
                            }
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        break;
                }

                //update xml view
                dObj.modelUpdatedFromUI();

            }
        });

        //entryPointTableModule listener
        TableModuleEntryPoints.addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {

                switch (e.getType()) {
                    case TableModelEvent.INSERT:
                        String insertedValue = (String) TableModuleEntryPoints.getValueAt(e.getFirstRow(), 0);
                        try {
                            int index = dObj.getModule().addEntryPoint(true);
                            dObj.getModule().setEntryPointClass(index, insertedValue);
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }

                        break;
                    case TableModelEvent.UPDATE:
                        String updatedValue = (String) TableModuleEntryPoints.getValueAt(e.getFirstRow(), 0);
                        if (updatedValue.trim().equals("")) { // NOI18N
                            //delete it -- not intrested in empty values
                            TableModuleEntryPoints.removeRow(e.getFirstRow());
                            try {
                                dObj.getModule().removeEntryPoint(e.getFirstRow());
                            }
                            catch (IOException ex) {
                                Exceptions.printStackTrace(ex);
                            }
                            break;
                        }
                        try {
                            dObj.getModule().setEntryPointClass(e.getFirstRow(), updatedValue);
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        break;

                    case TableModelEvent.DELETE:
                        try {
                            for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
                                dObj.getModule().removeEntryPoint(i);
                            }
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        break;
                }

                //update xml view
                dObj.modelUpdatedFromUI();

            }
        });

        //TableModuleJavaScriptFiles listener
        TableModuleJavaScriptFiles.addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {

                switch (e.getType()) {
                    case TableModelEvent.INSERT:
                        String insertedValue = (String) TableModuleJavaScriptFiles.getValueAt(e.getFirstRow(), 0);
                        try {
                            int index = dObj.getModule().addScript(""); // NOI18N
                            dObj.getModule().setScriptSrc(index, insertedValue);
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }

                        break;
                    case TableModelEvent.UPDATE:
                        String updatedValue = (String) TableModuleJavaScriptFiles.getValueAt(e.getFirstRow(), 0);
                        if (updatedValue.trim().equals("")) { // NOI18N
                            //delete it -- not intrested in empty values
                            TableModuleJavaScriptFiles.removeRow(e.getFirstRow());
                            try {
                                dObj.getModule().removeScript(dObj.getModule().getScript(e.getFirstRow()));
                            }
                            catch (IOException ex) {
                                Exceptions.printStackTrace(ex);
                            }
                            break;
                        }
                        try {
                            dObj.getModule().setScriptSrc(e.getFirstRow(), updatedValue);
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        break;

                    case TableModelEvent.DELETE:
                        try {
                            for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
                                dObj.getModule().removeScript(dObj.getModule().getScript(i));
                            }
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        break;
                }

                //update xml view
                dObj.modelUpdatedFromUI();

            }
        });


        //TableModuleStyleSheetFiles listener
        TableModuleStyleSheetFiles.addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {

                switch (e.getType()) {
                    case TableModelEvent.INSERT:
                        String insertedValue = (String) TableModuleStyleSheetFiles.getValueAt(e.getFirstRow(), 0);
                        try {
                            int index = dObj.getModule().addStylesheet(true);
                            dObj.getModule().setStylesheetSrc(index, insertedValue);
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }

                        break;
                    case TableModelEvent.UPDATE:
                        String updatedValue = (String) TableModuleStyleSheetFiles.getValueAt(e.getFirstRow(), 0);
                        if (updatedValue.trim().equals("")) { // NOI18N
                            //delete it -- not intrested in empty values
                            TableModuleStyleSheetFiles.removeRow(e.getFirstRow());
                            try {
                                dObj.getModule().removeStylesheet(e.getFirstRow());
                            }
                            catch (IOException ex) {
                                Exceptions.printStackTrace(ex);
                            }
                            break;
                        }
                        try {
                            dObj.getModule().setStylesheetSrc(e.getFirstRow(), updatedValue);
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        break;

                    case TableModelEvent.DELETE:
                        try {
                            for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
                                dObj.getModule().removeStylesheet(i);
                            }
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        break;
                }

                //update xml view
                dObj.modelUpdatedFromUI();

            }
        });

    }

    // <editor-fold defaultstate="collapsed" desc="SectionInnterPanel Overrides">
    public void setValue(javax.swing.JComponent source, Object value) {
        //Dont know what this does
    }

    @Override
    public void documentChanged(javax.swing.text.JTextComponent comp, String value) {
        //Dont know what this does
    }

    @Override
    public void rollbackValue(javax.swing.text.JTextComponent source) {
        //Dont know what this does
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Interfaces Impl">
    public void linkButtonPressed(Object arg0, String arg1) {
        //Dont know what this does
    }

    public JComponent getErrorComponent(String arg0) {
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextFieldRenameTo = new javax.swing.JTextField();
        jPanelInheritedModules = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableInherits = new javax.swing.JTable();
        jButtonInheritsRemove = new javax.swing.JButton();
        jButtonInheritsAdd = new javax.swing.JButton();
        jPanelEntryPoints = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableEntryPoints = new javax.swing.JTable();
        jButtonEntryPointRemove = new javax.swing.JButton();
        jButtonEntryPointAdd = new javax.swing.JButton();
        jPanelJavaScriptFiles = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableJavaScriptFiles = new javax.swing.JTable();
        jButtonJavaScriptFilesRemove = new javax.swing.JButton();
        jButtonJavaScriptFilesAdd = new javax.swing.JButton();
        jPanelStyleSheetsFiles = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableStyleSheetsFiles = new javax.swing.JTable();
        jButtonStyleSheetsFilesRemove = new javax.swing.JButton();
        jButtonStyleSheetsFilesAdd = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText(NbBundle.getMessage(ModulePanel.class, "RenTo")); // NOI18N
        jLabel1.setToolTipText(NbBundle.getMessage(ModulePanel.class, "CausesCompiler")); // NOI18N

        jTextFieldRenameTo.setToolTipText(NbBundle.getMessage(ModulePanel.class, "CausesCompiler")); // NOI18N
        jTextFieldRenameTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldRenameToActionPerformed(evt);
            }
        });
        jTextFieldRenameTo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldRenameToFocusLost(evt);
            }
        });

        jPanelInheritedModules.setBackground(new java.awt.Color(255, 255, 255));
        jPanelInheritedModules.setBorder(javax.swing.BorderFactory.createTitledBorder(NbBundle.getMessage(ModulePanel.class, "InhMod"))); // NOI18N
        jPanelInheritedModules.setName("Name of the modules to be Inherited:"); // NOI18N

        jTableInherits.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTableInherits.setModel(this.TableModuleInherits);
        jTableInherits.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTableInheritsFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(jTableInherits);

        jButtonInheritsRemove.setText(NbBundle.getMessage(ModulePanel.class, "Remove")); // NOI18N
        jButtonInheritsRemove.setEnabled(false);
        jButtonInheritsRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInheritsRemoveActionPerformed(evt);
            }
        });
        jButtonInheritsRemove.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButtonInheritsRemoveFocusGained(evt);
            }
        });

        jButtonInheritsAdd.setText(NbBundle.getMessage(ModulePanel.class, "Add")); // NOI18N
        jButtonInheritsAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInheritsAddActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelInheritedModulesLayout = new org.jdesktop.layout.GroupLayout(jPanelInheritedModules);
        jPanelInheritedModules.setLayout(jPanelInheritedModulesLayout);
        jPanelInheritedModulesLayout.setHorizontalGroup(
            jPanelInheritedModulesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelInheritedModulesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelInheritedModulesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelInheritedModulesLayout.createSequentialGroup()
                        .add(jButtonInheritsAdd)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jButtonInheritsRemove))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelInheritedModulesLayout.setVerticalGroup(
            jPanelInheritedModulesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelInheritedModulesLayout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelInheritedModulesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonInheritsAdd)
                    .add(jButtonInheritsRemove)))
        );

        jPanelEntryPoints.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEntryPoints.setBorder(javax.swing.BorderFactory.createTitledBorder(NbBundle.getMessage(ModulePanel.class, "EntryP"))); // NOI18N
        jPanelEntryPoints.setName(""); // NOI18N
        jPanelEntryPoints.setPreferredSize(new java.awt.Dimension(0, 0));

        jTableEntryPoints.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTableEntryPoints.setModel(this.TableModuleEntryPoints);
        jTableEntryPoints.setMaximumSize(new java.awt.Dimension(0, 500));
        jTableEntryPoints.setMinimumSize(new java.awt.Dimension(0, 20));
        jTableEntryPoints.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTableEntryPointsFocusGained(evt);
            }
        });
        jScrollPane2.setViewportView(jTableEntryPoints);
        jTableEntryPoints.getAccessibleContext().setAccessibleParent(this);

        jButtonEntryPointRemove.setText(NbBundle.getMessage(ModulePanel.class, "REMOVE")); // NOI18N
        jButtonEntryPointRemove.setEnabled(false);
        jButtonEntryPointRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEntryPointRemoveActionPerformed(evt);
            }
        });
        jButtonEntryPointRemove.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButtonEntryPointRemoveFocusGained(evt);
            }
        });

        jButtonEntryPointAdd.setText(NbBundle.getMessage(ModulePanel.class, "Add")); // NOI18N
        jButtonEntryPointAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEntryPointAddActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelEntryPointsLayout = new org.jdesktop.layout.GroupLayout(jPanelEntryPoints);
        jPanelEntryPoints.setLayout(jPanelEntryPointsLayout);
        jPanelEntryPointsLayout.setHorizontalGroup(
            jPanelEntryPointsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelEntryPointsLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelEntryPointsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                    .add(jPanelEntryPointsLayout.createSequentialGroup()
                        .add(jButtonEntryPointAdd)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jButtonEntryPointRemove)))
                .addContainerGap())
        );
        jPanelEntryPointsLayout.setVerticalGroup(
            jPanelEntryPointsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelEntryPointsLayout.createSequentialGroup()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelEntryPointsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonEntryPointAdd)
                    .add(jButtonEntryPointRemove)))
        );

        jPanelJavaScriptFiles.setBackground(new java.awt.Color(255, 255, 255));
        jPanelJavaScriptFiles.setBorder(javax.swing.BorderFactory.createTitledBorder(NbBundle.getMessage(ModulePanel.class, "JSFiles"))); // NOI18N
        jPanelJavaScriptFiles.setName("Name of the modules to be Inherited:"); // NOI18N

        jTableJavaScriptFiles.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTableJavaScriptFiles.setModel(this.TableModuleJavaScriptFiles);
        jTableJavaScriptFiles.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTableJavaScriptFilesFocusGained(evt);
            }
        });
        jScrollPane3.setViewportView(jTableJavaScriptFiles);

        jButtonJavaScriptFilesRemove.setText(NbBundle.getMessage(ModulePanel.class, "REMOVE")); // NOI18N
        jButtonJavaScriptFilesRemove.setEnabled(false);
        jButtonJavaScriptFilesRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonJavaScriptFilesRemoveActionPerformed(evt);
            }
        });
        jButtonJavaScriptFilesRemove.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButtonJavaScriptFilesRemoveFocusGained(evt);
            }
        });

        jButtonJavaScriptFilesAdd.setText(NbBundle.getMessage(ModulePanel.class, "ADD")); // NOI18N
        jButtonJavaScriptFilesAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonJavaScriptFilesAddActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelJavaScriptFilesLayout = new org.jdesktop.layout.GroupLayout(jPanelJavaScriptFiles);
        jPanelJavaScriptFiles.setLayout(jPanelJavaScriptFilesLayout);
        jPanelJavaScriptFilesLayout.setHorizontalGroup(
            jPanelJavaScriptFilesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelJavaScriptFilesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelJavaScriptFilesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelJavaScriptFilesLayout.createSequentialGroup()
                        .add(jButtonJavaScriptFilesAdd)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jButtonJavaScriptFilesRemove))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelJavaScriptFilesLayout.setVerticalGroup(
            jPanelJavaScriptFilesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelJavaScriptFilesLayout.createSequentialGroup()
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelJavaScriptFilesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonJavaScriptFilesAdd)
                    .add(jButtonJavaScriptFilesRemove)))
        );

        jPanelStyleSheetsFiles.setBackground(new java.awt.Color(255, 255, 255));
        jPanelStyleSheetsFiles.setBorder(javax.swing.BorderFactory.createTitledBorder(NbBundle.getMessage(ModulePanel.class, "SFiles"))); // NOI18N
        jPanelStyleSheetsFiles.setName("Name of the modules to be Inherited:"); // NOI18N

        jTableStyleSheetsFiles.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTableStyleSheetsFiles.setModel(this.TableModuleStyleSheetFiles);
        jTableStyleSheetsFiles.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTableStyleSheetsFilesFocusGained(evt);
            }
        });
        jScrollPane4.setViewportView(jTableStyleSheetsFiles);

        jButtonStyleSheetsFilesRemove.setText(NbBundle.getMessage(ModulePanel.class, "REMOVE")); // NOI18N
        jButtonStyleSheetsFilesRemove.setEnabled(false);
        jButtonStyleSheetsFilesRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStyleSheetsFilesRemoveActionPerformed(evt);
            }
        });
        jButtonStyleSheetsFilesRemove.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButtonStyleSheetsFilesRemoveFocusGained(evt);
            }
        });

        jButtonStyleSheetsFilesAdd.setText(NbBundle.getMessage(ModulePanel.class, "ADD")); // NOI18N
        jButtonStyleSheetsFilesAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStyleSheetsFilesAddActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelStyleSheetsFilesLayout = new org.jdesktop.layout.GroupLayout(jPanelStyleSheetsFiles);
        jPanelStyleSheetsFiles.setLayout(jPanelStyleSheetsFilesLayout);
        jPanelStyleSheetsFilesLayout.setHorizontalGroup(
            jPanelStyleSheetsFilesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelStyleSheetsFilesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelStyleSheetsFilesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelStyleSheetsFilesLayout.createSequentialGroup()
                        .add(jButtonStyleSheetsFilesAdd)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jButtonStyleSheetsFilesRemove))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelStyleSheetsFilesLayout.setVerticalGroup(
            jPanelStyleSheetsFilesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelStyleSheetsFilesLayout.createSequentialGroup()
                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelStyleSheetsFilesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonStyleSheetsFilesAdd)
                    .add(jButtonStyleSheetsFilesRemove)))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelInheritedModules, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldRenameTo, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE))
                    .add(jPanelJavaScriptFiles, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelEntryPoints, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                    .add(jPanelStyleSheetsFiles, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(20, 20, 20)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jTextFieldRenameTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelInheritedModules, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelEntryPoints, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelJavaScriptFiles, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelStyleSheetsFiles, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldRenameToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldRenameToActionPerformed

        try {
            this.dObj.getModule().setRenameTo(this.jTextFieldRenameTo.getText());
        }
        catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        //update xml view
        this.dObj.modelUpdatedFromUI();

    }//GEN-LAST:event_jTextFieldRenameToActionPerformed

    // <editor-fold defaultstate="collapsed" desc="Module Inherit Events">
    private void jButtonInheritsAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInheritsAddActionPerformed
        TableModuleInherits.addRow(new String[]{""}); // NOI18N
        jTableInherits.changeSelection(TableModuleInherits.getRowCount() - 1, 0, false, false);
        jTableInherits.editCellAt(TableModuleInherits.getRowCount() - 1, 0);
        jTableInherits.requestFocus();
    }//GEN-LAST:event_jButtonInheritsAddActionPerformed

    private void jTableInheritsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTableInheritsFocusGained
        if (this.TableModuleInherits.getRowCount() > 0) {
            this.jButtonInheritsRemove.setEnabled(true);
        }
        else {
            this.jButtonInheritsRemove.setEnabled(false);
        }
    }//GEN-LAST:event_jTableInheritsFocusGained

    private void jButtonInheritsRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInheritsRemoveActionPerformed
        this.TableModuleInherits.removeRow(this.jTableInherits.getSelectedRow());
        if (this.TableModuleInherits.getRowCount() <= 0) {
            this.jButtonInheritsRemove.setEnabled(false);
        }
    }//GEN-LAST:event_jButtonInheritsRemoveActionPerformed
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Entry Points Events">
    private void jTableEntryPointsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTableEntryPointsFocusGained
        if (this.TableModuleEntryPoints.getRowCount() > 0) {
            this.jButtonEntryPointRemove.setEnabled(true);
        }
        else {
            this.jButtonEntryPointRemove.setEnabled(false);
        }
    }//GEN-LAST:event_jTableEntryPointsFocusGained

    private void jButtonEntryPointRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEntryPointRemoveActionPerformed
        this.TableModuleEntryPoints.removeRow(this.jTableEntryPoints.getSelectedRow());
        if (this.TableModuleEntryPoints.getRowCount() <= 0) {
            this.jButtonEntryPointRemove.setEnabled(false);
        }
    }//GEN-LAST:event_jButtonEntryPointRemoveActionPerformed

    private void jButtonEntryPointAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEntryPointAddActionPerformed
        TableModuleEntryPoints.addRow(new String[]{NbBundle.getMessage(ModulePanel.class, NbBundle.getMessage(ModulePanel.class, "FQFN"))}); // NOI18N
//        jTableEntryPoints.changeSelection((TableModuleEntryPoints.getRowCount() == 0) ? 0 : TableModuleEntryPoints.getRowCount() - 1, 0, false, false);
        jTableEntryPoints.changeSelection(TableModuleEntryPoints.getRowCount() - 1, 0, false, false);
        jTableEntryPoints.editCellAt(TableModuleEntryPoints.getRowCount() - 1, 0);
        jTableEntryPoints.requestFocus();


    }//GEN-LAST:event_jButtonEntryPointAddActionPerformed

    private void jButtonEntryPointRemoveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButtonEntryPointRemoveFocusGained
        this.jTableEntryPointsFocusGained(evt);
    }//GEN-LAST:event_jButtonEntryPointRemoveFocusGained
    // </editor-fold>

    private void jTableJavaScriptFilesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTableJavaScriptFilesFocusGained
        if (this.TableModuleJavaScriptFiles.getRowCount() > 0) {
            this.jButtonJavaScriptFilesRemove.setEnabled(true);
        }
        else {
            this.jButtonJavaScriptFilesRemove.setEnabled(false);
        }
    }//GEN-LAST:event_jTableJavaScriptFilesFocusGained

    private void jButtonJavaScriptFilesRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonJavaScriptFilesRemoveActionPerformed
        this.TableModuleJavaScriptFiles.removeRow(this.jTableJavaScriptFiles.getSelectedRow());
        if (this.TableModuleJavaScriptFiles.getRowCount() <= 0) {
            this.jButtonJavaScriptFilesRemove.setEnabled(false);
        }
    }//GEN-LAST:event_jButtonJavaScriptFilesRemoveActionPerformed

    private void jButtonJavaScriptFilesRemoveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButtonJavaScriptFilesRemoveFocusGained
        this.jTableJavaScriptFilesFocusGained(evt);
    }//GEN-LAST:event_jButtonJavaScriptFilesRemoveFocusGained

    private void jButtonJavaScriptFilesAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonJavaScriptFilesAddActionPerformed
        TableModuleJavaScriptFiles.addRow(new String[]{NbBundle.getMessage(ModulePanel.class, NbBundle.getMessage(ModulePanel.class, "FQFN"))}); // NOI18N
        jTableJavaScriptFiles.changeSelection(TableModuleJavaScriptFiles.getRowCount() - 1, 0, false, false);
        jTableJavaScriptFiles.editCellAt(TableModuleJavaScriptFiles.getRowCount() - 1, 0);
        jTableJavaScriptFiles.requestFocus();
    }//GEN-LAST:event_jButtonJavaScriptFilesAddActionPerformed

    private void jButtonInheritsRemoveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButtonInheritsRemoveFocusGained
        this.jTableInheritsFocusGained(evt);
}//GEN-LAST:event_jButtonInheritsRemoveFocusGained

    private void jTableStyleSheetsFilesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTableStyleSheetsFilesFocusGained
        if (this.TableModuleStyleSheetFiles.getRowCount() > 0) {
            this.jButtonStyleSheetsFilesRemove.setEnabled(true);
        }
        else {
            this.jButtonStyleSheetsFilesRemove.setEnabled(false);
        }
    }//GEN-LAST:event_jTableStyleSheetsFilesFocusGained

    private void jButtonStyleSheetsFilesRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStyleSheetsFilesRemoveActionPerformed
        this.TableModuleStyleSheetFiles.removeRow(this.jTableStyleSheetsFiles.getSelectedRow());
        if (this.TableModuleStyleSheetFiles.getRowCount() <= 0) {
            this.jButtonStyleSheetsFilesRemove.setEnabled(false);
        }
    }//GEN-LAST:event_jButtonStyleSheetsFilesRemoveActionPerformed

    private void jButtonStyleSheetsFilesRemoveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButtonStyleSheetsFilesRemoveFocusGained
        this.jTableStyleSheetsFilesFocusGained(evt);
    }//GEN-LAST:event_jButtonStyleSheetsFilesRemoveFocusGained

    private void jButtonStyleSheetsFilesAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStyleSheetsFilesAddActionPerformed
        TableModuleStyleSheetFiles.addRow(new String[]{NbBundle.getMessage(ModulePanel.class, NbBundle.getMessage(ModulePanel.class, "FQFN"))}); // NOI18N
        jTableStyleSheetsFiles.changeSelection(TableModuleStyleSheetFiles.getRowCount() - 1, 0, false, false);
        jTableStyleSheetsFiles.editCellAt(TableModuleStyleSheetFiles.getRowCount() - 1, 0);
        jTableStyleSheetsFiles.requestFocus();
    }//GEN-LAST:event_jButtonStyleSheetsFilesAddActionPerformed

    private void jTextFieldRenameToFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldRenameToFocusLost
        try {
            this.dObj.getModule().setRenameTo(this.jTextFieldRenameTo.getText());
        }
        catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        //update xml view
        this.dObj.modelUpdatedFromUI();
    }//GEN-LAST:event_jTextFieldRenameToFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonEntryPointAdd;
    private javax.swing.JButton jButtonEntryPointRemove;
    private javax.swing.JButton jButtonInheritsAdd;
    private javax.swing.JButton jButtonInheritsRemove;
    private javax.swing.JButton jButtonJavaScriptFilesAdd;
    private javax.swing.JButton jButtonJavaScriptFilesRemove;
    private javax.swing.JButton jButtonStyleSheetsFilesAdd;
    private javax.swing.JButton jButtonStyleSheetsFilesRemove;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanelEntryPoints;
    private javax.swing.JPanel jPanelInheritedModules;
    private javax.swing.JPanel jPanelJavaScriptFiles;
    private javax.swing.JPanel jPanelStyleSheetsFiles;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTableEntryPoints;
    private javax.swing.JTable jTableInherits;
    private javax.swing.JTable jTableJavaScriptFiles;
    private javax.swing.JTable jTableStyleSheetsFiles;
    private javax.swing.JTextField jTextFieldRenameTo;
    // End of variables declaration//GEN-END:variables
}
