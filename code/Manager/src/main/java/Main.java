public class Main {
    public static void main(String[] args) {
        TerrainManager m = new TerrainManager(20, 20);
        Path p = m.getShortestPath("ASTAR");
        p.iterator().forEachRemaining(x->System.out.println(x));
    }
}
