package com.mysite.webapp.storage;

import com.mysite.webapp.storage.strategies.XmlStreamIOStrategy;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new XmlStreamIOStrategy()));
    }
}