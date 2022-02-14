package by.issoft.domain;

import by.issoft.domain.accessory.Weight;
import by.issoft.domain.building.Floor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Person {
    public static final Weight MIN_WEIGHT = Weight.of(30);
    public static final Weight MAX_WEIGHT = Weight.of(200);
    private final Weight weight;
    private final Floor finalFloor;
}
