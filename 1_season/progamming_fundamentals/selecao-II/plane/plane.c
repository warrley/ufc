#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/game10/Readme.md

int main() {
    int position, disc, plane, need;

    printf("\nHow many vertical positions are there?:  ");
    scanf("%d", &position);
    
    printf("\nWhat the rocket position:  "); 
    scanf("%d", &disc);
    
    printf("\nWhat the current plane position:  ");
    scanf("%d", &plane);

    if(position < 2 || position > 100 || disc < 1 || disc > position || plane < 1 || plane > position ){
        return 1;
    }

    if(disc >= plane) {
        need =  disc - plane;
    } else if(disc < plane) {
        need = disc - plane + position;
    }

    printf("\nYou need change position %d times", need);

    return 0;
}