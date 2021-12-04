package Day2;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Day2
{
    //oop day
    public static ArrayList<Day2Data> data;
    public static int x;
    public static int y;
    public static int aim;

    public static void readData()
    {
        data = new ArrayList<>();
        try
        {
            File myObj = new File("inputs/day2.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
            {
                String[] line = myReader.nextLine().split(" ");
                var nd = new Day2Data(line[0], line[1]);
                data.add(nd);
            }
            myReader.close();
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int part1()
    {
        x = 0;
        y = 0;
        data.forEach(d ->
        {
            switch (d.direction) { case down: y+=d.amount;break; case up: y-=d.amount;break; case forward: x+=d.amount;break;}
        });
        System.out.println(x + " " + y);
        return x*y;
    }

    public static int part2()
    {
        x = 0;
        y = 0;
        data.forEach(d ->
        {
            switch (d.direction)
            {
                case down:
                    aim+=d.amount;
                    break;
                case up:
                    aim-=d.amount;
                    break;
                case forward:
                    x+=d.amount;
                    y+=(aim * d.amount);
                    break;
            }
        });
        System.out.println(x + " " + y + " " + aim);
        return x*y;
    }

    public enum Direction
    {
        forward, up, down;
    }
    public static class Day2Data
    {
        public Direction direction;
        public int amount;
        public Day2Data(String dir, String strAmount)
        {
            direction = Direction.valueOf(dir);
            amount = Integer.parseInt(strAmount);
        }

        @Override
        public String toString()
        {
            return direction + " " + amount;
        }
    }
}