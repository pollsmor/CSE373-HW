import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String filename = "hw4/rsrc/s-rg-8-10";
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            int U = Integer.parseInt(reader.nextLine());
            int numSubsets = Integer.parseInt(reader.nextLine());

            Cover c = new Cover(U, numSubsets);

            for (int i = 0; i < numSubsets; i++)
                c.addSubset(reader.nextLine().split(" "));

            System.out.println(c);

            reader.close();
        } catch (FileNotFoundException e) {
            // Won't happen.
        }
    }
}
