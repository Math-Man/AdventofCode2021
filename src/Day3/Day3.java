package Day3;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Day3
{
    public static ArrayList<String> data;
    public static final int LENGTH = 12;

    private static int[] resolveCounts()
    {
        int[] counts = new int[LENGTH];
        for(String line : data)
        {
            var lineData = line.toCharArray();
            for(int i = 0; i < lineData.length; i++)
            {
                int cval = Character.getNumericValue(lineData[i]);
                var rs = cval == 0 ? (1) : (-1);
                counts[i] += rs;
            }
        }
        return counts;
    }

    public static int part1()
    {
        int[] counts = resolveCounts();

        String resultGamma = "";
        String resultEpsilon = "";
        for(int i = 0; i < LENGTH; i++)
        {
            resultGamma += counts[i] > 0 ? "0" : "1";
            resultEpsilon += counts[i] > 0 ? "1" : "0";
        }
        var result = Integer.parseInt(resultGamma, 2) * Integer.parseInt(resultEpsilon, 2);
        System.out.println("Gamma:" + resultGamma + " epsilon:" + resultEpsilon + " result: " + result);
        return result;
    }

    //I am not removing comments from this as it shows my mental decline into total insanity
    private static String resolveCountsForPart2(boolean oxygen)
    {
        //I feel like a big dummy
        String rsVal = "";
        for(int hIndex = 0; hIndex < LENGTH; hIndex++)
        {
            ArrayList<Integer> zeros = new ArrayList<Integer>();
            ArrayList<Integer> ones = new ArrayList<Integer>();

            for(int vIndex = 0; vIndex < data.size(); vIndex++)
            {
                System.out.println("vibe check for: " + data.get(vIndex) + " checking: " + rsVal);
                if((!data.get(vIndex).startsWith(rsVal)))
                {
                    System.out.println("vibe check failed");
                    continue;
                }
                int val = Character.getNumericValue(data.get(vIndex).charAt(hIndex));
                if(val == 0)
                    zeros.add(val);
                else
                    ones.add(val);
                System.out.println("Check values: " + zeros.size() + " " + ones.size());
            }
            //Kill check
            if(ones.size() + zeros.size() == 1)
            {
                String finalRsVal = rsVal;
                System.out.println("Kill check success");
                return data.stream().filter(l -> l.startsWith(finalRsVal)).findFirst().orElse(null);
            }

            if(oxygen)
            {
                rsVal += zeros.size() > ones.size() ? "0" : "1";
            }
            else
            {
                rsVal += zeros.size() > ones.size() ? "1" : "0";
            }
            System.out.println("added: filter " + rsVal);
        }
        return rsVal;
    }

    //I really messed this one up lol
    public static void part2()
    {
        String resultOxygen = resolveCountsForPart2(true);
        String resultCO2 = resolveCountsForPart2(false);

        var oxygenVal = Integer.parseInt(resultOxygen, 2);
        var CO2Val = Integer.parseInt(resultCO2, 2);

        var result = oxygenVal * CO2Val;
        System.out.println(oxygenVal + " " + CO2Val + " " + result); //2349 1190 2795310

    }

    public static void readData()
    {
        data = new ArrayList<>();
        try
        {
            File myObj = new File("inputs/day3.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
            {
                String line = myReader.nextLine();
                data.add(line);
            }
            myReader.close();
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}