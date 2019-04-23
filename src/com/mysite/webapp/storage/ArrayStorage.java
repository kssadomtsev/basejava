package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbsractArrayStorage {

    @Override
    protected Integer getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insert(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void remove(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected List<Resume> getSortedList(Resume[] resumes_as_array){
        List<Resume> resumes = new ArrayList<>(Arrays.asList(resumes_as_array));
        Collections.sort(resumes);
        return resumes;
    }
}

