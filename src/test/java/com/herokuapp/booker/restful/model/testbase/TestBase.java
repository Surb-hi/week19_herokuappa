package com.herokuapp.booker.restful.model.testbase;


import com.herokuapp.booker.restful.model.utils.PropertyReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;


public class TestBase {
    public static PropertyReader propertyReader;

    @BeforeClass
    public static void init() {
        propertyReader = PropertyReader.getInstance();
        RestAssured.baseURI = propertyReader.getProperty("baseUrl");
       // RestAssured.basePath = Path.USERS;

        //RestAssured.port = Integer.parseInt(propertyReader.getProperty("port"));
    }

}
