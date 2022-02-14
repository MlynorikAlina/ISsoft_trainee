package by.issoft.domain;

import static com.google.common.base.Preconditions.checkNotNull;

public class UserID {
    private String id;

    public UserID(String id) {
        checkNotNull(id, "id should not be null");
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
