# Simple-navigation-map

Language: Java<br />
IDE: IntelliJ IDEA<br /><br />

Functionality:

The program is intended to be a simple example navigation map. <br />
You can choose the point of departure and arrival and the program takes care of finding the fastest route between the two points.<br /><br />

Program description:

The first thing the program will is to write in the panel the data of the graph used as a map.<br />
Among them are the x and y coordinates of the node or vertices, the edges that exist and their corresponding values, and the relationship of one vertex to another.<br /><br />
The program has two panels, one at the top and one at the bottom covering most of the program.<br />
- In the upper panel there are buttons representing each of the locations on the map (the bottom panel). These are numbered from 0 to 23; in addition to this, the total time traveled is shown at the top left.<br />
- In the lower panel is the map, represented by a graph, which changes randomly each time the program is started. The numbers that appear on the road indicate the time in scale that it will take to cross, this is taken into account when looking for the most efficient route in time; for this the program uses Dijkstra's algorithm.<br />

<br /><br />

User's guide:

To use the program just click on one of the buttons on the upper panel to assign the node where the route will start and click on another one to select the final node of the route. The pin will proceed to the first corresponding node and will travel through each of the nodes (according to a route established using the aforementioned algorithm) until it reaches the final node.
