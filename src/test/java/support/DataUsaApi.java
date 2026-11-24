package support;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;

/**
 * REST client for the Data USA public API (documentation: https://datausa.io/about/api).
 *
 * NOTE: the legacy endpoint used by this project before,
 * "https://datausa.io/api/data?drilldowns=...&measures=...&year=latest",
 * was retired in 2025 and now returns 404/HTML. The current official API is
 * served from "https://api.datausa.io/tesseract/".
 */
public final class DataUsaApi {

    public static final String BASE_URI = "https://api.datausa.io";
    public static final String DATA_PATH = "/tesseract/data.jsonrecords";

    // ACS 5-year estimates cube with total population per state per year.
    public static final String POPULATION_CUBE = "acs_yg_total_population_5";

    private DataUsaApi() {
    }

    /**
     * GETs the total population for every US state (plus DC, PR, ...) for the
     * given year, e.g.:
     * /tesseract/data.jsonrecords?cube=acs_yg_total_population_5
     *   &amp;drilldowns=State,Year&amp;measures=Population&amp;include=Year:2023
     *
     * Response shape:
     * { "page": {...}, "columns": [...],
     *   "data": [ {"State ID": "...", "State": "Alabama", "Year": 2023, "Population": 5054253}, ... ] }
     */
    public static Response getPopulationByState(int year) {
        return RestAssured.given()
                    .baseUri(BASE_URI)
                    .queryParam("cube", POPULATION_CUBE)
                    .queryParam("drilldowns", "State,Year")
                    .queryParam("measures", "Population")
                    .queryParam("include", "Year:" + year)
                    .queryParam("limit", "100,0")
                .when()
                    .get(DATA_PATH)
                .then()
                    .statusCode(200)
                    .contentType(containsString("application/json"))
                    .body("data", not(empty()))
                    .body("page.total", greaterThan(0))
                .extract()
                    .response();
    }
}
