package com.mysite.webapp.model;

import java.util.Objects;

public class SectionText extends Section {
    private final String content;

    public SectionText(String content) {
        Objects.requireNonNull(content, "Content must be defined");
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SectionText)) return false;
        SectionText that = (SectionText) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return "SectionText{" +
                "content='" + content + '\'' +
                '}';
    }
}
