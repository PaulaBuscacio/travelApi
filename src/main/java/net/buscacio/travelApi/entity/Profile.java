package net.buscacio.travelApi.entity;

public enum Profile {
    ADMIN(1, "ROLE_ADMIN"),
    COSTUMER(2, "ROLE_COSTUMER");



    private int code;
    private String description;

    Profile(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription () {
        return description;
    }

}
