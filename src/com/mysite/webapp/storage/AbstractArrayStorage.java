package com.mysite.webapp.storage;

import com.mysite.webapp.exception.StorageException;
import com.mysite.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected abstract Integer getKey(String uuid);

    @Override
    protected void doUpdate(Resume resume, Object key) {
        storage[(Integer) key] = resume;
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        if (size < STORAGE_LIMIT) {
            insert(resume, (Integer) key);
            size++;
        } else {
            throw new StorageException("Storage is full!", resume.getUuid());
        }
    }

    @Override
    protected Resume doGet(Object key) {
        return storage[(Integer) key];
    }

    @Override
    protected void doDelete(Object key) {
        remove((Integer) key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isPresent(Object key) {
        return (Integer) key >= 0;
    }

    @Override
    protected List<Resume> getList() {
        Resume[] resumesAsArray = new Resume[size];
        System.arraycopy(storage, 0, resumesAsArray, 0, size);
        return new ArrayList<>(Arrays.asList(resumesAsArray));
    }

    protected abstract void insert(Resume resume, int key);

    protected abstract void remove(int key);
}
