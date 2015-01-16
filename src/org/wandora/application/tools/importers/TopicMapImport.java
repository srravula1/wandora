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
 * TopicMapImport.java
 *
 * Created on June 18, 2004, 9:37 AM
 */

package org.wandora.application.tools.importers;



import org.wandora.topicmap.*;
import org.wandora.topicmap.layered.*;
import org.wandora.application.*;
import org.wandora.application.gui.*;
import java.io.*;
import javax.swing.*;




/**
 * <p>
 * TopicMapImport is used to import JTM, LTM and XTM topic maps to Wandora.
 * </p>
 * 
 * @author  olli & ak
 */
public class TopicMapImport extends AbstractImportTool implements WandoraTool {
    


    
    /**
     * Creates a new instance of TopicMapImport
     */
    public TopicMapImport() {
    }
    
    public TopicMapImport(int options) {
        setOptions(options);
    }
    
    @Override
    public String getName() {
        return "Import topic map";
    }
    
    @Override
    public String getDescription() {
        return "Reads XTM, LTM or JTM topic map file and merges it to current layer or "+
               "creates new layer for the topic map.";
    }
    
    @Override
    public Icon getIcon() {
        return UIBox.getIcon("gui/icons/merge_topicmap.png");
    }

    
    // -------------------------------------------------------------------------
    
    
    

    @Override
    public void initialize(Wandora admin,org.wandora.utils.Options options,String prefix) throws TopicMapException {
    }
    
    @Override
    public boolean isConfigurable(){
        return true;
    }
    
    @Override
    public void configure(Wandora w,org.wandora.utils.Options options,String prefix) throws TopicMapException {
        //System.out.println(prefix);
        TopicMapImportConfiguration dialog=new TopicMapImportConfiguration(w);
        dialog.openDialog();
        if(!dialog.wasAccepted()){
            dialog.saveConfiguration();
        }
    }
    
    @Override
    public void writeOptions(Wandora admin,org.wandora.utils.Options options,String prefix){
    }
    

    
    // -------------------------------------------------------------------------
    
    
    
    @Override
    public void importStream(Wandora admin, String streamName, InputStream inputStream) {
        try {
            try {
                admin.getTopicMap().clearTopicMapIndexes();
            }
            catch(Exception e) {
                log(e);
            }
            
            TopicMap map = null;
            if(directMerge) {
                log("Merging '" + streamName + "' to context layer while reading.");
                map = solveContextTopicMap(admin, getContext());
            }
            else {
                map = new org.wandora.topicmap.memory.TopicMapImpl();
            }

            if(streamName.toLowerCase().endsWith(".ltm")) {
                map.importLTM(inputStream, getCurrentLogger());
            }
            else if(streamName.toLowerCase().endsWith(".jtm")) {
                map.importJTM(inputStream, getCurrentLogger());
            }
            else {
                map.importXTM(inputStream, getCurrentLogger());
            }

            if(!directMerge) {
                if(newLayer) {
                    LayerStack layerStack = admin.getTopicMap();
                    String layerName = streamName;
                    int c = 2;
                    if(layerStack.getLayer(layerName) != null) {
                        do {
                            layerName = streamName + " " + c;
                            c++;
                        }
                        while(layerStack.getLayer(layerName) != null);
                    }
                    log("Creating new layer for '" + layerName + "'.");
                    layerStack.addLayer(new Layer(map,layerName,layerStack));
                    admin.layerTree.resetLayers();
                }
                else {
                    log("Merging '" + streamName + "'.");
                    solveContextTopicMap(admin, getContext()).mergeIn(map);
                }
            }
        }
        catch(Exception e) {
            //info.dialog.setVisible(false);
            log("Reading '" + streamName + "' failed!", e);
        }
    }
    

    

    @Override
    public String getGUIText(int textType) {
        switch(textType) {
            case FILE_DIALOG_TITLE_TEXT: {
                return "Select XTM, LTM or JTM file to import";
            }
            case URL_DIALOG_MESSAGE_TEXT: {
                return "Type the internet address of imported XTM, LTM or JTM document";
            }
        }
        return "";
    }


}
