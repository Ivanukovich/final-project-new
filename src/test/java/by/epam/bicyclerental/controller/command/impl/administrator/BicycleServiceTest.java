package by.epam.bicyclerental.controller.command.impl.administrator;

import by.epam.bicyclerental.controller.Router;
import by.epam.bicyclerental.exception.CommandException;
import by.epam.bicyclerental.exception.ServiceException;
import by.epam.bicyclerental.model.entity.Bicycle;
import by.epam.bicyclerental.model.entity.BicycleStatus;
import by.epam.bicyclerental.model.service.BicycleService;
import by.epam.bicyclerental.model.service.impl.BicycleServiceImpl;
import org.mockito.Mockito;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BicycleServiceTest {
    HttpServletRequest mock;
    FindAllBicyclesCommand command;

    @BeforeMethod
    public void setUp(){
        command = new FindAllBicyclesCommand();
        mock = Mockito.mock(HttpServletRequest.class);
    }

    @DataProvider(name = "bicycles")
    public Object[][] bicycleData() {
        return new Object[][]{
                {List.of(new Bicycle(1, "Bicycle_model_1", BicycleStatus.FREE)), "/jsp/bicycle_list.jsp"},
        };
    }

    @Test(dataProvider = "bicycles")
    public void findAllBicyclesCommandTest(List<Bicycle> bicycles, String expected) throws CommandException {
        Router router = command.execute(mock);
        assertEquals(router.getPagePath(), expected);
    }
}
