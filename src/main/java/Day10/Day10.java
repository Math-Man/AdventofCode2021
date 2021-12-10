package Day10;

import java.io.File;
import java.util.*;

public class Day10
{
    //Could golf this down by not using a switch but this looks nice so I'll keep it
    public static void part1(boolean alsoDoPart2)
    {
        try
        {
            Stack<Character> linestack = null;
            File file = new File("inputs/day10.txt");
            Scanner reader = new Scanner(file);
            var totalpart1 = 0;
            var part2Totals = new ArrayList<Long>();
            while (reader.hasNextLine())
            {
                boolean badLine = false;
                linestack = new Stack<>();
                for(char c : reader.nextLine().toCharArray())
                {
                    switch (c)
                    {
                        case '(':
                        case '[':
                        case '{':
                        case '<':
                            linestack.add(c);
                            break;
                        case ')':
                            if(linestack.peek() == '(')
                                linestack.pop();
                            else
                                {badLine = true; linestack.pop(); totalpart1 += 3; break;};
                            break;
                        case ']':
                            if(linestack.peek() == '[')
                                linestack.pop();
                            else
                            {badLine = true; linestack.pop(); totalpart1 += 57; break;}
                            break;
                        case '}':
                            if(linestack.peek() == '{')
                                linestack.pop();
                            else
                            {badLine = true; linestack.pop(); totalpart1 += 1197; break;}
                            break;
                        case '>':
                            if(linestack.peek() == '<')
                                linestack.pop();
                            else
                            {badLine = true; linestack.pop(); totalpart1 += 25137; break;}
                            break;
                    }
                }
                if(alsoDoPart2 && !badLine)
                {
                    System.out.println("------PART2-----");
                    long lineTotal = 0;
                    while(!linestack.empty())
                    {
                        var c = linestack.pop();
                        System.out.println(c);
                        switch (c)
                        {
                            case '(':
                                lineTotal = (lineTotal * 5) + 1;
                                break;
                            case '[':
                                lineTotal = (lineTotal * 5) + 2;
                                break;
                            case '{':
                                lineTotal = (lineTotal * 5) + 3;
                                break;
                            case '<':
                                lineTotal = (lineTotal * 5) + 4;
                                break;
                        }
                    }
                    System.out.println("Part 2 line total: " + lineTotal);
                    part2Totals.add(lineTotal);
                    System.out.println("------PART2 end-----");
                }
            }
            System.out.println("PART1:"+totalpart1);
            Collections.sort(part2Totals);
            System.out.println("PART2:"+ part2Totals.get(part2Totals.size()/2));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
