package ordertest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderSteps;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListReceivingTest {
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверяем, что список заказов успешно получен")
    public void getOrderList() {
        OrderSteps orderSteps = new OrderSteps();
        ValidatableResponse responseOrderList = orderSteps.getOrderList();
        responseOrderList.assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}