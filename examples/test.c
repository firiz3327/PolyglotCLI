#include<stdio.h>

// libの方ではなく、languagesのclangを使う
// $graalvm/languages/llvm/native/bin/clang
int main(int argc, char const *argv[]) {
    printf("Hello World!\n");
    printf("args count %d\n", argc);
    for (int i = 0; i < argc; i++) {
        printf("[%d]: %s\n", i, argv[i]);
    }
    return 0;
}
