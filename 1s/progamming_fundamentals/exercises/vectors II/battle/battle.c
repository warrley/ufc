#include <stdio.h>
#include <ctype.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/revolta/Readme.md

int main() {
    int total, soldiers = 0, spy = 0;
    int num[50];

    printf("\nHow many groups come next?: ");
    scanf("%d", &total);

    for (int i = 0; i < total; i++) {
        printf("\nHow many?");
        scanf("%d", &num[i]);
        if(num[i] % 2 == 0) {
            spy+= num[i];
        } else if(num[i] % 2 != 0) {
            soldiers+= num[i];
        }
    }
    
    if(soldiers > spy) {
        printf("\n-------------");
        printf("\nSOLDIERS WINS");
        printf("\n-------------");
    } else if(spy > soldiers) {
        printf("\n-------------");
        printf("\nSPYS WINS");
        printf("\n-------------");
    } else {
        printf("\n-------------");
        printf("\nDRAW");
        printf("\n-------------");
    }

    return 0;
}