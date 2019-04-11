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
        int pos = getIndex(resume.getUuid());
        if (pos >= 0) {
            storage[pos] = resume;
        } else {
            System.out.println("Resume with UUID " + resume.getUuid() + " is not present");
        }
    }

    @Override
    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("Resume with UUID " + resume.getUuid() + " already present");
        } else {
            if (size < STORAGE_LIMIT) {
                insert(resume);
                size++;
            } else {
                System.out.println("Storage is full!");
            }
        }
    }

    @Override
    public Resume get(String uuid) {
        int pos = getIndex(uuid);
        if (pos >= 0) {
            return storage[pos];
        } else {
            System.out.println("Resume with UUID " + uuid + " is not present");
            return null;
        }
    }

    @Override
    public void delete(String uuid) {
        int pos = getIndex(uuid);
        if (pos >= 0) {
            swap(pos);
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

    protected abstract void insert(Resume resume);

    protected abstract void swap(int pos);
}
