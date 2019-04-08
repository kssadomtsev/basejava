package com.mysite.webapp;

import com.mysite.webapp.model.Resume;
import com.mysite.webapp.storage.ArrayStorage;
import com.mysite.webapp.storage.Storage;

/**
 * Test for your com.mysite.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private final static Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume();
        r1.setUuid("uuid1");
        final Resume r2 = new Resume();
        r2.setUuid("uuid2");
        final Resume r3 = new Resume();
        r3.setUuid("uuid3");
        final Resume r4 = new Resume();
        r4.setUuid("uuid4");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Get r4: " + ARRAY_STORAGE.get(r4.getUuid()));

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        ARRAY_STORAGE.delete(r4.getUuid());
        ARRAY_STORAGE.update(r2);
        ARRAY_STORAGE.update(r4);
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
