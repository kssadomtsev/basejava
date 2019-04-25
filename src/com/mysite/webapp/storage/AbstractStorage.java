package com.mysite.webapp.storage;

import com.mysite.webapp.exception.ExistStorageException;
import com.mysite.webapp.exception.NotExistStorageException;
import com.mysite.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object pos = getPos(resume.getUuid());
        isElementExist(pos, resume.getUuid());
        updatePerformed(resume, pos);
    }

    @Override
    public void save(Resume resume) {
        Object pos = getPos(resume.getUuid());
        isElementNotExist(pos, resume.getUuid());
        savePerformed(resume, pos);
    }

    @Override
    public Resume get(String uuid) {
        Object pos = getPos(uuid);
        isElementExist(pos, uuid);
        return getPerformed(pos);
    }

    @Override
    public void delete(String uuid) {
        Object pos = getPos(uuid);
        isElementExist(pos, uuid);
        deletePerformed(pos);
    }


    public void isElementExist(Object pos, String uuid) {
        if (!isPresent(pos)) {
            throw new NotExistStorageException(uuid);
        }
    }

    public void isElementNotExist(Object pos, String uuid) {
        if (isPresent(pos)) {
            throw new ExistStorageException(uuid);
        }
    }

    protected abstract Object getPos(String uuid);

    protected abstract void updatePerformed(Resume resume, Object pos);

    protected abstract void savePerformed(Resume resume, Object pos);

    protected abstract Resume getPerformed(Object pos);

    protected abstract void deletePerformed(Object pos);

    protected abstract boolean isPresent(Object pos);
}
