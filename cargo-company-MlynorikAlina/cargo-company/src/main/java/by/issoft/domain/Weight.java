package by.issoft.domain;

import java.util.HashMap;
import java.util.Map;

import static com.google.inject.internal.util.Preconditions.checkArgument;

public class Weight {
    private final int weight;
    private static Map<Integer, Weight> weightPool = new HashMap<>();

    private Weight(int weight) {
        this.weight = weight;
    }

    public static Weight of(int weight) {
        checkArgument(weight >= 0, "weight should not be negative");
        if (weightPool.containsKey(weight)) {
            return weightPool.get(weight);
        }
        Weight newWeight = new Weight(weight);
        if (weight <= 1000) {
            weightPool.put(weight, newWeight);
        }
        return newWeight;
    }

    public boolean less(Weight weight2) {
        return weight < weight2.intValue();
    }

    public boolean greater(Weight weight2) {
        return weight > weight2.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weight weight1 = (Weight) o;
        return weight == weight1.weight;
    }

    public int intValue() {
        return weight;
    }

    @Override
    public String toString() {
        return weight + "kg";
    }
}
