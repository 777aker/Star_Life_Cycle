import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class StaryBackground {

	// stores origins of all the stars
	ArrayList<Point> stars = new ArrayList<Point>();
	// enum for the different types of stars
	public enum starType {
		// these are the star classifications
		O, B, A, F, G, K, M;
		// pick a random star type
		public static starType genStarType() {
			Random random = new Random();
			return values()[random.nextInt(values().length)];
		}
	}
	// store the different star types
	ArrayList<starType> types = new ArrayList<starType>();
	// whether or not the star is in twinkled mode
	ArrayList<Boolean> twinkled = new ArrayList<Boolean>();
	// color of the star
	ArrayList<Color> colors = new ArrayList<Color>();
	
	StaryBackground(Dimension SCREEN_SIZE) {
		// go through every single point on the screen and decide whether
		// it has a star or not randomly
		// slightly more efficient to generate i and j outside of loop
		int i;
		int j;
		for(i = 0; i < SCREEN_SIZE.width; i++) {
			for(j = 0; j < SCREEN_SIZE.height; j++) {
				// do we do a star or not with probability
				if(Math.random() > .9995) {
					// generate star
					Point temp_pos = new Point(i, j);
					stars.add(temp_pos);
					starType temp_type = starType.genStarType();
					types.add(temp_type);
					twinkled.add(false);
					// depending on star type generated give it different color
					// also randomize the colors a little bit
					int r=0,g=0,b=0;
					// for all of these used a color picker then added some random
					// numbers for fun variation
					// also a few could go over 255 which would be bad so check for that
					switch(temp_type) {
					case O:
						r = 91 + (int) Math.random()*5 - 2;
						g = 160 + (int) Math.random()*5 - 2;
						b = 234 + (int) Math.random()*10 - 5;
						break;
					case B:
						r = 146 + (int) Math.random()*5 - 2;
						g = 197 + (int) Math.random()*5 - 2;
						b = 252 + (int) Math.random()*10 - 5;
						if(b > 255)
							b = 255;
						break;
					case A:
						r = 229 + (int) Math.random()*5 - 2;
						g = 241 + (int) Math.random()*5 - 2;
						b = 255 - (int) Math.random()*5;
						break;
					case F:
						r = 255 - (int) Math.random()*5;
						g = 250 + (int) Math.random()*5 - 2;
						b = 188 + (int) Math.random()*5 - 2;
						break;
					case G:
						r = 252 + (int) Math.random()*10 - 5;
						if(r > 255)
							r = 255;
						g = 241 + (int) Math.random()*5 - 2;
						b = 83 + (int) Math.random()*5 - 2;
						break;
					case K:
						r = 234 + (int) Math.random()*10 - 5;
						g = 123 + (int) Math.random()*5 - 2;
						b = 25 + (int) Math.random()*5 - 2;
						break;
					case M:
						r = 234 + (int) Math.random()*10 - 5;
						g = 25 + (int) Math.random()*5 - 2;
						b = 25 + (int) Math.random()*5 - 2;
						break;
					}
					Color temp_color = new Color(r, g, b);
					colors.add(temp_color);
				}
			}
		}
	}
	
	// draw the star
	public void draw(Graphics g) {
		// go through and draw each star
		for(int i = 0; i < stars.size(); i++) {
			// so uh, someone was like they should twinkle, so here I am, making my life difficult
			if(Math.random() > .85)
				twinkled.set(i, !twinkled.get(i));
			// draw twinkled version or not twinkled version
			if(twinkled.get(i)) {
				// I don't want them to be single points so crosses time
				g.setColor(colors.get(i).brighter());
				g.fillRect(stars.get(i).x-1, stars.get(i).y, 3, 1);
				g.fillRect(stars.get(i).x, stars.get(i).y-1, 1, 3);
				// then draw the center main color on top
				g.setColor(colors.get(i));
				g.fillRect(stars.get(i).x, stars.get(i).y, 1, 1);
			} else {
				// then draw the center main color on top
				g.setColor(colors.get(i).brighter());
				g.fillRect(stars.get(i).x, stars.get(i).y, 1, 1);
			}
		}
	}
	
}
