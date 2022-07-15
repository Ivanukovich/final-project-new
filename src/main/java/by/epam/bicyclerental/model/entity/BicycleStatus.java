package by.epam.bicyclerental.model.entity;

public enum BicycleStatus {
    FREE("free"),
    RENTED("rented"),
    INACTIVE("inactive");
    private final String statusName;

    BicycleStatus (String status){
        this.statusName = status;
    }

    public String getStatusName(){
        return statusName;
    }
}
