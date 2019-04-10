package Computation;

import Interfaces.ShortestPathSolver;
import Utils.Path;
import Utils.Position;
import Utils.Terrain;
import java.util.*;

public class AStarShortestPathSolver implements ShortestPathSolver{

    @Override
    public Path calculateShortestPath(Terrain terrain) {

        Graph graph = new Graph();
        Node[][] nodes = new Node[terrain.getSizeX()][terrain.getSizeY()];
        Node start = new Node(0, 0), end = new Node(0, 0);

        // Build Graph
        for(int i = 0; i < terrain.getSizeX(); i++)
        {
            for(int j = 0; j < terrain.getSizeY(); j++) {

                nodes[i][j] = new Node(i, j);
                graph.addNode(nodes[i][j]);
                // Get starting node
                if(i == terrain.getStart().getKey() && j == terrain.getStart().getValue()){
                    start = nodes[i][j];
                }

                // Get last node
                if(i == terrain.getEnd().getKey() && j == terrain.getEnd().getValue()){
                    end = nodes[i][j];
                }

            }
        }

        for(int i = 0; i < terrain.getSizeX(); i++)
        {
            for(int j = 0; j < terrain.getSizeY(); j++) {

                // Add left node
                if(i != 0)
                    nodes[i][j].addDestination(nodes[i-1][j], (int)terrain.getGrid()[i-1][j]);

                if(j != 0)
                    nodes[i][j].addDestination(nodes[i][j-1], (int)terrain.getGrid()[i][j-1]);

                if(i != terrain.getSizeX()-1)
                    nodes[i][j].addDestination(nodes[i+1][j], (int)terrain.getGrid()[i+1][j]);

                if(j != terrain.getSizeY()-1)
                    nodes[i][j].addDestination(nodes[i][j+1], (int)terrain.getGrid()[i][j+1]);
            }
        }

        Graph solved = calculateShortestPathFromSource(graph, start);

        List<Position> soluce = new ArrayList<>();
        for (Node n : end.getShortestPath()) {
            soluce.add(new Position(n.x, n.y));
        }

        return new Path(soluce);
    }



    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {

        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeigh = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static void CalculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    public class Graph {

        private Set<Node> nodes = new HashSet<>();

        public void addNode(Node nodeA) {
            nodes.add(nodeA);
        }

        public Set<Node> getNodes() {
            return nodes;
        }

        public void setNodes(Set<Node> nodes) {
            this.nodes = nodes;
        }
    }

    public class Node {

        private String name;

        private LinkedList<Node> shortestPath = new LinkedList<>();

        private Integer distance = Integer.MAX_VALUE;
        public Integer x;
        public Integer y;

        private Map<Node, Integer> adjacentNodes = new HashMap<>();

        public Node(Integer x, Integer y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }

        public void addDestination(Node destination, int distance) {
            adjacentNodes.put(destination, distance);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<Node, Integer> getAdjacentNodes() {
            return adjacentNodes;
        }

        public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
            this.adjacentNodes = adjacentNodes;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public List<Node> getShortestPath() {
            return shortestPath;
        }

        public void setShortestPath(LinkedList<Node> shortestPath) {
            this.shortestPath = shortestPath;
        }

    }
}