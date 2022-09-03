In this project, I worked with S3, a simple RTS game designed to test AI techniques. S3 looks very simple and has pretty basic graphics, but it features most of the characteristics of more complex RTS games such as Starcraft. The goal of this project is to learn how to implement pathfinding methods, which are the most common technique in computer games of all genres. Specifically, I implemented an A* approach to path finding.

One thing to note is that I implemented a Node class for creating and using Nodes in my A* algorithm. The Node class stores the parent, x, y, g, and h values for a Node and has a method to calculate the heuristic using the Manhatten Distance. Since I am using a HashSet, I overrided the hashCode and equals methods in my Node class.

To implement A* pathfinding in my program, I refernced the following algorithm:  
  
Start.g = 0;
Start.h = heuristic(Start)
OPEN = [Start];
CLOSED = []
WHILE OPEN is not empty
N = OPEN.removeLowestF()
IF goal(N) RETURN path to N
CLOSED.add(N)
FOR all children M of N not in CLOSED:
M.parent = N
M.g = N.g + 1;
M.h = heuristic(M)
OPEN.add(M)
ENDFOR
ENDWHILE

In order for A* to work properly, I had to implement three helper methods removeLowestF, retrievePath, and isCellFree. The removeLowestF method removes the node with the lowest F, the value of g and h combined, from the open list. The retrievePath method retrieves the shortest path by returning a list of the goal node's parents. The isCellFree method checks if the S3PhysicalEntity is able to a desired cell.

