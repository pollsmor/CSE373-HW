import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Cover {
    int U;
    int numSubsets;
    List<List<Integer>> subsets;
    int smallestSize;
    List<Integer> smallestSubsetIndices;
    int[] buckets;

    public Cover(int U, int numSubsets) {
        this.U = U;
        this.numSubsets = numSubsets;
        subsets = new ArrayList<>(numSubsets);
        smallestSize = numSubsets;
        smallestSubsetIndices = new ArrayList<>();
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

    public void minSetCover() {
        if (U != 0) {
            List<Integer> subsetIndices = new ArrayList<>();
            minSetCover(0, 0, subsetIndices);

            System.out.println("Size: " + smallestSize);
            for (int i : smallestSubsetIndices)
                System.out.println(subsets.get(i));
        } else System.out.println("Size: " + 0);
    }

    private void minSetCover(int k, int lastIdx, List<Integer> subsetIndices) {
        if (isSolution() && k < smallestSize) {
            smallestSize = k;
            smallestSubsetIndices = new ArrayList<>(subsetIndices); // Make a copy
        } else if (k >= smallestSize) {
                return;
        } else if (lastIdx < subsets.size()) {
            // If set already contains elements of subset, skip
            if (!bucketsContainSubset(subsets.get(lastIdx))) { // Don't want to unncessarily add subsets
                List<Integer> subsetIndicesCopy = new ArrayList<>(subsetIndices);
                subsetIndicesCopy.add(lastIdx);
                addSubsetToBuckets(subsets.get(lastIdx));

                minSetCover(k + 1, lastIdx + 1, subsetIndicesCopy);
                removeSubsetFromBuckets(subsets.get(lastIdx));
            }

            if (k < smallestSize - 1) // Removing one subset from a working set cover and adding another one is pointless
            minSetCover(k, lastIdx + 1, subsetIndices);
        }
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
