#include <stdio.h>
#include <math.h>


int main() {
    int movements, where[50], parkour = 0;

    printf("\nHow many movements? ");
    scanf("%d", &movements);

    for (int i = 0; i < movements; i++) {
        printf("\nWhere to? ");
        scanf("%d", &where[i]);
    }
    
    for (int i = 0; i < movements - 1; i++) {
        if(fabs(where[i] - where[i + 1]) > 1){
            parkour++;
        }
    }

    printf("\nNeed %d times movement parkour", parkour);

    return 0;
}