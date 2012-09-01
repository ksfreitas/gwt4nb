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
package com.nanosn.netbeans.gwtxml;

import com.nanosn.netbeans.gwtxml.gwtmodule.Module;
import com.nanosn.netbeans.gwtxml.ui.PanelFactory;
import com.nanosn.netbeans.gwtxml.ui.PanelType;
import org.netbeans.modules.xml.multiview.ToolBarMultiViewElement;
import org.netbeans.modules.xml.multiview.ui.SectionPanel;
import org.netbeans.modules.xml.multiview.ui.SectionView;
import org.netbeans.modules.xml.multiview.ui.ToolBarDesignEditor;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;

/**
 * Handles the UI for the node structure
 * @author selkhateeb
 */
class GwtxmlToolBarMultiViewElement extends ToolBarMultiViewElement {
    private static final long serialVersionUID = 1;
    
    //TODO Needs a lot of work

    private ToolBarDesignEditor comp;
    private SectionView view;
    private GwtXmlDataObject dObj_;
    private PanelFactory factory;

    public GwtxmlToolBarMultiViewElement(GwtXmlDataObject dObj) {
        super(dObj);
        this.dObj_ = dObj;
        comp = new ToolBarDesignEditor();
        factory = new PanelFactory(comp, dObj);
        setVisualEditor(comp);
    }

    @Override
    public SectionView getSectionView() {
        return view;
    }

    @Override
    public void componentShowing() {
        super.componentShowing();
        view = new ModuleView(dObj_);

        comp.setContentView(view);


        try {
            view.openPanel(dObj_.getModule());
        }
        catch (java.io.IOException ex) {
        }
        view.checkValidity();
    }

    private class ModuleView extends SectionView {
        private static final long serialVersionUID = 1;

        ModuleView(GwtXmlDataObject dObj) {
            super(factory);
            //Children rootChildren = new Children.Array();
            //Node root = new AbstractNode(rootChildren);
            Node root = null;
            try {
                Module module = dObj.getModule();
                
                final GeneralNode generalNode = new GeneralNode(module);
                final CompilerNode compilerNode = new CompilerNode(module);
                final ServletsNode servletsNode = new ServletsNode(module);
                final BindingsNode bindingsNode = new BindingsNode(module);
                final PropertiesNode propertiesNode = new PropertiesNode(module);

                Children.Array ch = new Children.Array();
                ch.add(new Node[] {generalNode,
                        compilerNode, servletsNode, bindingsNode,
                        propertiesNode});

                root = new AbstractNode(ch);

                // add panels
                addSection(new SectionPanel(this, generalNode, PanelType.ModulePanel)); // NOI18N
                addSection(new SectionPanel(this, compilerNode,
                        PanelType.CompilerPanel));
                addSection(new SectionPanel(this,
                        new PackagesNode(module), PanelType.PackagesPanel));
                addSection(new SectionPanel(this, servletsNode,
                        PanelType.PackagesPanel));
                addSection(new SectionPanel(this, bindingsNode,
                        PanelType.PackagesPanel));
                addSection(new SectionPanel(this, propertiesNode,
                        PanelType.PackagesPanel));
            }
            catch (java.io.IOException ex) {
                System.out.println("ex=" + ex); // NOI18N
                root.setDisplayName(NbBundle.getMessage(
                        GwtxmlToolBarMultiViewElement.class, 
                        "InvMod")); // NOI18N
            }
            setRoot(root);
        }
    }
// <editor-fold desc="Nodes">

    private class GeneralNode extends org.openide.nodes.AbstractNode {
        GeneralNode(Module module) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName(NbBundle.getMessage(
                    GwtxmlToolBarMultiViewElement.class, "GENERAL")); // NOI18N
        }
    }

    private class CompilerNode extends org.openide.nodes.AbstractNode {
        CompilerNode(Module module) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName(NbBundle.getMessage(
                    GwtxmlToolBarMultiViewElement.class, "COMPILER")); // NOI18N
        }
    }

    private class PackagesNode extends org.openide.nodes.AbstractNode {

        PackagesNode(Module module) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName(NbBundle.getMessage(
                    GwtxmlToolBarMultiViewElement.class, "PACKAGES")); // NOI18N
        }
    }

    private class ServletsNode extends org.openide.nodes.AbstractNode {
        ServletsNode(Module module) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName(NbBundle.getMessage(
                    GwtxmlToolBarMultiViewElement.class, "SERVLETS")); // NOI18N
        }
    }

    private class BindingsNode extends org.openide.nodes.AbstractNode {
        BindingsNode(Module module) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName(NbBundle.getMessage(
                    GwtxmlToolBarMultiViewElement.class, "BINDINGS")); // NOI18N
            setName("Bindings"); // NOI18N
        }
    }

    private class PropertiesNode extends org.openide.nodes.AbstractNode {
        PropertiesNode(Module module) {
            super(org.openide.nodes.Children.LEAF);
            setDisplayName(NbBundle.getMessage(
                    GwtxmlToolBarMultiViewElement.class, "PROPERTIES")); // NOI18N
        }
    }
    // </editor-fold>
}
