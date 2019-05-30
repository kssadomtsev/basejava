package com.mysite.webapp.storage;

import com.mysite.webapp.storage.strategies.JsonStreamIOStrategy;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new JsonStreamIOStrategy()));
    }
}
