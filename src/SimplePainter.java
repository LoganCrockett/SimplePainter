import javax.swing.*;
import java.awt.*;

/**
 * Acts as the main container for the application
 * @author Logan Crockett
 *
 */
public class SimplePainter extends JFrame {
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	private SimpleCanvas canvas = new SimpleCanvas();
	
	public SimplePainter() {
		super("Simple Painter");
		
		this.createAndShowGUI();
	}
	
	private void createAndShowGUI() {
		Dimension minimumSize = new Dimension(WIDTH,HEIGHT);
		setMinimumSize(minimumSize);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create a BorderLayout, and use it to align our content
		this.setLayout(new BorderLayout());
		
		// Step 1: Create a panel for tools (brush size, color, etc)
		JPanel toolsPanel = new JPanel();
		
		// Create a JSLider for our tools panel
		JSlider brushSize = new JSlider(0, 20, canvas.getBrushSize());
		brushSize.setPaintTicks(true);
		brushSize.setMajorTickSpacing(5);
		brushSize.setMinorTickSpacing(1);
		brushSize.setPaintLabels(true);
		brushSize.setSnapToTicks(true);
		brushSize.addChangeListener(canvas);
		
		toolsPanel.add(brushSize);
		
		// Create the mode selector, and add it
		ModeSelector modes = new ModeSelector(canvas);
		
		toolsPanel.add(modes);
		
		// Create a button for opening the color changing window
		JButton colorButton = new JButton();
		colorButton.setBackground(canvas.getCurrentColor());
		colorButton.setActionCommand("toggleVisibility");
		
		ColorSelector cs = new ColorSelector(canvas, colorButton);
		
		colorButton.addActionListener(cs);
		
		toolsPanel.add(colorButton);
		
		// Step 2: Add Tools panel to our frame
		this.getContentPane().add(toolsPanel, BorderLayout.NORTH);
		
		// Create a canvas panel
		JPanel canvasPanel = new JPanel();
		
		// Add the Simple canvas to our panel
		canvasPanel.add(canvas);
		
		// Add our panel
		this.getContentPane().add(canvasPanel, BorderLayout.CENTER);
		
		this.pack();
		
		setVisible(true);	
	}
	
	public static void main(String args[]) {
		SimplePainter p = new SimplePainter();
	}

}
