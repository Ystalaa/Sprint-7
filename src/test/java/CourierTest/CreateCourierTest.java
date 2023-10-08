package CourierTest;
import Client.CourierClient;
import Model.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class CreateCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Успешное создание курьера.")
    @Description("Проверка кода статуса и значение поля для /api/v1/courier (успешный запрос).")
    public void createCourierTest(){
        CourierClient clientStep = new CourierClient();
        String login = RandomStringUtils.randomAlphanumeric(1,10);
        String password = RandomStringUtils.randomAlphanumeric(6,8);
        String firstName = RandomStringUtils.randomAlphabetic(3,10);
        Courier courier = new Courier(login, password, firstName);
        Response response = clientStep.sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("ok", Matchers.is(true)).and().statusCode(201);

    }

    @Test
    @DisplayName("Создайте двух одинаковых курьеров.")
    @Description("Проверка кода статуса и наличие сообщения при создании двух одинаковых курьеров.")
    public void createTwoIdenticalCouriersTest(){
        CourierClient clientStep = new CourierClient();
        String login = RandomStringUtils.randomAlphanumeric(1,10);
        String password = RandomStringUtils.randomAlphanumeric(6,8);
        String firstName = RandomStringUtils.randomAlphabetic(3,10);
        Courier courier = new Courier(login, password, firstName);
        clientStep.sendPostRequestApiV1Courier(courier);
        Response response = clientStep.sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(409);
    }

    @Test
    @DisplayName("Создайте двух курьеров с одинаковыми логинами.")
    @Description("Проверка кода статуса и существования сообщения при создании двух курьеров с одинаковыми логинами.")
    public void createTwoIdenticalLoginTest(){
        CourierClient clientStep = new CourierClient();
        String login = RandomStringUtils.randomAlphanumeric(1,10);
        String password = RandomStringUtils.randomAlphanumeric(6,8);
        String firstName = RandomStringUtils.randomAlphabetic(3,10);
        Courier courier = new Courier(login, password, firstName);
        given().log().all()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
        courier.setPassword("abracadabra");
        Response response = clientStep.sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(409);
    }

    @Test
    @DisplayName("Создать курьера без логина и пароля.")
    @Description("Проверка кода статуса и существования сообщения при создании курьера без логина и пароля (неверный запрос).")
    public void createCourierWithoutLoginAndPassword(){
        CourierClient clientStep = new CourierClient();
        String firstName = RandomStringUtils.randomAlphabetic(3,10);
        Courier courier = new Courier();
        courier.setFirstName(firstName);
        Response response = clientStep.sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Создать курьера без логина и имени.")
    @Description("Проверка кода статуса и наличие сообщения при создании курьера без логина и имени (неверный запрос).")
    public void createCourierWithoutLoginAndFirstName(){
        CourierClient clientStep = new CourierClient();
        String password = RandomStringUtils.randomAlphabetic(6,8);
        Courier courier = new Courier();
        courier.setPassword(password);
        Response response = clientStep.sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Создать курьера без пароля и имени.")
    @Description("ППроверка кода статуса и наличие сообщения при создании курьера без пароля и имени (неверный запрос).")
    public void createCourierWithoutPasswordAndFirstName(){
        CourierClient clientStep = new CourierClient();
        String login = RandomStringUtils.randomAlphabetic(3,10);
        Courier courier = new Courier();
        courier.setLogin(login);
        Response response = clientStep.sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Создать курьера без пароля.")
    @Description("Проверка кода статуса и наличие сообщения при создании курьера без пароля (неверный запрос).")
    public void createCourierWithoutPassword(){
        CourierClient clientStep = new CourierClient();
        String login = RandomStringUtils.randomAlphabetic(1,10);
        String firstName = RandomStringUtils.randomAlphabetic(3,10);
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setFirstName(firstName);
        Response response = clientStep.sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Создать курьера без входа в систему.")
    @Description("Проверка кода статуса и наличие сообщения при создании курьера без входа в систему (неверный запрос).")
    public void createCourierWithoutLogin(){
        CourierClient clientStep = new CourierClient();
        String firstName = RandomStringUtils.randomAlphabetic(3,10);
        String password = RandomStringUtils.randomAlphanumeric(6,8);
        Courier courier = new Courier();
        courier.setFirstName(firstName);
        courier.setPassword(password);
        Response response = clientStep.sendPostRequestApiV1Courier(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }
}