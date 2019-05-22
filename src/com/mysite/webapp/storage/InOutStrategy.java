package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface InOutStrategy{

    void doWrite(Resume r, OutputStream out) throws IOException;

    Resume doRead(InputStream in) throws IOException;
}
