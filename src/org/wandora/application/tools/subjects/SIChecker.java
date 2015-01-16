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
 */

package org.wandora.application.tools.subjects;

import java.net.URI;
import org.wandora.application.tools.*;
import org.wandora.topicmap.*;
import org.wandora.application.*;
import org.wandora.application.contexts.*;
import java.util.*;



/**
 *
 * @author  akivela
 */
public class SIChecker extends AbstractWandoraTool implements WandoraTool {

    public SIChecker() {
        super();
    }
    public SIChecker(Context context) {
        setContext(context);
    }


    @Override
    public String getName() {
        return "SI checker";
    }

    @Override
    public String getDescription() {
        return "Investigates subject identifier and reports if they are not valid URIs.";
    }

    @Override
    public void execute(Wandora admin, Context context) throws TopicMapException {
        Iterator contextTopics = context.getContextObjects();
        if(contextTopics != null && contextTopics.hasNext()) {
            setDefaultLogger();
            setLogTitle("Checking SIs");
            Topic topic = null;
            Locator l = null;
            Collection sis = null;
            int progress = 0;
            int broken = 0;
            int checked = 0;

            while(contextTopics.hasNext() && !forceStop()) {
                try {
                    topic = (Topic) contextTopics.next();
                    if(topic != null && !topic.isRemoved()) {
                        setProgress(progress++);
                        sis = topic.getSubjectIdentifiers();
                        if(!sis.isEmpty()) {
                            for(Iterator siIterator = sis.iterator(); siIterator.hasNext(); ) {
                                l = (Locator) siIterator.next();
                                if(l != null) {
                                    checked++;
                                    hlog("Investigating SI\n"+l.toExternalForm());
                                    try {
                                        String si = l.toExternalForm();
                                        URI.create(si);
                                    }
                                    catch(Exception e) {
                                        log("Found invalid URI: " + l.toExternalForm());
                                        broken++;
                                    }
                                }
                            }
                        }
                    }
                }
                catch(Exception e) {
                    log(e);
                }
            }
            log("Checked "+checked+" subject identifiers.");
            if(broken == 0)
                log("All subject identifiers are valid URIs.");
            else {
                if(broken == 1) {
                    log("Found 1 subject identifier that is not valid URI.");
                }
                else {
                    log("Found "+broken+" subject identifiers that are not valid URIs.");
                }
            }
            setState(WAIT);
        }

    }

    
    @Override
    public boolean requiresRefresh() {
        return false;
    }
    
}
