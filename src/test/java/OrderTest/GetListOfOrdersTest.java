package OrderTest;
import Model.ListOfOrders;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class GetListOfOrdersTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Получить список заказов.")
    @Description("Получить список заказов /api/v1/orders и проверить код статуса.")
    public void getListOrdersTest(){
        given()
                .header("Content-type", "application/json")
                .log().all()
                .get("/api/v1/orders")
                .then()
                .assertThat()
                .statusCode(200);
        ListOfOrders listOfOrders = given()
                .header("Content-type", "application/json")
                .log().all()
                .get("/api/v1/orders")
                .body()
                .as(ListOfOrders.class);
        Assert.assertThat(listOfOrders.getOrders(),Matchers.not(Matchers.empty()));
    }
}