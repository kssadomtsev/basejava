package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] result = storage.values().toArray(new Resume[storage.size()]);
        Arrays.sort(result);
        return result;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected void updatePerformed(Resume resume, Object index) {
        storage.put((String) index, resume);
    }

    @Override
    protected void savePerformed(Resume resume, Object index) {
        storage.put((String) index, resume);
    }

    @Override
    protected Resume getPerformed(Object index) {
        return storage.get(index);
    }

    @Override
    protected void deletePerformed(Object index) {
        storage.remove(index);
    }

    @Override
    protected boolean isPresent(Object index) {
        return storage.containsKey(index);
    }

}
