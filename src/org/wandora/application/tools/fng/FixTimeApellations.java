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
 * FixTimeApellations.java
 *
 * Created on August 17, 2004, 7:12 PM
 */



package org.wandora.application.tools.fng;


import org.wandora.application.tools.AbstractWandoraTool;
import gnu.regexp.*;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

import org.wandora.topicmap.*;
import org.wandora.application.*;
import org.wandora.application.contexts.*;



/**
 *
 * @author  akivela
 */
public class FixTimeApellations extends AbstractWandoraTool implements WandoraTool {
    
    
    public void execute(Wandora admin, Context context) {
        try {
            TopicMap topicMap = admin.getTopicMap();
            int answer = JOptionPane.showConfirmDialog(admin,"You are about to fix time apellations!\nAre you sure?","Fix time apellations?", JOptionPane.YES_NO_OPTION);
            if(answer == JOptionPane.YES_OPTION) {
                FixTimeApellations.process(topicMap);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String getName() {
        return "Fix time apellations";
    }
    

    
    
    

    public static String trimExtraSpaces(String string) {
        return trimEndingSpaces(trimStartingSpaces(string));
    }
    

    public static String chop(String text) {
        return trimEndingSpaces(text);
    }
    
    
    
    public static String trimEndingSpaces(String string) {
        if (string != null) {
            int i = string.length()-1;
            while(i > 0 && Character.isWhitespace(string.charAt(i))) i--;
            string = string.substring(0, i+1);
        }
        return string;
    }

   
    public static String trimStartingSpaces(String string) {
        if (string != null) {
            int i = 0;
            while(i < string.length() && Character.isWhitespace(string.charAt(i))) i++;
            string = string.substring(i);
        }
        return string;
    }
    
    
    
    
    public static String fixTime(String oldTime) {
        RE re = null;
        try {
            
            // 1990 - 92
            re = new RE("([1-2][0-9]{3}) *\\- *([0-9][0-9])", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                REMatch match = re.getMatch(oldTime);
                String year1 = oldTime.substring(match.getStartIndex(1), match.getEndIndex(1));
                String year2 = oldTime.substring(match.getStartIndex(2), match.getEndIndex(2));
                year2 = year1.substring(0,2) + year2;
                
                return year1 + " - " + year2;
            }
            
            // 1990 - 1992
            re = new RE("([1-2][0-9]{3}) *\\- *([1-2][0-9]{3})", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                REMatch match = re.getMatch(oldTime);
                String year1 = oldTime.substring(match.getStartIndex(1), match.getEndIndex(1));
                String year2 = oldTime.substring(match.getStartIndex(2), match.getEndIndex(2));
                
                return year1 + " - " + year2;
            }
            
            // 90-luku
            re = new RE("([0-9]{2}) *\\-luku", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                REMatch match = re.getMatch(oldTime);
                String year = oldTime.substring(match.getStartIndex(1), match.getEndIndex(1));
                return "19" + year + "-luku";
            }
            
            
            // 1990-luku
            re = new RE("([1-2][0-9]{3}) *\\-luku", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                REMatch match = re.getMatch(oldTime);
                String year = oldTime.substring(match.getStartIndex(1), match.getEndIndex(1));
                return year + "-luku";
            }    
            
        

            // 13.-15.6.2001
            re = new RE("([0-9][0-9]?)\\.\\-([0-9][0-9]?) *\\. *([0-9][0-9]?) *\\. *([1-2][0-9][0-9][0-9])", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                REMatch match = re.getMatch(oldTime);
                String year = oldTime.substring(match.getStartIndex(4), match.getEndIndex(4));
                String month = oldTime.substring(match.getStartIndex(3), match.getEndIndex(3));
                String day1 = oldTime.substring(match.getStartIndex(1), match.getEndIndex(1));
                String day2 = oldTime.substring(match.getStartIndex(2), match.getEndIndex(2));
                
                if(month.length() == 1) month = "0" + month;
                if(day1.length() == 1) day1 = "0" + day1;
                if(day2.length() == 1) day2 = "0" + day2;
                
                return year + "-" + month + "-" + day1 + " - " + year + "-" + month + "-" + day2;
            }

            // 13.6.2001
            re = new RE("([0-9][0-9]?) *\\. *([0-9][0-9]?) *\\. *([1-2][0-9][0-9][0-9])\\,*", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                REMatch match = re.getMatch(oldTime);
                String year = oldTime.substring(match.getStartIndex(3), match.getEndIndex(3));
                String month = oldTime.substring(match.getStartIndex(2), match.getEndIndex(2));
                String day = oldTime.substring(match.getStartIndex(1), match.getEndIndex(1));
                
                if(month.length() == 1) month = "0" + month;
                if(day.length() == 1) day = "0" + day;
                
                return year + "-" + month + "-" + day;
            }

            
            // 2001.6.16.
            re = new RE("([1-2][0-9][0-9][0-9]) *\\. *([0-9][0-9]?) *\\. *([0-9][0-9]) *\\.?", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                REMatch match = re.getMatch(oldTime);
                String year = oldTime.substring(match.getStartIndex(1), match.getEndIndex(1));
                String month = oldTime.substring(match.getStartIndex(2), match.getEndIndex(2));
                String day = oldTime.substring(match.getStartIndex(3), match.getEndIndex(3));
                
                if(month.length() == 1) month = "0" + month;
                if(day.length() == 1) day = "0" + day;
                
                return year + "-" + month + "-" + day;
            }
            
            
            // .6.2001
            re = new RE("\\. *([0-9][0-9]?) *\\. *([1-2][0-9][0-9][0-9])", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                REMatch match = re.getMatch(oldTime);
                String year = oldTime.substring(match.getStartIndex(2), match.getEndIndex(2));
                String month = oldTime.substring(match.getStartIndex(1), match.getEndIndex(1));
                
                if(month.length() == 1) month = "0" + month;
                
                return year + "-" + month;
            }
            
            
            // .6/4.2001
            re = new RE("\\. *([0-9][0-9]?) *\\/ *([0-9][0-9]?) *\\. *([1-2][0-9][0-9][0-9])", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                REMatch match = re.getMatch(oldTime);
                String year = oldTime.substring(match.getStartIndex(3), match.getEndIndex(3));
                String month1 = oldTime.substring(match.getStartIndex(1), match.getEndIndex(1));
                String month2 = oldTime.substring(match.getStartIndex(2), match.getEndIndex(2));
                
                if(month1.length() == 1) month1 = "0" + month1;
                if(month2.length() == 1) month2 = "0" + month2;
                
                return year + "-" + month1 + " - " + year + "-" + month2;
            }
            

            // 13.6.01
            re = new RE("([0-9][0-9]?) *\\. *([0-9][0-9]?) *\\. *([0-9][0-9])", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                REMatch match = re.getMatch(oldTime);
                String year = oldTime.substring(match.getStartIndex(3), match.getEndIndex(3));
                String month = oldTime.substring(match.getStartIndex(2), match.getEndIndex(2));
                String day = oldTime.substring(match.getStartIndex(1), match.getEndIndex(1));
                
                if(month.length() == 1) month = "0" + month;
                if(day.length() == 1) day = "0" + day;
                if(Integer.parseInt(year) > 4) year = "19" + year;
                else year = "20" + year;
                
                return year + "-" + month + "-" + day;
            }
            
            
            // 6.2001
            re = new RE("([0-9]+) *\\. *([1-2][0-9][0-9][0-9])", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                REMatch match = re.getMatch(oldTime);
                String year = oldTime.substring(match.getStartIndex(2), match.getEndIndex(2));
                String month = oldTime.substring(match.getStartIndex(1), match.getEndIndex(1));
                
                if(month.length() == 1) month = "0" + month;
                
                return year + "-" + month;
            }
            
            
            // 6/2001
            re = new RE("([0-9][0-9]?) *\\/ *([1-2][0-9][0-9][0-9])", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                REMatch match = re.getMatch(oldTime);
                String year = oldTime.substring(match.getStartIndex(2), match.getEndIndex(2));
                String month = oldTime.substring(match.getStartIndex(1), match.getEndIndex(1));
                
                if(month.length() == 1) month = "0" + month;
                
                return year + "-" + month;
            }
            
            
            // tammikuu 2001
            re = new RE("(tammi|helmi|maalis|huhti|touko|kes.|hein.|elo|syys|loka|marras|joulu)kuu +([1-2][0-9][0-9][0-9])", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                REMatch match = re.getMatch(oldTime);
                String year = oldTime.substring(match.getStartIndex(2), match.getEndIndex(2));
                String month = oldTime.substring(match.getStartIndex(1), match.getEndIndex(1));
                
                if("tammi".equalsIgnoreCase(month)) month = "01";
                if("helmi".equalsIgnoreCase(month)) month = "02";
                if("maalis".equalsIgnoreCase(month)) month = "03";
                if("huhti".equalsIgnoreCase(month)) month = "04";
                if("touko".equalsIgnoreCase(month)) month = "05";
                if("kes�".equalsIgnoreCase(month)) month = "06";
                if("hein�".equalsIgnoreCase(month)) month = "07";
                if("elo".equalsIgnoreCase(month)) month = "08";
                if("syys".equalsIgnoreCase(month)) month = "09";
                if("loka".equalsIgnoreCase(month)) month = "10";
                if("marras".equalsIgnoreCase(month)) month = "11";
                if("joulu".equalsIgnoreCase(month)) month = "12";
                
                return year + "-" + month;
            }
            
                        
            // 2001-06-01
            re = new RE("[1-2][0-9]{3}\\-[0-9]+\\-[0-9]+", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                return oldTime;
            }
            
                        
            // 2001
            re = new RE("[1-2][0-9]{3}", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                return oldTime;
            }
            
            // n. 2001
            re = new RE("n\\. [1-2][0-9]{3}", RE.REG_ICASE);
            if(re.isMatch(oldTime)) {
                return oldTime;
            }  
            
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    
    public static void setDisplayName(Topic t, String lang, String name) throws TopicMapException {
        String langsi=XTMPSI.getLang(lang);
        Topic langT=t.getTopicMap().getTopic(langsi);
        String dispsi=XTMPSI.DISPLAY;
        Topic dispT=t.getTopicMap().getTopic(dispsi);
        HashSet scope=new HashSet();
        if(langT!=null) scope.add(langT);
        if(dispT!=null) scope.add(dispT);
        t.setVariant(scope, name);
    }
    
    
    
    
    public static void createRelated(TopicMap tm, Topic t, String fn) throws TopicMapException {
        RE re = null;
        REMatch match = null;
        
        try {
            // 1997-06-22
            re = new RE("([1-2][0-9]{3})\\-([0-9]+)\\-([0-9]+)", RE.REG_ICASE);
            match = re.getMatch(fn);

            if(match != null) {
                String year = fn.substring(match.getStartIndex(1), match.getEndIndex(1));
                String month = fn.substring(match.getStartIndex(2), match.getEndIndex(2));
                String day = fn.substring(match.getStartIndex(3), match.getEndIndex(3));

                addDecadeTopic(tm, t, year);
                addCenturyTopic(tm, t, year);
                addYearTopic(tm, t, year);
                addMonthTopic(tm, t, month);
                return;
            }
            
            
            // 1992-04
            re = new RE("([1-2][0-9]{3})\\-([0-9]+)", RE.REG_ICASE);
            match = re.getMatch(fn);

            if(match != null) {
                String year = fn.substring(match.getStartIndex(1), match.getEndIndex(1));
                String month = fn.substring(match.getStartIndex(2), match.getEndIndex(2));

                addDecadeTopic(tm, t, year);
                addCenturyTopic(tm, t, year);
                addMonthTopic(tm, t, month);
                return;
            }

                       
            // 1992 - 1995
            re = new RE("([1-2][0-9]{3}) \\- ([1-2][0-9]{3})", RE.REG_ICASE);
            match = re.getMatch(fn);

            if(match != null) {
                String year1 = fn.substring(match.getStartIndex(1), match.getEndIndex(1));
                String year2 = fn.substring(match.getStartIndex(2), match.getEndIndex(2));
                int y1 = Integer.parseInt(year1);
                int y2 = Integer.parseInt(year2);
                
                for(int y=y1; y<=y2; y++) {
                    addDecadeTopic(tm, t, "" + y);
                    addCenturyTopic(tm, t, "" + y);
                }
                return;
            }
            
            
            // 1992
            re = new RE("([1-2][0-9]{3})", RE.REG_ICASE);
            match = re.getMatch(fn);

            if(match != null) {
                String year = fn.substring(match.getStartIndex(1), match.getEndIndex(1));

                addDecadeTopic(tm, t, year);
                addCenturyTopic(tm, t, year);
                return;
            }
            
            
            // n. 1992
            re = new RE("n\\. ([1-2][0-9]{3})", RE.REG_ICASE);
            match = re.getMatch(fn);

            if(match != null) {
                String year = fn.substring(match.getStartIndex(1), match.getEndIndex(1));

                addDecadeTopic(tm, t, year);
                addCenturyTopic(tm, t, year);
                return;
            }
            
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    public static void createTopic(TopicMap tm, String si, String fname, String ename, String[] types) throws TopicMapException  {
        createTopic(tm, si, fname, fname, ename, types);
    }
    
    
    public static void createTopic(TopicMap tm, String si, String bname, String fname, String ename, String[] types) throws TopicMapException {
        Topic topic = tm.getTopic(si);
        if(topic == null) {
            Topic t = tm.createTopic();
            Locator siLocator = tm.createLocator(si);
            t.addSubjectIdentifier(siLocator);
            t.setBaseName(bname);
            setDisplayName(t, "fi" , fname);
            setDisplayName(t, "en" , ename);
            for(int i=0; i<types.length; i++) {
                Topic typeTopic = tm.getTopic(types[i]);
                if(typeTopic != null) {
                    t.addType(typeTopic);
                }
            }
            System.out.println("  topic created " + t.getBaseName());
        }
    }
    
    
 
    
    
    
    public static void process(TopicMap tm) throws TopicMapException {
        Vector v=new Vector();
        createDateTopics(tm);
        Topic timeApellationTopic = tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#kp-ajoitus");
        System.out.println("Time type == " + timeApellationTopic.getBaseName());       
        
        Iterator iter=tm.getTopics();
        while(iter.hasNext()) {
            Topic t=(Topic)iter.next();
            if(t.isOfType(timeApellationTopic)) {
                v.add(t);
            }
        }
        iter=v.iterator();
        int c=0;
        int unregocnized = 0;
        while(iter.hasNext()){
            Topic t=(Topic)iter.next();
            if(t.isRemoved()) continue;
            try {
                //System.out.println("Fixing " + t.getBaseName());
                c++;
                
                // fix finnish name (base name and subject identifiers)
                String fn = trimExtraSpaces(t.getDisplayName("fi"));
                String fixedfn = fixTime(fn);
                if(fixedfn != null) {
                    setDisplayName(t, "fi", fixedfn);
                    t.setBaseName(fixedfn);
                    
                    Collection sis = t.getSubjectIdentifiers();
                    Vector ve = new Vector();
                    for(Iterator i=sis.iterator(); i.hasNext(); ) {
                        ve.add((Locator) i.next());
                    }
                    for(int i=0; i<ve.size(); i++) {
                        Locator oldLoc = (Locator) ve.elementAt(i);
                        //t.removeSubjectIdentifier(oldLoc);
                    }
                    Locator newSI = tm.createLocator("http://www.fng.fi/muusa/FNG_CIDOC_v3.4.dtd#" + URLEncoder.encode(fixedfn,"UTF-8") );
                    t.addSubjectIdentifier(newSI);
                    
                    System.out.println("  Fixed base name: " + fixedfn);
                    
                    createRelated(tm, t, fixedfn);
                }
                
                // fix english name
                String en = trimExtraSpaces(t.getDisplayName("en"));
                String fixeden = fixTime(en);
                if(fixedfn != null) {
                     setDisplayName(t, "en", fixeden);
                }
            }
            catch (Exception e) {
                System.out.println("Can't fix " + t.getBaseName());
                e.printStackTrace();
            }
        }
        System.out.println("Total " + c + " topics fixed!");
        System.out.println("Total unregocnized " + unregocnized + " topics!");        
    }
    
    
    
    
    
    
    
    
    public static void createDateTopics(TopicMap tm) throws TopicMapException {
        createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi",
                    "kuukausi",
                    "month", 
                    new String[] { });        
        
        createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#tammikuu",
                    "tammikuu",
                    "January", 
                    new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi" });
                    
        createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#helmikuu",
                    "helmikuu",
                    "February", 
                    new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi" });
                    
        createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#maaliskuu",
                    "maaliskuu",
                    "March", 
                    new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi" });
                    
        createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#huhtikuu",
                    "huhtikuu",
                    "April", 
                    new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi" });

        createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#toukokuu",
                    "toukokuu",
                    "May", 
                    new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi" });


        createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#kesakuu",
                    "kes�kuu",
                    "Juni", 
                    new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi" });
        
        try { createTopic(tm,
                    URLEncoder.encode("http://www.fng.fi/wandora/wandora-fng.xtm#kes�kuu", "UTF-8"),
                    "kes�kuu",
                    "Juni", 
                    new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi" });
        } catch(Exception e) {}
                    
        try { createTopic(tm,
                    URLEncoder.encode("http://www.fng.fi/wandora/wandora-fng.xtm#heinakuu", "UTF-8"),
                    "hein�kuu",
                    "July", 
                    new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi" });
        } catch(Exception e) {}
                    
         createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#elokuu",
                    "elokuu",
                    "August", 
                    new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi" });
                    
        createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#syyskuu",
                    "syyskuu",
                    "September", 
                    new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi" });
                    
        createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#lokakuu",
                    "lokakuu",
                    "October", 
                    new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi" });
                    
        createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#marraskuu",
                    "marraskuu",
                    "November", 
                    new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi" });
                    
        createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#joulukuu",
                    "joulukuu",
                    "December", 
                    new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kuukausi" });
                    

        createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#vuosikymmen",
                    "vuosikymmen",
                    "Decade", 
                    new String[] { });
                    
        createTopic(tm,
                    "http://www.fng.fi/wandora/wandora-fng.xtm#vuosisata",
                    "vuosisata",
                    "Century", 
                    new String[] { });

    }
    
    
    
    
    public static void addDecadeTopic(TopicMap tm, Topic t, String year) throws TopicMapException {
        String decade = year.substring(0,3) + "0-luku";
        String decadeENName = year.substring(0,3) + "0s";
        String decadesi = "http://www.fng.fi/wandora/wandora-fng.xtm#" + decade;
        Topic decadeTopic = tm.getTopic(decadesi);
        if(decadeTopic == null) {
            createTopic(tm, decadesi, decade, decade, decadeENName, new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#vuosikymmen", "http://www.fng.fi/wandora/wandora-fng.xtm#kp-ajoitus" });
            decadeTopic = tm.getTopic(decadesi);
        }
        if(decadeTopic != null) {
            Topic centuryTopic = tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#vuosisata");
            if(decadeTopic.getTypes().contains(centuryTopic)) decadeTopic.removeType(centuryTopic);
            
            decadeTopic.addType(tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#kp-ajoitus"));
            decadeTopic.addType(tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#vuosikymmen"));
            t.addType(decadeTopic);
            if(decadeTopic.getTypes().contains(decadeTopic)) decadeTopic.removeType(decadeTopic);
            System.out.println("    Added decade type: " + decadeTopic.getBaseName());
        }
        addCenturyTopic(tm, decadeTopic, decade);
    }
    
    
     public static void addCenturyTopic(TopicMap tm, Topic t, String year) throws TopicMapException {
        String century = year.substring(0,2) + "00-luku";
        String centuryENName = (Integer.parseInt(year.substring(0,2)) + 1) + "th century";
        String centurysi = "http://www.fng.fi/wandora/wandora-fng.xtm#century_" + century;
        Topic centuryTopic = tm.getTopic(centurysi);
        if(centuryTopic == null) {
            createTopic(tm, centurysi, century  + " (vuosisata)", century, centuryENName, new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#vuosisata", "http://www.fng.fi/wandora/wandora-fng.xtm#kp-ajoitus" });
            centuryTopic = tm.getTopic(centurysi);
        }
        if(centuryTopic != null) {
            centuryTopic.addType(tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#vuosisata"));
            centuryTopic.addType(tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#kp-ajoitus"));
            t.addType(centuryTopic);
            System.out.println("    Added century type: " + centuryTopic.getBaseName());
        }
    }   
    
     
     
     public static void addMonthTopic(TopicMap tm, Topic t, String month) throws TopicMapException {
        Topic monthTopic = null;
        if("01".equals(month)) monthTopic = tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#tammikuu");
        else if("02".equals(month)) monthTopic = tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#helmikuu");
        else if("03".equals(month)) monthTopic = tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#maaliskuu");
        else if("04".equals(month)) monthTopic = tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#huhtikuu");
        else if("05".equals(month)) monthTopic = tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#toukokuu");
        else if("06".equals(month)) { try { monthTopic = tm.getTopic(URLEncoder.encode("http://www.fng.fi/wandora/wandora-fng.xtm#kesakuu", "UTF-8")); } catch (Exception e) {}}
        else if("07".equals(month)) { try { monthTopic = tm.getTopic(URLEncoder.encode("http://www.fng.fi/wandora/wandora-fng.xtm#heinakuu", "UTF-8")); } catch (Exception e) {}}
        else if("08".equals(month)) monthTopic = tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#elokuu");
        else if("09".equals(month)) monthTopic = tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#syyskuu");
        else if("10".equals(month)) monthTopic = tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#lokakuu");
        else if("11".equals(month)) monthTopic = tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#marraskuu");
        else if("12".equals(month)) monthTopic = tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#joulukuu");
        
        if(monthTopic != null) {
            t.addType(monthTopic);
            System.out.println("    Added month type: " + monthTopic.getBaseName());
        }
     }
     
     
     
     public static void addYearTopic(TopicMap tm, Topic t, String year) throws TopicMapException {
        String yearsi = "http://www.fng.fi/wandora/wandora-fng.xtm#" + year;
        Topic yearTopic = tm.getTopic(yearsi);
        if(yearTopic == null) {
            createTopic(tm, yearsi, year, year, new String[] { "http://www.fng.fi/wandora/wandora-fng.xtm#kp-ajoitus" });
            yearTopic = tm.getTopic(yearsi);
        }
        if(yearTopic != null) {
            yearTopic.addType(tm.getTopic("http://www.fng.fi/wandora/wandora-fng.xtm#kp-ajoitus"));
            t.addType(yearTopic);
            System.out.println("    Added year type: " + yearTopic.getBaseName());
        }
        addDecadeTopic(tm, yearTopic, year);
        addCenturyTopic(tm, yearTopic, year);
     }
     
     
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    
    
    

    // simple way to get milliseconds of the specified time (use to set expiration)
    public static void main(String args[]) throws Exception {       
        TopicMap tm=new org.wandora.topicmap.memory.TopicMapImpl();
        InputStream in=new FileInputStream(args[0]);
        tm.importXTM(in);
        in.close();
        
        FixTimeApellations.process(tm);
        
        OutputStream out=new FileOutputStream(args[1]);
        tm.exportXTM(out);
        out.close();
    }
    
   
    
}

    
