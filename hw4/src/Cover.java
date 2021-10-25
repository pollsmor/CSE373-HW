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

    public Cover(int U, int numSubsets) {
        this.U = U;
        this.numSubsets = numSubsets;
        subsets = new ArrayList<>();
        smallestSize = Integer.MAX_VALUE;
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

    public void minSetCover() {
        if (U != 0) {
            for (int i = 0; i < subsets.size(); i++) {
                List<Integer> subsetIndices = new ArrayList<>();
                Set<Integer> currSet = new HashSet<>();
                minSetCover(1, 0, subsetIndices, currSet);
            }
        }

        System.out.println(smallestSize);
        for (int i : smallestSubsetIndices)
            System.out.println(subsets.get(i));
    }

    private void minSetCover(int k, int lastIdx, List<Integer> subsetIndices, Set<Integer> currSet) {
        if (k >= smallestSize) return;
        else if (currSet.size() == U) {
            smallestSize = k;
            smallestSubsetIndices = new ArrayList<>(subsetIndices);
        } else {
            for (int i = lastIdx; i < subsets.size(); i++) {
                List<Integer> subsetIndicesCopy = new ArrayList<>(subsetIndices);
                Set<Integer> currSetCopy = new HashSet<>(currSet);
                List<Integer> newSubsetIndices = new ArrayList<>();
                Set<Integer> newCurrSet = new HashSet<>();

                subsetIndicesCopy.add(i);
                newSubsetIndices.add(i);

                for (int num : subsets.get(i)) {
                    currSetCopy.add(num);
                    newCurrSet.add(num);
                }

                minSetCover(k + 1, i + 1, subsetIndicesCopy, currSetCopy);
                minSetCover(1, i + 1, newSubsetIndices, newCurrSet);
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
