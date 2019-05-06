package com.mysite.webapp.model;

import java.util.Objects;

public class Link {
    private final String title;
    private final String URL;

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
