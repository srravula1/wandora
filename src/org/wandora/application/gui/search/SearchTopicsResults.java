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
 * SearchTopicsResults.java
 *
 * Created on 30. joulukuuta 2008, 22:23
 */

package org.wandora.application.gui.search;


import org.wandora.application.gui.table.MixedTopicTable;


import java.util.*;
import org.wandora.application.*;
import java.awt.*;
import javax.swing.*;
import org.wandora.application.gui.UIBox;


/**
 *
 * @author  akivela
 */
public class SearchTopicsResults extends javax.swing.JDialog {

    private boolean searchAgain = false;
    
    
    /** Creates new form SearchTopicsResults */
    public SearchTopicsResults(Wandora wandora, Collection results) {
        super(wandora, true);
        initComponents();
        
        if(results != null && results.size() > 0) {
            MixedTopicTable table = new MixedTopicTable(wandora);
            table.initialize(results.toArray(new Object[] {} ), null);
            JScrollPane sp=new JScrollPane(table);
            resultPanel.add(sp, BorderLayout.CENTER);
        }
        else {
            resultPanel.add(emptyResultSetLabel, BorderLayout.CENTER);
        }
        
        searchAgain = false;
        this.setSize(800, 600);
        if(wandora != null) UIBox.centerWindow(this, wandora);
    }


    public SearchTopicsResults(Wandora wandora, MixedTopicTable table) {
        super(wandora, true);
        initComponents();
        if(table != null) {
            JScrollPane sp=new JScrollPane(table);
            resultPanel.add(sp, BorderLayout.CENTER);
        }
        else {
            resultPanel.add(emptyResultSetLabel, BorderLayout.CENTER);
        }
        searchAgain = false;
        this.setSize(800, 600);
        if(wandora != null) UIBox.centerWindow(this, wandora);
    }



    // ------
    
    public boolean doSearchAgain() {
        return searchAgain;
    }
    public void hideAgainButton() {
        this.againButton.setVisible(false);
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        emptyResultSetLabel = new org.wandora.application.gui.simple.SimpleLabel();
        resultPanel = new javax.swing.JPanel();
        buttonPanel = new javax.swing.JPanel();
        againButton = new org.wandora.application.gui.simple.SimpleButton();
        closeButton = new org.wandora.application.gui.simple.SimpleButton();

        emptyResultSetLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        emptyResultSetLabel.setText("<html><p align=\"center\">Found no topics!</p></html>");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search results");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        resultPanel.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(resultPanel, gridBagConstraints);

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        againButton.setText("Again");
        againButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        againButton.setMaximumSize(new java.awt.Dimension(70, 23));
        againButton.setMinimumSize(new java.awt.Dimension(70, 23));
        againButton.setPreferredSize(new java.awt.Dimension(70, 23));
        againButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                againButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        buttonPanel.add(againButton, gridBagConstraints);

        closeButton.setText("Close");
        closeButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        closeButton.setMaximumSize(new java.awt.Dimension(70, 23));
        closeButton.setMinimumSize(new java.awt.Dimension(70, 23));
        closeButton.setPreferredSize(new java.awt.Dimension(70, 23));
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(closeButton, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 4, 5);
        getContentPane().add(buttonPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
    searchAgain = false;
    setVisible(false);
}//GEN-LAST:event_closeButtonActionPerformed

private void againButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_againButtonActionPerformed
    searchAgain = true;
    setVisible(false);
}//GEN-LAST:event_againButtonActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton againButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel emptyResultSetLabel;
    private javax.swing.JPanel resultPanel;
    // End of variables declaration//GEN-END:variables

}
