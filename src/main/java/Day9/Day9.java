package Day9;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CollectionCertStoreParameters;
import java.util.*;
import java.util.stream.Collectors;

public class Day9
{
    public static int LINE_COUNT = 100;
    public static int LINE_LENGTH = 100;
    public static void part1()
    {
        try
        {
            int[][] board = new int[LINE_COUNT][LINE_LENGTH];

            File file = new File("inputs/day9.txt");
            Scanner reader = new Scanner(file);

            int lineIndex = 0;
            while (reader.hasNextLine())
            {
                int x = 0;
                for(var t : reader.nextLine().toCharArray())
                {
                    var number = Character.getNumericValue(t);
                    board[lineIndex][x] = number;
                    x++;
                }
                x = 0;
                lineIndex++;
            }

            int lowTotal = 0;
            for(int y = 0; y < LINE_COUNT; y++)
            {
                for(int x = 0; x < LINE_LENGTH; x++)
                {
                    //I know I could use a single bool and &= the values but this is easier on the eyes
                    var current = board[y][x];
                    boolean left = ( x > 0 && current < board[y][x-1]) || (x == 0);
                    boolean right = (x < (LINE_LENGTH-1) && current < board[y][x+1] || x == (LINE_LENGTH-1));
                    boolean top = (y > 0 && current < board[y-1][x]) || y == 0;
                    boolean bot = (y < (LINE_COUNT-1) && current < board[y+1][x]) || y == (LINE_COUNT-1);
                    System.out.println( x + " " + y + " " +  (left && right && top && bot));
                    if(left && right && top && bot)
                        lowTotal+= (current+1);
                }
            }
            System.out.println(lowTotal);
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    //This part doesn't really work, I messed up the code while tinkering with it still uploading it for clarity
    public static void part2()
    {
        try
        {

            int[][] board = new int[LINE_COUNT][LINE_LENGTH];

            File file = new File("inputs/day9.txt");
            Scanner reader = new Scanner(file);

            int lineIndex = 0;
            while (reader.hasNextLine())
            {
                int x = 0;
                for(var t : reader.nextLine().toCharArray())
                {
                    var number = Character.getNumericValue(t);
                    board[lineIndex][x] = number;
                    x++;
                }
                x = 0;
                lineIndex++;
            }

            ArrayList<Integer> basinSizes = new ArrayList<Integer>();
            for(int y = 0; y < LINE_COUNT; y++)
            {
                for(int x = 0; x < LINE_LENGTH; x++)
                {
                    int[][] cloneBoard = board.clone();
                    for (int i = 0; i < cloneBoard.length; i++) {
                        cloneBoard[i] = cloneBoard[i].clone();
                    }

                    if (cloneBoard[y][x] < neighbour(cloneBoard, x, y)) {
                        basinSizes.add(basinSize(cloneBoard, x, y, 1));
                    }

                    //var size = basinSize(cloneBoard, x ,y, 1);
                    //basinSizes.add(size);
                   // System.out.print(size +" ");
                }
               // System.out.println("");
            }

            //I hate everything
            basinSizes.sort(Collections.reverseOrder());
            System.out.println(basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2));
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static int neighbour(int[][] board, int x, int y) {
        int lowest = 999;
        lowest = Math.min(lowest, onBorder(board, x - 1, y));
        lowest = Math.min(lowest, onBorder(board, x + 1, y));
        lowest = Math.min(lowest, onBorder(board, x, y - 1));
        lowest = Math.min(lowest, onBorder(board, x, y + 1));
        return lowest;
    }

    private static  int onBorder(int[][] board, int x, int y) {
        return (x < 0 ||
                x >= board[0].length ||
                y < 0 ||
                y >= board.length) ? 999 : board[y][x];
    }

    private static int basinSize(int[][] board, int x, int y, int size)
    {
        var current = board[y][x];
        boolean left = ( x > 0 && (current+1) == board[y][x-1]) && board[y][x-1] < 9;
        if(left)
            size = basinSize(board, x-1, y, (size+1));
        boolean right = (x < (LINE_LENGTH-1) && (current+1) == board[y][x+1] ) && board[y][x+1] < 9;
        if(right)
            size = basinSize(board, x+1, y, (size+1));
        boolean top = (y > 0 && (current+1) == board[y-1][x]) &&board[y-1][x] < 9;
        if(top)
            size = basinSize(board, x, y-1, (size+1));
        boolean bot = (y < (LINE_COUNT-1) && (current+1) == board[y+1][x]) &&  board[y+1][x] < 9;
        if(bot)
            size = basinSize(board, x, y+1, (size+1));


        //Mark visited tile by adding to it
        board[y][x] += 1000;

        return size;
    }


}