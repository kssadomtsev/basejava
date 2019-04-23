package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        Collections.sort(storage);
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void updatePerformed(Resume resume, Object index) {
        storage.set((Integer) index, resume);
    }

    @Override
    protected void savePerformed(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    protected Resume getPerformed(Object index) {
        return storage.get((Integer) index);
    }

    @Override
    protected void deletePerformed(Object index) {
        storage.remove((int) index);
    }

    @Override
    protected boolean isPresent(Object index) {
        return (Integer) index >= 0;
    }
}
