package courier;

import dto.DtoCourier;
import io.restassured.response.Response;
import org.junit.Test;
import request.CourierRequests;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class LoginCourierTest extends BaseCourierTest {

    @Test
    public void checkingRequiredFieldsDuringLoginPasswordTest() {
        String login = String.format("Alex%d", (int) (Math.random() * (9999 - 1111) + 1111));
        String password = "12345";
        DtoCourier courier = new DtoCourier(login, password, "Alexey");
        Response response = CourierRequests.createCourier(courier);
        response.then().assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        courier = new DtoCourier(login, "");
        response = CourierRequests.loginCourier(courier);
        response.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void checkingRequiredFieldsDuringPasswordTest() {
        String login = String.format("Alex%d", (int) (Math.random() * (9999 - 1111) + 1111));
        String password = "12345";
        DtoCourier courier = new DtoCourier(login, password, "Alexey");
        Response response = CourierRequests.createCourier(courier);
        response.then().assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        courier = new DtoCourier("", password);
        response = CourierRequests.loginCourier(courier);
        response.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
        courier = new DtoCourier(login, password);
        loginAndDeleteCourier(courier);
    }

    @Test
    public void nonExistentUsernameTest() {
        String login = String.format("Alex%d", (int) (Math.random() * (9999 - 1111) + 1111));
        String password = "12345";
        DtoCourier courier = new DtoCourier(login, password, "Alexey");
        Response response = CourierRequests.createCourier(courier);
        response.then().assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        courier = new DtoCourier(login + "1", password);
        response = CourierRequests.loginCourier(courier);
        response.then().assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }


    @Test
    public void nonExistentPasswordTest() {
        String login = String.format("Alex%d", (int) (Math.random() * (9999 - 1111) + 1111));
        String password = "12345";
        DtoCourier courier = new DtoCourier(login, password, "Alexey");
        Response response = CourierRequests.createCourier(courier);
        response.then().assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        courier = new DtoCourier(login, password + "1");
        response = CourierRequests.loginCourier(courier);
        response.then().assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}