package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;


    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void update(Resume resume) {
        if (getIndex(resume) != -1) {
            storage[getIndex(resume)] = resume;
        } else {
            System.out.println("Resume with UUID " + resume.getUuid() + " is not present");
        }
    }

    public void save(Resume resume) {
        if (getIndex(resume) != -1) {
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
        Resume resume = new Resume();
        resume.setUuid(uuid);
        if (getIndex(resume) != -1) {
            return storage[getIndex(resume)];
        } else {
            System.out.println("Resume with UUID " + uuid + " is not present");
            return null;
        }
    }

    public void delete(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        if (getIndex(resume) != -1) {
            for (int j = getIndex(resume); j < size - 1; j++) {
                storage[j] = storage[j + 1];
            }
            size--;
            storage[size] = null;
        } else {
            System.out.println("Resume with UUID " + uuid + " is not present");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] result = new Resume[size];
        System.arraycopy(storage, 0, result, 0,
                size);
        return result;
    }

    public int size() {
        return size;
    }


    private int getIndex(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(resume)) {
                return i;
            }
        }
        return -1;
    }
}

