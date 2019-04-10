package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbsractArrayStorage {

    @Override
    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("Resume with UUID " + resume.getUuid() + " already present");
        } else {
            if (size < STORAGE_LIMIT) {
                int pos = Math.abs(Arrays.binarySearch(storage, 0, size, resume) + 1);
                size++;
                System.arraycopy(storage, pos, storage, pos + 1, size - pos - 1);
                storage[pos] = resume;
            } else {
                System.out.println("Storage is full!");
            }
        }
    }

    @Override
    public void delete(String uuid) {
        int pos = getIndex(uuid);
        if (pos >= 0) {
            System.arraycopy(storage, pos + 1, storage, pos, size - pos + 1);
            size--;
        } else {
            System.out.println("Resume with UUID " + uuid + " is not present");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
