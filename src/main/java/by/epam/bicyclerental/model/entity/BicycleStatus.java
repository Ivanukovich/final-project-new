package by.epam.bicyclerental.model.entity;

public enum BicycleStatus {
    FREE("free"),
    ACTIVE("active"),
    INACTIVE("inactive");
    private String statusName;

    BicycleStatus (String status){
        this.statusName = status;
    }

    public String getStatusName(){
        return statusName;
    }
}
