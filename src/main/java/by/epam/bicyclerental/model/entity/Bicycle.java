package by.epam.bicyclerental.model.entity;

public class Bicycle {
    private long bicycleId;
    private String model;
    private BicycleStatus status;

    public Bicycle() {

    }

    public Bicycle(long bicycleId, String model, BicycleStatus status, String location) {
        this.bicycleId = bicycleId;
        this.model = model;
        this.status = status;
    }

    public long getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(long bicycleId) {
        this.bicycleId = bicycleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BicycleStatus getStatus() {
        return status;
    }

    public void setStatus(BicycleStatus status) {
        this.status = status;
    }

    public static class Builder {
        private Bicycle bicycle = new Bicycle();

        public Builder bicycleId(long bicycleId) {
            bicycle.bicycleId = bicycleId;
            return this;
        }

        public Builder status(BicycleStatus status) {
            bicycle.status = status;
            return this;
        }

        public Builder model(String model) {
            bicycle.model = model;
            return this;
        }


        public Bicycle build() {
            return bicycle;
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
        Bicycle bicycle = (Bicycle) o;
        return bicycleId == bicycle.bicycleId &&
                model.equals(bicycle.model) &&
                status.equals(bicycle.status);
    }

    @Override
    public int hashCode() {
        int result = (int) (bicycleId ^ (bicycleId >>> 32));
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Bicycle {");
        result .append("bicycleId: ").append(bicycleId);
        result .append(", model: ").append(model);
        result .append(", status: ").append(status);
        result.append('}');
        return result.toString();
    }
}
