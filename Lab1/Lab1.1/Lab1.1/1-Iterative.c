/* Author: Rikard Johansson
 * Date generated: 9/9-21
 * Updated 13/9-21
 * Solves Lab1.1 iterativa delen
 * Mata in en sträng i terminalen så kommer programmet att skriva ut strängen baklänges
 */

#include <stdio.h>
#include <string.h>     //för strlen()

void revstr(char c[]){                          //Skriver ut arrayen baklänges
    for(int i = strlen(c) - 1; i >= 0; i--){    //Går från sista elementet till första
        printf("%c", c[i]);                     //Skriver ut element för element från array
    }
}

int main(int argc, const char * argv[]) {
    char c[50];                                 //Char array med fast antal minnesplatser
    scanf("%[^\n]", c);                         //Läser in hel rad från terminalen
    revstr(c);
    
    return 0;
}
