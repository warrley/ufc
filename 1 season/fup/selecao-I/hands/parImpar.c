#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/impar/Readme.md

int main() {

    int P;
    int alice;
    int bob;
    int result;

    printf("\n0 para alice escolher par e 1 para bob escolher par:  ");

    scanf("%d", &P);

    printf("\nType a number to alice:  ");
    scanf("%d", &alice);
    printf("\nType a number to bob:  ");
    scanf("%d", &bob);

    result = alice + bob;

    if(alice > 5 || alice < 0 || bob > 5 || bob < 0) {
        printf("\nType a valid number");
    } else {
        if(P == 0){
            if( result % 2 == 0){
                printf("\n*************");
                printf("\nAlice wins");
                printf("\n*************");
            } else {

            }
        } else if(P == 1){
            if( result % 2 == 0){
                printf("\n*************");
                printf("\nBob wins");
                printf("\n*************");
            } else {
                printf("\n*************");
                printf("\nAlice wins");
                printf("\n*************");
            }
        } else {
            printf("\n********************");
            printf("\nType a valid guess");
            printf("\n********************");
        }
    }
    

    

    return 0;
}