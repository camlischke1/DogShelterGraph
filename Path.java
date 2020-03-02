/* This class represents the weighted edges of the graph that connects all buildings in the backyard. Kennel1 connects to Kennel2 with the same
 * weight that Kennel2 connects to Kennel1. The "weight" is stored in an integer named distance.
 *
 * CSC 221 Program 5, Dr. Burg
 * @author Cam Lischke <lisccd18@wfu.edu>
 *
 */
package com.company;

public class Path implements Comparable<Path>{
    private int distance;
    private Kennel kennel1;
    private Kennel kennel2;
    public boolean checked;

    public Path(int dist, Kennel a, Kennel b){
        kennel1 = a;
        kennel2 = b;
        distance = dist;
    }

    public int getDistance(){ return distance; }
    public Kennel getKennel1() { return kennel1; }
    public Kennel getKennel2() { return kennel2; }

    @Override
    public int compareTo(Path o) {
        if (getDistance() == o.getDistance())
            return 0;
        else if (getDistance() > o.getDistance())
            return 1;
        else
            return -1;
    }

    public String print(){
        String temp = String.format(" %14s %2s %2s %6s %s", kennel1.getName(),"<----",distance, "---->", kennel2.getName());
        return temp;
    }

    public void connect(){
        kennel1.addConnection(kennel2);
        kennel2.addConnection(kennel1);
    }
}
