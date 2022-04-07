package by.epam.bicyclerental.controller;

import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.controller.command.impl.administrator.*;
import by.epam.bicyclerental.controller.command.impl.guest.LogInCommand;
import by.epam.bicyclerental.controller.command.impl.guest.RegistrationCommand;
import by.epam.bicyclerental.controller.command.impl.user.FinishRentCommand;
import by.epam.bicyclerental.controller.command.impl.user.RentBicycleCommand;

import java.util.Optional;

public enum CommandType {
    REGISTRATION(new RegistrationCommand()),
    LOGIN(new LogInCommand()),
    FIND_ALL_USERS(new FindAllUsersCommand()),
    FIND_ALL_RENTAL_POINTS(new FindAllRentalPointsCommand()),
    EDIT_RENTAL_POINTS(new FindAllRentalPointsWithBicyclesCommand()),
    BLOCK_USER(new BlockUserCommand()),
    UNBLOCK_USER(new UnblockUserCommand()),
    REMOVE_BICYCLE(new RemoveBicycleCommand()),
    DELETE_USER(new DeleteUserCommand()),
    CREATE_ADMINISTRATOR(new AddAdministratorCommand()),
    ADD_BICYCLE(new AddBicycleToRentalPointCommand()),
    ADD_USER(new AddNewUserCommand()),
    RENT_START(new RentBicycleCommand()),
    FIND_FREE_BICYCLES(new FindAllFreeBicyclesCommand()),
    SELECT_BICYCLE(new FindBicycleCommand()),
    FINISH_RENT(new FinishRentCommand());

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
        }catch (IllegalArgumentException exp){
            resultCommand = Optional.empty();
        }
        return resultCommand;
    }
}
