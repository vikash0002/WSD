/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package scratch.scott;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.PajekNetReader;

/**
 * @author Scott White
 */
public class OpenFileHandler implements ActionListener {
    JFileChooser mFileChooser;
    VizApp mParent;
    int choice;

    OpenFileHandler(VizApp parent) {
        mFileChooser = new JFileChooser();
        mFileChooser.setSize(400, 300);
        //String[] fileFormats = new String[]{".net"};
        //FileFilter defaultFilter = new FileFilter(fileFormats[0],FileDescriptions[0]);

        mParent = parent;
    }

    public void actionPerformed(ActionEvent ae) {
        // Displays the jfc and sets the dialog to 'open'
        choice = mFileChooser.showOpenDialog(mParent);

        if (choice == JFileChooser.APPROVE_OPTION) {
            String fileName, line;
            BufferedReader reader;

            fileName = mFileChooser.getSelectedFile().getAbsolutePath();

            File file = new File(fileName);

            // KleinbergSmallWorldGenerator smallWorldGenerator = new KleinbergSmallWorldGenerator(5);
             //mParent.displayGraph(smallWorldGenerator.createSmallWorld(2));
            if (getExtension(file).toLowerCase().equals("net")) {
                PajekNetReader pnr = new PajekNetReader();
                Graph graph;
                try
                {
                    graph = pnr.load(fileName);
                    mParent.displayGraph(graph);
                }
                catch (IOException e)
                {
                    System.out.println("Error in loading file " + file);
                    e.printStackTrace();
                }
                //                PajekNetFile pajekFile = new PajekNetFile();
//                Graph graph = pajekFile.load(fileName);

            } else {
                throw new IllegalArgumentException("Only files ending in .net are currently supported.");
            }

            mFileChooser.setCurrentDirectory(file);
        }
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}