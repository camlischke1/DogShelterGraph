/* This class represents a Dog vertex in our graph when trying to place all dogs in a kennel such that no dogs that do not get along with
each other can be housed together.

 * CSC 221 Dr Burg, Program 5
 * @author Cam Lischke <lisccd18@wfu.edu>
 */

package com.company;

import java.util.ArrayList;

public class Dog implements Comparable<Dog>{
    private String name;
    private int idNum;
    public ArrayList<Dog> adjacencyList;
    private int numConnections = 0;
    public boolean placed = false;

    public Dog(String n, int id){       //constructor for new dog
        name = n;
        adjacencyList = new ArrayList<>();
        idNum = id;
    }
    public String getName(){ return name; }
    public int getNumConnections(){ return numConnections; }
    public int getIDNum(){ return idNum; }

    public void connect(Dog d){     //connects a pair of dogs to each other
        adjacencyList.add(d);
        numConnections++;
    }

    public void disconnect(Dog d){
        for (int i = 0; i < adjacencyList.size(); i++){
            if (adjacencyList.get(i).isEqual(d)){
                adjacencyList.remove(i);
                numConnections--;
            }
        }
    }


    //@Override
    public int compareTo(Dog o) {
        if (this.getNumConnections() > o.getNumConnections())
            return 1;
        else if (this.getNumConnections() < o.getNumConnections())
            return -1;
        else
            return 0;
    }

    public boolean isEqual(Dog a){
        return this.getName().equalsIgnoreCase(a.getName());
    }
}
