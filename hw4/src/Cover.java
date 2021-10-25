import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Cover {
    int U;
    int numSubsets;
    List<List<Integer>> subsets;
    int smallestSize;
    List<Integer> smallestSubsetIndices;
    int smallestPossibleSize;

    public Cover(int U, int numSubsets) {
        this.U = U;
        this.numSubsets = numSubsets;
        subsets = new ArrayList<>();
        smallestSize = numSubsets;
        smallestSubsetIndices = new ArrayList<>();
        smallestPossibleSize = numSubsets;
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

    private int calculateSmallestPossibleSize() {
        int elements = 0;
        int smallestSetSize = 0;
        int i = 0;
        while (elements < U) {
            elements += subsets.get(i).size();
            smallestSetSize++;
            i++;
        }

        return smallestSetSize;
    }

    public void minSetCover() {
        if (U != 0) {
            smallestPossibleSize = calculateSmallestPossibleSize();
            List<Integer> subsetIndices = new ArrayList<>();
            Set<Integer> currSet = new HashSet<>();
            minSetCover(0, 0, subsetIndices, currSet);

            System.out.println("Size: " + smallestSize);
            for (int i : smallestSubsetIndices)
            System.out.println(subsets.get(i));
        } else System.out.println("Size: " + 0);
    }

    private void minSetCover(int k, int lastIdx, List<Integer> subsetIndices, Set<Integer> currSet) {
        if (k >= smallestSize || smallestSize == smallestPossibleSize) return;
        else if (currSet.size() == U) {
            smallestSize = k;
            smallestSubsetIndices = subsetIndices;
        } else {
            for (int i = lastIdx; i < subsets.size(); i++) {
                // If set already contains elements of subset, skip
                if (!currSet.containsAll(subsets.get(i))) {
                    List<Integer> subsetIndicesCopy = new ArrayList<>(subsetIndices);
                    Set<Integer> currSetCopy = new HashSet<>(currSet);

                    subsetIndicesCopy.add(i);
                    for (int num : subsets.get(i))
                        currSetCopy.add(num);

                    minSetCover(k + 1, i + 1, subsetIndicesCopy, currSetCopy);
                }
                    
                minSetCover(k, i + 1, subsetIndices, currSet);
            }
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
