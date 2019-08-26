package com.mysite.webapp.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private static final LocalDate today = java.time.LocalDate.now();
    public static final OrganizationSection NEW =  new OrganizationSection(Arrays.asList(
            new Organization(new Link("", ""),
                    Arrays.asList(new Organization.Position(today, today, "", "")))));

    private List<Organization> organizationList;

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... items) {
        this(Arrays.asList(items));
    }

    public OrganizationSection(List<Organization> organizationList) {
        Objects.requireNonNull(organizationList, "Organization list must be defined");
        this.organizationList = organizationList;
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationSection)) return false;
        OrganizationSection organizationSection1 = (OrganizationSection) o;
        return organizationList.equals(organizationSection1.organizationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationList);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "organizationList=" + organizationList +
                '}';
    }

    public String toHtml() {
        return toString();
    }
}
