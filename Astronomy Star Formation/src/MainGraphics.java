import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;

// handles graphpics
// also suppress this warning bc I don't care about it
@SuppressWarnings("serial")
public class MainGraphics extends JComponent {

	// get screen dimensions
	private final static Dimension SCREEN_SIZE =
			new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
	
	// make star background
	StaryBackground background = new StaryBackground(SCREEN_SIZE);
	// make actual star we are watching
	Star star = new Star(SCREEN_SIZE);
	// draw star or not/start star physics
	Boolean drawstar = false;
	
	// constructor for graphics, ig just do this stuff when graphics starts
	MainGraphics() {}
	
	// things you want to draw here
	public void draw(Graphics g) {
		background.draw(g);
		// if ready to start drawing the star then draw the star
		if(drawstar)
			star.draw(g);
	}
	
	public void draw_star() {
		drawstar = true;
	}
	
	// double buffering variables
	Image dmImage;
	Graphics dbg;
	// painting method which is called a lot and
	// updates the screen a lot
	public void paint(Graphics g) {
		// double buffering, draw is where you actually draw stuff
		// don't touch this
		dmImage = createImage(getWidth(), getHeight());
		dbg = dmImage.getGraphics();
		draw(dbg);
		g.drawImage(dmImage, 0, 0, this);
		// dont forget to repaint sheesh, this is like the most important part
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}
	
}
