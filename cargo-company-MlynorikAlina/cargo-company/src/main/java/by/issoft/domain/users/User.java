package by.issoft.domain.users;

import by.issoft.domain.Age;

import static com.google.inject.internal.util.Preconditions.checkArgument;
import static com.google.inject.internal.util.Preconditions.checkNotNull;

public class User {
    private String firstName;
    private String lastName;
    Age age;

    public User(String firstName, String lastName, Age age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Age getAge() {
        return age;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean validate() {
        checkNotNull(age, "age should not be null");
        checkNotNull(firstName, "firstName should not be null");
        checkNotNull(lastName, "lastName should not be null");
        checkArgument(!"".equals(firstName), "firstName should contain something");
        checkArgument(!"".equals(lastName), "lastName should contain something");
        return true;
    }
}
