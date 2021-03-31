# Star_Life_Cycle

Program that simulates a star life cycle. Starts with a gas cloud and eventually dies in a supernova. To start the star life cycle press the spacebar. Can exit at anytime using esc.

Added a jar file so it's easy to run by just double clicking on the jar file (assuming you can run jar files).

Here's a youtube video of me explaining the process and a little bit of the program https://youtu.be/UU0OTRrI6Rs Slightly wrong because after the supernova a nuetron star or black hole should be left behind. TODO: Make a neutron star or black hole left behind after super nova. 

Future plans: centralize force variables so that you can see all sorts of different processes. ie: what happens when pressure too great for gravity, what if fusion was stronger, what if gravity was stronger, so on just to simulate other types of stuff. Currently pretty easy for me to do but not easy for someone else to do who doesn't know the program well.

Also fix up some of the physics and add more physics cause they are a little janky right now and just what would work and semi accurate/efficiency and ease over accuracy. May try to do more accurate and then fix up performance somehow in other areas/use efficient methods to do physics quickly. IDK yet we'll see.

Make stars objects or structs instead of several array lists that way can do lambda function for each drawing which would be more efficient and allow threaded drawing hopefully. (Not sure if g will allow that and multiple threads can draw to the screen at the same time but worth a shot)
