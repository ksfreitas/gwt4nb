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
public class CompilerPanel extends SectionInnerPanel {
    private static final long serialVersionUID = 1;

    private GwtXmlDataObject dObj;
    private DefaultTableModel TableModuleDefineLinker = new DefaultTableModel(
            new String[]{NbBundle.getMessage(CompilerPanel.class, 
            "NAME"),  // NOI18N
            NbBundle.getMessage(CompilerPanel.class,
            "CLASS")}, 0); // NOI18N
    private DefaultTableModel TableModuleAddLinker = new DefaultTableModel(
            new String[]{NbBundle.getMessage(CompilerPanel.class, 
            "NAME")}, 0); // NOI18N

    /** Creates new form CompilerPanel */
    public CompilerPanel(SectionView view, GwtXmlDataObject dObj) {
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
        //<define-linker name="" class="" />
        for (int i = 0; i < dObj.getModule().getDefineLinker().length; i++) {
            String defineLinkerName = dObj.getModule().getDefineLinkerName(i);
            String defineLinkerClass = dObj.getModule().getDefineLinkerClass(i);
            TableModuleDefineLinker.addRow(new String[]{defineLinkerName, defineLinkerClass});
        }
        //<add-linker name="" />
        for (int i = 0; i < dObj.getModule().getAddLinker().length; i++) {
            String addLinkerName = dObj.getModule().getAddLinkerName(i);
            TableModuleAddLinker.addRow(new String[]{addLinkerName});
        }

        
        //TableModuleDefineLinker listener
        TableModuleDefineLinker.addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                System.out.println("tableChanged"); // NOI18N
                switch (e.getType()) {
                    case TableModelEvent.INSERT:
                        String insertedNameValue = (String) TableModuleDefineLinker.getValueAt(e.getFirstRow(), 0);
                        String insertedClassValue = (String) TableModuleDefineLinker.getValueAt(e.getFirstRow(), 1);
                        try {
                            int index = dObj.getModule().addDefineLinker(true);
                            dObj.getModule().setDefineLinkerName(index, insertedNameValue);
                            dObj.getModule().setDefineLinkerClass(index, insertedClassValue);
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }

                        break;
                    case TableModelEvent.UPDATE:
                        String updatedNameValue = (String) TableModuleDefineLinker.getValueAt(e.getFirstRow(), 0);
                        String updatedClassValue = (String) TableModuleDefineLinker.getValueAt(e.getFirstRow(), 1);
                        if (updatedNameValue.trim().equals("") && updatedClassValue.trim().equals("")) { // NOI18N
                            //delete it -- not intrested in empty values
                            TableModuleDefineLinker.removeRow(e.getFirstRow());
                            try {
                                dObj.getModule().removeDefineLinker(e.getFirstRow());
                            }
                            catch (IOException ex) {
                                Exceptions.printStackTrace(ex);
                            }
                            break;
                        }

                        try {
                            dObj.getModule().setDefineLinkerName(e.getFirstRow(), updatedNameValue);
                            dObj.getModule().setDefineLinkerClass(e.getFirstRow(), updatedClassValue);

                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        break;

                    case TableModelEvent.DELETE:
                        try {
                            for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
                                dObj.getModule().removeDefineLinker(i);
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

        //TableModuleAddLinker listener
        TableModuleAddLinker.addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                System.out.println("tableChanged"); // NOI18N
                switch (e.getType()) {
                    case TableModelEvent.INSERT:
                        String insertedValue = (String) TableModuleAddLinker.getValueAt(e.getFirstRow(), 0);
                        try {
                            int index = dObj.getModule().addAddLinker(true);
                            dObj.getModule().setAddLinkerName(index, insertedValue);
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }

                        break;
                    case TableModelEvent.UPDATE:
                        String updatedValue = (String) TableModuleAddLinker.getValueAt(e.getFirstRow(), 0);
                        if (updatedValue.trim().equals("")) { // NOI18N
                            //delete it -- not intrested in empty values
                            TableModuleAddLinker.removeRow(e.getFirstRow());
                            try {
                                dObj.getModule().removeAddLinker(e.getFirstRow());
                            }
                            catch (IOException ex) {
                                Exceptions.printStackTrace(ex);
                            }
                            break;
                        }

                        try {
                            dObj.getModule().setAddLinkerName(e.getFirstRow(), updatedValue);
                        }
                        catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        break;

                    case TableModelEvent.DELETE:
                        try {
                            for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
                                dObj.getModule().removeAddLinker(i);
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelDefinedLinkersModules = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDefineLinkers = new javax.swing.JTable();
        jButtonDefinedLinkersRemove = new javax.swing.JButton();
        jButtonDefinedLinkersAdd = new javax.swing.JButton();
        jPanelAddLinkersModules = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableAddLinkers = new javax.swing.JTable();
        jButtonAddLinkersRemove = new javax.swing.JButton();
        jButtonAddLinkersAdd = new javax.swing.JButton();

        jPanelDefinedLinkersModules.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(CompilerPanel.class, "CompilerPanel.jPanelDefinedLinkersModules.border.title"))); // NOI18N
        jPanelDefinedLinkersModules.setName("Name of the modules to be Inherited:"); // NOI18N

        jTableDefineLinkers.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTableDefineLinkers.setModel(this.TableModuleDefineLinker);
        jTableDefineLinkers.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTableDefineLinkersFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDefineLinkers);

        jButtonDefinedLinkersRemove.setText(org.openide.util.NbBundle.getMessage(CompilerPanel.class, "CompilerPanel.jButtonDefinedLinkersRemove.text")); // NOI18N
        jButtonDefinedLinkersRemove.setEnabled(false);
        jButtonDefinedLinkersRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDefinedLinkersRemoveActionPerformed(evt);
            }
        });
        jButtonDefinedLinkersRemove.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButtonDefinedLinkersRemoveFocusGained(evt);
            }
        });

        jButtonDefinedLinkersAdd.setText(org.openide.util.NbBundle.getMessage(CompilerPanel.class, "CompilerPanel.jButtonDefinedLinkersAdd.text")); // NOI18N
        jButtonDefinedLinkersAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDefinedLinkersAddActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelDefinedLinkersModulesLayout = new org.jdesktop.layout.GroupLayout(jPanelDefinedLinkersModules);
        jPanelDefinedLinkersModules.setLayout(jPanelDefinedLinkersModulesLayout);
        jPanelDefinedLinkersModulesLayout.setHorizontalGroup(
            jPanelDefinedLinkersModulesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelDefinedLinkersModulesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelDefinedLinkersModulesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelDefinedLinkersModulesLayout.createSequentialGroup()
                        .add(jButtonDefinedLinkersAdd)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jButtonDefinedLinkersRemove))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelDefinedLinkersModulesLayout.setVerticalGroup(
            jPanelDefinedLinkersModulesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelDefinedLinkersModulesLayout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelDefinedLinkersModulesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonDefinedLinkersAdd)
                    .add(jButtonDefinedLinkersRemove)))
        );

        jPanelAddLinkersModules.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(CompilerPanel.class, "CompilerPanel.jPanelAddLinkersModules.border.title"))); // NOI18N
        jPanelAddLinkersModules.setName("Name of the modules to be Inherited:"); // NOI18N

        jTableAddLinkers.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTableAddLinkers.setModel(this.TableModuleAddLinker);
        jTableAddLinkers.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTableAddLinkersFocusGained(evt);
            }
        });
        jScrollPane2.setViewportView(jTableAddLinkers);

        jButtonAddLinkersRemove.setText(org.openide.util.NbBundle.getMessage(CompilerPanel.class, "CompilerPanel.jButtonAddLinkersRemove.text")); // NOI18N
        jButtonAddLinkersRemove.setEnabled(false);
        jButtonAddLinkersRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddLinkersRemoveActionPerformed(evt);
            }
        });
        jButtonAddLinkersRemove.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButtonAddLinkersRemoveFocusGained(evt);
            }
        });

        jButtonAddLinkersAdd.setText(org.openide.util.NbBundle.getMessage(CompilerPanel.class, "CompilerPanel.jButtonAddLinkersAdd.text")); // NOI18N
        jButtonAddLinkersAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddLinkersAddActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelAddLinkersModulesLayout = new org.jdesktop.layout.GroupLayout(jPanelAddLinkersModules);
        jPanelAddLinkersModules.setLayout(jPanelAddLinkersModulesLayout);
        jPanelAddLinkersModulesLayout.setHorizontalGroup(
            jPanelAddLinkersModulesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelAddLinkersModulesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelAddLinkersModulesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelAddLinkersModulesLayout.createSequentialGroup()
                        .add(jButtonAddLinkersAdd)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jButtonAddLinkersRemove))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelAddLinkersModulesLayout.setVerticalGroup(
            jPanelAddLinkersModulesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelAddLinkersModulesLayout.createSequentialGroup()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelAddLinkersModulesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonAddLinkersAdd)
                    .add(jButtonAddLinkersRemove)))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanelAddLinkersModules, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(20, 20, 20)
                        .add(jPanelDefinedLinkersModules, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelDefinedLinkersModules, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanelAddLinkersModules, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTableDefineLinkersFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTableDefineLinkersFocusGained
        if (this.TableModuleDefineLinker.getRowCount() > 0) {
            this.jButtonDefinedLinkersRemove.setEnabled(true);
        }
        else {
            this.jButtonDefinedLinkersRemove.setEnabled(false);
        }
}//GEN-LAST:event_jTableDefineLinkersFocusGained

    private void jButtonDefinedLinkersRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDefinedLinkersRemoveActionPerformed
        this.TableModuleDefineLinker.removeRow(this.jTableDefineLinkers.getSelectedRow());
        if (this.TableModuleDefineLinker.getRowCount() <= 0) {
            this.jButtonDefinedLinkersRemove.setEnabled(false);
        }
}//GEN-LAST:event_jButtonDefinedLinkersRemoveActionPerformed

    private void jButtonDefinedLinkersRemoveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButtonDefinedLinkersRemoveFocusGained
        this.jTableDefineLinkersFocusGained(evt);
}//GEN-LAST:event_jButtonDefinedLinkersRemoveFocusGained

    private void jButtonDefinedLinkersAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDefinedLinkersAddActionPerformed
        TableModuleDefineLinker.addRow(new String[]{""}); // NOI18N
        jTableDefineLinkers.changeSelection(TableModuleDefineLinker.getRowCount() - 1, 0, false, false);
        jTableDefineLinkers.editCellAt(TableModuleDefineLinker.getRowCount() - 1, 0);
        jTableDefineLinkers.requestFocus();
}//GEN-LAST:event_jButtonDefinedLinkersAddActionPerformed

    private void jTableAddLinkersFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTableAddLinkersFocusGained
        if (this.TableModuleAddLinker.getRowCount() > 0) {
            this.jButtonAddLinkersRemove.setEnabled(true);
        }
        else {
            this.jButtonAddLinkersRemove.setEnabled(false);
        }
    }//GEN-LAST:event_jTableAddLinkersFocusGained

    private void jButtonAddLinkersRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddLinkersRemoveActionPerformed
        this.TableModuleAddLinker.removeRow(this.jTableAddLinkers.getSelectedRow());
        if (this.TableModuleAddLinker.getRowCount() <= 0) {
            this.jButtonAddLinkersRemove.setEnabled(false);
        }
    }//GEN-LAST:event_jButtonAddLinkersRemoveActionPerformed

    private void jButtonAddLinkersRemoveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButtonAddLinkersRemoveFocusGained
        this.jTableAddLinkersFocusGained(evt);
    }//GEN-LAST:event_jButtonAddLinkersRemoveFocusGained

    private void jButtonAddLinkersAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddLinkersAddActionPerformed
        TableModuleAddLinker.addRow(new String[]{""}); // NOI18N
        jTableAddLinkers.changeSelection(TableModuleAddLinker.getRowCount() - 1, 0, false, false);
        jTableAddLinkers.editCellAt(TableModuleAddLinker.getRowCount() - 1, 0);
        jTableAddLinkers.requestFocus();
    }//GEN-LAST:event_jButtonAddLinkersAddActionPerformed

    // <editor-fold defaultstate="collapsed" desc="SectionInnerPaner Interface Methods">
    @Override
    public void setValue(JComponent arg0, Object arg1) {
        throw new UnsupportedOperationException("Not supported yet."); // NOI18N
    }

    public void linkButtonPressed(Object arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet."); // NOI18N
    }

    public JComponent getErrorComponent(String arg0) {
        throw new UnsupportedOperationException("Not supported yet."); // NOI18N
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Variables Declaration">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddLinkersAdd;
    private javax.swing.JButton jButtonAddLinkersRemove;
    private javax.swing.JButton jButtonDefinedLinkersAdd;
    private javax.swing.JButton jButtonDefinedLinkersRemove;
    private javax.swing.JPanel jPanelAddLinkersModules;
    private javax.swing.JPanel jPanelDefinedLinkersModules;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableAddLinkers;
    private javax.swing.JTable jTableDefineLinkers;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>
}
