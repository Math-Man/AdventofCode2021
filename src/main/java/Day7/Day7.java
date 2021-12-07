package Day7;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Day7
{

    public static void crabs(boolean basicCrabs)
    {
        try
        {
            File file = new File("inputs/day7.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine())
            {
                //Find the median than do stuff from there for the part 1. Used the apache library for median finding due to extreme laziness.
                var numberArray = Arrays.stream(reader.nextLine().split(","))
                        .mapToDouble(Double::parseDouble).toArray();



                int total = 0;
                //So part 1 is pretty easy just get the median, subtract the crab position and then get the total from there on
                if(basicCrabs)
                {
                    Median median = new Median();
                    var eval = median.evaluate(numberArray);
                    System.out.println("Median is: " + eval);
                    for (var crab : numberArray)
                        total += (Math.abs(crab - eval));
                }
                else
                {
                    //However things get whacky on part2.
                    //True to my name as soon as I looked at the weird math pattern# I wrote down the formulation for it
                    //So here it is: ((n * (n + 1)) / 2)
                    //If you plug in in the first few numbers you'll realize this is pretty much how we are going to calculate the total
                    //Instead of simply adding the numbers together

                    Mean mean = new Mean();
                    var eval = mean.evaluate(numberArray);
                    System.out.println("Mean is: " + eval);

                    //We need to round both ways
                    double totalLow = 0;
                    double totalHigh = 0;
                    double totalDefault = 0;

                    //Calculate median both ways, we will figure out the total later
                    double meanLow = Math.floor(eval);
                    double meanHigh = Math.ceil(eval);
                    System.out.println("Mean low is: " + meanLow + " mean high is: " + meanHigh);
                    for (var crab : numberArray)
                    {
                        // calculate n both ways just as we did to the median
                        var nLow = (Math.abs(crab - meanLow));
                        var nHigh = (Math.abs(crab - meanHigh));
                        var n = (Math.abs(crab - eval));

                        totalLow +=  (nLow * (nLow + 1)) * 0.5;
                        totalHigh += (nHigh * (nHigh + 1)) * 0.5;
                        totalDefault += (n * (n + 1)) * 0.5;
                    }
                    System.out.println("Total low is: " + (int)totalLow + ", Total high is: " + (int)totalHigh + ", non-rounded total is: " + (int)totalDefault);

                    //Real result
                    total += Math.min(totalLow, totalHigh);
                }
                System.out.println("Total is: " + total);
            }
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
