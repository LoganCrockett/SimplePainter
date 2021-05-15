import javax.swing.*;
import java.awt.*;

/**
 * Acts as the main container for the application
 * @author Logan Crockett
 *
 */
public class SimplePainter extends JFrame {
	private static final int width = 500;
	private static final int height = 500;
	
	public SimplePainter() {
		super("Simple Painter");
		
		Dimension minimumSize = new Dimension(width,height);
		setMinimumSize(minimumSize);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create a canvas panel
		JPanel canvasPanel = new JPanel();
		
		// Create a SimpleCanvas
		SimpleCanvas canvas = new SimpleCanvas();
		
		// Add the Simple canvas to our panel
		canvasPanel.add(canvas);
		
		// Add our panel
		this.getContentPane().add(canvasPanel);
		
		setVisible(true);		
	}
	
	public static void main(String args[]) {
		SimplePainter p = new SimplePainter();
	}

}
