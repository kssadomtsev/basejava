package com.mysite.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> organizationList;

    public OrganizationSection(){
    }

    public OrganizationSection(Organization... items) {
        this(Arrays.asList(items));
    }

    public OrganizationSection(List<Organization> organizationList) {
        Objects.requireNonNull(organizationList, "Organization list must be defined");
        this.organizationList = organizationList;
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
}
