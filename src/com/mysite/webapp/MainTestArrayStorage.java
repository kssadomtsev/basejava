package com.mysite.webapp;

import com.mysite.webapp.exception.NotExistStorageException;
import com.mysite.webapp.model.Resume;
import com.mysite.webapp.storage.ListStorage;
import com.mysite.webapp.storage.Storage;

/**
 * Test for your com.mysite.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private final static Storage ARRAY_STORAGE = new ListStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid1");
        final Resume r2 = new Resume("uuid2");
        final Resume r3 = new Resume("uuid3");
        final Resume r4 = new Resume("uuid4");
        final Resume r5 = new Resume("uuid5");

        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r3);

        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        try {
            System.out.println("Get r5: " + ARRAY_STORAGE.get(r5.getUuid()));
        } catch (NotExistStorageException e) {
            System.out.println(e);
        }

        try {
            System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        } catch (NotExistStorageException e) {
            System.out.println(e);
        }

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        ARRAY_STORAGE.delete(r4.getUuid());

        try {
            ARRAY_STORAGE.delete(r5.getUuid());
        } catch (NotExistStorageException e) {
            System.out.println(e);
        }


        ARRAY_STORAGE.update(r2);

        try {
            ARRAY_STORAGE.update(r5);
        } catch (NotExistStorageException e) {
            System.out.println(e);
        }


        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
