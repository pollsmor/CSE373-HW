import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        String filename = "hw3/rsrc/s-1-9-10.txt";
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            int edges = Integer.parseInt(reader.nextLine());
            int vertices = Integer.parseInt(reader.nextLine());

            Graph g = new Graph(vertices);

            for (int i = 0; i < edges; i++) {
                String[] edge = reader.nextLine().split(" "); // Array of size 2 representing an edge
                g.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
            }

            reader.close();

            g.connectedComponents();
        } catch (FileNotFoundException e) {
            // Won't happen.
        }
    }
}
