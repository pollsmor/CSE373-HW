import java.util.ArrayList;

public class Graph {
    int vertices;
    ArrayList<ArrayList<Integer>> adjList;
    boolean[] visited;

    Graph(int vertices) {
        this.vertices = vertices;
        adjList = new ArrayList<>(vertices + 1); // 1-indexed for convenience
        adjList.add(new ArrayList<>());

        for (int i = 1; i <= vertices; i++)
            adjList.add(i, new ArrayList<>());

        visited = new boolean[vertices + 1];

    }

    // Goes both ways
    void addEdge(int src, int dest) {
        adjList.get(src).add(dest);
        adjList.get(dest).add(src);
    }

    void dfs(int i) { // i is the origin vertex when called from connectedComponents
        visited[i] = true;
        System.out.print(i + " ");

        for (int neighbor : adjList.get(i)) {
            if (!visited[neighbor]) dfs(neighbor);
        }
    }

    void connectedComponents() {
        visited[0] = true; // Since I'm using 1-indexed
        for (int i = 1; i <= vertices; i++) {
            if (!visited[i]) {
                dfs(i);

                // New line for new set of connected components
                System.out.println();
            }
        }
    }
}
