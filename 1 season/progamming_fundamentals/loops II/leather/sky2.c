#include <stdio.h>
#include <math.h>
#include <ctype.h>

// https://github.com/qxcodefup/arcade/blob/master/base/colorido/Readme.md
int main() {
    int num1;
    char foot;

    printf("\nWhere is the stone? ");
    scanf("%d", &num1);

    printf("\nWhat the first foot?(R = Rigth), (L = Left):");
    scanf(" %c", &foot);

    foot = toupper(foot);

    if(foot != 'L' && foot != 'R'){
        printf("\nENTER A VALID VALUE");
        return 1;
    }

    for(int i = 0; i < 10; i++) {
        if(i == num1) {
            continue;
        }
        if(foot != 'L') {
            foot = 'L';
        } else {
            foot = 'R';
        }
        printf("%d%c\n", i, foot);
    }
    
    if(num1 == 10){
        printf("sky");
    }

    return 0;
}