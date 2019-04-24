package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

public class MapResumeStorage extends MapStorage {

    @Override
    protected Resume getIndex(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void updatePerformed(Resume resume, Object index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void savePerformed(Resume resume, Object index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getPerformed(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void deletePerformed(Object resume) {
        storage.remove(((Resume) resume).getUuid());
    }

    @Override
    protected boolean isPresent(Object resume) {
        return resume != null;
    }
}
