import javax.swing.*;
import java.awt.*;

/**
 * Acts as the main container for the application
 * @author Logan Crockett
 *
 */
public class SimplePainter extends JFrame {
	private static final int width = 1000;
	private static final int height = 800;
	
	public SimplePainter() {
		super("Simple Painter");
		
		setSize(width, height);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		// Call getGraphics after setting visible to true
		Graphics g = this.getGraphics();
		
		g.drawString("Hello, world", 10, 50);
		
	}
	
	public static void main(String args[]) {
		SimplePainter p = new SimplePainter();
	}

}
