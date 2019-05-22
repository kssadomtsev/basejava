package com.mysite.webapp.storage;

import com.mysite.webapp.exception.StorageException;
import com.mysite.webapp.model.Resume;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private InOutStrategy strategy;
    private String strategyName;
    private static final List<String> strategies = Arrays.asList("stream");

    protected PathStorage(String dir, String strategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(strategy, "strategy must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        if (!strategies.contains(strategy.toLowerCase())) {
            throw new IllegalArgumentException(strategy + " is not correct strategy");
        }
        if (strategy.toLowerCase().equals("stream")) {
            this.strategy = new StreamInOutStrategy();
            this.strategyName = "stream";
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", directory.getFileName().toString(), e);
        }
    }

    @Override
    public int size() {
        try {
            return Math.toIntExact(Files.list(directory).count());
        } catch (IOException e) {
            throw new StorageException("Directory read error", directory.getFileName().toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path Path) {
        try {
            if (strategyName.equals("stream")) {
                strategy.doWrite(r, Files.newOutputStream(Path));
            }
        } catch (IOException e) {
            throw new StorageException("Path write error " + Path.getFileName().toString(), r.getUuid(), e);
        }
    }

    @Override
    protected boolean isPresent(Path Path) {
        return Files.exists(Path);
    }

    @Override
    protected void doSave(Resume r, Path Path) {
        try {
            Files.createFile(Path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path " + directory.getFileName().toString(), Path.getFileName().toString(), e);
        }
        doUpdate(r, Path);
    }

    @Override
    protected Resume doGet(Path Path) {
        try {
            if (strategyName.equals("stream")) {
                return strategy.doRead(Files.newInputStream(Path));
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new StorageException("Cannot get path", Path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path Path) {
        try {
            Files.delete(Path);
        } catch (IOException e) {
            throw new StorageException("Cannot delete path", Path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> list = new ArrayList<>(size());
        try {
            Files.list(directory).forEach(item -> list.add(doGet(item)));
        } catch (IOException e) {
            throw new StorageException("Directory read error", directory.getFileName().toString(), e);
        }
        return list;
    }
}