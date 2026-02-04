#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/pedra/Readme.md

int main() {
    int competitors, winner = 0, diffCurrent = 101;

    printf("\nHow many competitors?: ");
    scanf("%d", &competitors);

    for (int i = 0; i < competitors; i++) {
        int first, second;

        printf("\nEnter the first alcance of competitor %d: ", i + 1);
        scanf("%d", &first);
        printf("\nEnter the second alcance of competitor %d: ", i + 1);
        scanf("%d", &second);

        if(first >= 10 && second >= 10 && first <= 100 && second <= 100) {
            int diff = fabs(first - second);
            if(diff < diffCurrent) {
            diffCurrent = diff;
            winner = i + 1;
        } else if(first < 10 && second < 10){
            printf("\nDesclassified");
        }
        }

        
    }

    if(winner > 0){
        printf("\nThe winner is %d", winner);
    } else {
        printf("\nNobody win");
    }

    return 0;
}