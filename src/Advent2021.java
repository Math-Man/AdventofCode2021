import Day1.Day1;
import Day2.Day2;
import Day3.Day3;
import Day4.Day4;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Advent2021
{
    public static void main(String[] args)
    {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        //Day1.part1();
        //Day2.readData();
        //System.out.println(Day2.part1()); ☠
        //System.out.println(Day2.part2());
        //Day3 day3 = new Day3();
        //Day3.readData();
        //Day3.part1();
        //Day3.part2();
        var day4 = Day4.createDay4Data();
        day4.validateNumbers(false);

    }


}
