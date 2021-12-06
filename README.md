# Polyglot CLI

Executes the code received as a command line argument and outputs the result to the standard output.

## Requirements

* [GraalVM](https://www.graalvm.org/)

## Install

1. Download and install graalvm
2. Install the language you want to use with the command `gu install <language>`

## Usage

* `java net.firiz.polyglotcli.Main [options] [language] [main] [arguments]`

## Options

* Common Options
    * `-m`: module
    * `-w`: working directory
* Java Options
    * `-C`: classpath

## Languages

- [x] LLVM
- [x] Java (Espresso)
- [x] JavaScript
- [x] Python
- [x] Ruby
- [ ] NodeJS
- [ ] WebAssembly

## Example

### JavaScript
* Command: `java net.firiz.polyglotcli.Main -w ./examples/ js ./examples/test.js`
* Source:
    ```
  console.log("Hello World!");
    ```
* Result:
    ```
    Hello World!
    ```
### C
* Command: `java net.firiz.polyglotcli.Main -w ./examples/ llvm ./examples/test 1 2`
* Compiled Source:
    ```
  #include<stdio.h>
  
  int main(int argc, char const *argv[]) {
      printf("Hello World!\n");
      printf("args count %d\n", argc);
      for (int i = 0; i < argc; i++) {
          printf("[%d]: %s\n", i, argv[i]);
      }
      return 0;
  }
    ```
* Result:
    ```
  Hello World!
  args count 3
  [0]: /polyglotCLI/examples/test
  [1]: 1
  [2]: 2
    ```
### Java
* Command: `java net.firiz.polyglotcli.Main -w ./ -classpath ./examples java Test 1 2`
* Compiled Source:
    ```
  import java.util.Arrays;
  
  public class Test {
        public static void main(String[] var0) {
            System.out.println("Hello World! args: " + Arrays.toString(var0));
            System.out.println(System.getProperty("java.home"));
        }
  }
    ```
* Result:
    ```
  Hello World! args: [1, 2]
  /graalvm
    ```