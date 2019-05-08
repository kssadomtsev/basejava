package com.mysite.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {
    private final Link linkOrganization;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Organization(Link linkOrganization, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(linkOrganization, "Link organization must be defined");
        Objects.requireNonNull(startDate, "Start date must be defined");
        Objects.requireNonNull(endDate, "End date must be defined");
        this.linkOrganization = linkOrganization;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization organization = (Organization) o;
        return linkOrganization.equals(organization.linkOrganization) &&
                startDate.equals(organization.startDate) &&
                endDate.equals(organization.endDate) &&
                title.equals(organization.title) &&
                Objects.equals(description, organization.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkOrganization, startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "linkOrganization=" + linkOrganization +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
