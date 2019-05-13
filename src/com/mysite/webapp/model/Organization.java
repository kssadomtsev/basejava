package com.mysite.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {
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
}
