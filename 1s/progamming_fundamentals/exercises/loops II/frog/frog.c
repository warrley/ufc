#include <stdio.h>

// https://github.com/qxcodefup/arcade/blob/master/base/sapinho/Readme.md
int main() {
    int deep, up, slide, before = 0, after = 0;

    printf("\nHow deep is the well? ");
    scanf("%d", &deep);

    printf("\nHow much did he go up? ");
    scanf("%d", &up);

    printf("\nHow much did he slip? ");
    scanf("%d", &slide);


    while (after < deep){
        printf("%d ", before);
        after = before + up;
        if(after < deep){
            printf("%d\n", after);
        } else {
            printf("saiu\n");
        }
        before = after - slide;
    };
    

    return 0;
}