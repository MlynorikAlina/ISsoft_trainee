import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static int binarySearch(int[] array, int value) {
        logger.debug("\n------------------------------\n");
        logger.debug("array: {}", Arrays.toString(array));
        logger.debug("leftIndex = 0, rightIndex = {}", array.length);
        return binarySearch(array, value, 0, array.length);
    }

    private static int binarySearch(int[] array, int value, int leftIndex, int rightIndex) {
        if (leftIndex == rightIndex) {
            logger.debug("{} not found. program returns {}", value, -(leftIndex + 1));
            return -(leftIndex + 1);
        }
        int mid = (leftIndex + rightIndex) / 2;
        if (value == array[mid]) {
            logger.debug("{} found at {}", value, mid);
            return mid;
        } else {
            if (value < array[mid]) {
                rightIndex = mid;
                logger.debug("{} <= {} changing rightIndex. leftIndex = {}, rightIndex = {}", value, array[mid], leftIndex, rightIndex);
            } else {
                leftIndex = mid + 1;
                logger.debug("{} > {} changing leftIndex. leftIndex = {}, rightIndex = {}", value, array[mid], leftIndex, rightIndex);
            }
        }
        return binarySearch(array, value, leftIndex, rightIndex);
    }
}
