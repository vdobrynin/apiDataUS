package definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import support.DataUsaApi;
import support.TestContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiStepDefs {

    @Given("I send GET request for population by state in year {int}")
    public void iSendGETRequestForPopulationByState(int year) {
        TestContext.setResponse(DataUsaApi.getPopulationByState(year));
    }

    @Then("I validate that year is the same in all elements")
    public void iValidateThatYearIsTheSameInAllElements() {
        List<Integer> years = TestContext.getResponse()
                .jsonPath()
                .getList("data.Year", Integer.class);

        assertThat(years).as("data[] array of the response").isNotEmpty();
        // Every element must carry the same year as the first one.
        assertThat(years).containsOnly(years.get(0));
    }

    @Then("I validate that each state name is unique")
    public void iValidateThatEachStateNameIsUnique() {
        List<String> states = TestContext.getResponse()
                .jsonPath()
                .getList("data.State", String.class);

        assertThat(states)
                .as("data[].State values")
                .isNotEmpty()
                .doesNotHaveDuplicates()
                .hasSizeGreaterThanOrEqualTo(50); // 50 states + DC + territories
    }
}
