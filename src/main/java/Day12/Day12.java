package Day12;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//Disclaimer for part2 (when MAX_VISIT_COUNT_FOR_SMALL is greater than 1)
//I misread the whole thing and instead made every small cave visitable multiple times
//To fix my error I just filtered out the visited nodes before recursively calling the method for them
//Its slow, inefficient, sad and stupid and I feel stupid
//goodbye

//Once again I am doing OOP 🗺
public class Day12
{
    private static ArrayList<Node> nodes;
    private static final ArrayList<ArrayList<Node>> allPaths = new ArrayList<>();
    private static final Integer MAX_VISIT_COUNT_FOR_SMALL = 2;
    private static final boolean PRINT_PATHS = true;

    public static void buildNodes()
    {
        nodes = new ArrayList<>();
        try
        {
            File file = new File("inputs/day12.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine())
            {
                String[] connection = reader.nextLine().split("-");
                handleNewConnection(connection);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("read done");
    }

    private static void handleNewConnection(String[] connection)
    {
        var node1 = tryGetNode(connection[0], false);
        var node2 = tryGetNode(connection[1], false);

        //Add connection to nodes
        node1.addConnection(node2);
        node2.addConnection(node1);
    }

    //Returns the node with identifier if it exists, otherwise adds a new node with the given identifier
    private static Node tryGetNode(String nodeIdentifier, boolean noAdd)
    {
        var node =  nodes.stream().filter(n -> n.identifier.equals(nodeIdentifier)).findFirst().orElse(null);
        if(node != null)  //Contains a node that matches this identifier
            return node;
        else if(!noAdd)//Doesn't contain the node that matches this identifier
        {
            Node n = new Node(nodeIdentifier, Character.isLowerCase(nodeIdentifier.toCharArray()[0]));
            nodes.add(n);
            return n;
        }
        return null;
    }
    public static void pathFindPart1()
    {
        HashMap<Node, Integer> visitedSmallCaves = new HashMap<Node, Integer>();
        ArrayList<Node> localList = new ArrayList<Node>();

        var start = tryGetNode("start", true);
        localList.add(start);
        pathFindPart1Inner(tryGetNode("start", true),
                tryGetNode("end", true),
                visitedSmallCaves,
                localList);
        System.out.println("Total number of possible paths: " + allPaths.size());
    }

    //Recursive part
    private static void pathFindPart1Inner(Node current, Node destination, HashMap<Node, Integer>  visitedSmall, ArrayList<Node> localList)
    {
        if(current.identifier.equals(destination.identifier))
        {
            //Found a path, print it or add it to a list idk
            if(PRINT_PATHS)
                System.out.println(localList);
            allPaths.add(localList);
            return;
        }

        //If the current node is a small node mark it as visited
        if(current.isSmall)
        {
            var currentNodeVisitCount = visitedSmall.get(current);

            //Special case for start
            if(current.identifier.equals("start"))
            {
                visitedSmall.put(current, 1);
            }
            else
            {
                if(currentNodeVisitCount == null)
                    visitedSmall.put(current, 1);
                else
                    visitedSmall.put(current, (currentNodeVisitCount+1));
            }
        }

        //So this is where I originally messed up when doing the part2
        //This thing filters out the small caves if a small cave was visited more than twice already
        // and if we are trying to visit this cave more than twice 😂🔫
        var badCheck = (visitedSmall.get(current) != null && (visitedSmall.get(current) >= MAX_VISIT_COUNT_FOR_SMALL &&
                visitedSmall.values().stream().filter(v -> v >= MAX_VISIT_COUNT_FOR_SMALL).count() > 1));

        if(!badCheck)
        {
            //Now the magical part
            //Recur for all connections of the current node we are looking at
            for(var node : current.connections)
            {
                var otherNode = tryGetNode(node, true);

                var ve = visitedSmall.values().stream().filter(v -> v > MAX_VISIT_COUNT_FOR_SMALL).count() < 1;

                //if its small and visited make sure we skip it
                if( !otherNode.isSmall ||
                        (otherNode.isSmall && (!otherNode.identifier.equals("start")) &&
                                (visitedSmall.get(otherNode) == null || (visitedSmall.get(otherNode) < MAX_VISIT_COUNT_FOR_SMALL))))
                {
                    //Add the node to the local path
                    localList.add(otherNode);

                    //recall
                    pathFindPart1Inner(otherNode, destination, visitedSmall, localList);

                    //Finally remove the current node from the local paths list
                    //we also need to remove the last occurance of it (logically)
                    localList.remove(localList.lastIndexOf(otherNode));
                    //Instead of doing this:
                    //localList.remove(otherNode);
                }
            }
        }

        //Remove the current node from visited list since we are trying to find all of the paths
        if(current.isSmall)
        {
            //So instead of removing the visited node, just decrease its visit count
            var currentSmallVisit = visitedSmall.get(current);

            if(!current.identifier.equals("start"))
                visitedSmall.put(current, (currentSmallVisit-1));
        }
    }

    public static class Node
    {
        private String identifier;
        private ArrayList<String> connections;
        private boolean isSmall;

        public Node(String identifier, boolean isSmall)
        {
            connections = new ArrayList<>();
            this.identifier = identifier;
            this.isSmall = isSmall;
        }

        public void addConnection(Node other)
        {
            connections.add(other.identifier);
        }

        @Override
        public String toString()
        {
            return identifier;
        }
    }
}
