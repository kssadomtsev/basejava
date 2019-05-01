package com.mysite.webapp.model;

import java.util.Objects;

public class Link {
    private final String title;
    private final String URI;

    public Link(String title, String URI) {
        Objects.requireNonNull(title, "Title must be defined");
        this.title = title;
        this.URI = URI;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;
        Link link = (Link) o;
        return title.equals(link.title) &&
                Objects.equals(URI, link.URI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, URI);
    }

    @Override
    public String toString() {
        return "Link{" +
                "title='" + title + '\'' +
                ", URI='" + URI + '\'' +
                '}';
    }
}
