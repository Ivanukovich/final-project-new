package by.epam.bicyclerental.model.entity;

public class RentalPoint {
    private long rentalPointId;
    private String location;

    public RentalPoint() {
    }

    public RentalPoint(long id, String location) {
        this.rentalPointId = id;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getRentalPointId() {
        return rentalPointId;
    }

    public void setRentalPointI(long rentalPointI) {
        this.rentalPointId = rentalPointI;
    }

    public static class Builder {
        private RentalPoint rentalPoint = new RentalPoint();

        public Builder rentalPointId(long rentalPointId) {
            rentalPoint.rentalPointId = rentalPointId;
            return this;
        }

        public Builder location(String location) {
            rentalPoint.location = location;
            return this;
        }

        public RentalPoint build() {
            return rentalPoint;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        RentalPoint rentalPoint = (RentalPoint) o;
        return this.rentalPointId == rentalPoint.rentalPointId &&
                location.equals(rentalPoint.location);
    }

    @Override
    public int hashCode() {
        int result = (int) (rentalPointId ^ (rentalPointId >>> 32));
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Rental point {");
        result .append("rentalPointId: ").append(rentalPointId);
        result .append(", location: ").append(location);
        result.append('}');
        return result.toString();
    }
}
