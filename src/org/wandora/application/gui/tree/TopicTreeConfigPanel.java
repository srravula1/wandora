/*
 * WANDORA
 * Knowledge Extraction, Management, and Publishing Application
 * http://wandora.org
 * 
 * Copyright (C) 2004-2015 Wandora Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * 
 * TopicTreeConfigPanel.java
 *
 * Created on 13. helmikuuta 2006, 10:54
 */

package org.wandora.application.gui.tree;


import javax.swing.*;
import java.util.*;
import java.awt.*;
import org.wandora.application.Wandora;
import org.wandora.topicmap.*;
import org.wandora.application.gui.simple.*;
import org.wandora.application.gui.GetTopicButton;



/**
 *
 * @author  olli, akivela
 */
public class TopicTreeConfigPanel extends javax.swing.JPanel {
    
    private boolean cancelled=true;
    
    private TopicTreeRelation[] allRelations;
    private ArrayList<JCheckBox> checkboxes;
    
    private Component parent;
    private Wandora wandora;
    
    
    
    /** Creates new form TopicTreeConfigPanel */
    public TopicTreeConfigPanel(TopicTreeRelation[] allRelations, Set<String> selectedRelations, String root, String name, Component parent, Wandora wandora) throws TopicMapException {
        this.wandora = wandora;
        rootButton = new GetTopicButton(wandora);
        initComponents();
        this.parent=parent;
        nameTextField.setText(name);
//        rootTextField.setText(root);
        ((GetTopicButton)rootButton).setTopic(root);
        
        updateRelationsUI(allRelations, selectedRelations);
        
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=allRelations.length+1;
        gbc.weighty=1.0;
        gbc.fill=GridBagConstraints.VERTICAL;
        relationsPanel.add(new JPanel(),gbc);
    }
    
    
    
    
    private void updateRelationsUI(TopicTreeRelation[] allRelations, Set<String> selectedRelations) {
        //relationsPanel.removeAll();
        
        checkboxes = new ArrayList<JCheckBox>();
        this.allRelations = allRelations;
        for(int i=0; i<allRelations.length; i++) {
            GridBagConstraints gbc=new GridBagConstraints();
            gbc.gridx=0;
            gbc.gridy=1+i;
            gbc.anchor=GridBagConstraints.WEST;
            gbc.fill=GridBagConstraints.HORIZONTAL;
            gbc.weightx=1.0;
            String aname=allRelations[i].name;
            JCheckBox checkbox=new SimpleCheckBox();
            checkbox.setText(aname);
            if(selectedRelations.contains(aname)) checkbox.setSelected(true);
            else checkbox.setSelected(false);
            checkboxes.add(checkbox);
            relationsPanel.add(checkbox,gbc);
        }
    }
    
    
    
    public Set<String> getSelectedRelations() {
        HashSet<String> selected=new HashSet<String>();
        for(JCheckBox cb : checkboxes) {
            if(cb.isSelected()) selected.add(cb.getText());
        }
        return selected;
    }
    
    public String getRoot() throws TopicMapException {
        return ((GetTopicButton)rootButton).getTopicSI();
    }
    
    public String getTreeName() {
        return nameTextField.getText();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        rootTextField = new org.wandora.application.gui.simple.SimpleField();
        jLabel1 = new org.wandora.application.gui.simple.SimpleLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        relationsPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        editRelationsButton = new SimpleButton();
        jPanel2 = new javax.swing.JPanel();
        okButton = new org.wandora.application.gui.simple.SimpleButton();
        cancelButton = new org.wandora.application.gui.simple.SimpleButton();
        rootButton = rootButton;
        jLabel2 = new org.wandora.application.gui.simple.SimpleLabel();
        nameTextField = new org.wandora.application.gui.simple.SimpleField();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Root");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 3, 5);
        add(jLabel1, gridBagConstraints);

        relationsPanel.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(relationsPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jScrollPane1, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        editRelationsButton.setText("Edit relations");
        editRelationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editRelationsButtonActionPerformed(evt);
            }
        });
        jPanel1.add(editRelationsButton, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        okButton.setText("OK");
        okButton.setMaximumSize(new java.awt.Dimension(70, 23));
        okButton.setMinimumSize(new java.awt.Dimension(70, 23));
        okButton.setPreferredSize(new java.awt.Dimension(70, 23));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel1.add(okButton, gridBagConstraints);

        cancelButton.setText("Cancel");
        cancelButton.setMaximumSize(new java.awt.Dimension(70, 23));
        cancelButton.setMinimumSize(new java.awt.Dimension(70, 23));
        cancelButton.setPreferredSize(new java.awt.Dimension(70, 23));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        jPanel1.add(cancelButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        add(jPanel1, gridBagConstraints);

        rootButton.setMaximumSize(new java.awt.Dimension(35, 20));
        rootButton.setMinimumSize(new java.awt.Dimension(35, 20));
        rootButton.setPreferredSize(new java.awt.Dimension(35, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 3, 5);
        add(rootButton, gridBagConstraints);

        jLabel2.setText("Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 5, 3, 5);
        add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(7, 5, 3, 5);
        add(nameTextField, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        cancelled=true;
        parent.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        cancelled=false;
        parent.setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed

    private void editRelationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editRelationsButtonActionPerformed
        TopicTreeRelationsEditor editor = new TopicTreeRelationsEditor();
        editor.open(Wandora.getWandora());
        if(editor.wasCancelled()) return;

        updateRelationsUI(editor.readRelationTypes(), getSelectedRelations());
        if(parent != null) {
            parent.revalidate();
            parent.repaint();
        }
    }//GEN-LAST:event_editRelationsButtonActionPerformed
    
    public boolean wasCancelled() {
        return cancelled;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton editRelationsButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel relationsPanel;
    private javax.swing.JButton rootButton;
    private javax.swing.JTextField rootTextField;
    // End of variables declaration//GEN-END:variables
    
}
