package com.mysite.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> contentList;

    public ListSection() {
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection(List<String> contentList) {
        Objects.requireNonNull(contentList, "Content list must be defined");
        this.contentList = contentList;
    }

    public List<String> getContentList() {
        return contentList;
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
        return String.join("\n", contentList);
    }

    public String toHtml() {
        final StringBuilder builder = new StringBuilder();
        contentList.forEach((val) -> builder.append("- ").append(val).append("<br/>"));
        return builder.toString();

    }
}
