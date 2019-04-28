package com.mysite.webapp.storage;

import com.mysite.webapp.exception.StorageException;
import com.mysite.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    protected abstract Integer getSearchKey(String uuid);

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        if (size < STORAGE_LIMIT) {
            insert(resume, searchKey);
            size++;
        } else {
            throw new StorageException("Storage is full!", resume.getUuid());
        }
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void doDelete(Integer searchKey) {
        remove(searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isPresent(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected List<Resume> getList() {
        Resume[] resumesAsArray = new Resume[size];
        System.arraycopy(storage, 0, resumesAsArray, 0, size);
        return Arrays.asList(resumesAsArray);
    }

    protected abstract void insert(Resume resume, int searchKey);

    protected abstract void remove(int searchKey);
}
