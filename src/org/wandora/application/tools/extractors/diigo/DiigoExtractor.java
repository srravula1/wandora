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
 * 
 * DiigoExtractor.java
 *
 * Created on 5.2.2009, 13:32
 */

package org.wandora.application.tools.extractors.diigo;


import org.wandora.application.contexts.*;
import org.wandora.application.tools.*;
import org.wandora.application.gui.*;
import org.wandora.application.gui.simple.*;
import org.wandora.application.*;

import javax.swing.*;



/**
 *
 * @author akivela
 */
public class DiigoExtractor extends AbstractWandoraTool {

    private static DiigoExtractorSelector selector = null;
    
    @Override
    public String getName() {
        return "Diigo extractor...";
    }
    @Override
    public String getDescription(){
        return "Convert various Diigo feeds to topic maps";
    }
    @Override
    public WandoraToolType getType() {
        return WandoraToolType.createExtractType();
    }
    @Override
    public Icon getIcon() {
        return UIBox.getIcon("gui/icons/extract_diigo.png");
    }
    
    
    
    public void execute(Wandora admin, Context context) {
        int counter = 0;   
        try {
            if(selector == null) {
                selector = new DiigoExtractorSelector(admin);
            }
            selector.setAccepted(false);
            selector.setWandora(admin);
            selector.setContext(context);
            selector.setVisible(true);
            if(selector.wasAccepted()) {
                setDefaultLogger();
                WandoraTool extractor = selector.getWandoraTool(this);
                if(extractor != null) {
                    extractor.setToolLogger(getDefaultLogger());
                    extractor.execute(admin, context);
                }
            }
            else {
                log("User cancelled the extraction!");
            }
        }
        catch(Exception e) {
            singleLog(e);
        }
        if(selector != null && selector.wasAccepted()) setState(WAIT);
    }

}
