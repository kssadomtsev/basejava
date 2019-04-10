package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbsractArrayStorage {

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) != -1) {
            System.out.println("Resume with UUID " + resume.getUuid() + " already present");
        } else {
            if (size < STORAGE_LIMIT) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Storage is full!");
            }
        }
    }

    public void delete(String uuid) {
        int pos = getIndex(uuid);
        if (pos != -1) {
            storage[pos] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume with UUID " + uuid + " is not present");
        }
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}

