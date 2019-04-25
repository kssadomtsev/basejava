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
    protected Integer getPos(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void updatePerformed(Resume resume, Object pos) {
        storage.set((Integer) pos, resume);
    }

    @Override
    protected void savePerformed(Resume resume, Object pos) {
        storage.add(resume);
    }

    @Override
    protected Resume getPerformed(Object pos) {
        return storage.get((Integer) pos);
    }

    @Override
    protected void deletePerformed(Object pos) {
        storage.remove((int) pos);
    }

    @Override
    protected boolean isPresent(Object pos) {
        return (Integer) pos >= 0;
    }
}
