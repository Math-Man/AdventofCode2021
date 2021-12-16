package Day16;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Day16Old
{
    private static ArrayList<Packet> packets = new ArrayList<>();

    public static void decode()
    {
        try
        {
            File file = new File("inputs/day16.txt");
            Scanner reader = new Scanner(file);

            var binary = hexToBin(reader.nextLine());
            int index = 0;

            binary = "110100101111111000101000";

            while(index < binary.length())
            {
                var version = binaryToInt(binary.substring(index, index+=3));
                var id = binaryToInt(binary.substring(index, index+=3));

                StringBuilder body = new StringBuilder();
                switch (id)
                {
                    case 1: //1
                    case 2: //2
                    case 3: //3
                        break;
                    case 4: //4
                        //Parse body for literal
                        index = resolveLiteralPacket(binary, index, body);
                        break;
                }

                packets.add(new Packet(version, id,  ""+binaryToInt(body.toString())));
            }

        }
        catch(Exception e){e.printStackTrace();}
    }

    private static int resolveLiteralPacket(String binary, int index, StringBuilder body)
    {
        boolean isLastPart = binary.substring(index, index+=1) == "0";
        do
        {
            var part = binary.substring(index, index+=4);
            body.append(part);
            isLastPart = binary.substring(index, index+=1).equals("0");
        }
        while(!isLastPart);
        var part = binary.substring(index, index+=4); //Handle last part
        body.append(part);
        return index;
    }


    private static int binaryToInt(String in)
    {
        return Integer.parseInt(in, 2);
    }

    //Keeps the leading and trailing zeros which is what is needed for this puzzle
    private static String hexToBin(String hex){
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

    private static class Packet
    {
        public int version;
        public int identifier;
        public String value;

        public Packet(int ver, int id, String val)
        {
            version = ver;
            identifier = id;
            value = val;
        }

    }
}
