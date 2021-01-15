import org.junit.Before;
import org.junit.Test;
import ro.mta.se.lab.controller.ControllerApp;
import ro.mta.se.lab.controller.ReadInputFile;
import ro.mta.se.lab.model.City;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * <h1>ControllerAppTest</h1>
 * This class is used to perform unit testing of some of the methods from
 * ControllerApp class using mock objects and mockito library.
 *
 * @author Novac Oana
 * @version 1.0
 */
public class ControllerAppTest {
    /**
     * Members description are composed by an instance of class whose methods
     * are tested and two mock objects, one ReadInputFile class and one for
     * City class.
     */
    ControllerApp instance = new ControllerApp();

    ReadInputFile mockRead;
    City mockCity;

    /**
     * This function provides help for testing by creating a list of cities
     * through mock objects.
     * @return the specific list.
     */
    public List<City> addCities(){
        List<City> listOfCities = new ArrayList<City>();
        mockCity = new City();

        mockCity.setId(2643743);
        mockCity.setName("London");
        mockCity.setCountry("GB");
        mockCity.setLatitude((float)51.50853);
        mockCity.setLongitude((float)(-0.12574));
        listOfCities.add(mockCity);

        mockCity.setId(658225);
        mockCity.setName("Helsinki");
        mockCity.setCountry("FI");
        mockCity.setLatitude((float)60.16952);
        mockCity.setLongitude((float)24.93545);
        listOfCities.add(mockCity);

        mockCity.setId(683506);
        mockCity.setName("Brasov");
        mockCity.setCountry("RO");
        mockCity.setLatitude((float)45.648609);
        mockCity.setLongitude((float)25.606131);
        listOfCities.add(mockCity);

        return listOfCities;
    }

    /**
     * Method that is called before each test.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Initialize mock objects
        mockRead = mock(ReadInputFile.class);
        mockCity = mock(City.class);

        // Define return value for method getListCities() of ReadInputFile class
        when(mockRead.getListCities()).thenReturn(addCities());

        // Member myReadInputFile of instance is set with mock object
        instance.myReadInputFile = mockRead;
    }

    /**
     * Test of method setCityList() of ControllerApp class.
     * It is called by instance and it is checked if the size of the city list
     * of the instance is equal to 3. For this to happen, in the tested method
     * should be called the getListCities() method by the mockRead objects.
     * This is checked by the method verify that implements a behavior testing.
     */
    @Test
    public void setCityListTest() {
        instance.setCityList();
        assertEquals(3,instance.cityList.size());
        verify(mockRead).getListCities();
    }

    /**
     * Test of method setCityByName() of ControllerApp class.
     * It is called the previous method which has been tested and the method
     * which is being tested now. It is given a specific name for currentCity
     * member of instance and is checked if the member value is equal to the set
     * value. The method verify is done again.
     */
    @Test
    public void setCityByNameTest() {
        instance.setCityList();
        instance.setCityByName("Brasov");
        assertEquals("Brasov",instance.currentCity.getName());
        verify(mockRead).getListCities();
    }

}
