package by.epam.bicyclerental.controller;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.impl.administrator.*;
import by.epam.bicyclerental.controller.command.impl.guest.ChangeLanguageCommand;
import by.epam.bicyclerental.controller.command.impl.guest.LogInCommand;
import by.epam.bicyclerental.controller.command.impl.guest.LogOutCommand;
import by.epam.bicyclerental.controller.command.impl.guest.RegistrationCommand;
import by.epam.bicyclerental.controller.command.impl.user.*;

import java.util.Optional;

public enum CommandType {
    REGISTRATION(new RegistrationCommand()),
    LOGIN(new LogInCommand()),
    LOGOUT(new LogOutCommand()),

    FIND_AVAILABLE_RENTAL_POINTS(new FindAvailableRentalPointsCommand()),
    FIND_AVAILABLE_BICYCLES(new FindAvailableBicyclesCommand()),
    SELECT_BICYCLE(new SelectBicycleCommand()),
    RENT_START(new RentBicycleCommand()),
    FINISH_RENT(new FinishRentCommand()),
    EDIT_PROFILE(new EditProfileCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),

    FIND_ALL_USERS(new FindAllUsersCommand()),
    FIND_ALL_RENTAL_POINTS(new FindAllRentalPointsCommand()),
    FIND_ALL_BICYCLES(new FindAllBicyclesCommand()),
    FIND_ALL_RENT_RECORDS(new FindAllRentRecordsCommand()),
    FIND_USER(new FindUserCommand()),
    FIND_RENTAL_POINT(new FindRentalPointCommand()),
    FIND_BICYCLE(new FindBicycleCommand()),
    EDIT_USER(new EditUserCommand()),
    EDIT_RENTAL_POINT(new EditRentalPointCommand()),
    EDIT_BICYCLE(new EditBicycleCommand()),
    BLOCK_USER(new BlockUserCommand()),
    UNBLOCK_USER(new UnblockUserCommand()),
    REMOVE_BICYCLE(new RemoveBicycleCommand()),
    DELETE_USER(new DeleteUserCommand()),
    DELETE_RENTAL_POINT(new DeleteRentalPointCommand()),
    DELETE_BICYCLE(new DeleteBicycleCommand()),
    DELETE_RENT_RECORD(new DeleteRentRecordCommand()),
    CREATE_ADMINISTRATOR(new AddAdministratorCommand()),
    REMOVE_ADMINISTRATOR(new RemoveAdministratorCommand()),
    ADD_BICYCLE_TO_RENTAL_POINT(new AddBicycleToRentalPointCommand()),
    ADD_USER(new AddNewUserCommand()),
    ADD_BICYCLE(new AddNewBicycleCommand()),
    ADD_RENTAL_POINT(new AddNewRentalPointCommand()),
    LANGUAGE_CHANGE(new ChangeLanguageCommand());

    private final Command command;

    CommandType(Command command){
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }

    public static Optional<Command> provideCommand(String command){
        Optional<Command> resultCommand;
        if(command == null || command.isEmpty()){
            return Optional.empty();
        }
        try{
            CommandType commandType = CommandType.valueOf(command.toUpperCase());
            resultCommand = Optional.of(commandType.getCommand());
        }
        catch (IllegalArgumentException exp){
            resultCommand = Optional.empty();
        }
        return resultCommand;
    }
}
