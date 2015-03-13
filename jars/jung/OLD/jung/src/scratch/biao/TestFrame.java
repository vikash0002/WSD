/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
/*
 * Created on Jul 7, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package scratch.biao;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * @author Yan-Biao Boey
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestFrame extends JFrame {
	
	JTextArea tf;
	JScrollPane sp;
	
	public TestFrame() {
		super();
		setSize(new Dimension(600, 400));
		//setExtendedState(JFrame.MAXIMIZED_VERT | JFrame.MAXIMIZED_HORIZ);  
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = getContentPane();
		tf = new JTextArea();
		sp = new JScrollPane(tf);
		sp.setPreferredSize(new Dimension(0, 200));
		contentPane.add(sp, BorderLayout.NORTH);
		contentPane.addMouseListener(new MyMouseInputAdapter());
		
		validate();
	}

	private class MyMouseInputAdapter extends MouseInputAdapter {
		public void mouseClicked(MouseEvent e) {
			//tf.append("Clicked!\n");
			int andMask = InputEvent.CTRL_DOWN_MASK | MouseEvent.BUTTON1_DOWN_MASK;
			if ((e.getModifiersEx() & andMask) == andMask)
				tf.append(MouseEvent.getMouseModifiersText(e.getModifiersEx()) + " " + e.getX() + ", " + e.getY() + "\n");
		}
		
		public void mousePressed(MouseEvent e) {
			//int mod = e.getModifiersEx();
			/*
			if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK) { 
				tf.append("1\n");
			} else if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) == InputEvent.BUTTON3_DOWN_MASK) { 
				tf.append("3\n");
			} 
			*/
			int andMask = InputEvent.BUTTON1_DOWN_MASK | InputEvent.BUTTON3_DOWN_MASK;
			if ((e.getModifiersEx() & andMask) == andMask) { 
				//tf.append("1 & 3\n");
				tf.append(InputEvent.getModifiersExText(e.getModifiersEx()) + " " + e.getX() + ", " + e.getY() + "\n");
			}
			
			/*try {
				wait(1000);
			} catch (InterruptedException ex) {}*/
			//tf.setText("");
		}
		
		public void mouseReleased(MouseEvent e) {
			
			if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK)
				tf.append(MouseEvent.getMouseModifiersText(e.getModifiers()) + "\n");
			else if ((e.getModifiers() & (MouseEvent.BUTTON3_MASK & MouseEvent.META_MASK)) == (MouseEvent.BUTTON3_MASK & MouseEvent.META_MASK))
				tf.append(MouseEvent.getMouseModifiersText(e.getModifiers()) + "\n");
			
			/*
			if ((e.getModifiersEx() & (MouseEvent.META_DOWN_MASK)) == (MouseEvent.META_DOWN_MASK))
				tf.append("Button3" + "\n");
			else if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK)
				tf.append("Button3" + "\n");
			else if ((e.getModifiersEx() & MouseEvent.NOBUTTON) == MouseEvent.NOBUTTON)
				tf.append("Button1" + "\n");
			*/
		}
		
		public void mouseDragged(MouseEvent e) {
		}
		
		public void mouseMoved(MouseEvent e) {
		}
	}

    public static void main(String[] args) {
    	TestFrame tf = new TestFrame();
		tf.setVisible(true);
		//tf.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	int a = 4;
    	a += 8/2  * 2 + 3;
    	System.out.println(a);
    }
}
