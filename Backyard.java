/* This class represents the diagram of the backyard when we have already placed dogs in the kennels. We use this class to find the minimum
 * spanning tree with the given distances. It uses an adjacency matrix to represent the weighted, undirected graph.
 *
 * CSC 221 Program 5, Dr. Burg
 * @author Cam Lischke <lisccd18@wfu.edu>
 *
 */
package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class Backyard {
    public ArrayList<Kennel> buildings;
    public int[][] distances;
    private int numBuildings;
    private ArrayList<Path> minimumSpanningTree;

    public Backyard(int n) {
        distances = new int[n][n];
        minimumSpanningTree = new ArrayList<Path>();
    }

    /*Populates the 2D adjacency matrix with the inputted path lengths that connect all buildings in the backyard.
     *Kennels and the owner’s house count as buildings.
     * @return void
     */
    public void populateDistance(int a, int b, int distance) {           //populates the backyard adjacency matrix
            distances[a][b] = distance;
            distances[b][a] = distance;
    }

    public void resetDistances(ArrayList<Kennel> list){
        buildings = new ArrayList<Kennel>(list);
        buildings.add(new Kennel(new Dog("Owner", Integer.MAX_VALUE)));
        numBuildings = buildings.size();
        int temp[][] = distances;
        distances = new int[numBuildings][numBuildings];
        for (int i = 0; i < numBuildings; i++){
            for (int j = 0; j < numBuildings; j++){
                distances[i][j] = temp[i][j];
            }
        }
    }

    public void printDistances() {
        System.out.println("=====================================================================================================");
        System.out.println("Distances between all kennels (including the Owner's house): ");
        for (int i = 0; i < distances.length && i < numBuildings; i++) {
            for (int j = i; j < distances[0].length && j < numBuildings; j++) {
                    if (i != j && i != numBuildings - 1 && j != numBuildings - 1)
                        System.out.println("Kennel" + i + " and Kennel" + j + ": " + distances[i][j]);
                    else if (i == numBuildings - 1 && i != j)
                        System.out.println("Owner's House and Kennel" + j + ": " + distances[i][j]);
                    else if (j == numBuildings - 1 && i != j)
                        System.out.println("Kennel" + i + " and Owner's House: " + distances[i][j]);
            }
        }
    }

    /* Uses Kruskal’s algorithm to find the minimum spanning tree. Iterates through the 2d adjacency matrix and creates
     * an ArrayList of Path objects. That complexity is O(N2). Then sorts with a complexity of O(NlogN). Then iterates
     * through the sorted ArrayList and checks each to make sure it does not create a cycle. The formsCycle() function is
     * described below, but also adds complexity to this function as a whole. formsCycle() is used N times, so that complexity is O(N2).
     * @return void
     */
    public void findMST() {                  //using Kruskall's algorithm
        ArrayList<Path> allPaths = new ArrayList<>();
        for (int i = 0; i < distances.length && i < numBuildings; i++) {
            for (int j = 0; j < distances[0].length && j < numBuildings; j++) {
                if (i >= j && i!=j && distances[i][j] != 0 && distances[j][i] != 0){
                    if (i != numBuildings - 1 && j != numBuildings - 1) {
                        allPaths.add(new Path(distances[i][j], buildings.get(i), buildings.get(j)));
                        buildings.get(i).setName(("Kennel" + i));
                        buildings.get(j).setName(("Kennel" + j));
                    } else if (i == numBuildings - 1) {
                        allPaths.add(new Path(distances[i][j], buildings.get(i), buildings.get(j)));
                        buildings.get(i).setName("Owner's House");
                        buildings.get(j).setName(("Kennel" + j));
                    } else if (j == numBuildings - 1) {
                        allPaths.add(new Path(distances[i][j], buildings.get(i), buildings.get(j)));
                        buildings.get(j).setName("Owner's House");
                        buildings.get(i).setName(("Kennel" + i));
                    }
                }
            }
        }
        Collections.sort(allPaths);

        ArrayList<Path> finalPath = new ArrayList<Path>();
        if (allPaths.size() > 0) {
            finalPath.add(allPaths.get(0));

            for (int i = 1; i < allPaths.size(); i++) {
                Path currentPath = allPaths.get(i);
                if (!formsCycle(finalPath, currentPath) && finalPath.size() < numBuildings - 1)
                    finalPath.add(currentPath);
            }
        }
        minimumSpanningTree = new ArrayList<>(finalPath);
    }


    /*Checks all Paths already placed in the minimum spanning tree. Temporarily adds the current Path to check if it
     * forms a cycle. Each path item stores a source and a destination. I iterate through the temporary minimum spanning
     * tree from the last destination. I follow the path backwards, ensuring that the beginning of the path does not equal
     * the final destination. Returns true if it does.
     * @return boolean
     */
    public boolean formsCycle(ArrayList<Path> possiblePaths, Path a){
        ArrayList<Path> temp = new ArrayList<>(possiblePaths);
        Kennel source, dest;
        if (a.getKennel1().getName().compareTo(a.getKennel2().getName()) < 0) {
            dest = a.getKennel2();
            source = a.getKennel1();
        }
        else{
            dest = a.getKennel1();
            source = a.getKennel2();
        }

        for (int i = temp.size()-1; i >-1; i--){
            if (source.equals(dest))
                return true;
            else if (temp.get(i).getKennel2().equals(source)) {
                source = temp.get(i).getKennel1();
                temp.remove(i);
                i = temp.size();
            }
            else if (temp.get(i).getKennel1().equals(source)) {
                source = temp.get(i).getKennel2();
                temp.remove(i);
                i = temp.size();
            }
        }
        if (source.equals(dest))
            return true;
        else return false;
    }

    public void printMST(){
        System.out.println("=====================================================================================================");
        System.out.println("Final Minimum Spanning Tree: ");
        int total = 0;
        for (int i = 0; i < minimumSpanningTree.size(); i++){
            System.out.println(minimumSpanningTree.get(i).print());
            total += minimumSpanningTree.get(i).getDistance();
        }
        System.out.println("The minimum amount of path require to connect all kennels to the house is " + total + " units.");
    }
}
