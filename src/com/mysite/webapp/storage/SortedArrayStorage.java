package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.util.*;

public class SortedArrayStorage extends AbstractArrayStorage {

    /*    private static class ResumeComparator implements Comparator<Resume> {
            @Override
            public int compare(Resume o1, Resume o2) {
                return o1.getUuid().compareTo(o2.getUuid());
            }
        } */
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getPos(String uuid) {
        Resume searchKey = new Resume(uuid, "Something");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void insert(Resume resume, int index) {
        int pos = Math.abs(index + 1);
        System.arraycopy(storage, pos, storage, pos + 1, size - pos);
        storage[pos] = resume;
    }

    @Override
    protected void remove(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index + 1);
    }

    @Override
    protected List<Resume> getSortedList(Resume[] resumesAsArray) {
        List<Resume> resumes = new ArrayList<>(Arrays.asList(resumesAsArray));
        return resumes;
    }
}
