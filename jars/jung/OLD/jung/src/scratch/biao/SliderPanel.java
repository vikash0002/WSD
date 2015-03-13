/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University 
 * of California
 * All rights reserved.
 *
 * This software is open-source under the BSD license; see either
 * "license.txt" or
 * http://jung.sourceforge.net/license.txt for a description.
 */
package scratch.biao;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

/**
 * @author Yan-Biao Boey
 */
public class SliderPanel extends JPanel implements PropertyChangeListener, ChangeListener {
	
	JSlider slider;
	JFormattedTextField textField;
	int max_val, min_val, init_val, majorTickSpacing, minorTickSpacing;
	String label;
	NumberFormat numberFormat;
	NumberFormatter formatter;
	
	public SliderPanel(String label) {
		this(label, 0, 100, 50);
	}
	
	public SliderPanel(String label,
					   int min_val,
					   int max_val,
					   int init_val
					   ) {
		this.label = label;
		this.min_val = min_val;
		this.max_val = max_val;
		this.init_val = init_val;
		
		//Create the slider
		slider = new JSlider(JSlider.HORIZONTAL);
		slider.setMinimum(min_val);
		slider.setMaximum(max_val);
		slider.setValue(init_val);
		slider.setLabelTable(slider.createStandardLabels(max_val - min_val));
		slider.addChangeListener(this);
		
		initComponents();
	}
		
	public void initComponents() {
		setOpaque(true);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//setPreferredSize(new Dimension(243, 98));
		//setMaximumSize(new Dimension(243, 98));
		
		JPanel labelAndTextPanel = new JPanel();
		//BoxLayout boxLO = new BoxLayout(labelAndTextPanel, BoxLayout.X_AXIS);
		//labelAndTextPanel.setLayout(boxLO);
		labelAndTextPanel.setOpaque(true);
		
		JLabel sliderLabel = new JLabel(label, JLabel.CENTER);
		sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		numberFormat = NumberFormat.getIntegerInstance();
		formatter = new NumberFormatter(numberFormat);
		formatter.setMinimum(new Integer(min_val));
		formatter.setMaximum(new Integer(max_val));
		textField = new JFormattedTextField(formatter);
		textField.setValue(new Integer(init_val));
		textField.setColumns(5); //get some space
		textField.addPropertyChangeListener(this);

		//React when the user presses Enter.
		textField.getInputMap().put(KeyStroke.getKeyStroke(
										KeyEvent.VK_ENTER, 0),
										"check");
		textField.getActionMap().put("check", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (!textField.isEditValid()) { //The text is invalid.
					Toolkit.getDefaultToolkit().beep();
					textField.selectAll();
				} else try {                    //The text is valid,
					textField.commitEdit();     //so use it.
				} catch (java.text.ParseException exc) {}
			}
		});

		labelAndTextPanel.add(sliderLabel);
		labelAndTextPanel.add(textField);
		//labelAndTextPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		
		slider.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		//slider.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		add(labelAndTextPanel);
		add(slider);
		//add(Box.createVerticalGlue());
		//setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		//setBorder(BorderFactory.createEtchedBorder());
	}

	/** Listen to the slider. */
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		int val = (int)source.getValue();
		textField.setText(String.valueOf(val));
	}

	/**
	 * Listen to the text field.  This method detects when the
	 * value of the text field (not necessarily the same
	 * number as you'd get from getText) changes.
	 */
	public void propertyChange(PropertyChangeEvent e) {
		if ("value".equals(e.getPropertyName())) {
			Number value = (Number)e.getNewValue();
			if (slider != null && value != null) {
				slider.setValue(value.intValue());
			}
		}
	}
	
	public JSlider getSlider() {
		return slider;
	}
	
	public void changeValues(int min_val, int max_val) {
		changeValues(min_val, max_val, (min_val + max_val)/2);
	}
	
	public void changeValues(int min_val, int max_val, int init_val) {
		this.min_val = min_val;
		this.max_val = max_val;
		this.init_val = init_val;
		formatter.setMinimum(new Integer(min_val));
		formatter.setMaximum(new Integer(max_val));
		textField.setValue(new Integer(init_val));
		slider.setMinimum(min_val);
		slider.setMaximum(max_val);
		slider.setValue(init_val);
		//Hashtable d = new Hashtable();
		//d.put(new Integer(min_val), new JLabel(Integer.toString(min_val)));
		//d.put(new Integer(max_val), new JLabel(Integer.toString(max_val)));
		//slider.setLabelTable(d);
		slider.setLabelTable(slider.createStandardLabels(max_val - min_val));
	}
	
	public void addChangeListener(ChangeListener l) {
		slider.addChangeListener(l);
	}
	
	public int getValue() {
		return slider.getValue();
	}
	
	public void setEnabled(boolean isEnabled) {
			slider.setEnabled(isEnabled);
			textField.setEnabled(isEnabled);
	}
}
