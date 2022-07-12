package by.epam.bicyclerental.model.entity;


import java.sql.Timestamp;

public class RentRecord {
    private long rentRecordId;
    private long bicycleId;
    private long rentalPointId;
    private long userId;
    private Timestamp start;
    private Timestamp finish;
    private double cost;

    public RentRecord() {
    }

    public RentRecord(long rentRecordId, long bicycleId, long rentalPointId, long userId, Timestamp start, Timestamp finish, double cost) {
        this.rentRecordId = rentRecordId;
        this.bicycleId = bicycleId;
        this.rentalPointId = rentalPointId;
        this.userId = userId;
        this.start = start;
        this.finish = finish;
        this.cost = cost;
    }

    public static class Builder {
        private RentRecord rentRecord = new RentRecord();

        public Builder rentId(long rentId) {
            rentRecord.rentRecordId = rentId;
            return this;
        }

        public Builder bycicleId(long bicycleId) {
            rentRecord.bicycleId = bicycleId;
            return this;
        }

        public Builder rentalPointId(long rentalPointId) {
            rentRecord.rentalPointId = rentalPointId;
            return this;
        }

        public Builder userId(long userId) {
            rentRecord.userId = userId;
            return this;
        }

        public Builder start(Timestamp start) {
            rentRecord.start = start;
            return this;
        }

        public Builder finish(Timestamp finish) {
            rentRecord.finish = finish;
            return this;
        }

        public Builder cost(double cost) {
            rentRecord.cost = cost;
            return this;
        }

        public RentRecord build() {
            return rentRecord;
        }
    }

    public long getRentalPointId() {
        return rentalPointId;
    }

    public void setRentalPointId(long rentalPointId) {
        this.rentalPointId = rentalPointId;
    }

    public long getRentRecordId() {
        return rentRecordId;
    }

    public void setRentRecordId(long rentRecordId) {
        this.rentRecordId = rentRecordId;
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
        RentRecord rentRecord = (RentRecord) o;
        return rentRecordId == rentRecord.rentRecordId &&
                bicycleId == rentRecord.bicycleId &&
                rentalPointId == rentRecord.rentalPointId &&
                userId == rentRecord.userId &&
                start.equals(rentRecord.start) &&
                finish.equals(rentRecord.finish) &&
                cost == rentRecord.cost;
    }

    @Override
    public int hashCode() {
        int result = (int) (rentRecordId ^ (rentRecordId >>> 32));
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
        result .append("rent id: ").append(rentRecordId);
        result .append(", bicycle id: ").append(bicycleId);
        result .append(", user id: ").append(userId);
        result .append(", start: ").append(start);
        result .append(", finish: ").append(finish);
        result .append(", cost: ").append(cost);
        result.append('}');
        return result.toString();
    }
}

