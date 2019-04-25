package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage {

    @Override
    protected String getPos(String uuid) {
        return uuid;
    }

    @Override
    protected void updatePerformed(Resume resume, Object pos) {
        storage.put((String) pos, resume);
    }

    @Override
    protected void savePerformed(Resume resume, Object pos) {
        storage.put((String) pos, resume);
    }

    @Override
    protected Resume getPerformed(Object pos) {
        return storage.get(pos);
    }

    @Override
    protected void deletePerformed(Object pos) {
        storage.remove(pos);
    }

    @Override
    protected boolean isPresent(Object pos) {
        return storage.containsKey(pos);
    }
}
