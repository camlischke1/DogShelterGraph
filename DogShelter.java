/* This class represents the placing of dogs into the minimum number of kennels.
 *
 * CSC 221 Program 5, Dr. Burg
 * @author Cam Lischke <lisccd18@wfu.edu>
 *
 */
package com.company;

import java.util.*;

import static java.lang.System.exit;


public class DogShelter{
    public ArrayList<Dog> population;
    public ArrayList<Kennel> kennels;
    private int numDogs;
    private int numMaxKennels;
    private int numPairs;
    private int numMinKennels;

    public int getNumDogs(){ return numDogs; }                  //STANDARD GET AND SET METHODS
    public int getNumMaxKennels() { return numMaxKennels; }
    public int getNumMinKennels() { return numMinKennels; }
    public void setNumDogs(int i) { numDogs = i; }
    public void setNumMaxKennels(int i) { numMaxKennels = i; }
    public int getNumPairs(){ return numPairs;}
    public void setNumPairs(int i){ numPairs = i; }

    public DogShelter(){                                        //constructor method
        population = new ArrayList<>();
        kennels = new ArrayList<>();
    }

    /*Connects all dogs at the onset of the program, regardless if they can be “paired” or not
     * @return void
     */
    public void connectAll(){
        for (int i = 0; i < population.size(); i++){
            for (int j = 0; j < population.size(); j++){
                if (i != j)
                    connect(population.get(i), population.get(j));
            }
        }
    }


    public void connect(Dog a, Dog b){                          //adds the two parameters to each other's adjacency lists
        if (!a.isEqual(b)) {
            a.connect(b);
        }
    }

    /*Disconnects dogs that cannot be paired. Because I perform this as I read the input file in the Main class,
    *the function itself has linear complexity. In a broader sense though, disconnecting all pairs of dogs that
    * cannot be housed together is actually O(N2 - N)
    * @return void
     */
    public void disconnect(Dog a, Dog b){
        if (!a.isEqual(b)){
            a.disconnect(b);
            b.disconnect(a);
        }
    }

    /* Places each dog into a kennel that does not contain a dog with which they do not get along. Uses canHouse() which adds to the complexity.
     * @return void
     */
    public void assignKennels(){
        Collections.sort(population);
        //INITIALIZE THE KENNELS AND ASSIGN DOGS TO EACH USING MODIFIEDTYPE OF TOPOLOGICAL SORT
        if (kennels.isEmpty() && getNumMaxKennels() != 0 && getNumDogs() != 0) {
            kennels.add(new Kennel());

            for (int i = 0; i < population.size(); i++){
                Dog currentDog = population.get(i);

                for (int j = 0; j < kennels.size(); j++){
                    Kennel currentKennel = kennels.get(j);
                    if (currentKennel.canHouse(currentDog)) {
                        currentKennel.add(currentDog);
                        break;
                    }
                }
                if (!currentDog.placed){
                    kennels.add(new Kennel(currentDog));
                }
            }
        }
        else {
            System.out.println("No possible solution.");
            exit(1);
        }
        numMinKennels = kennels.size();
    }

    public void print(){
        System.out.println("=====================================================================================================");
        System.out.println("Number of Dogs: " + numDogs + "   Maximum Number of Kennels: " + numMaxKennels + "   Minimum Number of Kennels: " + numMinKennels);
        for (int i = 0; i < kennels.size(); i++){
            System.out.print("Kennel" + i + ": ");
            for (int j = 0; j < kennels.get(i).dogList.size(); j++){
                System.out.print(kennels.get(i).dogList.get(j).getName() + "(" + kennels.get(i).dogList.get(j).getIDNum() + ")  ");
            }
            System.out.println();
        }
        if (numMinKennels > numMaxKennels){
            System.out.println("***The problem cannot be solved using the inputted number of max kennels.***");
            exit(109);
        }
    }
}
