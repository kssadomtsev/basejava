package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.util.*;

public abstract class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>(Arrays.asList(storage.values().toArray(new Resume[storage.size()])));
        Collections.sort(resumes);
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
