package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbsractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Resume with UUID " + resume.getUuid() + " is not present");
        }
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("Resume with UUID " + resume.getUuid() + " already present");
        } else {
            if (size < STORAGE_LIMIT) {
                insert(resume, index);
                size++;
            } else {
                System.out.println("Storage is full!");
            }
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Resume with UUID " + uuid + " is not present");
            return null;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            swap(index);
            size--;
        } else {
            System.out.println("Resume with UUID " + uuid + " is not present");
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insert(Resume resume, int index);

    protected abstract void swap(int index);
}
