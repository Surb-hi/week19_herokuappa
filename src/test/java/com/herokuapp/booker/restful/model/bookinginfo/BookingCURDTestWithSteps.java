package com.herokuapp.booker.restful.model.bookinginfo;

import com.herokuapp.booker.restful.model.herokuappsteps.BookingSteps;
import com.herokuapp.booker.restful.model.testbase.TestBase;
import com.herokuapp.booker.restful.model.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class BookingCURDTestWithSteps extends TestBase {
    static String firstname = "firstname" + TestUtils.getRandomValue();
    static String lastname = "lastname" + TestUtils.getRandomValue();
    static int totalprice=898;
    static boolean depositpaid=true;
    static String additionalneeds= "additionalneeds"+ TestUtils.getRandomValue();
    static int BookingId;

    @Steps
    BookingSteps bookingSteps;

    @Title("This will create a new booking ")
    @Test
    public void test001() {
        HashMap<Object, Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");
        ValidatableResponse response =bookingSteps.createBooking(firstname,lastname,totalprice,depositpaid,dates,additionalneeds);
        response.log().all().statusCode(200);
        BookingId= response.log().all().extract().path("bookingid");
    }

    @Title("Verify that the booking added in to stack")
    @Test
    public void test002() {
        HashMap<String, Object>  bookingMap =bookingSteps.getCreatedBookingId(BookingId);
        Assert.assertThat(bookingMap, hasValue(firstname));
        System.out.println(BookingId);
    }

    @Title("This will Updated created id")
    @Test
    public void test003() {
        HashMap<Object, Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");
        ValidatableResponse response =bookingSteps.updateBooking (BookingId,firstname,lastname,totalprice,depositpaid,dates,additionalneeds);
        response.log().all().statusCode(200);
        HashMap<String, Object> bookingMap =bookingSteps.getCreatedBookingId(BookingId);
        Assert.assertThat(bookingMap, hasValue(firstname));
        System.out.println(BookingId);
    }

    @Title("This will Delate created id")
    @Test
    public void test004() {
        bookingSteps.deleteBooking(BookingId).statusCode(201);
        bookingSteps.getServicesById(BookingId).statusCode(404);
    }

}
