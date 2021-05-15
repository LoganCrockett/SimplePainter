import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * The canvas for the user to draw onto
 * @author Logan Crockett
 *
 */
public class SimpleCanvas extends Canvas implements MouseListener {
	public SimpleCanvas() {
		super();
		
		this.setSize(800, 500);
		this.setBackground(Color.RED);
		
		this.addMouseListener(this);
	}
	
	public void paint() {
		System.out.println("Painting");
		if (this.getBackground() == Color.RED) {
			this.setBackground(Color.BLUE);
		}
		else {
			this.setBackground(Color.RED);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("Mouse Clicked");
		this.paint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {
		System.out.println("Pressed");
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	
}
