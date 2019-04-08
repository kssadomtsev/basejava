package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int pos = getIndex(resume.getUuid());
        if (pos != -1) {
            storage[pos] = resume;
        } else {
            System.out.println("Resume with UUID " + resume.getUuid() + " is not present");
        }
    }

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) != -1) {
            System.out.println("Resume with UUID " + resume.getUuid() + " already present");
        } else {
            if (size < storage.length) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Storage is full!");
            }
        }
    }

    public Resume get(String uuid) {
        int pos = getIndex(uuid);
        if (pos != -1) {
            return storage[pos];
        } else {
            System.out.println("Resume with UUID " + uuid + " is not present");
            return null;
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

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] result = new Resume[size];
        System.arraycopy(storage, 0, result, 0, size);
        return result;
    }

    public int size() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}

