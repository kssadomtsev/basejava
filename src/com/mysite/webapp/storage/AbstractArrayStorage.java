package com.mysite.webapp.storage;

import com.mysite.webapp.exception.StorageException;
import com.mysite.webapp.model.Resume;

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
    public List<Resume> getAllSorted() {
        Resume[] resumesAsArray = new Resume[size];
        System.arraycopy(storage, 0, resumesAsArray, 0, size);
        return getSortedList(resumesAsArray);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected abstract Integer getPos(String uuid);

    @Override
    protected void updatePerformed(Resume resume, Object pos) {
        storage[(Integer) pos] = resume;
    }

    @Override
    protected void savePerformed(Resume resume, Object pos) {
        if (size < STORAGE_LIMIT) {
            insert(resume, (Integer) pos);
            size++;
        } else {
            throw new StorageException("Storage is full!", resume.getUuid());
        }
    }

    @Override
    protected Resume getPerformed(Object pos) {
        return storage[(Integer) pos];
    }

    @Override
    protected void deletePerformed(Object pos) {
        remove((Integer) pos);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isPresent(Object pos) {
        return (Integer) pos >= 0;
    }

    protected abstract void insert(Resume resume, int index);

    protected abstract void remove(int index);

    protected abstract List<Resume> getSortedList(Resume[] resumesAsArray);
}
