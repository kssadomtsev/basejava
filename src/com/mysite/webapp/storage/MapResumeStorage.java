package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected Resume getKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return (Resume) key;
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove(((Resume) key).getUuid());
    }

    @Override
    protected boolean isPresent(Object key) {
        return key != null;
    }
}
