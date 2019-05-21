package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.io.IOException;

public interface InOutStrategy<O, I> {

    void doWrite(Resume r, O out) throws IOException;

    Resume doRead(I in) throws IOException;
}
