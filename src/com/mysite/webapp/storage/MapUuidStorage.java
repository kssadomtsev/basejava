package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage {

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        storage.put((String) key, resume);
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        storage.put((String) key, resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get(key);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove(key);
    }

    @Override
    protected boolean isPresent(Object key) {
        return storage.containsKey(key);
    }
}
