package com.mysite.webapp.storage.strategies;

import com.mysite.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IOStrategy {

    void doWrite(Resume resume, OutputStream out) throws IOException;

    Resume doRead(InputStream in) throws IOException;
}
