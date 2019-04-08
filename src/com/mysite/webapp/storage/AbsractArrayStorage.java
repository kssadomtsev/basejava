package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

public abstract class AbsractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int pos = getIndex(uuid);
        if (pos != -1) {
            return storage[pos];
        } else {
            System.out.println("Resume with UUID " + uuid + " is not present");
            return null;
        }
    }

    protected abstract int getIndex(String uuid);

}
