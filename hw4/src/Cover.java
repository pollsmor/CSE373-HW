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

    public Cover(int U, int numSubsets) {
        this.U = U;
        this.numSubsets = numSubsets;
        subsets = new ArrayList<>(numSubsets);
        smallestSize = numSubsets;
        smallestSubsetIndices = new ArrayList<>();
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
            List<Integer> subsetIndices = new ArrayList<>();
            Set<Integer> currSet = new CopyOnWriteArraySet<>();
            minSetCover(0, 0, subsetIndices, currSet);

            System.out.println("Size: " + smallestSize);
            for (int i : smallestSubsetIndices)
                System.out.println(subsets.get(i));
        } else System.out.println("Size: " + 0);
    }

    private void minSetCover(int k, int lastIdx, List<Integer> subsetIndices, Set<Integer> currSet) {
        if (currSet.size() == U && k < smallestSize) {
            smallestSize = k;
            smallestSubsetIndices = subsetIndices;
        } else if (k >= smallestSize) {
                return;
        } else if (lastIdx < subsets.size()) {
            // If set already contains elements of subset, skip
            if (!currSet.containsAll(subsets.get(lastIdx))) { // Don't want to unncessarily add subsets
                List<Integer> subsetIndicesCopy = new ArrayList<>(subsetIndices);
                Set<Integer> currSetCopy = new CopyOnWriteArraySet<>(currSet);
                subsetIndicesCopy.add(lastIdx);
                currSetCopy.addAll(subsets.get(lastIdx));

                minSetCover(k + 1, lastIdx + 1, subsetIndicesCopy, currSetCopy);
            }

            if (k < smallestSize - 1) // Removing one subset from a working set cover and adding another one is pointless
            minSetCover(k, lastIdx + 1, subsetIndices, currSet);
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
