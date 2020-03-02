/* A text input file called "input.txt" will be given to you as a sample problem. You have n dogs and k kennels. There are p pairs of dogs
 * that cannot get along. First, you want to find a way to put multiple dogs in one kennel such that there aren't any dogs in the same kennel
 * who don't get along. Also, you want to do this so that you use the minimum number of kennels. There's a path between every kennel and the
 * owner's house. Next, are accessible by a paved path from the back door, and the minimum amount of paving material is used. Using Kruskal's
 * algorithm, I find the minimum spanning tree of the graph that has the vertices as the kennels and house.
 *
 * CSC 221 Program 5, Dr. Burg
 * @author Cam Lischke <lisccd18@wfu.edu>
 *
 */

package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream input = new FileInputStream("input.txt");
        Scanner in = new Scanner(input);

        if (in.hasNext()) {
            DogShelter shelter = new DogShelter();
            shelter.setNumDogs(in.nextInt());
            shelter.setNumMaxKennels(in.nextInt());
            shelter.setNumPairs(in.nextInt());
            Backyard backyard = new Backyard(shelter.getNumMaxKennels());

            for (int i = 0; i < shelter.getNumDogs(); i++) {              //assigns the dog names
                String name = in.nextLine();
                if (!name.equals(""))
                    shelter.population.add(new Dog(name, i));
                else i--;
            }
            shelter.connectAll(); //ASSIGN ALL POSSIBLE CONNECTIONS TO ALL DOGS, DISREGARD THE PAIRS THAT DO NOT GET ALONG

            while (in.hasNextLine()){
                String[] line = in.nextLine().split(", | \\s | \\n");
                if (line.length == 3)
                    backyard.populateDistance(parseInt(line[0]), parseInt(line[1]), parseInt(line[2]));      //populates the backyard adjacency matrix
                else if (line.length ==2)
                    shelter.disconnect(shelter.population.get(parseInt(line[0])), shelter.population.get(parseInt(line[1])));
            }

            shelter.assignKennels();
            shelter.print();

            backyard.resetDistances(shelter.kennels);
            backyard.printDistances();
            backyard.findMST();
            backyard.printMST();
        }
        else {
            System.out.println("INVALID FILE. MUST PROVIDE CORRECT .TXT FILE FORMAT AS SPECIFIED IN ASSIGNMENT 5");
            exit(26);
        }
    }


}
