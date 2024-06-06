[
https://codereview.stackexchange.com/q/194538/31562
]

This program is a JSON formatter
It takes a valid(!) JSON input and outputs formatted JSON


Memory layout used:
0   input
1   input copy
2   switch flag
3   input copy for switch
4   indent
5   indent copy
6   indent copy
Zero separated strings
7   zero
8   placeholder
?   zero
?   JSON specific chars
?   zero
?   "while inside string" memory


Zero separated strings

Filling placeholder "  " (two spaces)
>>>>>>>
>  >++[-<++++++++++++++++>]<
>  >++[-<++++++++++++++++>]<
>zero

Filling JSON specific chars after placeholder
>  0a  \n              ++++++++++
>  20  space           >[-]++[-<++++++++++++++++>]<
>zero

Back to cell 0
<[<]<[<]<<<<<<<


Initial input
,

while input [

    Input in cell 0 already

    Zeroing memory in cells 1 2 3
    >[-]>[-]>[-]<<<

    Copying input to cells 1 3
    [-
        >+<
        >>>+<<<
    ]

    switch flag = on
    >>+

    >


    The Switch

    switch cell 3
    \t  ---------[
    \n           -[
    \r            ---[
    space            -------------------[
    "                                   --[
    comma                                 ----------[
    :                                               --------------[
    ( square bracket                                              ---------------------------------[
    ) square bracket                                                                               --[
    {                                                                                                ------------------------------[
    }                                                                                                                              --

    default [[-]<-
        in each switch case <.> outputs current char
    >]

    case '}' <[-

        Newline              >>>>>>[>]>.
        Back to cell 2       [<]<[<]<<<<<
        Decrementing indent  >>-
        Copying indent       [->+>+<<]
        Placeholders         >>[->>[.>]<[<]<]
        Copying indent back  <[-<+>]
        Back to cell 2       <<<
        Closing brace        <.>
    ]>]

    case '{' <[-

        Opening brace        <.>
        Newline              >>>>>>[>]>.
        Back to cell 2       [<]<[<]<<<<<
        Incrementing indent  >>+
        Copying indent       [->+>+<<]
        Placeholders         >>[->>[.>]<[<]<]
        Copying indent back  <[-<+>]
        Back to cell 2       <<<
    ]>]

    case closing square bracket <[-

        Newline              >>>>>>[>]>.
        Back to cell 2       [<]<[<]<<<<<
        Decrementing indent  >>-
        Copying indent       [->+>+<<]
        Placeholder          >>[->>[.>]<[<]<]
        Copying indent back  <[-<+>]
        Back to cell 2       <<<
        Closing bracket      <.>
    ]>]

    case opening square bracket <[-

        Opening bracket      <.>
        Newline              >>>>>>[>]>.
        Back to cell 2       [<]<[<]<<<<<
        Incrementing indent  >>+
        Copying indent       [->+>+<<]
        Placeholders         >>[->>[.>]<[<]<]
        Copying indent back  <[-<+>]
        Back to cell 2       <<<
    ]>]

    case ':' <[-

        Colon                <.>
        Space                >>>>>>[>]>>.
        Back to cell 2       [<]<[<]<<<<<
    ]>]

    case comma <[-

        Comma                <.>
        Newline              >>>>>>[>]>.
        Back to cell 2       [<]<[<]<<<<<
        Copying indent       >>[->+>+<<]
        Placeholder          >>[->>[.>]<[<]<]
        Copying indent back  <[-<+>]
        Back to cell 2       <<<
    ]>]

    case '"' <[-

        Quotation mark       <.>
        Going to string memory after JSON chars
                             >>>>>>[>]>[>]>

        Memory layout:
        0   string loop flag
        1   switch flag
        2   string input
        3   escape flag
        4   temp

        Zeroing memory
        [-]>[-]>[-]>[-]>[-]<<<<

        string loop flag = on
        +

        while string loop flag [

            Echo string char >>,.

            if escape flag >>[-]+<[

                escape flag = off
                [-]

                >-<
            ]
            else >[-

                switch cell 2 <<<+>
                "  ----------------------------------[
                \                                    ----------------------------------------------------------

                default [<->[-]]

                case '\' <[-

                    escape flag = on
                    >>+<<
                ]>]

                case '"' <[-

                    Unescaped quote ends string
                    string loop flag = off
                    <->
                ]>
                >>

            ]<<<<
        ]

        Back to switch cell
        <<[<]<[<]<<<<<
    ]>]

    Skipping original formatting
    case space <[-]>]
    case '\r'  <[-]>]
    case '\n'  <[-]>]
    case '\t'  <[-]>

    Back to cell 0
    <<<

    Next char
    ,
]