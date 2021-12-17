package Day17;

import java.util.ArrayList;

public class Day17
{


    public static void part1()
    {
        String input = "target area: x=32..65, y=-225..-177";
        String[] parts = input.split(":")[1].split(",");

        String xRangeS = parts[0];
        String yRangeS = parts[1];
        int x1 = Integer.parseInt(xRangeS.split("=")[1].split("\\..")[0]);
        int x2 = Integer.parseInt(xRangeS.split("=")[1].split("\\..")[1]);
        int y1 = Integer.parseInt(yRangeS.split("=")[1].split("\\..")[0]);
        int y2 = Integer.parseInt(yRangeS.split("=")[1].split("\\..")[1]);

        //Forgot to mention this is a triangle, just draw some lines on a paper math will make sense
        var mabsMinY = (Math.abs(Math.min(y1, y2)));
        int maxYPos =  (mabsMinY - 1) * (mabsMinY) / 2;
        System.out.println(maxYPos);
    }

    public static void part2()
    {
        //I've decided to brute force it, there is no need for logic where we are going.
        String input = "target area: x=32..65, y=-225..-177";
        String[] parts = input.split(":")[1].split(",");

        String xRangeS = parts[0];
        String yRangeS = parts[1];
        int x1 = Integer.parseInt(xRangeS.split("=")[1].split("\\..")[0]);
        int x2 = Integer.parseInt(xRangeS.split("=")[1].split("\\..")[1]);
        int y1 = Integer.parseInt(yRangeS.split("=")[1].split("\\..")[0]);
        int y2 = Integer.parseInt(yRangeS.split("=")[1].split("\\..")[1]);

        ArrayList<String> starts = new ArrayList<>();

        for (int j = 0; j <= Math.max(x1, x2); j++)
        {
            for (int i = Math.min(y1, y2); i <= 10000; i++)
            {
                int velX = j; int velY = i;
                int posX = 0;  int posY = 0;
                int posYmax = -999999;

                while(true)
                {
                    posX += velX;
                    posY += velY;
                    velY--;

                    if (velX > 0)
                        velX--;
                    else if (velX < 0)
                        velX++;

                    if (posY >= posYmax)
                        posYmax = posY;

                    if (posX >= x1 && posX <= x2 && posY >= y1 && posY <= y2)
                    {
                        starts.add(j + ", " + i);
                        break;
                    }
                    if (posX > Math.max(x2, x1) || posY < Math.min(y2, y1))
                        break;
                }
            }
        }
        System.out.println(starts.size());
    }
}
