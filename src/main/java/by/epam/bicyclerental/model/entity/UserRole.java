package by.epam.bicyclerental.model.entity;

public enum UserRole {
    ADMINISTRATOR ("administrator"),
    USER ("user");
    private final String roleName;

    UserRole (String role){
        this.roleName = role;
    }

    public String getRoleName(){
        return roleName;
    }

}
