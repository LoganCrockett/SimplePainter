import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import javax.swing.JPanel;


/**
 * The canvas for the user to draw onto
 * @author Logan Crockett
 *
 */
public class SimpleCanvas extends JPanel implements MouseListener, MouseMotionListener {
	// Store every path made so we can re-draw it when the canvas updates (or is re-sized)
	private ArrayList<Path2D> totalPaths = new ArrayList<Path2D>();
	private Path2D path = new Path2D.Double(); // Holds the points to draw
	
	public SimpleCanvas() {
		super();
		
		this.setPreferredSize(new Dimension(500, 500));
		this.setBackground(Color.WHITE);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		// Start by clearing the Canvas
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		// Go through and draw each path now
		for (Path2D p: totalPaths) {
			g2.draw(p);
		}
		
		g2.dispose();
		
	}
	
	/**
	 * Draws the given path for the mouse movement
	 */
	private void drawMouseMovement() {
		Graphics2D g = (Graphics2D) getGraphics();
		
		// Draw our path
		g.draw(path);
		
		g.dispose();
	}

	@Override
	// Do nothing with this event
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	// Do nothing with this event
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	// Do nothing with this event
	public void mouseExited(MouseEvent arg0) {}

	@Override
	// Mouse Pressed fires before mouse clicked
	public void mousePressed(MouseEvent arg0) {
		// Start by adding this point to the beginning of the path
		path.moveTo(arg0.getX(), arg0.getY());
		// Go ahead and draw the line for this path
		path.lineTo(arg0.getX(), arg0.getY());
		this.drawMouseMovement();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// Add our path to the current list
		totalPaths.add(path);
		
		/*// Commented out
		// On release, reset our path, as we will have drawn everything by now
		// path.reset();
		 * 
		 */
		
		// Set our path to a new instance of Path2D
		// By doing so, it prevents us from writing over the previous path in our totalPaths arraylist
		path = new Path2D.Double();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// Add our points as the mouse drags
		path.lineTo(arg0.getX(), arg0.getY());
		this.drawMouseMovement();
	}

	@Override
	// Do nothing with this event
	public void mouseMoved(MouseEvent arg0) {}
	
}
