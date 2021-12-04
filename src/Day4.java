import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day4
{
    private ArrayList<BingoCard> cards;
    private ArrayList<BingoCard> winningCards;
    public int[] numbers;

    public Day4()
    {
        cards = new ArrayList<>();
        winningCards = new ArrayList<BingoCard>();
    }

    public void createCard(String[] cardLines)
    {
        cards.add(new BingoCard(cardLines));
    }

    public int validateNumbers(boolean part1)
    {
        for(int i = 0; i < numbers.length; i++)
        {
            validateNumber(numbers[i]);
            if(i > 5)
            {
                if(part1)
                {
                    var total = getUnmarkedTotalOfWinningCard();
                    if(total != 0)
                    {
                        System.out.println("total: " + total + " mult: " + numbers[i] + " result " + total * numbers[i]);
                        return total * numbers[i];
                    }
                }
                else //I regret everything, I regret everything I have ever done
                {
                    var total = getUnmarkedTotalOfWinningCard();
                    var winningCard = getWinningCard();
                    while(winningCard != null)
                    {
                        if(total != 0)
                        {
                            winningCard.total = total;
                            winningCard.localeIndex = numbers[i];
                            winningCards.add(winningCard);
                            cards.remove(winningCard);
                            total = getUnmarkedTotalOfWinningCard();
                            winningCard = getWinningCard();
                        }
                        else
                            break;
                    }

                }
            }
        }

        //There was no final card, so just grab the one added last to the winningCards List
        if(!part1)
        {
            var last = winningCards.get(winningCards.size() - 1);
            System.out.println("total: " + last.total + " mult: " + last.localeIndex + " result " + last.total * last.localeIndex);
            return last.total *last.localeIndex;
        }

        return -1;
    }


    public void validateNumber(int number)
    {
        for(BingoCard card : cards)
        {
            card.tryMarkValid(number);
        }
    }


    public int getUnmarkedTotalOfWinningCard()
    {
        for(BingoCard card : cards)
        {
            if(card.checkWin())
            {
                return card.getUnmarkedValueTotal();
            }
        }
        //No winning cards?
        return 0;
    }

    public BingoCard getWinningCard()
    {
        for(BingoCard card : cards)
        {
            if(card.checkWin())
            {
                return card;
            }
        }
        //No winning cards?
        return null;
    }


    private class BingoCard
    {
        private final int DIMENSIONS = 5;

        private Tile[][] board;

        public boolean hasWon;

        public int localeIndex;
        public int total;

        public BingoCard(String[] lines)
        {
            board = new Tile[DIMENSIONS][DIMENSIONS];
            buildBoard(lines);
        }

        private void buildBoard(String[] lines)
        {
            for(int i = 0 ; i < lines.length; i++)
            {
                String[] words = lines[i].split(" ");
                for(int j = 0 ; j < words.length; j++)
                {
                    board[i][j] = (new Tile(Integer.parseInt(words[j])));
                }
            }
        }

        public void tryMarkValid(int value)
        {
            for(int i = 0; i < DIMENSIONS; i++)
            {
                for(int j = 0; j < DIMENSIONS; j++)
                {
                    if(board[j][i].value == value)
                    {
                        board[j][i].isValid = true;
                        return;
                    }
                }
            }

        }

        public int getUnmarkedValueTotal()
        {
            int total = 0;
            for(int i = 0; i < DIMENSIONS; i++)
            {
                for(int j = 0; j < DIMENSIONS; j++)
                {
                    if(!board[j][i].isValid)
                    {
                        total += board[j][i].value;
                    }
                }
            }
            return total;
        }

        public boolean checkWin()
        {
            //Rows
            boolean found = false;
            for(int i = 0; i < DIMENSIONS; i++)
            {
                found = true;
                for(int j = 0; j < DIMENSIONS; j++)
                {
                    if(!board[i][j].isValid)
                    {
                        found = false;
                        break;
                    }
                }
                if(found)
                    break;
            }
            if(found)
            {
                hasWon = true;
                return true;
            }


            //Columns
            found = true;
            for(int i = 0; i < DIMENSIONS; i++)
            {
                found = true;
                for(int j = 0; j < DIMENSIONS; j++)
                {
                    if(!board[j][i].isValid)
                    {
                        found = false;
                        break;
                    }
                }
                if(found)
                    break;
            }

            if(found)
                hasWon = true;

            return found;
        }

        @Override
        public String toString()
        {
            StringBuilder rs = new StringBuilder();
            for(int i = 0; i < DIMENSIONS; i++)
            {
                for(int j = 0; j < DIMENSIONS; j++)
                {
                    rs.append(board[j][i].toString() + " ");
                }
                rs.append("\n");
            }
            return rs.toString();
        }

        private class Tile
        {
            public Tile(int value)
            {
                this.value = value;
            }

            public int value;
            public boolean isValid;


            @Override
            public String toString()
            {
                return value + "" + (isValid ? "*" : "");
            }

        }

    }

}
