package Day5;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day5
{
    private static final int MAX_BOARD_SIZE = 1000;
    private static int[][] Board = new int[MAX_BOARD_SIZE][MAX_BOARD_SIZE];

    public static long DO_THE_THING_LOL(boolean isPart1) //4745 and 18442
    {
        try
        {
            File file = new File("inputs/day5.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine())
            {
                String[] line = reader.nextLine().replaceAll("-> ", "").replaceAll( " ", ",").split(",");
                int x1 = Integer.parseInt(line[0]); int y1 = Integer.parseInt(line[1]);
                int x2 = Integer.parseInt(line[2]); int y2 = Integer.parseInt(line[3]);

                if(isHorizontalOrVertical(x1,y1,x2,y2))
                    incrementHorizontalOrVertical(x1,y1,x2,y2);
                else if(!isPart1)
                    incrementDiagonal(x1,y1,x2,y2);
            }
            reader.close();
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return collectBoardEntriesHigherThanParam(1);
    }

    private static boolean isHorizontalOrVertical(int x1, int y1, int x2, int y2)
    {
        return x1 == x2 || y1 == y2;
    }

    private static void incrementHorizontalOrVertical(int x1, int y1, int x2, int y2)
    {
        for(int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++)
            for(int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++)
                Board[x][y]++;
    }

    private static void incrementDiagonal(int x1, int y1, int x2, int y2)
    {
        while(x1 != x2 && y1 != y2)
        {
            Board[x1][y1]++;
            x1 += x1 < x2 ? 1 : -1;
            y1 += y1 < y2 ? 1 : -1;
        }
        Board[x1][y1]++;
    }

    private static long collectBoardEntriesHigherThanParam(int number)
    {
        printBoard();
        return Arrays
                .stream(Board)
                .flatMapToInt(Arrays::stream)
                .filter(tile -> tile > number).count();
    }

    //Why not
    private static void printBoard()
    {
        for(int y = 0; y < MAX_BOARD_SIZE; y++)
        {
            for(int x = 0; x < MAX_BOARD_SIZE; x++)
            {
                System.out.print(Board[x][y] == 0 ? "." : Board[x][y]);
            }
            System.out.println("");
        }
    }

}
