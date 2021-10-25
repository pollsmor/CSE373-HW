import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws Exception {
        String filename = "hw4/rsrc/s-rg-31-15";
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            int U = Integer.parseInt(reader.nextLine());
            int numSubsets = Integer.parseInt(reader.nextLine());

            Cover c = new Cover(U, numSubsets);

            for (int i = 0; i < numSubsets; i++)
                c.addSubset(reader.nextLine().split(" "));
            
            reader.close();
            Collections.sort(c.getSubsets(), new LengthComparator());
            long startTime = System.currentTimeMillis();
            c.minSetCover();
            System.out.println(System.currentTimeMillis() - startTime + "ms");
        } catch (FileNotFoundException e) {
            // Won't happen.
        }
    }
}

class LengthComparator implements Comparator<List<Integer>> {
    public int compare(List<Integer> list1, List<Integer> list2) {
        return list2.size() - list1.size(); // Sort descending in length
    }
}
