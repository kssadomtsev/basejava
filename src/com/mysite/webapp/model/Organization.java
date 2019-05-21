package com.mysite.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.mysite.webapp.util.DateUtil.NOW;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Link linkOrganization;
    private final List<Position> positionList;

    public Organization(Link linkOrganization, List<Position> positionList) {
        Objects.requireNonNull(linkOrganization, "Link organization must be defined");
        Objects.requireNonNull(positionList, "Position list must be defined");
        this.linkOrganization = linkOrganization;
        this.positionList = positionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return linkOrganization.equals(that.linkOrganization) &&
                positionList.equals(that.positionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkOrganization, positionList);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "linkOrganization=" + linkOrganization +
                ", positionList=" + positionList +
                '}';
    }

    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;

        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public Position(LocalDate startDate, String title, String description) {
            this(startDate, NOW, title, description);
        }

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
}
