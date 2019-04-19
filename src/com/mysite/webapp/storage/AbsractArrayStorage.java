package com.mysite.webapp.storage;

import com.mysite.webapp.exception.StorageException;
import com.mysite.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbsractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected abstract int getIndex(String uuid);

    @Override
    protected void updateHelper(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void saveHelper(Resume resume, int index) {
        if (size < STORAGE_LIMIT) {
            insert(resume, index);
            size++;
        } else {
            throw new StorageException("Storage is full!", resume.getUuid());
        }
    }

    @Override
    protected Resume getHelper(int index) {
        return storage[index];
    }

    @Override
    protected void deleteHelper(int index) {
        swap(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void insert(Resume resume, int index);

    protected abstract void swap(int index);
}
