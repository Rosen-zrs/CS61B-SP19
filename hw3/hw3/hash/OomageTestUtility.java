package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] backets = new int[M];
        int num = oomages.size();

        for (Oomage om : oomages) {
            int backet_index = (om.hashCode() & 0x7FFFFFFF) % M;
            backets[backet_index] += 1;
        }

        for (int b : backets) {
            if (b < (num / 50) || b > (num / 2.5)) {
                return false;
            }
        }

        return true;
    }
}
