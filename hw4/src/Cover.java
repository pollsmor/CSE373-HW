import java.util.List;
import java.util.ArrayList;

public class Cover {
    int U;
    int numSubsets;
    List<List<Integer>> subsets;
    int largestSize;
    List<Integer> smallestSubsetIndices;

    // "State" of each traversal
    List<Integer> subsetIndices;
    int[] buckets;

    public Cover(int U, int numSubsets) {
        this.U = U;
        this.numSubsets = numSubsets;
        subsets = new ArrayList<>(numSubsets);
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
            // Initial solution to cap the largest size ASAP
            buckets = new int[U + 1];
            smallestSubsetIndices = new ArrayList<>();
            for (int i = 0; i < numSubsets && largestSize == 0; i++) {
                addSubsetToBuckets(subsets.get(i));
                if (isSolution()) largestSize = i + 1;
            }

            buckets = new int[U + 1]; // Reset
            minSetCover(0, 0, 0);
            System.out.println("Size: " + largestSize);
            for (int i : smallestSubsetIndices)
                System.out.println(subsets.get(i));
        } else System.out.println("Size: " + 0);
    }

    private void minSetCover(int k, int lastIdx, int numsAddedToBuckets) {
        if (numsAddedToBuckets >= U && isSolution()) {
            largestSize = k;
            smallestSubsetIndices = new ArrayList<>(subsetIndices); // Make a copy
        } else if (k < largestSize - 1 && lastIdx < numSubsets) {
            List<Integer> subset = subsets.get(lastIdx);
            if (!bucketsContainSubset(subset)) { // If set already contains elements of subset, skip
                subsetIndices.add(lastIdx);
                addSubsetToBuckets(subset);

                minSetCover(k + 1, lastIdx + 1, numsAddedToBuckets + subset.size());
                subsetIndices.remove(subsetIndices.size() - 1);
                removeSubsetFromBuckets(subset);
            }

            minSetCover(k, lastIdx + 1, numsAddedToBuckets);
        }
    }

    // Buckets handling functions =================================================================================
    private boolean isSolution() {
        for (int i = 1; i <= U; i++)
            if (buckets[i] == 0) return false;

        return true;
    }

    private boolean bucketsContainSubset(List<Integer> subset) {
        for (int i : subset)
            if (buckets[i] == 0) return false;

        return true;
    }

    private void addSubsetToBuckets(List<Integer> subset) {
        for (int i : subset) buckets[i]++;
    }

    private void removeSubsetFromBuckets(List<Integer> subset) {
        for (int i : subset) buckets[i]--;
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