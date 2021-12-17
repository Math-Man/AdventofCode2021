package Day17;

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


        var mabsMinY = (Math.abs(Math.min(y1, y2)));
        int maxYPos =  (mabsMinY - 1) * (mabsMinY) / 2;
        System.out.println(maxYPos);
    }

    public static void part2()
    {
        //This part is beyond me, there surely is a smart way to set constraints that can be used to produce some quadratic formula but I can't figure it out.
    }

}
