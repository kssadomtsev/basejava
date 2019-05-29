package com.mysite.webapp.storage.strategies;

import com.mysite.webapp.model.*;
import com.mysite.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamIOStrategy implements IOStrategy {
    private XmlParser xmlParser;

    public XmlStreamIOStrategy() {
        xmlParser = new XmlParser(
                Resume.class, Organization.class, Link.class,
                OrganizationSection.class, TextSection.class, ListSection.class, Organization.Position.class);
    }

    @Override
    public void doWrite(Resume resume, OutputStream out) throws IOException {
        try (Writer w = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, w);
        }
    }

    @Override
    public Resume doRead(InputStream in) throws IOException {
        try (Reader r = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
