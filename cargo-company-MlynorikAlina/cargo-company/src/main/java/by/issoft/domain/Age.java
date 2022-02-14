package by.issoft.domain;

import java.util.HashMap;
import java.util.Map;

import static com.google.inject.internal.util.Preconditions.checkArgument;

public class Age {
    private final int age;
    private static final Map<Integer, Age> poolOfAges = new HashMap<>();
    public final static Age MAX_AGE = Age.of(129);
    public final static Age MIN_AGE = Age.of(0);

    private Age(int age) {
        this.age = age;
    }

    public static Age of(int age) {
        checkArgument(age >= 0 && age < 130, "Age must be positive and less than 130");

        if (poolOfAges.containsKey(age)) {
            return poolOfAges.get(age);
        }

        Age newAge = new Age(age);
        poolOfAges.put(age, newAge);
        return newAge;
    }

    public boolean less(Age age2) {
        return age < age2.intValue();
    }

    public boolean greater(Age age2) {
        return age > age2.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Age age1 = (Age) o;
        return age == age1.age;
    }

    public int intValue() {
        return age;
    }

    @Override
    public String toString() {
        return Integer.toString(age);
    }
}
