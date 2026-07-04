#include <stdio.h>
#include <ctype.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/patas/Readme.md

int main() {
    int cebolinha, chico, animals, total = 0;
    char type[50];

    printf("\nEnter a guess of CHICO BENTO: ");
    scanf("%d", &chico);

    printf("\nEnter a guess of CEBOLINHA: ");
    scanf("%d", &cebolinha);

    printf("\nEnter a number of animals: ");
    scanf("%d", &animals);

    for (int i = 0; i < animals; i++) {
        printf("\nEnter a type the animal %d: (Sheep - S) (H - Horse) (C - Chicken)", i + 1);
        scanf(" %c", &type[i]);
        type[i] = toupper(type[i]);
    }

    for (int i = 0; i <= animals; i++){
        if(type[i] == 'S' || type[i] == 'H') {
            total = total + 4;
        } else if (type[i] == 'C'){
            total = total + 2;
        }
    }

    printf("\nThere are %d legs\n", total);


    if(fabs(cebolinha - total) > fabs(chico - total)){
        printf("**************");
        printf("\nCHICO win\n");
        printf("**************");
    } else if (fabs(cebolinha - total) < fabs(chico - total)){
        printf("**************");
        printf("\nCEBOLINHA win\n");
        printf("**************");
    }

    return 0;
}