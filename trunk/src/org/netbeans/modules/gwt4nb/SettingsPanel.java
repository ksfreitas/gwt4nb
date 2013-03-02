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
package org.netbeans.modules.gwt4nb;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.swing.ComboBoxModel;

/**
 * GWT project settings.
 */
public class SettingsPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 1;
    private static final List<String> OUTPUT_STYLES =
            Arrays.asList(new String[]{
                "OBFUSCATED", "PRETTY", "DETAILED"}); // NOI18N

    /**
     * Creates new form SettingsPanel
     */
    public SettingsPanel() {
        initComponents();
    }

    /**
     * Fills this panel with the data from gwt.properties
     *
     * @param props gwt.properties
     */
    public void fillPanel(Properties props) {
        final String m = props.getProperty("gwt.module"); // NOI18N
        String[] ms = m.split(" "); // NOI18N
        jTextAreaModules.setText(GWT4NBUtil.join(Arrays.asList(ms),
                "\n")); // NOI18N
        jTextFieldCompilerOutputDirectory.setText(
                props.getProperty("gwt.output.dir")); // NOI18N
        jComboBoxCompilerOutputStyle.setSelectedIndex(
                prefixIndex(props.getProperty(
                "gwt.compiler.output.style"), OUTPUT_STYLES)); // NOI18N
        jTextFieldCompilerJVMArgs.setText(
                props.getProperty("gwt.compiler.jvmargs")); // NOI18N
        jSpinnerCompilerWorkers.setValue(new Integer(props.getProperty(
                "gwt.compiler.local.workers"))); // NOI18N
        jComboBoxCompilerLogLevel.setSelectedItem(props.getProperty(
                "gwt.compiler.logLevel")); // NOI18N
        jComboBoxShellOutputStyle.setSelectedIndex(
                prefixIndex(props.getProperty(
                "gwt.shell.output.style"), OUTPUT_STYLES)); // NOI18N
        jComboBoxShellLogLevel.setSelectedItem(props.getProperty(
                "gwt.shell.logLevel")); // NOI18N
        jTextFieldShellJVM.setText(
                props.getProperty("gwt.shell.java")); // NOI18N
        jTextFieldShellJVMArgs.setText(
                props.getProperty("gwt.shell.jvmargs")); // NOI18N
        jCheckBoxOpenBrowser.setSelected(props.getProperty("gwt.debug.openbrowser") != null
                && props.getProperty("gwt.debug.openbrowser").equals("yes"));
        jCheckBoxSuperDev.setSelected(props.getProperty("gwt.debug.superdev") != null
                && props.getProperty("gwt.debug.superdev").equals("yes"));

        // select the newest version, if unknown
        ComboBoxModel cbm = jComboBoxGWTVersion.getModel();
        String v = props.getProperty("gwt.version"); // NOI18N
        int index = -1;
        for (int i = 0; i < cbm.getSize(); i++) {
            if (cbm.getElementAt(i).equals(v)) {
                index = i;
                break;
            }
        }
        if (index < 0) {
            index = jComboBoxGWTVersion.getItemCount() - 1;
        }
        jComboBoxGWTVersion.setSelectedIndex(index);

        jSpinnerShellServerPort.setValue(new Integer(props.getProperty(
                "gwt.shell.code.server.port"))); // NOI18N
        jSpinnerShellPort.setValue(new Integer(props.getProperty(
                "gwt.shell.port"))); // NOI18N
        jTextFieldCompilerArgs.setText(
                props.getProperty("gwt.compiler.args")); // NOI18N
        jTextFieldTestsArgs.setText(
                props.getProperty("gwt.test.jvmargs")); // NOI18N
        jTextFieldShellArgs.setText(
                props.getProperty("gwt.shell.args")); // NOI18N
    }

    /**
     * Stores the data from this panel in gwt.properties.
     *
     * @param props gwt.properties
     */
    public void fillData(Properties props) {
        final String m = jTextAreaModules.getText();
        String[] ms = m.split("\n"); // NOI18N
        props.setProperty("gwt.module", // NOI18N
                GWT4NBUtil.join(Arrays.asList(ms), // NOI18N
                " ")); // NOI18N
        props.setProperty("gwt.output.dir", // NOI18N
                jTextFieldCompilerOutputDirectory.getText());
        props.setProperty("gwt.compiler.output.style", // NOI18N
                OUTPUT_STYLES.get(
                jComboBoxCompilerOutputStyle.getSelectedIndex()));
        props.setProperty("gwt.compiler.jvmargs", // NOI18N
                jTextFieldCompilerJVMArgs.getText());
        props.setProperty("gwt.compiler.local.workers", // NOI18N
                jSpinnerCompilerWorkers.getValue().toString());
        props.setProperty("gwt.compiler.logLevel", // NOI18N
                (String) jComboBoxCompilerLogLevel.getSelectedItem());
        props.setProperty("gwt.shell.output.style", // NOI18N
                OUTPUT_STYLES.get(
                jComboBoxShellOutputStyle.getSelectedIndex()));
        props.setProperty("gwt.shell.logLevel", // NOI18N
                (String) jComboBoxShellLogLevel.getSelectedItem());
        props.setProperty("gwt.shell.java", // NOI18N
                jTextFieldShellJVM.getText());
        props.setProperty("gwt.shell.jvmargs", // NOI18N
                jTextFieldShellJVMArgs.getText());
        props.setProperty("gwt.version", // NOI18N
                (String) jComboBoxGWTVersion.getSelectedItem());
        props.setProperty("gwt.shell.code.server.port", // NOI18N
                jSpinnerShellServerPort.getValue().toString());
        props.setProperty("gwt.shell.port", // NOI18N
                jSpinnerShellPort.getValue().toString());
        props.setProperty("gwt.compiler.args", // NOI18N
                jTextFieldCompilerArgs.getText());
        props.setProperty("gwt.test.jvmargs", // NOI18N
                jTextFieldTestsArgs.getText());
        props.setProperty("gwt.shell.args", // NOI18N
                jTextFieldShellArgs.getText());
        props.setProperty("gwt.debug.openbrowser", jCheckBoxOpenBrowser.isSelected()
                ? "yes" : "no");
        props.setProperty("gwt.debug.superdev", jCheckBoxSuperDev.isSelected()
                ? "yes" : "no");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaModules = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabelCompilerJVMArgs = new javax.swing.JLabel();
        jTextFieldCompilerJVMArgs = new javax.swing.JTextField();
        jLabelCompilerArgs = new javax.swing.JLabel();
        jTextFieldCompilerArgs = new javax.swing.JTextField();
        jLabelCompilerOutputStyle = new javax.swing.JLabel();
        jComboBoxCompilerOutputStyle = new javax.swing.JComboBox();
        jLabelCompilerLogLevel = new javax.swing.JLabel();
        jComboBoxCompilerLogLevel = new javax.swing.JComboBox();
        jLabelCompilerWorkers = new javax.swing.JLabel();
        jSpinnerCompilerWorkers = new javax.swing.JSpinner();
        jLabelCompilerOutputDir = new javax.swing.JLabel();
        jTextFieldCompilerOutputDirectory = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabelShellJVMArgs = new javax.swing.JLabel();
        jTextFieldShellJVMArgs = new javax.swing.JTextField();
        jLabelShellArgs = new javax.swing.JLabel();
        jTextFieldShellArgs = new javax.swing.JTextField();
        jLabelShellOutputStyle = new javax.swing.JLabel();
        jComboBoxShellOutputStyle = new javax.swing.JComboBox();
        jLabelShellLogLevel = new javax.swing.JLabel();
        jComboBoxShellLogLevel = new javax.swing.JComboBox();
        jLabelShellWebServerPort = new javax.swing.JLabel();
        jSpinnerShellPort = new javax.swing.JSpinner();
        jLabelShellCodeServerPort = new javax.swing.JLabel();
        jSpinnerShellServerPort = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldShellJVM = new javax.swing.JTextField();
        jLabelGWTVersion = new javax.swing.JLabel();
        jComboBoxGWTVersion = new javax.swing.JComboBox();
        jLabelGWTTestsJVMArgs = new javax.swing.JLabel();
        jTextFieldTestsArgs = new javax.swing.JTextField();
        jCheckBoxOpenBrowser = new javax.swing.JCheckBox();
        jCheckBoxSuperDev = new javax.swing.JCheckBox();

        setLayout(new java.awt.BorderLayout());

        jScrollPane2.setHorizontalScrollBar(null);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(11, 11, 12, 12));

        jLabel1.setLabelFor(jTextAreaModules);
        jLabel1.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabel1.text")); // NOI18N

        jTextAreaModules.setColumns(20);
        jTextAreaModules.setRows(5);
        jTextAreaModules.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextAreaModules.toolTipText")); // NOI18N
        jScrollPane1.setViewportView(jTextAreaModules);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jPanel1.border.title"))); // NOI18N
        jPanel1.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jPanel1.toolTipText")); // NOI18N

        jLabelCompilerJVMArgs.setLabelFor(jTextFieldCompilerJVMArgs);
        jLabelCompilerJVMArgs.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelCompilerJVMArgs.text")); // NOI18N

        jTextFieldCompilerJVMArgs.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextField1.text")); // NOI18N
        jTextFieldCompilerJVMArgs.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextFieldCompilerJVMArgs.toolTipText")); // NOI18N

        jLabelCompilerArgs.setLabelFor(jTextFieldCompilerArgs);
        jLabelCompilerArgs.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelCompilerArgs.text")); // NOI18N

        jTextFieldCompilerArgs.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextField1.text")); // NOI18N
        jTextFieldCompilerArgs.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextFieldCompilerArgs.toolTipText")); // NOI18N

        jLabelCompilerOutputStyle.setLabelFor(jComboBoxCompilerOutputStyle);
        jLabelCompilerOutputStyle.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelCompilerOutputStyle.text")); // NOI18N

        jComboBoxCompilerOutputStyle.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Obfuscated", "Pretty", "Detailed" }));
        jComboBoxCompilerOutputStyle.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jComboBoxCompilerOutputStyle.toolTipText")); // NOI18N

        jLabelCompilerLogLevel.setLabelFor(jComboBoxCompilerLogLevel);
        jLabelCompilerLogLevel.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelCompilerLogLevel.text")); // NOI18N

        jComboBoxCompilerLogLevel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ERROR", "WARN", "INFO", "TRACE", "DEBUG" }));
        jComboBoxCompilerLogLevel.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jComboBoxCompilerLogLevel.toolTipText")); // NOI18N

        jLabelCompilerWorkers.setLabelFor(jSpinnerCompilerWorkers);
        jLabelCompilerWorkers.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelCompilerWorkers.text")); // NOI18N

        jSpinnerCompilerWorkers.setModel(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        jSpinnerCompilerWorkers.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jSpinnerCompilerWorkers.toolTipText")); // NOI18N

        jLabelCompilerOutputDir.setLabelFor(jTextFieldCompilerOutputDirectory);
        jLabelCompilerOutputDir.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelCompilerOutputDir.text")); // NOI18N

        jTextFieldCompilerOutputDirectory.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextField1.text")); // NOI18N
        jTextFieldCompilerOutputDirectory.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextFieldCompilerOutputDirectory.toolTipText")); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelCompilerJVMArgs)
                    .add(jLabelCompilerArgs)
                    .add(jLabelCompilerOutputStyle)
                    .add(jLabelCompilerLogLevel)
                    .add(jLabelCompilerWorkers)
                    .add(jLabelCompilerOutputDir))
                .add(4, 4, 4)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jTextFieldCompilerOutputDirectory)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextFieldCompilerArgs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                    .add(jTextFieldCompilerJVMArgs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jSpinnerCompilerWorkers, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jComboBoxCompilerOutputStyle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jComboBoxCompilerLogLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelCompilerJVMArgs)
                        .add(jTextFieldCompilerJVMArgs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(26, 26, 26)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabelCompilerArgs)
                            .add(jTextFieldCompilerArgs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .add(6, 6, 6)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelCompilerOutputStyle)
                    .add(jComboBoxCompilerOutputStyle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelCompilerLogLevel)
                    .add(jComboBoxCompilerLogLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelCompilerWorkers)
                    .add(jSpinnerCompilerWorkers, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelCompilerOutputDir)
                    .add(jTextFieldCompilerOutputDirectory, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jPanel2.border.title"))); // NOI18N
        jPanel2.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jPanel2.toolTipText")); // NOI18N

        jLabelShellJVMArgs.setLabelFor(jTextFieldShellJVMArgs);
        jLabelShellJVMArgs.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelShellJVMArgs.text")); // NOI18N

        jTextFieldShellJVMArgs.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextField1.text")); // NOI18N
        jTextFieldShellJVMArgs.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextFieldShellJVMArgs.toolTipText")); // NOI18N

        jLabelShellArgs.setLabelFor(jTextFieldShellArgs);
        jLabelShellArgs.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelShellArgs.text")); // NOI18N

        jTextFieldShellArgs.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextField1.text")); // NOI18N
        jTextFieldShellArgs.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextFieldShellArgs.toolTipText")); // NOI18N

        jLabelShellOutputStyle.setLabelFor(jComboBoxShellOutputStyle);
        jLabelShellOutputStyle.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelShellOutputStyle.text")); // NOI18N

        jComboBoxShellOutputStyle.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Obfuscated", "Pretty", "Detailed" }));
        jComboBoxShellOutputStyle.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jComboBoxShellOutputStyle.toolTipText")); // NOI18N

        jLabelShellLogLevel.setLabelFor(jComboBoxShellLogLevel);
        jLabelShellLogLevel.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelShellLogLevel.text")); // NOI18N

        jComboBoxShellLogLevel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ERROR", "WARN", "INFO", "TRACE", "DEBUG" }));
        jComboBoxShellLogLevel.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jComboBoxShellLogLevel.toolTipText")); // NOI18N

        jLabelShellWebServerPort.setLabelFor(jSpinnerShellPort);
        jLabelShellWebServerPort.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelShellWebServerPort.text")); // NOI18N

        jSpinnerShellPort.setModel(new javax.swing.SpinnerNumberModel(0, 0, 65535, 1));
        jSpinnerShellPort.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jSpinnerShellPort.toolTipText")); // NOI18N

        jLabelShellCodeServerPort.setLabelFor(jSpinnerShellServerPort);
        jLabelShellCodeServerPort.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelShellCodeServerPort.text")); // NOI18N

        jSpinnerShellServerPort.setModel(new javax.swing.SpinnerNumberModel(0, 0, 65535, 1));
        jSpinnerShellServerPort.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jSpinnerShellServerPort.toolTipText")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabel2.text")); // NOI18N

        jTextFieldShellJVM.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextFieldShellJVM.text_1")); // NOI18N
        jTextFieldShellJVM.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "GWTShellJVMToolTip")); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelShellJVMArgs)
                    .add(jLabelShellCodeServerPort)
                    .add(jLabelShellOutputStyle)
                    .add(jLabelShellArgs)
                    .add(jLabelShellLogLevel)
                    .add(jLabelShellWebServerPort)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jComboBoxShellOutputStyle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jComboBoxShellLogLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jSpinnerShellServerPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jSpinnerShellPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldShellJVMArgs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                    .add(jTextFieldShellArgs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                    .add(jTextFieldShellJVM, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jTextFieldShellJVM, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelShellJVMArgs)
                    .add(jTextFieldShellJVMArgs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelShellArgs)
                    .add(jTextFieldShellArgs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelShellOutputStyle)
                    .add(jComboBoxShellOutputStyle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelShellLogLevel)
                    .add(jComboBoxShellLogLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelShellWebServerPort)
                    .add(jSpinnerShellPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelShellCodeServerPort)
                    .add(jSpinnerShellServerPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabelGWTVersion.setLabelFor(jComboBoxGWTVersion);
        jLabelGWTVersion.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelGWTVersion.text")); // NOI18N

        jComboBoxGWTVersion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1.5", "1.6", "1.7", "2.0", "2.1", "2.2", "2.3", "2.4", "2.5", " " }));
        jComboBoxGWTVersion.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jComboBoxGWTVersion.toolTipText")); // NOI18N
        jComboBoxGWTVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxGWTVersionActionPerformed(evt);
            }
        });

        jLabelGWTTestsJVMArgs.setLabelFor(jTextFieldTestsArgs);
        jLabelGWTTestsJVMArgs.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jLabelGWTTestsJVMArgs.text")); // NOI18N

        jTextFieldTestsArgs.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextField1.text")); // NOI18N
        jTextFieldTestsArgs.setToolTipText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jTextFieldTestsArgs.toolTipText")); // NOI18N

        jCheckBoxOpenBrowser.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jCheckBoxOpenBrowser.text")); // NOI18N

        jCheckBoxSuperDev.setText(org.openide.util.NbBundle.getMessage(SettingsPanel.class, "SettingsPanel.jCheckBoxSuperDev.text")); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelGWTVersion)
                    .add(jLabelGWTTestsJVMArgs))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jComboBoxGWTVersion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jCheckBoxSuperDev)
                        .add(0, 216, Short.MAX_VALUE))
                    .add(jTextFieldTestsArgs))
                .addContainerGap())
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jCheckBoxOpenBrowser))
                .add(0, 0, Short.MAX_VALUE))
            .add(jScrollPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCheckBoxOpenBrowser)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelGWTVersion)
                    .add(jComboBoxGWTVersion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jCheckBoxSuperDev))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelGWTTestsJVMArgs)
                    .add(jTextFieldTestsArgs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(0, 0, 0))
        );

        jScrollPane2.setViewportView(jPanel3);

        add(jScrollPane2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxGWTVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxGWTVersionActionPerformed
        boolean b = jComboBoxGWTVersion.getSelectedIndex() >= 8; // GWT ver. >= 2.5
        
        jCheckBoxSuperDev.setEnabled(b);
        if (!b) {
            jCheckBoxSuperDev.setSelected(false);
        }
    }//GEN-LAST:event_jComboBoxGWTVersionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBoxOpenBrowser;
    private javax.swing.JCheckBox jCheckBoxSuperDev;
    private javax.swing.JComboBox jComboBoxCompilerLogLevel;
    private javax.swing.JComboBox jComboBoxCompilerOutputStyle;
    private javax.swing.JComboBox jComboBoxGWTVersion;
    private javax.swing.JComboBox jComboBoxShellLogLevel;
    private javax.swing.JComboBox jComboBoxShellOutputStyle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelCompilerArgs;
    private javax.swing.JLabel jLabelCompilerJVMArgs;
    private javax.swing.JLabel jLabelCompilerLogLevel;
    private javax.swing.JLabel jLabelCompilerOutputDir;
    private javax.swing.JLabel jLabelCompilerOutputStyle;
    private javax.swing.JLabel jLabelCompilerWorkers;
    private javax.swing.JLabel jLabelGWTTestsJVMArgs;
    private javax.swing.JLabel jLabelGWTVersion;
    private javax.swing.JLabel jLabelShellArgs;
    private javax.swing.JLabel jLabelShellCodeServerPort;
    private javax.swing.JLabel jLabelShellJVMArgs;
    private javax.swing.JLabel jLabelShellLogLevel;
    private javax.swing.JLabel jLabelShellOutputStyle;
    private javax.swing.JLabel jLabelShellWebServerPort;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerCompilerWorkers;
    private javax.swing.JSpinner jSpinnerShellPort;
    private javax.swing.JSpinner jSpinnerShellServerPort;
    private javax.swing.JTextArea jTextAreaModules;
    private javax.swing.JTextField jTextFieldCompilerArgs;
    private javax.swing.JTextField jTextFieldCompilerJVMArgs;
    private javax.swing.JTextField jTextFieldCompilerOutputDirectory;
    private javax.swing.JTextField jTextFieldShellArgs;
    private javax.swing.JTextField jTextFieldShellJVM;
    private javax.swing.JTextField jTextFieldShellJVMArgs;
    private javax.swing.JTextField jTextFieldTestsArgs;
    // End of variables declaration//GEN-END:variables

    /**
     * Searches for a prefix in a list of strings.
     *
     * @param prefix searching for this prefix
     * @param values values for searching
     * @return index in {@code values} or -1
     */
    private static int prefixIndex(String prefix,
            List<String> values) {
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).startsWith(prefix)) {
                return i;
            }
        }
        return -1;
    }
}
