package com.mysite.webapp.storage;

import com.mysite.webapp.exception.StorageException;
import com.mysite.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] listFiles = directory.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        return directory.listFiles().length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isPresent(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }

    }

    @Override
    protected void doDelete(File file) {
        if (file.delete()) {
            System.out.println("Deleting " + file.getName());
        } else {
            throw new StorageException("Failed delete file", file.getName());
        }

    }

    @Override
    protected List<Resume> getList() {
        ArrayList<Resume> resumes = new ArrayList<>();
        File[] listFiles = directory.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                try {
                    resumes.add(doRead(file));
                } catch (IOException e) {
                    throw new StorageException("IO error", file.getName(), e);
                }
            }
        }
        return resumes;
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}
