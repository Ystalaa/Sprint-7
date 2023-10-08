package CourierTest;
import Client.CourierClient;
import Model.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class CourierLoginTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Курьер входит в систему.")
    @Description("Проверка кода статуса при входе курьера в систему (успешный запрос).")
    public void authorizationTest(){
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("kotik2023");
        courier.setPassword("12345");
        Response response = clientStep.sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("id", Matchers.notNullValue()).and().statusCode(200);

    }

    @Test
    @DisplayName("Курьер заходит в систему без логина.")
    @Description("Проверка кода статуса, когда курьер заходит в систему без логина (неверный запрос).")
    public void authorizationWithoutLoginTest(){
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setPassword("12345");
        Response response = clientStep.sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Недостаточно данных для входа")).
                and().statusCode(400);

    }
// Тест падает по причине бага в  Авторизации
    @Test
    @DisplayName("Курьер входит в систему без пароля.")
    @Description("Проверка кода статуса, когда курьер входит в систему без пароля (неверный запрос).")
    public void authorizationWithoutPasswordTest(){
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("kotik2023");
        Response response = clientStep.sendPostRequestApiV1CourierLogin(courier);
        ValidatableResponse body = response.then().log().all()
                .assertThat().statusCode(400).and().body("message", Matchers.is("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Курьер входит в систему с неправильным паролем.")
    @Description("Проверка кода статуса, когда курьер входит в систему с неправильным паролем.")
    public void authorizationWithWrongPasswordTest(){
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("kotik2023");
        courier.setPassword("12345000");
        Response response = clientStep.sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);

    }

    @Test
    @DisplayName("Курьер заходит в систему с неправильным логином.")
    @Description("Проверка кода статуса, когда курьер входит в систему с неправильным логином.")
    public void authorizationWithWrongLoginTest(){
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("kotik2023Keksiki");
        courier.setPassword("12345");
        Response response = clientStep.sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);

    }

    @Test
    @DisplayName("Курьер заходит в систему с неправильным логином и паролем.")
    @Description("Проверка кода статуса, когда курьер заходит в систему с неправильным логином и паролем.")
    public void authorizationWithWrongLoginAndPasswordTest(){
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("TakogoLoginaNeSuschestvuet");
        courier.setPassword("TakogoParolyaNet");
        Response response = clientStep.sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(404);
    }
}
