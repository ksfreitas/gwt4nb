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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package org.netbeans.modules.gwt4nb;

import java.io.IOException;
import javax.swing.JTextField;
import junit.framework.Test;
import org.netbeans.jellytools.Bundle;
import org.netbeans.jellytools.EditorOperator;
import org.netbeans.jellytools.MainWindowOperator;
import org.netbeans.jellytools.NbDialogOperator;
import org.netbeans.jellytools.NewWebProjectNameLocationStepOperator;
import org.netbeans.jellytools.actions.EditAction;
import org.netbeans.jellytools.actions.OpenAction;
import org.netbeans.jellytools.modules.j2ee.J2eeTestCase;
import org.netbeans.jellytools.modules.web.nodes.WebPagesNode;
import org.netbeans.jellytools.nodes.Node;
import org.netbeans.jellytools.NewProjectWizardOperator;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.Timeouts;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.Operator.DefaultStringComparator;
import org.netbeans.junit.NbModuleSuite;

/**
 * Just a sample for now.
 * Add
 * test-qa-functional-sys-prop.tomcat.home=C\:\\Programme\\apache-tomcat-6.0.18
 * to your nbproject/private/platform-private.properties
 * to run these tests.
 */
public class SampleTest extends J2eeTestCase {
    public static final String PROJECT_NAME = "GWTWebApplication";

    public SampleTest(String string) {
        super(string);
    }
    
    @Override
    public void setUp() {
        System.out.println("########  "+getName()+"  #######");
        final Timeouts timeouts = MainWindowOperator.getDefault().getTimeouts();
        timeouts.setTimeout("ComponentOperator.WaitStateTimeout", 1000);
        timeouts.setTimeout("Waiter.WaitingTime", 60000);
    }

    /**
     * Without this method the test will work, But you don't get a UI.
     */
    public static Test suite() {
        NbModuleSuite.Configuration testConfig =
                NbModuleSuite.createConfiguration(
                SampleTest.class);
        testConfig = addServerTests(Server.ANY, testConfig,
                "testNewProject");
        testConfig.clusters(".*").enableModules(".*");
        //testConfig.gui(true);
        return NbModuleSuite.create(testConfig);
    }

    /**
     * A sample.
     */
    public void testNewProject() throws IOException {
        /*
        new Action("File|New Project", null).perform();
        NbDialogOperator newProjectDialog = new NbDialogOperator("New Project");
        JTreeOperator categoriesTree = new JTreeOperator(newProjectDialog);
        TreePath javaWebCategory = categoriesTree.findPath("Java Web");
        categoriesTree.selectPath(javaWebCategory);
        JListOperator projectsList = new JListOperator(newProjectDialog, 1);
        projectsList.selectItem("Web Application");
        JButtonOperator buttonNext = new JButtonOperator(newProjectDialog,
                "Next");
        buttonNext.push();
        buttonNext.push();
        buttonNext.push();*/
        // "Web"
        String web = Bundle.getStringTrimmed(
                "org.netbeans.modules.web.core.Bundle",
                "OpenIDE-Module-Display-Category");
        // "Web Application"
        String webApplication = Bundle.getStringTrimmed(
                "org.netbeans.modules.web.project.ui.wizards.Bundle",
                "Templates/Project/Web/emptyWeb.xml");
        NewProjectWizardOperator nop = NewProjectWizardOperator.invoke();
        nop.selectCategory(web);
        nop.selectProject(webApplication);
        nop.next();
        NewWebProjectNameLocationStepOperator lop =
                new NewWebProjectNameLocationStepOperator();
        lop.setProjectName(PROJECT_NAME);
        lop.setProjectLocation(getDataDir().getCanonicalPath());
        lop.next();
        lop.next();
        NewProjectWizardOperator frameworkStep = new NewProjectWizardOperator();
        // select GWT
        JTableOperator tableOper = new JTableOperator(frameworkStep);
        for(int i=0; i<tableOper.getRowCount(); i++) {
            if(tableOper.getValueAt(i, 1).toString().startsWith(
                    "org.netbeans.modules.gwt4nb.GWTFrameworkProvider")) { // NOI18N
                tableOper.selectCell(i, 0);
                break;
            }
        }
        // set ApplicationResource location
        new JTextFieldOperator(
                (JTextField)new JLabelOperator(frameworkStep,
                "GWT Installation Folder:").getLabelFor()
                ).setText("C:\\Dokumente und Einstellungen\\tim.TIMPC\\Eigene Dateien\\libs\\gwt-2.0.0");
        frameworkStep.btFinish().pushNoBlock();
        frameworkStep.getTimeouts().setTimeout("ComponentOperator.WaitStateTimeout", 60000);
        frameworkStep.waitClosed();
        // Opening Projects
        String openingProjectsTitle = Bundle.getString(
                "org.netbeans.modules.project.ui.Bundle",
                "LBL_Opening_Projects_Progress");
        try {
            // wait at most 60 second until progress dialog dismiss
            NbDialogOperator openingOper = new NbDialogOperator(openingProjectsTitle);
            frameworkStep.getTimeouts().setTimeout("ComponentOperator.WaitStateTimeout", 60000);
            openingOper.waitClosed();
        } catch (TimeoutExpiredException e) {
            // ignore when progress dialog was closed before we started to wait for it
        }
        // ProjectSupport.waitScanFinished();
        // Check project contains all needed files.
        WebPagesNode webPages = new WebPagesNode(PROJECT_NAME);
        new Node(webPages, "welcomeGWT.html");
    }
}
