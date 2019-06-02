package com.mysite.webapp.storage;

import com.mysite.webapp.storage.strategies.DataStreamIOStrategy;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new DataStreamIOStrategy()));
    }
}
