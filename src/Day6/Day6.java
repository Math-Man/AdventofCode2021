package Day6;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day6
{
    private static final int SIM_LENGTH = 256;

    public static void DOTHETHINGBUTSMART()
    {
        long[] fishes = new long[9];
        try
        {
            File file = new File("inputs/day6.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine())
            {
                Arrays.stream(reader.nextLine().split(","))
                        .mapToInt(Integer::parseInt).forEach(fish -> fishes[fish]++);
            }
            for(int day = 0; day < SIM_LENGTH; day++)
            {
                long newGenFish = fishes[0];
                fishes[0] = fishes[1];
                fishes[1] = fishes[2];
                fishes[2] = fishes[3];
                fishes[3] = fishes[4];
                fishes[4] = fishes[5];
                fishes[5] = fishes[6];
                fishes[6] = (fishes[7] + newGenFish);
                fishes[7] = fishes[8];
                fishes[8] = newGenFish;
                System.out.println("Fish count for day " + (day+1) + ": " + Arrays.stream(fishes).sum());
            }
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Extremely dumb and stupid brute force method. Just to flex my stream knowledge of course, not that I'd even try this for real 😶.
    public static void DOTHETHING()
    {
        ArrayList<Byte> fishes = null;
        try
        {
            File file = new File("inputs/day6.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine())
            {
                fishes = (ArrayList<Byte>) Arrays.stream(reader.nextLine().split(","))
                                .map(Byte::parseByte).collect(Collectors.toList());
            }
            System.out.println(fishes.size());
            System.out.println("InitialState: " + fishes.stream().map(e -> e.toString()+",").reduce("", String::concat));
            int day = 0;
            while(day < SIM_LENGTH)
            {
                ArrayList<Byte> newFish = new ArrayList<Byte>();
                for(int i = 0; i < fishes.size(); i++)
                {
                    int fish = fishes.get(i);
                    if(fish == 0)
                    {
                        fish = 7;
                        newFish.add((byte) (8));
                    }
                    fishes.set(i , (byte) (fish-1));
                }
                fishes.addAll(newFish);
                System.out.println("After  " + (day+1) + " count: " + fishes.size() + " days: " + fishes.stream().map(e -> e.toString()+",").reduce("", String::concat));
                day++;
            }
            reader.close();
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
