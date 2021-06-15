import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Provides the button for allowing the user to choose what mode they
 * want to use (draw, erase)
 * @author Logan Crockett
 *
 */
public class ModeSelector extends JPanel implements ActionListener {
	private SimpleCanvas sc;
	public ModeSelector(SimpleCanvas c) {
		super();
		
		sc = c;
		
		// File Path is assumed to be from root folder of project
		ImageIcon paintBrush = new ImageIcon("assets/paintBrush.png", "Draw");
		ImageIcon eraser = new ImageIcon("assets/eraser.png", "Erase");
		
		// Create our buttons
		JButton drawMode = new JButton(paintBrush);
		JButton eraserMode = new JButton(eraser);
		
		drawMode.setToolTipText("Draw");
		drawMode.setActionCommand("draw");
		eraserMode.setToolTipText("Erase");
		eraserMode.setActionCommand("erase");
		
		// Add our buttons to the panel
		this.add(drawMode);
		this.add(eraserMode);
		
		drawMode.addActionListener(this);
		eraserMode.addActionListener(this);
		
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		sc.setDrawingMode(e.getActionCommand());
		
	}

}
