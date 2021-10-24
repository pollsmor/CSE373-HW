import java.util.List;
import java.util.ArrayList;

public class Cover {
    int U;
    int numSubsets;
    List<List<Integer>> subsets;

    public Cover(int U, int numSubsets) {
        this.U = U;
        this.numSubsets = numSubsets;
        subsets = new ArrayList<>();
    }

    public void addSubset(String[] line) {
        List<Integer> subset = new ArrayList<>();
        for (String s : line) {
            if (!s.equals(""))
                subset.add(Integer.parseInt(s));
        }

        subsets.add(subset);
    }

    // Debugging function
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("U: " + U);
        str.append("\nNumber of subsets: " + numSubsets + "\n");
        str.append(subsets);

        return str.toString();
    }
}
