package ua.testing.task8.model.entity.authentication;

public class UserIsNotUnique extends Exception {

    private String keyName;

    public UserIsNotUnique(Throwable cause, String keyName) {
        super(cause);
        this.keyName = keyName;
    }

    public UserIsNotUnique(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }

    @Override
    public String toString() {
        return "User is not unique{" +
                "keyName='" + keyName + '\'' +
                '}';
    }
}
