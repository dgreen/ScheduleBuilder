/*
 * File: Dispatch.java
 * Author: David Green DGreen@uab.edu
 * Assignment:  ScheduleBuilder - EE333 Fall 2018
 * Vers: 1.0.0 11/19/2018 dgg - initial coding
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.nio.file.StandardCopyOption.*;


/**
 * Dispatch various external programs to perform desired behaviors
 * @author David Green DGreen@uab.edu
 */
public class Dispatch {
    
    /**
     * Dispatch the spreadsheet to allow schedule creation.Presently, don't hang 
     * program but merely delay 10 seconds to allow the spreadsheet to show.
     * @param path path to courses
     * @param course specific course
     * @param semester specific semester
     * @return String representation of result
     */
    public String createEditSchedule(String path, String course, String semester) {
        try {
            String fullPath        = path + "/" + course + "/" + semester;
            String spreadSheetName = "schedule.numbers";        
            File schedule          = new File(fullPath + "/" + spreadSheetName);
            
            if (! schedule.exists()) {
                Files.copy((new File("resources/schedule-template.numbers")).toPath(), 
                            new File(fullPath + "/" + spreadSheetName).toPath(), 
                           COPY_ATTRIBUTES);
                schedule = new File(fullPath + "/" + spreadSheetName);
            }
            
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec( "open " + spreadSheetName, (String[]) null, new File(fullPath) );

            Thread.sleep(10000);
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Dispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    /**
     * Edit the template behavior -- invoke editor assigned to .md suffix
     * @param path path to courses
     * @param course specific course
     * @param semester specific semester
     * @return String representation of result
     */
    public String editTemplate(String path, String course, String semester) {
        try {
            String fullPath = path + "/" + course + "/" + semester;
            
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec( "open schedule.md", (String[]) null, new File(fullPath) );

            Thread.sleep(10000);
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Dispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * Merge the CSV from the spreadsheet into the Template replacing any exising
     * old schedule
     * @param path path to courses
     * @param course specific course
     * @param semester specific semester
     * @return String representation of result     * @return
     */
    public String mergeSchedule(String path, String course, String semester) {
        
        boolean finished = false;

        try {
            String fullPath = path + "/" + course + "/" + semester;
            
            // ensure the schedule.old has been removed
            File file = new File(fullPath + "/schedule.old");
            file.delete();           // quietly continues even if file not there            
            
        //    Runtime rt = Runtime.getRuntime();
        //    Process p = rt.exec("/bin/sh -c \"/usr/bin/touch x.txt\"; sleep 100 ", (String[]) null, new File(fullPath) );
          
            ProcessBuilder pb = new ProcessBuilder("./insert-schedule.pl");
            pb.directory(new File(fullPath));
            File log = new File(fullPath + "/merge.log");
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
        
            Process p = pb.start();
            finished = p.waitFor(2, TimeUnit.SECONDS);
                        
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Dispatch.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }

        if (! finished)
            return "Merge failed";
        else
            return "Merge complete";
    }
    
    /**
     * Allow touching up file after merge (presently only beautification of .md)
     * using the editor assigned to .md suffix.
     * @param path path to courses
     * @param course specific course
     * @param semester specific semester
     * @return String representation of result
     */
    public String touchUpResult(String path, String course, String semester) {
        try {
            String fullPath = path + "/" + course + "/" + semester;
            
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec( "open schedule.md", (String[]) null, new File(fullPath) );

            Thread.sleep(10000);
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Dispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    /**
     * View result in production view (using Marked which also reads the .md file)
     * @param path path to courses
     * @param course specific course
     * @param semester specific semester
     * @return String representation of result
     */
    public String viewResult(String path, String course, String semester) {
        try {
            String fullPath = path + "/" + course + "/" + semester;
            
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec( "open schedule.md -a Marked.app", (String[]) null, new File(fullPath) );

            Thread.sleep(10000);
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Dispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    /**
     * Export the schedule to HTML using multi-markdown script mmd
     * @param path path to courses
     * @param course specific course
     * @param semester specific semester
     * @return String representation of result
     */
    public String exportResult(String path, String course, String semester) {
        
        boolean finished = false;

        try {
            String fullPath = path + "/" + course + "/" + semester;
        
            ProcessBuilder pb = new ProcessBuilder("/Users/dgreen/bin/mmd", "schedule.md");
            pb.directory(new File(fullPath));
            File log = new File(fullPath + "/merge.log");
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));

            Map<String, String> env = pb.environment();
            
            Process p = pb.start();
            finished = p.waitFor(2, TimeUnit.SECONDS);

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Dispatch.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }

        if (! finished)
            return "Export failed";
        else
            return "Export complete";
    }
}
