/* This class represents a kennel that houses dogs. No dogs that do not get along can be in the same kennel. It stores the dogs that it houses
 * the number of dogs, and its connections to other kennels.
 *
 * CSC 221 Program 5, Dr. Burg
 * @author Cam Lischke <lisccd18@wfu.edu>
 *
 */
package com.company;

import java.util.ArrayList;

public class Kennel {
    private int numDogs = 0;
    public ArrayList<Dog> dogList;
    private String name;
    public ArrayList<Kennel> connections;




    public Kennel(){
        dogList = new ArrayList<Dog>();
        connections = new ArrayList<>();
    }

    public Kennel(Dog a){
        dogList = new ArrayList<Dog>();
        dogList.add(a);
        numDogs++;
        a.placed = true;
        connections = new ArrayList<>();
    }

    public void setName(String a) { name = a; }
    public String getName(){ return name; }
    public boolean isConnected(){
        if (connections.size() > 0) return true;
        else return false;
    }

    public int getNumDogs(){ return numDogs; }

    public void addDog(Dog a){
        dogList.add(a);
        numDogs++;
    }

    /* Checks to see if the current Kennel can house a dog by checking the adjacency lists of all dogs that are currently in the kennel.
     * @return boolean
     */
    public boolean canHouse(Dog a){
        int numDogsCan = 0;

        for (int i = 0; i < dogList.size() ; i++){
            boolean thisDogCan = false;
            ArrayList<Dog> currentList = dogList.get(i).adjacencyList;
            for (int j = 0; j < dogList.get(i).adjacencyList.size(); j++) {
                Dog currentGuess = currentList.get(j);
                if (a.isEqual(currentGuess)) {
                    numDogsCan++;
                    break;
                }
            }
        }
        if (dogList.isEmpty())
            return true;
        else if (numDogsCan == dogList.size()) return true;
        else return false;
    }

    public void add(Dog a){
        dogList.add(a);
        numDogs++;
        a.placed = true;
    }

    public void addConnection(Kennel k){
        connections.add(k);
    }

    public boolean equals(Kennel o){
        if (o instanceof Kennel){
            Kennel temp = (Kennel) o;
            return (name.equals(o.getName()));
        }
        return false;
    }

}
