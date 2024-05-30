[
https://codereview.stackexchange.com/q/163334/31562
]
$ wrapValue CRASH
$ wrapMemory CRASH
$ minValue 0
$ maxValue 255

$ name 'countdown'
>
 +++++ +++++ 100
 +++++ +++++ 200
 +++++ 250
 [- < +++++ +++++ >] <
 +++++ 255
> +++++ +++++ Line break
$ name 'lineBreak'

Write out the first ones
>+++++ +++++ +++++ +++++ +++++ +++++ +++++ +++++ +++++ +++.<.>+.<.>[-]
$ name 'zero'

>
>
>+
$ name 'digitStart'
Digit 1 Value A
> >
Digit 1 Value B
> >
Digit 1 Value C
>+
>
>
>
>+++++ +++++ +++++ +++++ +++++ +++++ +++++ +++++ +++++ ++++
$ name 'digit'
<<<<<
<<<<< <<<<

Memory
Countdown or loop condition
New line
Two empty cells

Series of DIGITS:
0 or 1 for if digit is activated
A
A
B
B
C
10 minus C
temp
temp2
digit as char
Empty
Empty



go to start
while (has more digits) {
3   b = a
5   a = c
8   c = a plus b
   if c exceeds 10 during addition then activate next digit and increase it by 1
}


Use countdown
[
$ assert hasName('countdown')
->>>>>
[ For each activated digit
$ assert hasName('digitStart')
    >>> >>>[-]+++++ +++++
    $ name '10minusC'
    <<<
    $ name 'B'
    [-] Clear B
    <    < [->+>+<<] at A after B = A
    $ name 'A'
    >>>>[->>>>-<<<< <<<<+>>>>] Set A = C and stop at C
    $ name 'C'

    Now do C = A plus B
    <
    $ name 'overflow'
<<
    $ name 'A_2'


     customRun('31')
    [
$ assert hasName('A_2')
->>>+>-
>+<[>-]>[<
code2 <<   +>> +++++ +++++
>->]<<
      <<<<] C = B
    <


    [
$ assert hasName('A')
->+>>>+>- Move 1 from A to the current C value

>+<[>-]>[<
code2 <<   +>> +++++ +++++
>->]<<
      <<<<<]

    >>>
    $ assert hasName('overflow')
    [
        increase next digit and activate it if it wasn't activated already
        >>>>>>>

        Check if next digit was not previously activated

        $ name 'activatedTemp'
          +
        $ name 'activatedTemp2'
          >[<-]<[>
            Activate Digit
            >>> >>> +++++ +++++
            >>> +++++ +++++ +++++ +++++ +++++ +++++ +++++ +++++ +++++ +++
            $ name 'digit'
            <<<
            <<< <<<
            End Activate digit
        <-<]>>
        End check if next digit was not previously activated
        $ name 'digitStart'
        [-]+ > +
        <<<< <<
        End increase next digit
        <<----- ----- <-
    ] C plus equals A
    $ assert hasName('overflow')
    <<
    [-<+>] Move right of A back to A

    Move C to number
    >>> [->>+>>+<<<<] >> [-<<+>>]

Goto next activated digits
>>>>>

] End of all activated digits

Print numbers
<<<

Go back and print numbers
[
$ assert hasName('digit')
. <<<<< <<<< <<<]

Print new line
<.

No more numbers to print now go to the counter
<
]
$ assert hasName('countdown')