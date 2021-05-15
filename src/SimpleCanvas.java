import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import java.util.ArrayList;


/**
 * The canvas for the user to draw onto
 * @author Logan Crockett
 *
 */
public class SimpleCanvas extends Canvas implements MouseListener, MouseMotionListener {
	private Path2D path = new Path2D.Double(); // Holds the points to draw
	
	public SimpleCanvas() {
		super();
		
		this.setSize(800, 500);
		this.setBackground(Color.WHITE);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.setVisible(true);
	}
	
	public void paint() {
		Graphics g = getGraphics();
		
		g.setColor(Color.GREEN);
		g.drawRect(0,0,100, 100);
		
		g.dispose();
	}
	
	// Draws the given path for the mouse movement
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
		// On release, reset our path, as we will have drawn everything by now
		path.reset();
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
