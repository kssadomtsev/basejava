package com.mysite.webapp.storage;

import com.mysite.webapp.storage.strategies.ObjectStreamIOStrategy;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamIOStrategy()));
    }
}
