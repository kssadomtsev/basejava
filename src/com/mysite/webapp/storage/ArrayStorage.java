package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insert(Resume resume, int searchKey) {
        storage[size] = resume;
    }

    @Override
    protected void remove(int searchKey) {
        storage[searchKey] = storage[size - 1];
    }
}

