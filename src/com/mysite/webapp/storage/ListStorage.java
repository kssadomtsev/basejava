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
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void updatePerformed(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected void savePerformed(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected Resume getPerformed(int index) {
        return storage.get(index);
    }

    @Override
    protected void deletePerformed(int index) {
        storage.remove(index);
    }
}
