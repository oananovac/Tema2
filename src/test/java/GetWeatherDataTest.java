import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.mta.se.lab.controller.GetWeatherData;

import static org.junit.Assert.assertEquals;

/**
 * <h1>GetWeatherDataTest</h1>
 * This class is used to perform unit testing of an independent class, namely
 * GetWeatherData.
 *
 * @author Novac Oana
 * @version 1.0
 */
public class GetWeatherDataTest {
    /**
     * Member description
     * myClass member represents an instance of the class being tested and the
     * other elements help to perform the tests.
     */
    GetWeatherData myClass;
    String myJsonString = "{ \"coord\": {\"lon\":-0.5805, \"lat\": 44.8404},"
            + "\"weather\": [{\"id\": 804,\"main\": \"Clouds\",\"description\":"
            + "\"overcast clouds\",\"icon\": \"04n\"}],\"base\": \"stations\","
            + "\"main\": {\"temp\": 282.99,\"feels_like\": 278.75,\"temp_min\":"
            + "282.59,\"temp_max\": 283.15,\"pressure\": 1025,\"humidity\": 93"
            + "},\"visibility\": 10000,\"wind\": {\"speed\": 5.66,\"deg\": 310"
            + "},\"clouds\": {\"all\": 90},\"dt\": 1610652076,\"sys\": {\"typ"
            + "e\": 1,\"id\": 6450,\"country\": \"FR\",\"sunrise\": 1610609811,"
            + "\"sunset\": 1610642732},\"timezone\": 3600,\"id\": 3031582,"
            + "\"name\": \"Bordeaux\",\"cod\":200}";
    String urlString = "http://api.openweathermap.org/data/2.5/weather?id=30315"
            + "82&appid=ed5023c5edf0f36887d312934e014c0d";
    String apiKey = "ed5023c5edf0f36887d312934e014c0d";
    String cityId = "3031582";

    /**
     * Method that is called before each test.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        myClass = new GetWeatherData();
        System.out.println("Before");
    }

    /**
     * Method called after each test.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        System.out.println("After");
    }

    /**
     * This function represents a test for parseData(String) method of
     * GetWeatherData class.
     */
    @Test
    public void parseData() {
        String[] result = new String[]{"overcast clouds", "Clouds", "04n",
                "282.99", "93.0", "5.66"};
        assertEquals(result, myClass.parseData(myJsonString));
    }

    /**
     * Test for function that returns the url string used for connection.
     */
    @Test
    public void urlStringById() {
        assertEquals(urlString, myClass.getUrlStringById(cityId, apiKey));
    }

    /**
     * Test for method obtainJSONData(). It established the connection with API
     * and returns a string buffer with json data.
     */
    @Test
    public void obtainJSONData() {
        String myClassString = myClass.obtainJSONData(apiKey,
                urlString).toString();
        String mySeg = (myClassString.substring(myClassString.length()
                - 20)).substring(0, 8);
        assertEquals("Bordeaux", mySeg);
    }
}