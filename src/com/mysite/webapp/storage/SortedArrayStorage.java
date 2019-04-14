package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbsractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insert(Resume resume, int index) {
        int pos = Math.abs(index + 1);
        System.arraycopy(storage, pos, storage, pos + 1, size - pos);
        storage[pos] = resume;
    }

    @Override
    protected void swap(int index) {
        for (int i = index; i < size - 1; i++) {
            storage[i] = storage[i + 1];
        }
    }
}
