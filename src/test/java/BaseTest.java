import io.restassured.RestAssured;

public class BaseTest {

    public BaseTest()
    {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }
}
