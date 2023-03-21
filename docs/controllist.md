## Control list for static error checking:

1.  Are all variables initialized
3.	Where a variable is initialized in a declarative statement, is it properly initialized?
4.	Will we get an IndexOutOfBoundsException  somewhere in our program?
5.	Do all actual variable types match the expected variable types ?
6.	Do we have infinite loop
7.	For object-oriented languages, are all inheritance requirements met in the implementing class?
8.	Is each variable assigned the correct length and datatype?
9.	Are there operations with variables which have different types
10.	Is an overflow or underflow expression possible during the computation of an expression?
11.	Is it impossible to divide by zero
12.	Are all comparisons done between variables having the same data type
13.	Are the comparison operators correct?
14.	 For expressions containing more than one Boolean operator, are the assumptions about the order of evaluation and the precedence of operators correct?
15.	Does the way in which the compiler evaluates Boolean expressions affect the program?
16.	Does the actual number of arguments and their order match the expected number of arguments and their order
17.	Are all constants set to static final
18.	There aren’t any attempts to write to a read – only variable
19.	Are all streams closed after being used
20.	Are there spelling or grammatical errors in any text that is printed or displayed by the program?

## Report after conducted static code inspection :
1.	Yes
2.	Yes
3.	No loops present in the program
4.	Yes
5.	No loops present in the program
6.	Inheritance is not used
7.	Yes
8.	There are operations with percentages which should be handled with care, but for now no errors were found
9.	Yes, if the number of tickets is too big
10.	Yes
11.	Yes
12.	Yes
13.	Yes
14.	Yes, the program relies on the order of comparisons to evaluate the biggest discount possible
15.	Yes
16.	No
17.	Yes
18.	There are no streams used
19.	There were variables with poor naming 
