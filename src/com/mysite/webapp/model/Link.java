package com.mysite.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String URL;

    public Link() {
    }

    public Link(String title, String URL) {
        Objects.requireNonNull(title, "Title must be defined");
        this.title = title;
        this.URL = URL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;
        Link link = (Link) o;
        return title.equals(link.title) &&
                Objects.equals(URL, link.URL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, URL);
    }

    @Override
    public String toString() {
        return "Link{" +
                "title='" + title + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }
}
