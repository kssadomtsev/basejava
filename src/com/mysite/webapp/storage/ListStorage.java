package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getList() {
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer getKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        storage.set((Integer) key, resume);
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove((int) key);
    }

    @Override
    protected boolean isPresent(Object key) {
        return (Integer) key >= 0;
    }
}
