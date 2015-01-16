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
 * CrawlerConfigurationPanel.java
 *
 * Created on 29. marraskuuta 2004, 11:05
 */

package org.wandora.application.tools.extractors.datum;



import org.wandora.application.tools.extractors.*;
import org.wandora.application.gui.simple.*;
import org.wandora.application.gui.*;
import org.wandora.application.*;
import javax.swing.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;


/**
 *
 * @author  olli
 */
public class CrawlerConfigurationPanel extends javax.swing.JPanel implements DropTargetListener {
    
//    private Vector urlList;
    private DefaultListModel urlList;
    private CrawlerDataSource parent;
    
    /** Creates new form CrawlerConfigurationPanel */
    public CrawlerConfigurationPanel(CrawlerDataSource parent) {
        this.parent=parent;
//        urlList=new Vector();
        urlList=new DefaultListModel();
        initComponents();
        new DropTarget(list,this);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        list = new JList(urlList);
        jPanel1 = new javax.swing.JPanel();
        addFileButton = new javax.swing.JButton();
        addURLButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setViewportView(list);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridLayout(3, 1));

        addFileButton.setText("Add File");
        addFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFileButtonActionPerformed(evt);
            }
        });

        jPanel1.add(addFileButton);

        addURLButton.setText("Add URL");
        addURLButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addURLButtonActionPerformed(evt);
            }
        });

        jPanel1.add(addURLButton);

        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        jPanel1.add(removeButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridLayout(2, 0));

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jPanel2.add(cancelButton);

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        jPanel2.add(startButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);

    }//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        parent.guiCancel();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        for(int i=0;i<urlList.size();i++){
            parent.addToQueue(urlList.get(i));
        }
        parent.guiStart();
    }//GEN-LAST:event_startButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        int[] selected=list.getSelectedIndices();
        for(int i=selected.length-1;i>=0;i--){
            urlList.remove(selected[i]);
        }
    }//GEN-LAST:event_removeButtonActionPerformed

    private void addURLButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addURLButtonActionPerformed
        String url=WandoraOptionPane.showInputDialog(this,"Enter URL");
        try{
            URL u=new URL(url);
            urlList.addElement(u);
        }catch(MalformedURLException me){
            WandoraOptionPane.showMessageDialog(this,"Malformed URL entered!", WandoraOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addURLButtonActionPerformed

    private void addFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFileButtonActionPerformed
       SimpleFileChooser fc=UIConstants.getFileChooser();
       fc.setMultiSelectionEnabled(true);
       if(fc.open(this)==SimpleFileChooser.APPROVE_OPTION){
           File[] selected=fc.getSelectedFiles();
           for(int i=0;i<selected.length;i++){
               try{
                   URL u=selected[i].toURL();
                   urlList.addElement(u);
               }catch(MalformedURLException me){
                   WandoraOptionPane.showMessageDialog(this,"Malformed URL exception!", WandoraOptionPane.ERROR_MESSAGE);
               }
           }
       }
    }//GEN-LAST:event_addFileButtonActionPerformed

    public void dragEnter(java.awt.dnd.DropTargetDragEvent dtde) {
    }    
    
    public void dragExit(java.awt.dnd.DropTargetEvent dte) {
    }    
    
    public void dragOver(java.awt.dnd.DropTargetDragEvent dtde) {
    }
    
    public void drop(java.awt.dnd.DropTargetDropEvent dtde) {
        if(!dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
            dtde.rejectDrop();
            return;
        }
        dtde.acceptDrop(DnDConstants.ACTION_LINK);
        Transferable t=dtde.getTransferable();
        try{
            List l=(List)t.getTransferData(DataFlavor.javaFileListFlavor);
            Iterator iter=l.iterator();
            while(iter.hasNext()){
                File f=(File)iter.next();
                try{
                    URL u=f.toURL();
                    urlList.addElement(u);
                }
                catch(MalformedURLException mue){
                   WandoraOptionPane.showMessageDialog(this,"Malformed URL exception!", WandoraOptionPane.ERROR_MESSAGE);                    
                }
            }
        }catch(Exception e){}
    }
    
    public void dropActionChanged(java.awt.dnd.DropTargetDragEvent dtde) {
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFileButton;
    private javax.swing.JButton addURLButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList list;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
    
}
