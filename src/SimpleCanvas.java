import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
// import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * The canvas for the user to draw onto
 * @author Logan Crockett
 *
 */
public class SimpleCanvas extends JPanel implements MouseListener, MouseMotionListener, ChangeListener {
	// Store every path made so we can re-draw it when the canvas updates (or is re-sized)
	// private ArrayList<Path2D> totalPaths = new ArrayList<Path2D>(); // Commented out. Use BufferedImage drawing now
	private Path2D path = new Path2D.Double(); // Holds the points to draw
	private BufferedImage oldImage; // Previous Image
	private BufferedImage canvasImage; // The current image we are drawing onto
	private Graphics2D canvasGraphics; // The current graphics of the image we are drawing on
	private boolean isDrawingMode = true; // Flag for if we are in drawing mode or erasing mode
	private int previousMouseX, previousMouseY = 0; // Holds the previous mouse positions so we can clear the circle being drawn around the mouse
	
	// User Data Fields. Use getter methods to access and pass to input fields (sliders, choosers, etc.)
	private int brushSize = 2;
	private Color currentColor = Color.black;
	
	public SimpleCanvas() {
		super();
		
		this.setPreferredSize(new Dimension(500, 500));
		this.setBackground(Color.WHITE);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.setVisible(true);
	}
	
	/**
	 * Gets the current brush size
	 * @return the current brush size
	 */
	public int getBrushSize() {
		return this.brushSize;
	}
	
	/**
	 * Gets the current color
	 * @return the current color
	 */
	public Color getCurrentColor() {
		// If canvas graphics is not defined, then return the default color (black)
		return currentColor;
	}
	
	/**
	 * Sets the current color
	 * @param c the new color
	 */
	public void setCurrentColor(Color c) {
		currentColor = c;
		
		// If we are drawing, go ahead and update immediately. Otherwise, it will be handled later
		if (isDrawingMode) {
			canvasGraphics.setColor(c);
		}
	}
	
	/**
	 * Changes the drawing mode to "draw" or "erase" and updates
	 * the current color based on the mode
	 * @param mode the mode we are switching to
	 */
	public void setDrawingMode(String mode) {
		isDrawingMode = mode.toLowerCase().equals("draw");
		
		// Drawing mode
		if (isDrawingMode) {
			// Set color to current selected color if we are drawing
			canvasGraphics.setColor(currentColor);
			
			// Go ahead and clear the circle around the mouse from erasing mode as well
			this.clearCircleAroundMouse();
		}
		// Erase Mode
		else {
			// Set color to current background color if we are erasing
			canvasGraphics.setColor(canvasGraphics.getBackground());
		}
	}
	
	/**
	 * Paints our component initially and on window resize
	 * Use this to redraw our canvas content when the window changes size
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// If we don't have a canvas image, create one to use
		if (canvasImage == null) {
			canvasImage = this.getGraphicsConfiguration().createCompatibleImage(this.getWidth(), this.getHeight());
			// Create the graphics object to use as well
			canvasGraphics = canvasImage.createGraphics();
			
			// Set our basic properties
			
			// canvasGraphics.setColor(Color.black); // Set to black for now. It defaults to white
			canvasGraphics.setColor(currentColor);
			canvasGraphics.setBackground(Color.white);
			canvasGraphics.setStroke(new BasicStroke(this.brushSize));
		}
		
		// Clear the background to white (or current background color), or it will just be black
		canvasGraphics.setBackground(Color.white);
		// Now clear the canvas rectangle
		canvasGraphics.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		// If we have an old image, then draw it onto our canvas image before drawing the canvas image
		// or else nothing will show up
		if (oldImage != null) {
			canvasGraphics.drawImage(oldImage, 0, 0, null);
		}
		
		// Draw the canvas image now
		g.drawImage(canvasImage, 0, 0, null);
		
		/*// Commented Out. Old way of drawing the canvas
		 * // Commented out. Don't need with BufferedImage drawing
		
		Graphics2D g2 = (Graphics2D) g;
		
		// Start by clearing the Canvas
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		// Go through and draw each path now
		for (Path2D p: totalPaths) {
			g2.draw(p);
		}
		
