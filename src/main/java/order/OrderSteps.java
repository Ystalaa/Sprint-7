package Order;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static Constant.ScooterApiEndpoints.*;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    public static RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }

    @Step("Создание заказа")
    public ValidatableResponse createNewOrder(OrderModel orderModel) {
        return requestSpec()
                .body(orderModel)
                .when()
                .post(ORDER_POST_CREATE)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return requestSpec()
                .when()
                .get(ORDER_GET_LIST)
                .then();
    }

    @Step("Отмена заказа")
    public void cancelOrder(int track) {
        requestSpec()
                .body(track)
                .when()
                .put(ORDER_PUT_CANCEL)
                .then();
    }
}