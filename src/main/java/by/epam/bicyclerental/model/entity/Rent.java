package by.epam.bicyclerental.model.entity;


import java.sql.Timestamp;

public class Rent {
    private long rentId;
    private long bicycleId;
    private long rentalPointId;
    private long userId;
    private Timestamp start;
    private Timestamp finish;
    private double cost;

    public Rent() {
    }

    public Rent(long rentId, long bicycleId, long rentalPointId, long userId, Timestamp start, Timestamp finish, double cost) {
        this.rentId = rentId;
        this.bicycleId = bicycleId;
        this.rentalPointId = rentalPointId;
        this.userId = userId;
        this.start = start;
        this.finish = finish;
        this.cost = cost;
    }

    public static class Builder {
        private Rent rent = new Rent();

        public Builder rentId(long rentId) {
            rent.rentId = rentId;
            return this;
        }

        public Builder bycicleId(long bicycleId) {
            rent.bicycleId = bicycleId;
            return this;
        }

        public Builder rentalPointId(long rentalPointId) {
            rent.rentalPointId = rentalPointId;
            return this;
        }

        public Builder userId(long userId) {
            rent.userId = userId;
            return this;
        }

        public Builder start(Timestamp start) {
            rent.start = start;
            return this;
        }

        public Builder finish(Timestamp finish) {
            rent.finish = finish;
            return this;
        }

        public Builder cost(double cost) {
            rent.cost = cost;
            return this;
        }

        public Rent build() {
            return rent;
        }
    }

    public long getRentalPointId() {
        return rentalPointId;
    }

    public void setRentalPointId(long rentalPointId) {
        this.rentalPointId = rentalPointId;
    }

    public long getRentId() {
        return rentId;
    }

    public void setRentId(long rentId) {
        this.rentId = rentId;
    }

    public long getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(long bicycleId) {
        this.bicycleId = bicycleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getFinish() {
        return finish;
    }

    public void setFinish(Timestamp finish) {
        this.finish = finish;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Rent rent = (Rent) o;
        return rentId == rent.rentId &&
                bicycleId == rent.bicycleId &&
                rentalPointId == rent.rentalPointId &&
                userId == rent.userId &&
                start.equals(rent.start) &&
                finish.equals(rent.finish) &&
                cost == rent.cost;
    }

    @Override
    public int hashCode() {
        int result = (int) (rentId ^ (rentId >>> 32));
        result = 31 * result + (int) (bicycleId ^ (bicycleId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (finish != null ? finish.hashCode() : 0);
        result = 31 * result + Double.valueOf(cost).hashCode(); ;
        return result;
    }
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Rent {");
        result .append("rent id: ").append(rentId);
        result .append(", bicycle id: ").append(bicycleId);
        result .append(", user id: ").append(userId);
        result .append(", start: ").append(start);
        result .append(", finish: ").append(finish);
        result .append(", cost: ").append(cost);
        result.append('}');
        return result.toString();
    }
}

