package by.epam.bicyclerental.model.entity;

public enum UserStatus {
    INACTIVE("inactive"),
    ACTIVE("active");
    private String statusName;

    UserStatus (String status){
        this.statusName = status;
    }

    public String getStatusName(){
        return statusName;
    }
}
