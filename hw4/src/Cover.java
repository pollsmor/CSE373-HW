import java.util.List;
import java.util.ArrayList;

public class Cover {
    int U;
    int numSubsets;
    List<List<Integer>> subsets;
    int smallestSize;
    List<Integer> smallestSubsetIndices;

    // "State" of each traversal
    List<Integer> subsetIndices;
    int[] buckets;

    public Cover(int U, int numSubsets) {
        this.U = U;
        this.numSubsets = numSubsets;
        subsets = new ArrayList<>(numSubsets);
        smallestSize = numSubsets; // Obviously adding everything is a solution

        subsetIndices = new ArrayList<>();
        buckets = new int[U + 1]; // 1-indexed for convenience
    }

    public void addSubset(String[] line) {
        List<Integer> subset = new ArrayList<>();
        for (String s : line) {
            if (!s.equals(""))
                subset.add(Integer.parseInt(s));
        }

        subsets.add(subset);
    }

    public List<List<Integer>> getSubsets() {
        return subsets;
    }

    public void minSetCover() {
        if (U != 0) {
            minSetCover(0, 0);
            System.out.println("Size: " + smallestSize);
            for (int i : smallestSubsetIndices)
                System.out.println(subsets.get(i));
        } else System.out.println("Size: " + 0);
    }

    private void minSetCover(int k, int lastIdx) {
        if (isSolution() && k < smallestSize) {
            smallestSize = k;
            smallestSubsetIndices = new ArrayList<>(subsetIndices); // Make a copy
        } else if (k >= smallestSize) {
                return;
        } else if (lastIdx < subsets.size()) {
            // If set already contains elements of subset, skip
            if (!bucketsContainSubset(subsets.get(lastIdx))) { // Don't want to unncessarily add subsets
                subsetIndices.add(lastIdx);
                addSubsetToBuckets(subsets.get(lastIdx));

                minSetCover(k + 1, lastIdx + 1);
                subsetIndices.remove(subsetIndices.size() - 1);
                removeSubsetFromBuckets(subsets.get(lastIdx));
            }

            // Removing one subset from a working set cover and adding another one is pointless
            if (k < smallestSize - 1) minSetCover(k, lastIdx + 1);
        }
    }

    // Buckets handling functions =================================================================================
    private boolean isSolution() {
        for (int i = 1; i <= U; i++)
            if (buckets[i] == 0) return false;

        return true;
    }

    private boolean bucketsContainSubset(List<Integer> subset) {
        for (int i = 0; i < subset.size(); i++)
            if (buckets[subset.get(i)] == 0) return false;

        return true;
    }

    private void addSubsetToBuckets(List<Integer> subset) {
        for (int i = 0; i < subset.size(); i++) 
            buckets[subset.get(i)]++;
    }

    private void removeSubsetFromBuckets(List<Integer> subset) {
        for (int i = 0; i < subset.size(); i++) 
            buckets[subset.get(i)]--;
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