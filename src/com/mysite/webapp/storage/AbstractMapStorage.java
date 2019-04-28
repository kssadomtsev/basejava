package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage<SK> extends AbstractStorage<SK> {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
