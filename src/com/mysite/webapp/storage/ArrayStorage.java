package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;


    private boolean isResumePresent(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    private int indexResumeInArray(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void update(Resume resume) {
        if (isResumePresent(resume.getUuid())) {
            storage[indexResumeInArray(resume.getUuid())].setUuid(resume.getUuid() + "_updated");
        } else {
            System.out.println("Resume with UUID " + resume.getUuid() + " is not present");
        }
    }

    public void save(Resume resume) {
        if (isResumePresent(resume.getUuid())) {
            System.out.println("Resume with UUID " + resume.getUuid() + " already present");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        if (isResumePresent(uuid)) {
            return storage[indexResumeInArray(uuid)];
        } else {
            System.out.println("Resume with UUID " + uuid + " is not present");
            return null;
        }
    }

    public void delete(String uuid) {
        if (isResumePresent(uuid)) {
            for (int j = indexResumeInArray(uuid); j < size - 1; j++) {
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
}

