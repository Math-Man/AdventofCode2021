package Day20;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class Day20
{

    public static void solve(int steps) throws IOException
    {
        var lines = Files.readAllLines(new File("inputs/Day20.txt").toPath());
        var code = lines.get(0);
        HashSet<Tile> tiles = new HashSet<>();
        var columns = lines.get(2).length();
        var rows = lines.size() - 2;

        for(int y = 0; y < rows; ++y)
        {
            for(int x = 0; x < columns; ++x)
                if(lines.get(y + 2).charAt(x) == '#')
                    tiles.add(new Tile(x, y, 1));
        }
//17172

        for(int step = 0; step < steps; ++step)
        {
            System.out.println(step);
            tiles = solveInner(code, tiles, step);
        }
        System.out.println(tiles.size());
    }

    private static HashSet<Tile> solveInner(String code, HashSet<Tile> tiles, int step)
    {
        int yMin = Integer.MAX_VALUE;
        int xMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;
        int xMax = Integer.MIN_VALUE;

        for(var tile : tiles)
        {
            yMin = Math.min(yMin, tile.y);
            xMin = Math.min(xMin, tile.x);
            yMax = Math.max(yMax, tile.y);
            xMax = Math.max(xMax, tile.x);
        }
        var newTiles = new HashSet<Tile>();

        for(int y = yMin -1 ; y <= yMax +1; ++y)
        {
            for(int x = xMin - 1; x <= xMax + 1; ++x)
            {
                String number = "";
                for(int cy = -1; cy <= 1; ++cy)
                {
                    for(int cx = -1; cx <= 1; ++cx)
                    {
                        int y2 = y + cy;
                        int x2 = x + cx;

                        if(step%2==0)
                            number+= setContains(tiles, x2, y2) ? "1" : "0";//tiles.contains(new Tile(x2, y2, 1)) ? "1" : "0";
                        else
                        {
                            boolean contained = y2 >= yMin && y2 <= yMax && x2 >= xMin && x2 <= xMax;
                            if(contained || code.charAt(0) == '.')
                                number+= setContains(tiles, x2, y2) ? "1" : "0"; // contains(new Tile(x2, y2, 1)) ? "1" : "0";
                            else
                                number+= "1";
                        }
                    }
                }

                var n = binaryToInt(number);
                int next = code.charAt(n) == '#' ? 1 : 0;
                if(next == 1)
                    newTiles.add(new Tile(x, y, 1));
            }
        }
        return newTiles;
    }

    private static boolean setContains(HashSet<Tile> set, int x, int y)
    {
        return set.stream().anyMatch(tile -> tile.x == x && tile.y == y);
    }

    private static int binaryToInt(String in)
    {
        return Integer.parseInt(in, 2);
    }


    private static class Tile
    {
        public int x, y, state;
        public Tile(int x, int y, int s)
        {
            this.x = x;
            this.y = y;
            this.state = s;
        }
    }


}
