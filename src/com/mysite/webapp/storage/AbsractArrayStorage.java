package com.mysite.webapp.storage;

import com.mysite.webapp.exception.StorageException;
import com.mysite.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

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
    public List<Resume> getAllSorted() {
        Resume[] resumes_as_array = new Resume[size];
        System.arraycopy(storage, 0, resumes_as_array, 0, size);
        return getSortedList(resumes_as_array);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected abstract Integer getIndex(String uuid);

    @Override
    protected void updatePerformed(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void savePerformed(Resume resume, Object index) {
        if (size < STORAGE_LIMIT) {
            insert(resume, (Integer) index);
            size++;
        } else {
            throw new StorageException("Storage is full!", resume.getUuid());
        }
    }

    @Override
    protected Resume getPerformed(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void deletePerformed(Object index) {
        remove((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isPresent(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract void insert(Resume resume, int index);

    protected abstract void remove(int index);

    protected abstract List<Resume> getSortedList(Resume[] resumes_as_array);
}
