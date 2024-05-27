package nerd.example.inha_project.account.register;

import androidx.annotation.NonNull;

public enum CHECK_TYPE {

    ID("id"),
    EMAIL("email");

    private final String value;

    CHECK_TYPE(String value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }

}
