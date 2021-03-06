package com.mysite.webapp;

import com.mysite.webapp.model.Resume;
import com.mysite.webapp.storage.SortedArrayStorage;
import com.mysite.webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Interactive test for com.mysite.webapp.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private final static Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save1 name | save2 uuid name | delete uuid | update uuid name | get uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save1":
                    r = new Resume(params[1]);
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "save2":
                    r = new Resume(params[1], params[2]);
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "delete":
                    ARRAY_STORAGE.delete(params[1]);
                    printAll();
                    break;
                case "update":
                    r = new Resume(params[1], params[2]);
                    ARRAY_STORAGE.update(r);
                    printAll();
                    break;
                case "get":
                    System.out.println(ARRAY_STORAGE.get(params[1]));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        List<Resume> all = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}