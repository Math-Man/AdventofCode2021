package Day8;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day8
{
    //took me a while to understand this...

    public static int what_part1()
    {
        try
        {
            File file = new File("inputs/day8.txt");
            Scanner reader = new Scanner(file);

            int huh = 0;
            while (reader.hasNextLine())
            {
                huh += Arrays.stream(reader.nextLine().split(" \\| ")[1].split(" "))
                        .filter(n -> !n.equals("|") && (n.length() == 2 || n.length() == 4 || n.length() == 3 || n.length() == 7))
                        .count();
            }

            System.out.println(huh);
            return huh;
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return -1;
    }

    public static int part2()
    {

        /* Indexing of the display
             1111
            7    2
            7    2
             6666
            5    3
            5    3
             4444

        We know that for:
        Number | Segments
          1*        2, 3
          4*        2, 3, 6, 7
          7*     1, 2 ,3
          8*     1, 2, 3, 4, 5, 6, 7


        Number | Segments
          0      1, 2, 3, 4, 5     7
          1*        2, 3
          2      1, 2,    4, 5, 6
          3      1, 2, 3, 4,    6,
          4*        2, 3,       6, 7
          5      1,    3, 4,    6, 7
          6      1,    3, 4, 5, 6, 7
          7*     1, 2 ,3
          8*     1, 2, 3, 4, 5, 6, 7

          Note that we can deduce segments 1, 2, 3, 7 and 6 just by the first 4 givens
          To deduce the 5 and 4 a comparison between number where only unknown is segment 5 and two known differences are segment 7 and segment 3

          Therefore for numbers unique cases are;
            3; length of 5, matching count of 2 with 1
            2; length of 5, NON-MATCHING count of 3 with 3
            6; length of 6, matching count of 1 with 1
            9; length of 6, matching count of 4 with 4
            5; length of 5, matching count of 5 with 6
            0; length of 6, matching count of 4 with 5

        * */
     return -1;
    }




}
