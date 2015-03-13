/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 * 
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 */
package scratch.danyel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.PajekNetReader;
import edu.uci.ics.jung.visualization.GraphDraw;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.SpringLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.contrib.KKLayout;

public class ReflectiveLayoutDemo {

	private static final class LayoutChooser implements ActionListener {
		private final JComboBox jcb;
		private final Graph g;
		private final GraphDraw gd;
		private LayoutChooser(JComboBox jcb, Graph g, GraphDraw gd) {
			super();
			this.jcb = jcb;
			this.g = g;
			this.gd = gd;
		}
		public void actionPerformed(ActionEvent arg0) {
			Object[] constructorArgs = { g };

			Class layoutC = (Class) jcb.getSelectedItem();
			System.out.println("Setting to " + layoutC);
			Class lay = layoutC;
			try {
				Constructor constructor = lay.getConstructor(constructorArgsWanted);
				Object o = constructor.newInstance(constructorArgs);
				Layout l = (Layout) o;
				VisualizationViewer vv = gd.getVisualizationViewer();
				vv.setGraphLayout(l);
				vv.restart();
			} catch (Exception e) {
				System.out.println("Can't handle " + lay);
			}
		}
	}

	static final Class[] constructorArgsWanted = { Graph.class };
	
	public static void main(String[] args) throws IOException {
		JFrame jf = new JFrame();
        PajekNetReader pnr = new PajekNetReader();
        final Graph g = pnr.load("samples/datasets/smyth.net");
//		PajekNetFile file = new PajekNetFile();
//		final Graph g = file.load("samples/datasets/smyth.net");
//		final Graph g = TestGraphs.getDemoGraph();
		final GraphDraw gd = new GraphDraw(g);
		gd.getVisualizationViewer().setGraphLayout(new KKLayout(g));
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		jp.add(gd, BorderLayout.CENTER);

		Class[] combos = getCombos();
		final JComboBox jcb = new JComboBox(combos);
		jcb.setSelectedItem( SpringLayout.class );
		jcb.addActionListener(new LayoutChooser(jcb, g, gd));
		jp.add(jcb, BorderLayout.NORTH);
		jf.getContentPane().add(jp);
		jf.pack();
		jf.show();
	}

	/**
	 * @return
	 */
	private static Class[] getCombos() {
		Set layouts = collectLayouts();
		Class[] ch = new Class[ layouts.size() ];
		int i = 0;
		for (Iterator iter = layouts.iterator(); iter.hasNext();) {
			Class layout = (Class) iter.next();
			ch[i++] = layout;
		}
//
//		ClassHolder a = new ClassHolder(FRLayout.class);
//		ClassHolder b = new ClassHolder(SpringLayout.class);
//		ClassHolder[] rv = new ClassHolder[2];
//		rv[0] = a;
//		rv[1] = b;
		return ch;
	}

	static final int SUFFIX_LENGTH = ".class".length();

	static public Set collectLayouts() {
		String classPath = System.getProperty("java.class.path");
		Set result = collectFilesInPath(classPath);
		return result;
	}

	static  public Set collectFilesInPath(String classPath) {
		Set result = collectFilesInRoots(splitClassPath(classPath));
		return result;
	}

	static  Set collectFilesInRoots(Vector roots) {
		Set result = new HashSet();
		Enumeration e = roots.elements();
		while (e.hasMoreElements())
			gatherFiles(new File((String) e.nextElement()), "", result);
		return result;
	}

	public static final Class LayoutClass = Layout.class;

	static  void gatherFiles(File classRoot, String classFileName, Set result) {
		File thisRoot = new File(classRoot, classFileName);
		if (thisRoot.isFile()) {
			if (isLayoutClass(classFileName)) {
				String className = classNameFromFile(classFileName);
				Class classL = figureClass(className);
				if (classL == null)
					return;
				if ((classL.getModifiers() & Modifier.ABSTRACT) > 0) 
					return;
				try {
					classL.getConstructor(constructorArgsWanted);										
				} catch( Exception e ) {
					return;
				}
				Set interfaces = collectInterfaces( classL );
				if (interfaces.contains(LayoutClass)) {
					System.out.println("Found interface " + classL);
					result.add(classL);
				}
			}
			return;
		}
		String[] contents = thisRoot.list();
		if (contents != null) {
			for (int i = 0; i < contents.length; i++)
				gatherFiles(classRoot, classFileName + File.separatorChar + contents[i], result);
		}
	}
	
	
	private static Set collectInterfaces( Class c ) {
		Set s = new HashSet();
		collectInterfaces(c, s);
		return s;
	}
	private static void collectInterfaces( Class c, Set interfaces ) {
		interfaces.addAll( Arrays.asList( c.getInterfaces() ));
		Class sup = c.getSuperclass();
		if ( sup != null ) {
			collectInterfaces( sup, interfaces );
		}			
	}

	/**
	 * @param className
	 * @return
	 */
	static  private Class figureClass(String className) {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		Class rv;
		try {
			rv = cl.loadClass(className);
//			System.out.println("Just loaded " + rv);
			return rv;
		} catch (ClassNotFoundException e) {
			System.out.println("Not found: " + className);
			return null;
		}
	}

	static  Vector splitClassPath(String classPath) {
		Vector result = new Vector();
		String separator = System.getProperty("path.separator");
		StringTokenizer tokenizer = new StringTokenizer(classPath, separator);
		while (tokenizer.hasMoreTokens())
			result.addElement(tokenizer.nextToken());
		return result;
	}

	static  protected boolean isLayoutClass(String classFileName) {
		return classFileName.endsWith(".class")
			&& classFileName.indexOf('$') < 0
			&& classFileName.indexOf("Layout") > 0;
	}

	static  protected String classNameFromFile(String classFileName) {
		// convert /a/b.class to a.b
		String s = classFileName.substring(0, classFileName.length() - SUFFIX_LENGTH);
		String s2 = s.replace(File.separatorChar, '.');
		if (s2.startsWith("."))
			return s2.substring(1);
		return s2;
	}

}
