package com.mysite.webapp.storage;

import com.mysite.webapp.exception.StorageException;
import com.mysite.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.fail;

public abstract class AbsractArrayStorageTest extends AbstractStorageTest {

    protected AbsractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverSize() throws Exception {
        super.storage.clear();
        try {
            for (int i = 1; i <= AbsractArrayStorage.STORAGE_LIMIT; i++) {
                super.storage.save(new Resume());
            }

        } catch (StorageException e) {
            fail("Storage was overflow earlier that it needed " + e);
        }
        super.storage.save(new Resume());
    }
}