package com.company;

/* Author: Rikard Johansson
 * Date generated: 28/9-21
 * Updated 28/9-21
 * Solves Lab3.1
 * Description: Filtrerar ut alla tecken som inte är med i alfabetet, blanksteg eller newline. Ersätter otillåtet tecken med blanksteg
 * Code taken from: -
 */

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner inFile = new Scanner(new FileReader("Lab3.txt")).useDelimiter("");

        while(inFile.hasNext()){
            filter(inFile.next().charAt(0));
        }
    }

    public static void filter(char c){                                  //Filtret
        if(Character.isAlphabetic(c) || c == ' ' || c == '\n'){         //De tillåtna tecknen.
            System.out.print(c);
        } else {
            System.out.print(' ');
        }
    }
}
