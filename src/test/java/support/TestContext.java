package support;

import io.restassured.response.Response;

/**
 * Holds the REST response of the most recent request so that the following
 * Cucumber steps can assert on it. (The previous version of this class only
 * existed to hand out a Selenium WebDriver, which this project no longer uses.)
 */
public class TestContext {
    private static Response response;

    public static Response getResponse() {
        return response;
    }

    public static void setResponse(Response response) {
        TestContext.response = response;
    }
}
