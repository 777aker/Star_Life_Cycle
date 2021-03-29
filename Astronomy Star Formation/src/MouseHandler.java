import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// handles mouse stuff
public class MouseHandler extends MouseAdapter {

	// hande mouse being pressed
	public void MousePressed(MouseEvent m) {
		
	}
	
	// hande mouse being released
	public void MouseReleased(MouseEvent m) {
		
	}
	
	// handle the mouse being pressed then released
	// soon after, never really works well I avoid using
	public void MouseClicked(MouseEvent m) {}
	
	// when the mouse is moved update the mouse position
	public int mx, my;
	public void MouseMoved(MouseEvent m) {
		mx = m.getX();
		my = m.getY();
	}
	
}
