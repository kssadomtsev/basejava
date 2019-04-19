package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected ArrayList<Resume> storage = new ArrayList<>();

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
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void updateHelper(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected void saveHelper(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected Resume getHelper(int index) {
        return storage.get(index);
    }

    @Override
    protected void deleteHelper(int index) {
        storage.remove(index);
    }
}
