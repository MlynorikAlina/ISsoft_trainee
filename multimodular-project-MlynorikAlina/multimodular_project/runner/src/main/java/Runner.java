import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Runner {

    public static void main(String[] args) {
        int[] array = getArrayOfInt(20);
        Arrays.sort(array);
        System.out.println(Utils.binarySearch(array,10));
        System.out.println(Arrays.binarySearch(array,10));
    }

    static int[] getArrayOfInt(int size) {
        int[] arrayToReturn = new int[size];

        for (int i = 0; i < size; i++) {
            arrayToReturn[i] = (int) (Math.random() * 20);
        }

        return arrayToReturn;
    }
}
