[
https://codereview.stackexchange.com/q/26648/31562
]

max   == 255
LF    == 10
space == 32
'0'   == 48
'9'   == 57
':'   == 58
Memory: counter
        ':' space LF
        char max&1 cmp1 0 0
        num1 '9'&1 cmp2 0 0
        num2 '9'&1 cmp3 0 0
        num3 '9'&1 cmp4 0 0

+++++ +++
[
    > +++++ ++
    > ++++
    > +

    >
    ++++
    [
        > +++++ +++
        < -
    ]

    >>>>
    > +++++ +
    > +++++ ++
    >>>
    > +++++ +
    > +++++ ++
    >>>
    > +++++ +
    > +++++ ++

    <<<<< <<<<< <<<<< <<<<< -
]
> ++
>> ++
>>
>>>>> ++
>>>>> ++
>>>>> ++

<<<<< <<<<< <<<<< <<<<<

Memory: 0
        58 32  10
        0  256 0 0 0
        48 57  0 0 0
        48 57  0 0 0
        48 57  0 0 0

>>>>> >
+
[
    -

    >>> .
    >>>>> .
    >>>>> .
    <<<<< <<<<< <<<<< <<<
    .
    > .
    >> .
    < .

    Number increasing logic
        >>>>> >>>>> >>>>> >
        +
            Comparer: num1 num2 out 0 0
            [
                -
                >-
                >>>+
                <<<<
            ]
            >>+ set equal flag
            <   if num1 != num2
            [
                >-  clear equal flag
            ]
            >  if num1 == num2
            [
                >
            ]
            >  go to last and put numbers back
            [
                <<<+
                <+
                >>>>-
            ]
            <<<<   reset pointer
        >>
        [
            << ----- -----
            <<<<< +
            >>>>> >> -
        ]
        <<

        <<<<<
            Comparer: num1 num2 out 0 0
            [
                -
                >-
                >>>+
                <<<<
            ]
            >>+ set equal flag
            <   if num1 != num2
            [
                >-  clear equal flag
            ]
            >  if num1 == num2
            [
                >
            ]
            >  go to last and put numbers back
            [
                <<<+
                <+
                >>>>-
            ]
            <<<<   reset pointer
        >>
        [
            << ----- -----
            <<<<< +
            >>>>> >> -
        ]
        <<

    <<<<< <<<<<
    +

        Comparer: num1 num2 out 0 0
        [
            -
            >-
            >>>+
            <<<<
        ]
        >>+ set equal flag
        <   if num1 != num2
        [
            >-  clear equal flag
        ]
        >  if num1 == num2
        [
            >
        ]
        >  go to last and put numbers back
        [
            <<<+
            <+
            >>>>-
        ]
        <<<<   reset pointer

    >>

        Inverter: in/out 0 0
        [
            > +
            < -
        ]
        +
        >
        [
            < -
            > -
        ]
        <
]