package com.mysite.webapp.storage;

import com.mysite.webapp.exception.StorageException;
import com.mysite.webapp.model.Resume;

import java.io.*;

public class StreamInOutStrategy implements InOutStrategy<OutputStream, InputStream> {
    @Override
    public void doWrite(Resume r, OutputStream out) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(r);
        }
    }

    @Override
    public Resume doRead(InputStream in) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
