package com.mysite.webapp.storage;

import com.mysite.webapp.storage.strategies.ObjectStreamIOStrategy;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamIOStrategy()));
    }
}