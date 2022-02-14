package by.issoft.domain.accessory;

import com.google.inject.internal.util.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
@RequiredArgsConstructor(staticName = "of")
public class Intensity {
    byte value;
    private final static Map<Byte, Intensity> intensityPool = new HashMap<>();

    public static Intensity of(int value) {
        Preconditions.checkArgument(value >= 0 && value <= 100, "value should be in interval [0, 100]");
        byte newValue = (byte) value;
        Intensity toReturn;
        if (intensityPool.containsKey(newValue)) {
            toReturn = intensityPool.get(newValue);
        } else {
            toReturn = new Intensity(newValue);
            intensityPool.put(newValue, toReturn);
        }
        return toReturn;
    }

    public int intValue() {
        return value;
    }
}
