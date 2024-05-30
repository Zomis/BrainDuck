[
https://codereview.stackexchange.com/q/57382/31562
]
++++++++++[>++++++++++<-]> Initialize 100 (number of times to perform FizzBuzz)

TAPE MEANINGS
255 Start
254 A Fizz or Buzz text to print
253 End of Fizzes and Buzzes
252 Currently processed FizzBuzz calculation

TAPE OVERVIEW
Remaining Iterations
10 for Line Break
255 Start Marker
Counter
Boolean 1 or 0 for whether or not a fizzbuzz matches current counter
Some empty space for converting counter to string
  Any Number of Sequences of the following
    254 Indicator for FizzBuzz sequence
    Counter
    Countdown until next text output
    Text any number of characters
    Zero
    Zero
254 and 253 marker to indicate the end of sequences


>++++++++++                   Line break
>-                            Start marker
>>>>>>>>>>>>>>>>              Empty space for counter to string conversion


SETUP Create the Fizz and Buzz sequences on the tape

FIZZ
-->                               Create indicator
+++++++[->++++++++++<]>           Create F
[->+>+>+>+<<<<]                   Copy 4x F
+++                               Set modulo operator to 3
>>+++>>>                          Adjust second letter to I
++++++++[-<++++<++++<++++>>>]     Make the last three lowercase to Fiff
+++++[-<++++<++++>>]              Modify the last two F to Z by adding 20
>>                                Leave two zeros at the end

BUZZ
-->                               Create indicator
++++++[->+++++++++++<]>           Create B
[->+>+>+>+<<<<]                   Copy 4x B
+++++                             Set modulo operator to 5
>>+>++++++>++++++>                Adjust BBBB to BCHH
++++++++[-<++++<++++<++++>>>]     Make lower case
++++++[-<+++<+++<+++>>>]          Adjust Bchh to Buzz
>>                                Leave two zeros at the end



-->---          Mark the ending with 254 and 253

END OF SETUP

ALGORITHM START

+[-<+]-         Go backwards to the 255 mark
<

[
 +[->+]-        Go forward to the start position 255 marker
 <<->>          Decrease countdown
 >+             Increase counter
 >[-]           Reset boolean for if we have found a match or not

 ++[-->++]-->   Find next 254 and go one step beyond it

                Loop through all 254s
    +++[---         Make sure that we are not at 253 (end)
     ++[--<++]--    Find last value 254
     --             Change marker to 252 to indicate that we're processing it
     >>             Go to the countdown
     -<             Decrease fizzbuzz countdown
     +>             Increase fizzbuzz counter

     If current marker is NOT zero
         [
             ++++[----<++++]--   Find value 252 and change to 254
             [>]                 Position to a place that is zero to avoid repeat
             ++[-->++]-- <       Find NEXT 254 marker and stop right before it
         ]

     >++
         Check if we are positioned on a 254 already then if skip this
         [--

             We have a match so find start position and mark match

             +[-<+]-  >>         Find 255 marker and go to the boolean
             [-]+                Set boolean to 1 whatever the previous value is
             ++++[---->++++]--   Find value 252 and change to 254
             >[->+<]             Reset the current FizzBuzz countdown
             >>[.>]              Print the text
             ++[-->++]           Go to next 254 change to 256 to break loop
         ]
     -->

     +++ # Detect if we are at the 253 end
     ]
  ---

ALL FIZZBUZZES PROCESSED
Use the boolean to check whether or not to print the number


  +[-<+]-     Go back to the 255 marker
  >>          Go to boolean
  -[          If boolean is zero then print the number
         +>>>
         +[-<+]-    Go back to the 255 marker
         >>          We are positioned after the counter
         Code taken from StackOverflow below for printing a number
>++++++++++<<[->+>-[>+>>]>[+[-<+>]>+>>]<<<<<<]>>[-]>>>++++++++++<[->-[>+>>]>[+[-
<+>]>+>>]<<<<<]>[-]>>[>++++++[-<++++++++>]<.<<+>+>[-]]<[<[->-<]++++++[->++++++++
<]>.[-]]<<++++++[-<++++++++>]<.[-]<<[-<+>]
         End of StackOverflow code

         +[-<+]->>    Locate the 255 marker and goto the boolean
  ]+

  Boolean is always one here so print new line
  [-]<<<.>>>     Print new line

  +[-<+]-    Go back to the 255 marker
  <<         Go to the countdown to find out if we should go another round
]