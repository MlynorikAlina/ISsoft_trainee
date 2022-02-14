package by.issoft.domain.users;

import by.issoft.domain.users.data.UserDataTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {
    private UserDataTest userData = new UserDataTest();

    @Test
    void validate_nullPointerEx() {
        assertThrows(NullPointerException.class, () -> userData.userWithNullFirstName().validate());
        assertThrows(NullPointerException.class, () -> userData.userWithNullLastName().validate());
        assertThrows(NullPointerException.class, () -> userData.userWithNullAge().validate());
    }

    @Test
    void validate_illegalArgumentEx() {
        assertThrows(IllegalArgumentException.class, () -> userData.userWithEmptyFirstName().validate());
        assertThrows(IllegalArgumentException.class, () -> userData.userWithEmptyLastName().validate());
    }

    @Test
    void validate_success() {
        assertTrue(userData.someUser().validate());
    }
}