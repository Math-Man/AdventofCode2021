package Day11;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

//Took a more literal approach to the whole thing, lots of things can be improved, the flashing can be done recursively
// or the whole thing could be written within a single loop and a recursive function etc
// but I wanted it to look nice and didn't want to kill my weekend by spending too much time on this (hint: I still spent way too much time... 🐙)
public class Day11
{
    public static Board BoardInstance;

    public static void Squid()
    {
        BoardInstance = new Board(1000);
    }

    private static class Board
    {
        private int simLength;
        private Tile[][] tiles;

        public Board(int simLength)
        {
            this.simLength = simLength;
            buildBoard();
            simulate(true);
        }

        public void buildBoard()
        {
            tiles = new Tile[10][10];
            try
            {
                File file = new File("inputs/day11.txt");
                Scanner reader = new Scanner(file);
                int line = 0;
                while (reader.hasNextLine())
                {
                    int ch = 0;
                    for (var t : reader.nextLine().toCharArray())
                    {
                        tiles[line][ch] = new Tile(ch, line, Character.getNumericValue(t));
                        ch++;
                    }
                    line++;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        public void simulate(boolean print)
        {
            int totalFlashCount = 0;
            int allFlashStepIndex = 0;
            int allFlashNaturalStepIndex = 0;
            for(int stepCount = 0; stepCount < simLength; stepCount++)
            {
                var flashCountStep = step();
                if(allFlashStepIndex == 0 && (flashCountStep[0] + flashCountStep[1]) == 100)
                    allFlashStepIndex = stepCount;
                if(allFlashNaturalStepIndex == 0 && (flashCountStep[0] == 100))
                    allFlashNaturalStepIndex = stepCount;

                totalFlashCount += (flashCountStep[0] + flashCountStep[1]);

                //Print the board
                if(print)
                {
                    System.out.println("Step: " + (stepCount+1));
                    for(int y = 0; y < tiles.length; y++)
                    {
                        for(int x = 0; x < tiles[0].length; x++)
                        {
                            System.out.print(tiles[y][x].energy);
                        }
                        System.out.println("");
                    }
                }
            }
            System.out.println("After step " + simLength + " there have been a total of " + totalFlashCount + " flashes.");
            System.out.println("The first time all octopuses flash one reason or another is step : " + (allFlashStepIndex+1));
            System.out.println("The first time all octopuses flash naturally is step : " + (allFlashNaturalStepIndex+1)); //I got confused on the wording so I also found this whatever
        }

        private int[] step()
        {
            final int[] flashes = {0, 0};
            Arrays.stream(tiles).flatMap(Arrays::stream).forEach(tile -> {
                tile.energy++;
                if(tile.energy > 9)
                {
                    flashSurroundingTiles(tile);
                    flashes[0]++;
                    tile.flashed = true;
                }
            });
            System.out.println("Natural flash count: " + flashes[0]);
            var highEnergyTiles = Arrays.stream(tiles).flatMap(Arrays::stream).filter(tile -> !tile.flashed && tile.energy > 9).collect(Collectors.toList());
            while(highEnergyTiles.size() > 0)
            {
                for (Tile tile : highEnergyTiles)
                {
                    flashSurroundingTiles(tile);
                    flashes[1]++;
                    tile.flashed = true;
                }
                highEnergyTiles = Arrays.stream(tiles).flatMap(Arrays::stream).filter(tile -> !tile.flashed && tile.energy > 9).collect(Collectors.toList());
            }
            System.out.println("Resulted flash count: " + flashes[1]);
            System.out.println("Total flash count: " + (flashes[0] + flashes[1]));
            //Reset the tiles
            Arrays.stream(tiles).flatMap(Arrays::stream).filter(tile -> tile.flashed).forEach(tile -> {tile.energy = 0; tile.flashed = false;});

            return flashes;
        }

        private void flashSurroundingTiles(Tile tile)
        {
            for(int y = (tile.y-1); y <= (tile.y+1); y++)
            {
                for(int x = (tile.x-1); x <= (tile.x+1); x++)
                {
                    //Skip the tile if its the current tile of it the coordinates are out of bounds
                    if( x < 0 || x > 9 || y < 0 || y > 9 || (x == tile.x && y == tile.y))
                        continue;
                    var otherTile = tiles[y][x];
                    otherTile.energy++;
                }
            }
        }


    }

    private static class Tile
    {
        public int x;
        public int y;
        public int energy;
        public boolean flashed = false;

        public Tile(int x, int y, int energy)
        {
            this.x = x;
            this.y = y;
            this.energy = energy;
        }

        @Override
        public String toString()
        {
            return "Tile: " + x + "," + y + " energy: " + energy + " flashed: " + flashed;

        }
    }


}
