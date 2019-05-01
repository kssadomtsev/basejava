package com.mysite.webapp.model;

import java.util.List;
import java.util.Objects;

public class PlaceList extends Section {
    private final List<Place> placeList;

    public PlaceList(List<Place> placeList) {
        Objects.requireNonNull(placeList, "Place list must be defined");
        this.placeList = placeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceList)) return false;
        PlaceList placeList1 = (PlaceList) o;
        return placeList.equals(placeList1.placeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeList);
    }

    @Override
    public String toString() {
        return "PlaceList{" +
                "placeList=" + placeList +
                '}';
    }
}
