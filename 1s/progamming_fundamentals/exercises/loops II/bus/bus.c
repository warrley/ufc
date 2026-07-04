#include <stdio.h>

// https://github.com/qxcodefup/arcade/blob/master/base/lotado/Readme.md
int main() {
    int capacity, people, change;

    printf("\nWhich the capacity? ");
    scanf("%d", &capacity);

    printf("\nHow many people are there? ");
    scanf("%d", &people);

    while(1) {
        if(people <= 0){
            printf("\nEmpty");
        }
        if(people < capacity) {
            printf("\nThere is still space");
        }
        if((people < 2 * capacity && people > capacity) || people == capacity) {
            printf("\nFull");
        }
        if(people >= 2 * capacity) {
            printf("\nIt's time to go");
            break;
        }
            printf("\nHow many people got in or out? ");
            scanf("%d", &change);
            people = people + change;
    }

    return 0;
}