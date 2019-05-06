package com.mysite.webapp.model;

import java.util.List;
import java.util.Objects;

public class PlaceSection extends AbstractSection {
    private final List<Place> placeList;

    public PlaceSection(List<Place> placeList) {
        Objects.requireNonNull(placeList, "Place list must be defined");
        this.placeList = placeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceSection)) return false;
        PlaceSection placeSection1 = (PlaceSection) o;
        return placeList.equals(placeSection1.placeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeList);
    }

    @Override
    public String toString() {
        return "PlaceSection{" +
                "placeList=" + placeList +
                '}';
    }
}
