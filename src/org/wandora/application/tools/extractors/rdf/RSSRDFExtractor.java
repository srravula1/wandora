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
 * RSSRDFExtractor.java
 *
 * Created on 26.2.2009,14:25
 */


package org.wandora.application.tools.extractors.rdf;

import javax.swing.*;
import org.wandora.application.gui.*;
import org.wandora.utils.Tuples.*;
import org.wandora.application.tools.extractors.rdf.rdfmappings.*;


/**
 *
 * @author akivela
 */
public class RSSRDFExtractor extends AbstractRDFExtractor {

    private RDF2TopicMapsMapping[] mappings = new RDF2TopicMapsMapping[] {
        new RSSMapping(),
        new RDFSMapping(),
        new RDFMapping(),
        new DublinCoreMapping(),
    };
    
    
    
    public RSSRDFExtractor() {
        
    }
    

    @Override
    public String getName() {
        return "RSS RDF extractor...";
    }
    
    @Override
    public String getDescription(){
        return "Read RSS 1.0 RDF feed and convert it to a topic map.";
    }
    
    @Override
    public Icon getIcon() {
        return UIBox.getIcon("gui/icons/extract_rss.png");
    }
    

    public RDF2TopicMapsMapping[] getMappings() {
        return mappings;
    }
    
}
