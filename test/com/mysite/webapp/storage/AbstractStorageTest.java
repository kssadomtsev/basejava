package com.mysite.webapp.storage;

import com.mysite.webapp.ResumeTestData;
import com.mysite.webapp.exception.ExistStorageException;
import com.mysite.webapp.exception.NotExistStorageException;
import com.mysite.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_4 = "uuid4";
    private static final String DUMMY = "dummy";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;
    private static final Resume RESUME_DUMMY;

    static {
        RESUME_1 = ResumeTestData.RESUME_1;
        RESUME_2 = ResumeTestData.RESUME_2;
        RESUME_3 = ResumeTestData.RESUME_3;
        RESUME_4 = ResumeTestData.RESUME_4;
        RESUME_DUMMY = new Resume(DUMMY);
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        final Resume resume = new Resume(UUID_1, DUMMY);
        storage.update(resume);
        assertSame(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_DUMMY);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(DUMMY);
    }

    @Test
    public void getAll() throws Exception {
        final List<Resume> resumes = storage.getAllSorted();
        final List<Resume> resumesTested = new ArrayList<Resume>();
        resumesTested.add(RESUME_1);
        resumesTested.add(RESUME_2);
        resumesTested.add(RESUME_3);
        Collections.sort(resumesTested);
        assertEquals(resumesTested, resumes);
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(DUMMY);
    }
}