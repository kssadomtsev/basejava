package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

public interface InOutStrategy<O, I> {

    void doWrite(Resume r, O out) throws Exception;

    Resume doRead(I in) throws Exception;
}
