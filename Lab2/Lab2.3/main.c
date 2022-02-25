#include <stdlib.h>
#include <stdio.h>

/* Author: Rikard Johansson
 * Date generated: 25/9-21
 * Updated 25/9-21
 * Solves Lab2.3
 * Description: Puts all negative integers before positiv ingeres in an array
*/

void isNegative(int a[], int len){
    int p = 0;
    for(int i = 0; i < len; i++){
        if(a[i]<0){
            for(int j = i; j > p; j--){
                a[j] = a[j] + a[j-1];
                a[j-1] = a[j] - a[j-1];
                a[j] = a[j] - a[j-1];
            }
            p++;
        }
    }
}

int main(int argc, char *argv[])
{
    printf("Lenght of array: ");
    int ARRAY_LENGHT;
    scanf("%d", &ARRAY_LENGHT);
    int arr[ARRAY_LENGHT];
    for(int i = 0; i < ARRAY_LENGHT; i++){
        scanf("%d", &arr[i]);
    }
    isNegative(arr, ARRAY_LENGHT);
    for (int i = 0; i < ARRAY_LENGHT; ++i) {
        printf("%d, ", arr[i]);
    }
}
