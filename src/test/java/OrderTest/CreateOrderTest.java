package OrderTest;
import Model.Colors;
import Model.Order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Collections;
import java.util.List;
import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final Order order;

    public CreateOrderTest(Order order){
        this.order = order;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new AllureRestAssured());
    }

    @Parameterized.Parameters(name="Тестовые данные: {0}")
    public static Object[][] getOrderParameters(){
        return new Object[][] {
                {new Order("Kristina", "Carter", "Sochi", "2",
                        "8-800-555-35-35", "2020-12-14", "Privet",
                        List.of(Colors.GRAY.name()), 6)},
                {new Order("Emily", "Bell", "LA, 99 apt.", "4",
                        "+7 800 355 35 35", "2020-06-06",
                        "Privet", List.of(Colors.BLACK.name()), 5)},
                {new Order("Tatyana","Carabinova","Moscow","10",
                        "+7-999-999-99-99", "2022-07-25", "Privet",
                        List.of(Colors.GRAY.name(), Colors.BLACK.name()), 2)},
                {new Order("Vladislav", "Vladimirovich", "Samara", "1",
                        "+7-123-456-78-90", "2014-02-28",
                        "Privet", Collections.emptyList(), 8)}
        };
    }

    @Test
    @DisplayName("Создать тест заказа.")
    @Description("Успешное создание заказа с разными параметрами.")
    public void createOrderTest(){
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders");
        response.then().log().all()
                .assertThat().body("track", Matchers.notNullValue()).and().statusCode(201);
    }

}
