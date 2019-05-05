package com.mysite.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Place {
    private final Link linkPlace;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Place(Link linkPlace, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(linkPlace, "Link place must be defined");
        Objects.requireNonNull(startDate, "Start date must be defined");
        Objects.requireNonNull(endDate, "End date must be defined");
        this.linkPlace = linkPlace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;
        Place place = (Place) o;
        return linkPlace.equals(place.linkPlace) &&
                startDate.equals(place.startDate) &&
                endDate.equals(place.endDate) &&
                title.equals(place.title) &&
                Objects.equals(description, place.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkPlace, startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        return "Place{" +
                "linkPlace=" + linkPlace +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
