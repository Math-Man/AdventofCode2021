package Day13;
import java.io.File;
import java.util.Scanner;

//I know this looks like alot of code but its mostly repetitive for loops.
public class Day13
{
    private final static int PAPER_SIZE = 2500; //This is arbitrary, size doesnt break math.
    public static boolean[][] pointsPaper = new boolean[PAPER_SIZE][PAPER_SIZE];
    public static void symmetry(boolean part1)
    {
        //Fold essentially carries all values above the fold coordinate and adds the difference between fold coordinate multiplied by two
        //This makes it possible to just count true values for coordinates that are higher than the last fold coordinate
        int lowestXFold = PAPER_SIZE;
        int lowestYFold = PAPER_SIZE;

        try
        {
            File file = new File("inputs/day13.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine())
            {
                var nextLine = reader.nextLine();
                if(nextLine.contains("fold"))
                {
                    var foldLine = nextLine.split(" ");
                    var fold = foldLine[2].split("=");
                    var foldCord = Integer.parseInt(fold[1]);

                    if(fold[0].equals("x"))
                    {
                        //Todo: convert these to stream maybe idk?
                        for(int y = 0; y < lowestYFold; y++)
                        {
                            for(int x = 0; x < lowestXFold; x++)
                            {
                                if(x > foldCord && pointsPaper[y][x])
                                {
                                    pointsPaper[y][((2*foldCord-x))] = true;
                                    //pointsPaper[y][x - (2*(x - foldCord))] = true;
                                }
                            }
                        }

                        lowestXFold = Math.min(lowestXFold, foldCord);
                    }
                    else if(fold[0].equals("y"))
                    {
                        //Todo: convert these to stream maybe idk?
                        for(int y = 0; y < lowestYFold; y++)
                        {
                            for(int x = 0; x < lowestXFold; x++)
                            {
                                if(y > foldCord && pointsPaper[y][x])
                                {
                                    pointsPaper[((2*foldCord-y))][x] = true;
                                    //pointsPaper[y - (2*(y - foldCord))][x] = true;
                                }
                            }
                        }
                        lowestYFold = Math.min(lowestYFold, foldCord);
                    }

                    if(part1)
                        break;
                }
                else if(nextLine.contains(","))
                {
                    String[] point = nextLine.split(",");
                    pointsPaper[Integer.parseInt(point[1])][Integer.parseInt(point[0])] = true;
                }
            }


            int afterFoldTotal = 0;
            //Todo: convert these to stream maybe idk?
            for(int y = 0; y < lowestYFold; y++)
            {
                for(int x = 0; x < lowestXFold; x++)
                {
                    if(pointsPaper[y][x])
                    {
                        System.out.print("#");
                        afterFoldTotal++;
                    }
                    else
                    {
                        System.out.print(".");
                    }
                }
                System.out.print("\n");
            }

            System.out.println(afterFoldTotal);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
