package com.mysite.webapp.storage.strategies;

import com.mysite.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamIOStrategy implements IOStrategy {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeMap(dos, contacts, (d, data) -> d.writeUTF((String) data));
            Map<SectionType, AbstractSection> sections = r.getSections();
            writeMap(dos, sections, (d, data) -> {
                String className = data.getClass().getSimpleName();
                d.writeUTF(className);
                switch (className) {
                    case "TextSection":
                        TextSection textSection = (TextSection) data;
                        d.writeUTF(textSection.getContent());
                        break;
                    case "ListSection":
                        ListSection listSection = (ListSection) data;
                        List<String> contentList = listSection.getContentList();
                        writeList(d, contentList, (d1, data1) -> d1.writeUTF((String) data1));
                        break;
                    case "OrganizationSection":
                        OrganizationSection organizationSection = (OrganizationSection) data;
                        List<Organization> organizationList = organizationSection.getOrganizationList();
                        writeList(d, organizationList, (d1, data1) -> {
                            Organization organization = (Organization) data1;
                            Link link = organization.getLinkOrganization();
                            d1.writeUTF(link.getTitle());
                            d1.writeUTF(link.getURL());
                            List<Organization.Position> positionList = organization.getPositionList();
                            writeList(d1, positionList, (d2, data2) -> {
                                Organization.Position position = (Organization.Position) data2;
                                d2.writeUTF(position.getStartDate().toString());
                                d2.writeUTF(position.getEndDate().toString());
                                d2.writeUTF(position.getTitle());
                                d2.writeUTF(position.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readMap(dis, resume, (r, d) -> r.setContact(ContactType.valueOf(d.readUTF()), d.readUTF()));
            readMap(dis, resume, (r, d) -> {
                SectionType sectionType = SectionType.valueOf(d.readUTF());
                String className = d.readUTF();
                switch (className) {
                    case "TextSection":
                        TextSection textSection = new TextSection(d.readUTF());
                        r.setSection(sectionType, textSection);
                        break;
                    case "ListSection":
                        ListSection listSection = new ListSection(readList(d, DataInput::readUTF));
                        r.setSection(sectionType, listSection);
                        break;
                    case "OrganizationSection":
                        OrganizationSection organizationSection = new OrganizationSection(readList(d, d1 -> {
                            Link link = new Link(d1.readUTF(), d1.readUTF());
                            List<Organization.Position> positionList = readList(d1, d2 ->
                                    new Organization.Position(LocalDate.parse(d2.readUTF()),
                                            LocalDate.parse(d2.readUTF()), d2.readUTF(), d2.readUTF()));
                            return new Organization(link, positionList);
                        }));
                        r.setSection(sectionType, organizationSection);
                        break;
                }
            });
            return resume;
        }
    }

    private interface WriterUTF<T> {
        void writeUTF(DataOutputStream dos, T data) throws IOException;
    }

    private <K extends Enum<K>, V> void writeMap(DataOutputStream dos, Map<K, V> map, WriterUTF writerUTF) throws IOException {
        dos.writeInt(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            writerUTF.writeUTF(dos, entry.getValue());
        }
    }

    private <T> void writeList(DataOutputStream dos, List<T> list, WriterUTF writerUTF) throws IOException {
        dos.writeInt(list.size());
        for (T entry : list) {
            writerUTF.writeUTF(dos, entry);
        }
    }

    private interface ReaderUTF<T> {
        T readUTF(DataInputStream dis) throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, ReaderUTF readerUTF) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add((T) readerUTF.readUTF(dis));
        }
        return list;
    }

    private interface FillerResume {
        void fillResume(Resume resume, DataInputStream dis) throws IOException;
    }

    private void readMap(DataInputStream dis, Resume resume, FillerResume fillerResume) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            fillerResume.fillResume(resume, dis);
        }
    }
}
