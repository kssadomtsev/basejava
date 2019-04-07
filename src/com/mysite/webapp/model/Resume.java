package com.mysite.webapp.model;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private String uuid;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public boolean equals(Object obj) {
        Resume other = (Resume) obj;
        if (other.getUuid().equals(this.uuid)) {
            return true;
        }
        return false;
    }

}
