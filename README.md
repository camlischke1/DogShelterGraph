# DogShelterGraph
Example of minimum spanning tree problem and minimum vertex coloring in Java


There are two problems presented by this program, both solved through the implementation of a graph abstract data structure. 

The first is that given a list of dogs and also a list of dogs that do not get along, we must place all dogs in a kennel that does not contain a pair that do not get along, while using the least number of kennels as possible. To solve this problem, I used an unweighted, undirected graph with Dog objects as the vertices. For each dog given, the dog class stores a name, ID number, whether or not it has been placed in a kennel, and adjacency list. The adjacency list is our implementation of the graph. For each dog with which it can be housed, there is a “connection” in the adjacency list. This essentially means that all possible dogs are stored in the adjacency list. I iterated through the list of all dogs that had not yet been placed into a kennel, and for each one, checked to make sure that every single dog already in that kennel was contained in the dog’s adjacency list. If not, I moved to the next kennel. The DogShelter class is responsible for placing all Dog objects into a Kennel object.

The second problem is, that after we have placed all dogs in the minimum amount of kennels, we must find the least amount of path required to ensure that the owner’s house is connected to all kennels. To solve this, we think about finding a minimum spanning tree. This time, the kennels (and the house) are the vertices in this undirected, weighted graph. I created a new Backyard class that holds a 2D adjacency matrix for all kennels (and house) in the graph. The Backyard class implements Kruskal’s algorithm for finding a minimum spanning tree of weighted Path edges to the Kennel vertices. For n Kennels (not including the owner’s house), there are ∑_(i= 0)^n▒i Paths. For n Kennels (including the owner’s house), there are ∑_(i= 0)^(n-1)▒i Paths. In the minimum spanning tree, there are n-1 Paths (when n includes the owner’s house). Using Kruskal’s algorithm, I ordered all Paths by the distance variable (weight). Iterating through the sorted list, I checked to see if the new Path would create a cycle in the graph. If it would, I skipped it. If not, the Path was added to the minimum spanning tree. 

The input file will be a simple .txt file formatted as follows:
input.txt is a sample text input
====================================================================
n		//number # of dogs n, # of kennels k, # of pairs of fighting dogs, p
k
p		
dog0		
dog1		//The names of dogs as strings.	
dog2
dog3
…
dogn-1
1   0   10		//locations 1 and 0 are 10 feet apart. Number the locations from 0.
2   0   12		//locations 2 and 0 are 12 feet apart
2   1   9
3   0   14			
3   1   21
3   2   18
…
m    0   13
m   2   11
m   3   15
m-1   m-2   20
di   dj 			//List of dogs that can't get along, by their numbers. For instance, if dog1 and dog2 do not get along, put "1 2"
di   dj 
di   dj 
…
di   dj 
