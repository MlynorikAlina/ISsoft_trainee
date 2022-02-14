package by.issoft.domain;

import by.issoft.domain.users.Driver;
import by.issoft.domain.users.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class License {
    private final UUID licenseNumber;
    private final UUID driverId;
    private final LocalDate dayOfReceiving;
    private static final Map<User, License> givenLicenses = new HashMap<>();

    private License() {
        this.licenseNumber = UUID.randomUUID();
        this.driverId = UUID.randomUUID();
        dayOfReceiving = LocalDate.now();
    }

    public static License getLicense(Driver driver) {
        if (givenLicenses.containsKey(driver)) {
            return givenLicenses.get(driver);
        } else {
            if (driver.getAge().less(Age.of(18))) {
                throw new IllegalArgumentException("driver is too young and cant get a license");
            }
            License newLicense = new License();
            givenLicenses.put(driver, newLicense);
            return newLicense;
        }
    }
}