		g2.dispose();
		*/
	}
	
	/**
	 * Draws a circle on the Panel (not the canvas image) when user is in erasing mode
	 * @param x x-coordinate of mouse position
	 * @param y y-coordinate of mouse position
	 */
	private void drawCircleAroundMouse(int x, int y) {
		Graphics2D g = (Graphics2D)this.getGraphics();
		
		// Clear the old circle first
		this.clearCircleAroundMouse();
		
		// Now redraw the circle around the mouse
		g.setColor(Color.black);
		g.drawOval(x - (this.brushSize / 2), y - (this.brushSize / 2), this.brushSize, this.brushSize);
		
		// Update our old values to our current ones
		previousMouseX = x;
		previousMouseY = y;
		
		g.dispose();
	}
	
	/**
	 * Fills in the old circle around the mouse to the current background color of the canvas image
	 */
	private void clearCircleAroundMouse() {
		Graphics2D g = (Graphics2D)this.getGraphics();
		
		// Fill in the oval to the current background color of the canvas
		g.setColor(canvasGraphics.getBackground());
		g.fillOval(previousMouseX - (this.brushSize / 2)-1, previousMouseY - (this.brushSize / 2)-1, this.brushSize+3, this.brushSize+3);
		
		g.dispose();
	}
	
	/**
	 * Draws the given path for the mouse movement
	 */
	private void drawMouseMovement() {
		// Grab the graphics for the current component
		Graphics2D g = (Graphics2D) getGraphics();
		
		// Draw our path onto the canvas image
		canvasGraphics.draw(path);
		
		
		// Redraw the image to reflect current changes
		g.drawImage(canvasImage, 0, 0, null);
		
		g.dispose();
	}

	// Do nothing with this event
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	// Do nothing with this event
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	// Do nothing with this event
	@Override
	public void mouseExited(MouseEvent arg0) {}

	// Mouse Pressed fires before mouse clicked
	@Override
	public void mousePressed(MouseEvent arg0) {
		// Start by adding this point to the beginning of the path
		path.moveTo(arg0.getX(), arg0.getY());
		// Go ahead and draw the line for this path
		path.lineTo(arg0.getX(), arg0.getY());
		this.drawMouseMovement();
		
		// If we are erasing, we need to draw a circle around the mouse after we have drawn the path
		// Or it will erase as we draw
		if (!isDrawingMode) {
			this.drawCircleAroundMouse(arg0.getX(), arg0.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// Add our path to the current list
		// totalPaths.add(path); // Commented Out. Use BufferedImage drawing now
		
		// On release, reset our path, as we will have drawn everything by now
		path.reset();
		
		/*
		 * Commented Out. With BufferedImage drawing, we don't have to reset the path to a new instance
		// Set our path to a new instance of Path2D
		// By doing so, it prevents us from writing over the previous path in our totalPaths arraylist
		// path = new Path2D.Double();
		 * 
		 */
		
		// Set our old image to the current one
		oldImage = canvasImage;
		// canvasGraphics.dispose(); // Dispose of the old graphics object for our canvas
		
		// Create a new image to draw onto
		canvasImage = this.getGraphicsConfiguration().createCompatibleImage(this.getWidth(), this.getHeight());
		// Create the graphics for this new image
		Graphics2D newGraphics = canvasImage.createGraphics();
		
		// Copy all of our old properties over
		newGraphics.setStroke(canvasGraphics.getStroke());
		newGraphics.setColor(canvasGraphics.getColor());
		newGraphics.setBackground(canvasGraphics.getBackground());
		
		// canvasGraphics = canvasImage.createGraphics(); // Commented Out. Don't need anymore
		
		canvasGraphics.dispose();
		
		canvasGraphics = newGraphics;
		
		
		
		// Lastly, draw our old image onto the new image
		canvasGraphics.drawImage(oldImage, 0, 0, null);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// Add our points as the mouse drags
		path.lineTo(arg0.getX(), arg0.getY());
		this.drawMouseMovement();
		
		// If we are erasing, we need to draw a circle around the mouse after we have drawn the path
		// Or it will just erase it as we draw
		if (!isDrawingMode) {
			this.drawCircleAroundMouse(arg0.getX(), arg0.getY());
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// If we are erasing, we need to draw a circle around the mouse
		if (!isDrawingMode) {
			this.drawCircleAroundMouse(arg0.getX(), arg0.getY());
		}
	}

	/**
	 * Monitors changes in the slider for brush size, and updates accordingly
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		int newSize = ((JSlider)e.getSource()).getValue();
		if (newSize < 0) {
			canvasGraphics.setStroke(new BasicStroke(0));
			this.brushSize = 0;
		}
		else if (newSize > 20) {
			canvasGraphics.setStroke(new BasicStroke(20));
			this.brushSize = 20;
		}
		else {
			canvasGraphics.setStroke(new BasicStroke(newSize));
			this.brushSize = newSize;
		}
	}
}
