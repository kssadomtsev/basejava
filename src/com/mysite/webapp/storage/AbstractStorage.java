package com.mysite.webapp.storage;

import com.mysite.webapp.exception.ExistStorageException;
import com.mysite.webapp.exception.NotExistStorageException;
import com.mysite.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object key = getElementExist(resume.getUuid());
        doUpdate(resume, key);
    }

    @Override
    public void save(Resume resume) {
        Object key = getElementNotExist(resume.getUuid());
        doSave(resume, key);
    }

    @Override
    public Resume get(String uuid) {
        Object key = getElementExist(uuid);
        return doGet(key);
    }

    @Override
    public void delete(String uuid) {
        Object key = getElementExist(uuid);
        doDelete(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getList();
        Collections.sort(list);
        return list;
    }

    private Object getElementExist(String uuid) {
        Object key = getKey(uuid);
        if (!isPresent(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private Object getElementNotExist(String uuid) {
        Object key = getKey(uuid);
        if (isPresent(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract Object getKey(String uuid);

    protected abstract void doUpdate(Resume resume, Object key);

    protected abstract void doSave(Resume resume, Object key);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    protected abstract boolean isPresent(Object key);

    protected abstract List<Resume> getList();
}
