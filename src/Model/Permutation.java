package Model;

import java.util.ArrayList;

class Permutation {
    public static void swap(ArrayList<Integer> data, int left, int right) {
        Integer temp = data.get(left);
        data.set(left, data.get(right));
        data.set(right, temp);
    }

    public static void reverse(ArrayList<Integer> data, int left, int right) {
        while (left < right) {
            Integer temp = data.get(left);
            data.set(left++, data.get(right));
            data.set(right--, temp);
        }
    }

    public static boolean findNextPermutation(ArrayList<Integer> data) {

        if (data.size() <= 1)
            return false;

        int last = data.size() - 2;
        while (last >= 0) {
            if (data.get(last) < data.get(last + 1)) {
                break;
            }
            last--;
        }

        if (last < 0)
            return false;

        int nextGreater = data.size() - 1;

        for (int i = data.size() - 1; i > last; i--) {
            if (data.get(i) > data.get(last)) {
                nextGreater = i;
                break;
            }
        }

        swap(data, nextGreater, last);

        reverse(data, last + 1, data.size() - 1);

        return true;
    }
}
