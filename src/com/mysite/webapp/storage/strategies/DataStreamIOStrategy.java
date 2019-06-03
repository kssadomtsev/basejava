package com.mysite.webapp.storage.strategies;

import com.mysite.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamIOStrategy implements IOStrategy {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeCollection(dos, contacts.entrySet(), data -> {
                dos.writeUTF(data.getKey().name());
                dos.writeUTF(data.getValue());
            });
            Map<SectionType, AbstractSection> sections = r.getSections();
            writeCollection(dos, sections.entrySet(), data -> {
                SectionType sectionType = data.getKey();
                AbstractSection section = data.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = (TextSection) section;
                        dos.writeUTF(textSection.getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection listSection = (ListSection) section;
                        List<String> contentList = listSection.getContentList();
                        writeCollection(dos, contentList, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection organizationSection = (OrganizationSection) section;
                        List<Organization> organizationList = organizationSection.getOrganizationList();
                        writeCollection(dos, organizationList, organization -> {
                            Link link = organization.getLinkOrganization();
                            dos.writeUTF(link.getTitle());
                            dos.writeUTF(link.getURL() != null ? link.getURL() : "null");
                            List<Organization.Position> positionList = organization.getPositionList();
                            writeCollection(dos, positionList, position -> {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription() != null ? position.getDescription() : "null");
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
            readCollection(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readCollection(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = new TextSection(dis.readUTF());
                        resume.setSection(sectionType, textSection);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection listSection = new ListSection(getList(dis, dis::readUTF));
                        resume.setSection(sectionType, listSection);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection organizationSection = new OrganizationSection(getList(dis, () -> {
                            String urlTitle = dis.readUTF();
                            String url = dis.readUTF();
                            Link link = new Link(urlTitle, url.equals("null") ? null : url);
                            System.out.println(link);
                            List<Organization.Position> positionList = getList(dis, () -> {
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                return new Organization.Position(startDate, endDate, title, description.equals("null") ? null : description);
                            });
                            return new Organization(link, positionList);
                        }));
                        resume.setSection(sectionType, organizationSection);
                        break;
                }
            });
            return resume;
        }
    }

    private interface Writer<T> {
        void writeData(T data) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T entry : collection) {
            writer.writeData(entry);
        }
    }

    private interface Reader<T> {
        T readData() throws IOException;

    }

    private <T> List<T> getList(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.readData());
        }
        return list;
    }

    private interface FillerResume {
        void fillResume() throws IOException;
    }

    private void readCollection(DataInputStream dis, FillerResume fillerResume) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            fillerResume.fillResume();
        }
    }
}
