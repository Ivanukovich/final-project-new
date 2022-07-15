package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.controller.command.Command;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.RentRecord;
import by.epam.bicyclerental.model.service.BicycleService;
import by.epam.bicyclerental.model.service.RentRecordService;
import by.epam.bicyclerental.model.service.RentalPointService;
import by.epam.bicyclerental.model.service.UserService;
import by.epam.bicyclerental.model.service.impl.BicycleServiceImpl;
import by.epam.bicyclerental.model.service.impl.RentRecordServiceImpl;
import by.epam.bicyclerental.model.service.impl.RentalPointServiceImpl;
import by.epam.bicyclerental.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static by.epam.bicyclerental.controller.command.PagePath.RENT_RECORD_LIST_PAGE;
import static by.epam.bicyclerental.controller.command.Parameter.*;

public class FindAllRentRecordsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final BicycleService bicycleService = BicycleServiceImpl.getInstance();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final RentalPointService rentalPointService = RentalPointServiceImpl.getInstance();
    private static final RentRecordService rentRecordService = RentRecordServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            List<RentRecord> rentRecordList = rentRecordService.findAllRentRecords();
            Map<Long, String> userLogins = userService.findUserLogins();
            Map<Long, String> rentalPointLocations = rentalPointService.findRentalPointLocations();
            Map<Long, String> bicycleModels = bicycleService.findBicycleModels();
            request.setAttribute(RENT_RECORD_LIST, rentRecordList);
            request.setAttribute(USER_LOGINS, userLogins);
            request.setAttribute(RENTAL_POINT_LOCATIONS, rentalPointLocations);
            request.setAttribute(BICYCLE_MODELS, bicycleModels);
            return new Router(RENT_RECORD_LIST_PAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException("Exception in FindAllRentRecordsCommand: ", e);
        }
    }
}
