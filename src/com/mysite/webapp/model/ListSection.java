package com.mysite.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private final List<String> contentList;

    public ListSection(List<String> contentList) {
        Objects.requireNonNull(contentList, "Content list must be defined");
        this.contentList = contentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection)) return false;
        ListSection that = (ListSection) o;
        return contentList.equals(that.contentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentList);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "contentList=" + contentList +
                '}';
    }
}
