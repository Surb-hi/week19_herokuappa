package com.herokuapp.booker.restful.model.cucumber.steps;

import com.herokuapp.booker.restful.model.herokuappsteps.BookingSteps;
import com.herokuapp.booker.restful.model.utils.TestUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class MyStepdefs {

    static String firstname = "firstname" + TestUtils.getRandomValue();
    static String lastname = "lastname" + TestUtils.getRandomValue();
    static int totalprice = 898;
    static boolean depositpaid = true;
    static String additionalneeds = "additionalneeds" + TestUtils.getRandomValue();
    static int BookingId;
    static ValidatableResponse response;

    @Steps
    BookingSteps bookingSteps;

    @When("^User sends a GET request to list endpoint$")
    public void userSendsAGETRequestToListEndpoint() {
        response = bookingSteps.getAllUsers();
    }

    @Then("^User get back a valid status code (\\d+)$")
    public void userGetBackAValidStatusCode(int code) {
        response.log().all().statusCode(code);
    }

    @When("^User sends a Post request to list endpoint$")
    public void userSendsAPostRequestToListEndpoint() {
        HashMap<Object, Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");
         response =bookingSteps.createBooking(firstname,lastname,totalprice,depositpaid,dates,additionalneeds);
        response.log().all().statusCode(200);
        BookingId= response.log().all().extract().path("bookingid");
    }

    @And("^Verify that user created sucessfully$")
    public void verifyThatUserCreatedSucessfully() {
        HashMap<String, Object>  bookingMap =bookingSteps.getCreatedBookingId(BookingId);
        Assert.assertThat(bookingMap, hasValue(firstname));
        System.out.println(BookingId);
    }

    @When("^User sends a patch request to list endpoint$")
    public void userSendsAPatchRequestToListEndpoint() {
        HashMap<Object, Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");
        firstname="firstname"+TestUtils.getRandomValue();
        response =bookingSteps.updateBooking (BookingId,firstname,lastname,totalprice,depositpaid,dates,additionalneeds);
        HashMap<String, Object> bookingMap =bookingSteps.getCreatedBookingId(BookingId);
        Assert.assertThat(bookingMap, hasValue(firstname));
        System.out.println(BookingId);
    }

    @When("^User sends a delete request to list endpoint$")
    public void userSendsADeleteRequestToListEndpoint() {
        response=bookingSteps.deleteBooking(BookingId);
        //response=bookingSteps.getServicesById(BookingId);
    }
}
