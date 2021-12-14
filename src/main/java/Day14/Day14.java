package Day14;

import java.io.File;
import java.util.*;

//I had some help for this one, got ideas from other people on the internet such as merging the hashsets and counting the actual end value
public class Day14
{
    static Map<String, String> pairSet = new HashMap<>();

    public static void day14(int simLength)
    {
        try
        {
            File file = new File("inputs/day14.txt");
            Scanner reader = new Scanner(file);
            String template = reader.nextLine();
            Map<String, Long> pairCount = new LinkedHashMap<>();

            for (int i = 0; i < template.length() - 1; i++)
                pairCount.put(template.substring(i, i + 2),  pairCount.getOrDefault(template.substring(i, i + 2), 0L) + 1);

            while (reader.hasNext())
            {
                String[] pair = reader.nextLine().split(" -> ");
                pairSet.put(pair[0], pair[1]);
            }

            for (int i = 0; i < simLength; i++)
            {
                System.out.println(simLength);
                Map<String, Long> newCount = new LinkedHashMap<>();
                for (String key : pairCount.keySet())
                {
                    newCount.merge(key.charAt(0) + pairSet.get(key), pairCount.get(key), Long::sum);
                    newCount.merge(pairSet.get(key) + key.charAt(1), pairCount.get(key), Long::sum);
                }
                pairCount = newCount;
            }

            Map<Character, Long> charCount = new LinkedHashMap<>();
            for (String key : pairCount.keySet())
            {
                charCount.merge(key.charAt(0),
                        pairCount.get(key), Long::sum);
                charCount.merge(key.charAt(1),
                        pairCount.get(key), Long::sum);
            }
            List<Map.Entry<Character, Long>> sortedCharCount = new ArrayList<>(charCount.entrySet());
            sortedCharCount.sort(Map.Entry.comparingByValue());
            System.out.println((sortedCharCount.get(sortedCharCount.size() - 1).getValue() - sortedCharCount.get(0).getValue()) / 2);
        }
        catch(Exception e){e.printStackTrace();}

    }

}
