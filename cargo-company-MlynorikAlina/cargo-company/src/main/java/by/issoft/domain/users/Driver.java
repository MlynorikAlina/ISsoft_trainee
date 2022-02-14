package by.issoft.domain.users;

import by.issoft.domain.Age;
import by.issoft.domain.License;

public class Driver extends User {
    private final License license;

    public Driver(String firstName, String lastName, Age age) {
        super(firstName, lastName, age);
        this.license = License.getLicense(this);
    }

    public License getLicense() {
        return license;
    }
}
