package by.issoft.domain.users.data;

import by.issoft.domain.Age;
import by.issoft.domain.users.User;

import java.util.UUID;

public class UserDataTest {

    public User userWithNullFirstName() {
        return new User(null, UUID.randomUUID().toString(), Age.MAX_AGE);
    }

    public User userWithNullLastName() {
        return new User(UUID.randomUUID().toString(), null, Age.MAX_AGE);
    }

    public User userWithNullAge() {
        return new User(UUID.randomUUID().toString(), UUID.randomUUID().toString(), null);
    }

    public User userWithEmptyFirstName() {
        return new User("", UUID.randomUUID().toString(), Age.MAX_AGE);
    }

    public User userWithEmptyLastName() {
        return new User(UUID.randomUUID().toString(), "", Age.MAX_AGE);
    }

    public User someUser() {
        return new User(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Age.MAX_AGE);
    }

}
