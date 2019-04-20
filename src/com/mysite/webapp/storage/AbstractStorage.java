package com.mysite.webapp.storage;

import com.mysite.webapp.exception.ExistStorageException;
import com.mysite.webapp.exception.NotExistStorageException;
import com.mysite.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object index = getIndex(resume.getUuid());
        if (isPresent(index)) {
            updatePerformed(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        Object index = getIndex(resume.getUuid());
        if (isPresent(index)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            savePerformed(resume, index);
        }
    }

    @Override
    public Resume get(String uuid) {
        Object index = getIndex(uuid);
        if (isPresent(index)) {
            return getPerformed(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        Object index = getIndex(uuid);
        if (isPresent(index)) {
            deletePerformed(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract Object getIndex(String uuid);

    protected abstract void updatePerformed(Resume resume, Object index);

    protected abstract void savePerformed(Resume resume, Object index);

    protected abstract Resume getPerformed(Object index);

    protected abstract void deletePerformed(Object index);

    protected abstract boolean isPresent(Object index);
}
