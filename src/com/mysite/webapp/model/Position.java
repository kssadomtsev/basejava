package com.mysite.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Position {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "Start date must be defined");
        Objects.requireNonNull(endDate, "End date must be defined");
        Objects.requireNonNull(title, "Title must be defined");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return startDate.equals(position.startDate) &&
                endDate.equals(position.endDate) &&
                title.equals(position.title) &&
                Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        return "Position{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
