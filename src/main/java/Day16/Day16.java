package Day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;

//Couldn't get it to work still uploading for completeness sake

public class Day16
{
    String hexInput;
    String binaryInput;

    protected HashMap<Integer, Function<ArrayList<Long>, Long>> ops = new HashMap<>()
    {
        {put(0, longs -> longs.stream().reduce((aLong, aLong2) -> aLong + aLong2).orElse(null));}
        {put(1, longs -> longs.stream().reduce((aLong, aLong2) -> aLong * aLong2).orElse(null));}
        {put(2, longs -> longs.stream().min(Long::compareTo).orElse(null));}
        {put(3, longs -> longs.stream().max(Long::compareTo).orElse(null));}
        //4 is literal
        {put(5, longs -> Long.valueOf(longs.get(0) > longs.get(1) ? 1 : 0));}
        {put(6, longs -> Long.valueOf(longs.get(0) < longs.get(1) ? 1 : 0));}
        {put(7, longs -> Long.valueOf(longs.get(0) == longs.get(1) ? 1 : 0));}
    };

    private final int LITERAL_PACKET = 4;
    private final char LENGTH_TYPE_TOTAL_LENGTH = '0';
    private final char LENGTH_TYPE_NUMBER_PACKETS = '1';


    public void Init() throws FileNotFoundException
    {
        File file = new File("inputs/day16.txt");
        Scanner reader = new Scanner(file);

        hexInput = reader.nextLine();
        binaryInput = hexToBin(hexInput);
    }


    public void Part1()
    {
        var part = ParsePacket(binaryInput, 0);
        System.out.println("Version sum: " + part.index + " " +part.sum  + " " + part.vl);
    }


    public void Part2()
    {
        //System.out.println("Evaluation: " + ParsePacket(binaryInput, out _));
    }

    protected ParseSet ParsePacket(String input, int versionSum)
    {
        int startIndex = 0;
        versionSum = 0;
        ParseSet set = new ParseSet();
        ParsePacket(input, set, 1, true, null);
        return set;
    }


    protected void ParsePacket(String input, ParseSet set, int packetCount, boolean isCount, Function<ArrayList<Long>, Long> operation)
    {

        set.sum = 0;
        int endOfPacket = set.index + packetCount;
        ArrayList<Long> evaluations = new ArrayList<>();

        for(int counter = 0; isCount ? counter < packetCount : set.index < endOfPacket; counter ++)
        {
            int packetVersion = binaryToInt(input.substring(set.index, set.index+3));     //Convert.ToInt32(input.Substring(currIndex, 3), 2);
            int packetTypeID = binaryToInt(input.substring(set.index+3, set.index+6));    //Convert.ToInt32(input.Substring(currIndex + 3, 3), 2);
            set.index += 6; // Every packet consumes at least 6 characters
            if (packetTypeID != LITERAL_PACKET) { // Operator Packet
                char lengthTypeID = input.charAt(set.index);
                int subPacketVersionSum = 0;
                set.index++;
                if (lengthTypeID == LENGTH_TYPE_TOTAL_LENGTH) { // Total Length Specified
                    int totalLength = binaryToInt(input.substring(set.index, set.index+15)); //Convert.ToInt32(input.Substring(currIndex, 15), 2);
                    set.index += 15;

                    ParsePacket(input, set, totalLength, false, ops.get(packetTypeID));
                    //rsRec.sum += subPacketVersionSum;
                    //currIndex = rsRec.index;
                    evaluations.add(set.vl);

                } else { // Packet count specified
                    int subPacketCount = binaryToInt(input.substring(set.index, set.index+11)); // Convert.ToInt32(input.Substring(currIndex, 11), 2);
                    set.index += 11;
                    ParsePacket(input,set, subPacketCount, true, ops.get(packetTypeID));
                    evaluations.add(set.vl);
                }
                set.sum += subPacketVersionSum + packetVersion;
            } else { // Literal Packet
                set.sum += packetVersion;
                int pos = set.index;
                String value = "";
                while(true)
                {
                    value += input.substring(pos + 1, set.index+4);
                    set.index += 5;
                    if(input.charAt(pos) == ('0')) {
                        break;
                    } else {
                        pos += 5;
                    }
                }
                evaluations.add((long) binaryToInt(value)); //Convert.ToUInt64(value, 2));
            }
        }

        if (operation != null) {
            operation.apply(evaluations);
        }
        //return evaluations[0];
    }


    private class ParseSet
    {
        public long vl; //end value
        public int sum; //version sum
        public int index; //index
    }

    private int binaryToInt(String in)
    {
        return Integer.parseInt(in, 2);
    }

    private String hexToBin(String hex){
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }

}
