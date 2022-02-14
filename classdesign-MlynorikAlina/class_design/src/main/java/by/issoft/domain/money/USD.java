package by.issoft.domain.money;

public class USD {
    private int value;

    private USD(int value) {
        this.value = value;
    }

    public static USD of(int value){
        return new USD(value);
    }

    public int getValue() {
        return value;
    }

    public USD add(USD sum){
        this.value += sum.getValue();
        return this;
    }
}
