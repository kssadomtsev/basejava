package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

public class MapUuidStorage extends MapStorage {

    @Override
    protected String getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected void updatePerformed(Resume resume, Object index) {
        storage.put((String) index, resume);
    }

    @Override
    protected void savePerformed(Resume resume, Object index) {
        storage.put((String) index, resume);
    }

    @Override
    protected Resume getPerformed(Object index) {
        return storage.get(index);
    }

    @Override
    protected void deletePerformed(Object index) {
        storage.remove(index);
    }

    @Override
    protected boolean isPresent(Object index) {
        return storage.containsKey(index);
    }
}
