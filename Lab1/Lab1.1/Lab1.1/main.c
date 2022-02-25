/* Author: Rikard Johansson
 * Date generated: 9/9-21
 * Updated 13/9-21
 * Solves Lab1.1 Rekursiva delen
 * Mata in en sträng i terminalen så kommer programmet att skriva ut strängen baklänges
 */

#include <stdio.h>
void revstr(char *c){       //Skriver ut arrayen baklänges med hjälp av pekare
    if(*c){                 //Finns?
        revstr(c+1);        //Går steg för steg till den sista nås
        putchar(*c);        //Skriver ut och hoppar tillbaka till föregående
    }
}

int main(int argc, const char * argv[]) {
    char c[] = "";          //Skapar array
    scanf("%[^\n]", c);     //Läser in hel rad från terminalen
    revstr(c);              //Skickar arrayen till metoden
    
    return 0;
}


