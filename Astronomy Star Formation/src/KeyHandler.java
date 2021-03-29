import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


// handles keyboard stuff
public class KeyHandler extends KeyAdapter {

	// was space pressed already
	Boolean space_pressed = false;
	
	// if key is pressed do a thing
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		// if esc pressed then close program
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		// if space pressed generate particles and start star process
		case KeyEvent.VK_SPACE:
			if(!space_pressed)
				// kind of terrible way of doing this but want MainGraphics to be efficient and this is called
				// once and doesn't do much so won't be too bad
				MainWindow.mainWindow.mainGraphics.draw_star();
			break;
		}
	}
	
	// when a key is released do a thing
	public void keyReleased(KeyEvent e) {
		
	}
	
	// key pressed then quickly released, never really works
	// well I avoid using it
	public void keyTyped(KeyEvent e) {}
	
}
