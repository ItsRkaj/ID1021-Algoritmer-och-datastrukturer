package com.company;

/* Author: Rikard Johansson
 * Date generated: 9/9-21
 * Updated 13/9-21
 * Solves Lab1.2 Rekursiva delen
 * Mata in en sträng i terminalen så kommer programmet att skriva ut strängen baklänges
 */

import java.util.Scanner;

public class Main {

    public static StringBuilder sb = new StringBuilder();

    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);                          //För inmatning
        String str = scan.nextLine();                                   //Sparar inmatning som string

        revstr(str);
        System.out.println(sb.toString());                              //Utskrift
    }

    static void revstr(String str)                                      //Skriver ut en sträng baklänges
    {
        if ((str==null)||(str.length() <= 1)) //1, 0 eller inget
            sb.append("[" + str + "], ");
        else
        {
            sb.append("[" + str.charAt(str.length()-1) + "], ");        //Skriver ut sista bokstaven i Stringen
            revstr(str.substring(0,str.length()-1));                    //Sänder Stringen till funktionen igen fast sista bokstaven är borttagen
        }
    }
}
