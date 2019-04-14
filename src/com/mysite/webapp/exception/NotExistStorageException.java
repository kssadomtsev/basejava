package com.mysite.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Resume with UUID " + uuid + " is not present", uuid);
    }
}
