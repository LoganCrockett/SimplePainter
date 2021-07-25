import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Provides the user a window for selecting the color
 * @author Logan Crockett
 *
 */
public class ColorSelector extends JFrame implements ActionListener {
	private SimpleCanvas sc;
	// This panel is a preview of the current selected color
	private JPanel previewWindow = new JPanel();
	private JButton colorButton;
	
	// Holds the colors that we will create and display buttons for
	private static final Color[] colors = {
			Color.green,Color.blue,Color.red,
			Color.yellow, Color.pink, Color.orange,
			Color.black, Color.gray, Color.white
			};
	
	public ColorSelector(SimpleCanvas c, JButton b) {
		super();
		sc = c;
		colorButton = b;
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(800, 800));
		this.setTitle("Select a Color");
		// Using this GridLayout, it gives each panel half of the total Frame size
		this.setLayout(new GridLayout(0, 2));
		
		this.createGUI();
		this.setVisible(false);
	}
	
	/**
	 * Creates the GUI the user will interact with
	 */
	private void createGUI() {
		// This panel holds all of the color buttons
		JPanel colorSelectionPanel = new JPanel();
		
		// Add a border between the two panels
		colorSelectionPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
		 
		// colorSelectionPanel.setLayout(new GridLayout(3,3));
		colorSelectionPanel.setLayout(new GridBagLayout());
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		int row = 0;
		// Go through our list of colors and create buttons for them
		for (int i = 0; i < colors.length; i++) {
			JButton c = new JButton();
			c.setBackground(colors[i]);
			c.setActionCommand("colorChange");
			c.addActionListener(this);
			
			// If we hit the last column (2 in this case), update our logic
			if (i != 0 && i % 3 == 0) {
				row++;
			}
			
			buttonConstraints.gridx = row;
			buttonConstraints.gridy = i % 3;
			buttonConstraints.ipadx = 50;
			buttonConstraints.ipady = 50;
			buttonConstraints.insets = new Insets(5, 5, 5, 5);
			
			colorSelectionPanel.add(c, buttonConstraints);
		}
		
		previewWindow.setBackground(sc.getCurrentColor());
		
		JPanel previewPanel = new JPanel();
		previewPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 1;
		c.gridy = 0;
		// Bottom Padding Only
		c.insets = new Insets(0, 0, 10, 0);
		previewPanel.add(new JLabel("Preview Color"),c);
		
		c.ipadx = 150; // Component's X size
		c.ipady = 150; // Component's Y Size
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0); // Reset the padding
		previewPanel.add(previewWindow, c);
		
		// Add our panels to the frame
		this.add(colorSelectionPanel);
		this.add(previewPanel);
	}
	
	/**
	 * With this event, it will:
	 * 1. Toggle the visibility of this frame (or bring it to the front if already visible)
	 * 2. Change the color
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("toggleVisibility")) {
			// If it is already visible, move it to the front
			if (this.isVisible()) {
				this.toFront();
			}
			// Otherwise, set it to visible
			else {
				this.setVisible(true);
			}
		}
		// Change Color
		else {
			// this.setBackground(((JButton)arg0.getSource()).getBackground());
			previewWindow.setBackground(((JButton)arg0.getSource()).getBackground());
			colorButton.setBackground(((JButton)arg0.getSource()).getBackground());
			sc.setCurrentColor(((JButton)arg0.getSource()).getBackground());
		}
	}
}
