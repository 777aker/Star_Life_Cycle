import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Star {

	// since we know how many particles we will have making it an array
	// everything else has been arraylist since I don't know how many I want
	// actually, arraylist easier though and different size screens and maybe
	// want some randomness so arraylist it is
	// also once star collapses or expands may want to remove and add points 
	ArrayList<Point> particles = new ArrayList<Point>();
	// dang, wanted to avoid more double list stuff but need an angle for rotating and such
	ArrayList<Double> angles = new ArrayList<Double>();
	// also need the distances
	ArrayList<Double> distances = new ArrayList<Double>();
	// screen size
	final Dimension SCREEN_SIZE;
	// start doing physics or not
	Boolean start = false;
	// center of the screen / where the center of the star is
	// makes gravity and such easier bc we will just use center as our point of doing stuff
	Point center = new Point();
	// mass of our cloud
	int mass;
	// is the particle fusing or not
	ArrayList<Boolean> fusing = new ArrayList<Boolean>();
	// mass left to fuse hydrogen
	int hydrogen_mass = 500;
	// whether it's reached red stage or not
	boolean red_giant = false;
	// it's finally time for red giant life span
	int red_giant_lifetime = 200;
	
	public Star(Dimension SCREEN_SIZE_ARG) {
		SCREEN_SIZE = SCREEN_SIZE_ARG;
		center.x = SCREEN_SIZE.width/2;
		center.y = SCREEN_SIZE.height/2;
		// time for some math
		// want the star to generate gas cloud about 3/4 of screen starting at center
		// and forming a circle? hmmm...how to do this
		// ig go around the center in a circle like pattern using sin cosine
		// basically rotate around circle forming ever expanding ring and generate
		// particles along the path
		// also don't want center to be too thick so change how much we increase
		// angle by at each step depending on distance from center
		Double distance = 0.0;
		Double angle = 0.0;
		// distance want to only go 2/3 of the way from half the screen (that wording was bad)
		// so we are going in a circle from center of the screen, want the circle to expand to be
		// 2/3 of half the screen in radius, which would mean the circle is 2/3 the screen
		// usually height is less than width so do it based on height not width
		for(distance = 0.0; distance < SCREEN_SIZE.height/2*2/3; distance++) {
			// so angle has a few weird math things going on each step
			// 1, distance +1 so not divide by 0 ever
			// 2, +1 after so angle does increase incase something like 90/181 which = 0
			// 3, its 90/distance bc want angle to be smaller the further from center we are
			// so it adds more points
			for(angle = 0.0; angle < 360.0; angle+=(90.0/(distance+1.0))+1.0) {
				// the circle was waaayyy too thick and want some randomness so here we go
				// yet another math.random
				if(Math.random() > .75) {
					Point temp = new Point();
					temp.x = center.x + (int)(Math.random()*5) + (int)(distance*Math.cos(Math.toRadians(angle)));
					temp.y = center.y + (int)(Math.random()*5) + (int)(distance*Math.sin(Math.toRadians(angle)));
					// want the stars to have some randomness so kinda weird little thing happens here
					// where I one don't use the distance but use a new distance computed after randomizing position a little
					// and then also use an angle randomized a little
					angles.add(Math.toRadians(angle)+Math.random()/2-.25);
					distances.add(center.distance(temp));
					fusing.add(false);
					particles.add(temp);
				}
			}
		}
		// I'm using particles.size enough might as well make mass variable instead
		mass = particles.size();
		// calculate angular momentum of our cloud and store it
		angular_momentum = mass*distances.get(distances.size()-1)*w;
	}
	
	// this is where we will do physics steps basically
	// this is called each time we repaint and will apply
	// whatever physics need to be applied to each star and draw them
	// I was thinking of slowing down repaint but we aren't threading
	// so we don't need to worry about some processes happening too fast/out of sync
	// /too slow
	// I need an angular momentum of the gas cloud variable
	double angular_momentum; 
	double w = 1;
	// so I'm going to do this weird thing with density
	double density;
	public void draw(Graphics g) {
		// first update angles so it spins
		angles.replaceAll(a -> a+2*w);
		// calculate graviational force on each particle and change distance accordingly
		// assume each particle mass is 1 which means mass of cloud is particles.size
		// and we don't have to do mass of each particle since they are just 1
		// and G is just an arbitrary constant so
		// Gmm/r^2 simplifies to m/r^2
		// abs is there since technically distance could become negative with this and keep increasing forever
		// had to add large number to distance so that particles.size wouldn't dominate and send things flying out large distances
		// I'm adding in that seemingly random * by last element bc I want it to collapse faster the smaller it is/gravitational force stronger the 
		// smaller it is
		// adding strength bc I need gravity to be a certain strength to look good
		double strength = 5000;
		// also need to correct for how much distance effects gravity
		double distance_effect = 1;
		// the +300 next to distance is so particles don't get flung into space when d is small and so small d effects gravity less
		// changed it to max instead of math.abs so that particles don't get flung from center when really negative
		distances.replaceAll(d -> Math.max(d-(strength*(1/(2*distances.get(distances.size()-1)))*mass/Math.pow(d*distance_effect+300, 2)), 0));
		
		
		// now I need to do angular momentum conservation
		// ie make it speed up bc cloud smaller
		// I'll do this based off of the furthest point
		// which usually you would search through a list and do the max, however
		// that's not very efficient and based on how this was constructed I know the last point
		// is actually the furthest/close enough to the furthest for these purposes
		// L = Iw, Iw = Iw, w = Iw/I
		// conservation of angular momentum our w is going to change to I_old*w_old/I_new
		// wasn't increasing by enough for change so, beef it up with arbitrary constant
		double new_ang_mom = mass*Math.pow(distances.get(distances.size()-1), 2);
		w = angular_momentum*w/new_ang_mom;
		angular_momentum = new_ang_mom;
		
		/* so I tried some hacky ways, but I guess I'll just do it correctly sheesh
		 * even though it is pretty inneficient
		// now I need some pressure so particles push off each other
		// I'm basically just gonna say based on our density push outward
		// arbitrary *2 bc i want outward force to be stronger
		// also /d bc the closer to the center you are obviously the more dense
		// and this is how I'm going to easily account for that
		//density = mass/Math.pow(distances.get(distances.size()-1), 2);
		density = 0;
		for(int i = 0; i < distances.size(); i++) {
			if(distances.get(i) < 10)
				density += 1;
		}
		//distances.replaceAll(d -> d+density*2*1/d);
		
		// decided gravity and density should happen in the same place
		distances.replaceAll(d -> Math.abs(d-distances.get(distances.size()-1)*.01*mass/Math.pow(d+100, 2)+density*2*1/d));
		*/
		int i = 0;
		double density;
		// so I realized something as I was trying different things, our array is basicaly in order from closest to center to farthest,
		// so basically i is just how many particles are closer than you, which means we don't need to actually check how many
		// particles are before you, we can gestimate based on how many came before you
		// this creates a slight issue where i is really small for a lot of particles that are actually at the center and so 
		// they don't realize they are massive which leads to a kind of weird thing
		// but this is efficient so leaving it for now
		// TODO make density work better especially near center
		for(i = 0; i < mass; i++) {
			density = (i+1)/Math.pow(distances.get(i)+20, 2);
			distances.set(i, distances.get(i)+1.2*density);
			// once it reaches a certain density particles start fusing
			if(density > 5)
				fusing.set(i, true);
		}
		// but there's hydrogen fusion and then there is heavy fusion
		// so first let's do hydrogen
		if(hydrogen_mass > 0) {
			// depending on how many are fusing change how strong fusion force is
			int number_fusing = 0;
			for(i = 0; i < mass; i++)
				if(fusing.get(i))
					number_fusing += 1;
			final double fusion_force = .001*number_fusing;
			// apply our fusion force to the star
			distances.replaceAll(d -> d + fusion_force);
			// so now we need the star to run out of hydrogen
			if(number_fusing > 10)
				if(Math.random() > 0)
					hydrogen_mass -= 1;
		} else {
			// no longer fusing hydrogen cause we out
			fusing.replaceAll(f -> false);
			// want it to shrink before we go into red giant mode so
			if(Math.random() > .95)
				red_giant = true;
		}
		
		if(red_giant && !(red_giant_lifetime <= 0)) {
			for(i = 0; i < fusing.size()/4; i++) {
				// i want 1/4 of them to be fusing
				fusing.set(i, true);
			}
			distances.replaceAll(d -> d + .028*mass/4/distances.get(mass-1));
			red_giant_lifetime -= 1;
		}
		
		// time to explode you beautiful baby boy
		if(red_giant_lifetime <= 0) {
			distances.replaceAll(d -> d + 5);
		}
			
		
		// now go through all of them and change position based on our new values
		// sadly idk how to do lambda on this and don't really want to figure it out
		for(i = 0; i < particles.size(); i++) {
			particles.get(i).setLocation(center.x+(int)(distances.get(i)*Math.cos(angles.get(i))),
					center.y+(int)(distances.get(i)*Math.sin(angles.get(i))));
		}
		
		// draw each particle after applying physics
		for(i = 0; i < particles.size(); i++) {
			if(fusing.get(i)) {
				if(red_giant)
					g.setColor(new Color(252, 24, 20, 180));
				else
					g.setColor(new Color(255, 215, 20, 120));
			} else {
				g.setColor(new Color(206, 121, 30, 80));
			}	
			g.fillRect(particles.get(i).x, particles.get(i).y, 2, 2);
		}
		
	}
	
}
