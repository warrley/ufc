#include <stdio.h>
#include <math.h>


int main() {
    int batraqs, anfibs, typeb[50], typea[50], same = 0;

    printf("\nHow many batraqs? ");
    scanf("%d", &batraqs);

    for (int i = 0; i < batraqs; i++) {
        printf("\nWhich type of batraq? ");
        scanf("%d", &typeb[i]);
    }

    printf("\nHow many amphibians? ");
    scanf("%d", &anfibs);

    for (int i = 0; i < anfibs; i++) {
        printf("\nWhich type of amphibians? ");
        scanf("%d", &typea[i]);
    }

    for (int i = 0; i < batraqs; i++) {
        int element = typeb[i];
        for (int j = 0; j < anfibs; j++) {
            if(typea[j] == element) {
                same++;
            }
        }
    }

    same == batraqs ? printf("\nThey are same") : printf("\nThey aren't same");

    return 0;
}