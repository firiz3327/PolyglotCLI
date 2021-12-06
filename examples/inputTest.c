#include<stdio.h>
#include <string.h>

int main(void) {
    printf("input\n");

//    int v;
//    scanf("%d", &v);
//    printf("text: %d\n", v);

    char name[21];
    fgets(name, sizeof(name), stdin);
    name[strlen(name) - 1] = '\0';
    printf("text: %s\n", name);
    fflush(stdin);

    return 0;
}
