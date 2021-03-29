import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

// surpress this warning bc I don't care about it
@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	// get dimensions of the screen
	private final static Dimension SCREEN_SIZE = 
			new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
	
	// mouse, key, and graphics classes initialized
	KeyHandler keyHandler = new KeyHandler();
	MouseHandler mouseHandler = new MouseHandler();
	MainGraphics mainGraphics = new MainGraphics();
	
	// creates the window and set its properties
	public MainWindow() {
		// set size to screen resolution
		this.setSize(SCREEN_SIZE);
		// make it borderless
		this.setUndecorated(true);
		// want people to see it
		this.setVisible(true);
		// makes it fake full screen, creates errors otherwise
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// don't want window to be resizable get over it
		this.setResizable(false);
		// space background? should this be black? 
		this.setBackground(Color.BLACK);
		// add the handlers to the window
		// they actually do most of the work
		this.addKeyListener(keyHandler);
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.add(mainGraphics);
	}
	
	// starts the program
	static MainWindow mainWindow = new MainWindow();
	// probably only java program on the planet that doesn't need
	// or use public static void main since window made outside of it
	public static void main(String[] args) {}
	
}
