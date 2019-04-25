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
    public List<Resume> getAllSorted() {
        Collection<Resume> storageValues = storage.values();
        ArrayList<Resume> listOfValues = new ArrayList<>(storageValues);
        Collections.sort(listOfValues);
        return listOfValues;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
