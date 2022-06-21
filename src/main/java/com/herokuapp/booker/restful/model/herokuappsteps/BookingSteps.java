package com.herokuapp.booker.restful.model.herokuappsteps;

import com.herokuapp.booker.restful.model.constants.EndPoints;
import com.herokuapp.booker.restful.model.herokuappinfo.BookingPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class BookingSteps {
    @Step("Creating Booking with perameter")
    public ValidatableResponse createBooking(String firstname,String lastname,int totalprice,boolean depositpaid,HashMap<Object, Object> Bookingdates,String additionalneeds) {

        HashMap<Object, Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(dates);
        bookingPojo.setAdditionalneeds(additionalneeds);

        return SerenityRest.given().log().all()
                 .contentType(ContentType.JSON)
                .header("Authorization", "Bearer e367431b1feb1bd")
                .body(bookingPojo)
                .when()
                .post(EndPoints.CREATE_USER)
                .then();
    }
    @Step("Getting the Booking information from Id")
    public HashMap<String, Object> getCreatedBookingId(int BookingId) {

        HashMap<String, Object> bookingMap = SerenityRest.given().log().all()
                .header("Authorization", "Bearer e367431b1feb1bd")
                .when()
                .pathParam("BookingId",BookingId)
                .get(EndPoints.CREATED_USER_ID)
                .then()
                .statusCode(200)
                .extract()
                .path("");
        return bookingMap;
    }
    @Step("Update Booking with perameter")
    public ValidatableResponse updateBooking(int BookingId,String firstname,String lastname,int totalprice,boolean depositpaid,HashMap<Object, Object> Bookingdates,String additionalneeds){

        HashMap<Object, Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(Bookingdates);
        bookingPojo.setAdditionalneeds(additionalneeds);

        return SerenityRest.given().log().all()
                .auth().preemptive().basic("admin","password123")
                .header("Content-Type", "application/json")
                .body(bookingPojo)
                .when()
                .pathParam("BookingId",BookingId)
                .patch(EndPoints.UPDATE_USER_BY_ID)
                .then();
    }
    @Step("Deleting Booking information with -")
    public ValidatableResponse deleteBooking(int BookingId) {
        return SerenityRest.given().log().all()
                .auth().preemptive().basic("admin","password123")
                .pathParam("BookingId",BookingId)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then();
    }

    @Step("Getting -information with ")
    public ValidatableResponse getServicesById(int BookingId) {
        return SerenityRest.given().log().all()
                .auth().preemptive().basic("admin","password123")
                .pathParam("BookingId",BookingId)
                .when()
                .get(EndPoints.CREATED_USER_ID)
                .then();
    }
    @Step("Getting All students information")
    public ValidatableResponse getAllUsers(){
        return SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_USER)
                .then();
    }
}
