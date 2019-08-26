package com.mysite.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement(name="Resume")
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;
    public static final Resume NEW = new Resume();

    static {
        NEW.setSection(SectionType.EXPERIENCE, OrganizationSection.NEW);
        NEW.setSection(SectionType.EDUCATION,  OrganizationSection.NEW);
        System.out.println("Creating new resume");
        System.out.println(NEW);
    }

    // Unique identifier
    private String uuid;
    private String fullName;
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(fullName, "FullName must be defined");
        Objects.requireNonNull(uuid, "UUID must be defined");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public AbstractSection getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void setSection(SectionType sectionType, AbstractSection section) {
        sections.put(sectionType, section);
    }

    public void setContact(ContactType contactType, String contact) {
        contacts.put(contactType, contact);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contacts='" + contacts.toString() + '\'' +
                '}';
    }

    @Override
    public int compareTo(Resume o) {
        final int res = fullName.compareTo(o.fullName);
        return res != 0 ? res : uuid.compareTo(o.uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName) &&
                Objects.equals(sections, resume.sections) &&
                Objects.equals(contacts, resume.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sections, contacts);
    }
}
