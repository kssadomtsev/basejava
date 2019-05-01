package com.mysite.webapp.model;

import java.util.List;
import java.util.Objects;

public class SectionList extends Section {
    private final List<String> contentList;

    public SectionList(List<String> contentList) {
        Objects.requireNonNull(contentList, "Content list must be defined");
        this.contentList = contentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SectionList)) return false;
        SectionList that = (SectionList) o;
        return contentList.equals(that.contentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentList);
    }

    @Override
    public String toString() {
        return "SectionList{" +
                "contentList=" + contentList +
                '}';
    }
}
