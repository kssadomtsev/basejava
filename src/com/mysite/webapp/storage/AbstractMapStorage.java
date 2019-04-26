package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getList() {
        Collection<Resume> storageValues = storage.values();
        return new ArrayList<>(storageValues);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
