#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/final/Readme.md

int main() {
    int pr1, pr2, pr3;

    printf("\nEnter your grade test 1:  ");
    scanf("%d", &pr1);

    printf("\nEnter your grade test 2:  ");
    scanf("%d", &pr2);

    printf("\nEnter your grade test 3:  ");
    scanf("%d", &pr3);

    if((pr1 + pr2) / 2 >= 7 ) {
        printf("\nYou are approved!");
    } else if ((pr1 + pr2) / 2 < 4) {
        printf("\nYou are failed!");
    } else if((pr1 + pr2) / 2 > 4 || (pr1 + pr2) / 2 <= 7) {
        if((pr1 + pr2 + pr3) / 3 >= 5){
            printf("\nYou are approved on final test");
        } else {
            printf("\nYou are failed on final test");
        }
    }

    

    return 0;
}