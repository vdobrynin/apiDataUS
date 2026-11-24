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

    @Given("I send GET request for available years in the population cube")
    public void iSendGETRequestForAvailableYears() {
        TestContext.setResponse(DataUsaApi.getAvailableYears());
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

    @Then("I validate that every element has year {int}")
    public void iValidateThatEveryElementHasYear(int year) {
        List<Integer> years = TestContext.getResponse()
                .jsonPath()
                .getList("data.Year", Integer.class);

        assertThat(years).as("data[].Year values").containsOnly(year);
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

    @Then("I validate that the available years are sorted")
    public void iValidateThatTheAvailableYearsAreSorted() {
        assertThat(availableYears())
                .as("members[].key values")
                .isNotEmpty()
                .isSorted();
    }

    @Then("I validate that the year {int} is available")
    public void iValidateThatTheYearIsAvailable(int year) {
        assertThat(availableYears()).as("members[].key values").contains(year);
    }

    private List<Integer> availableYears() {
        return TestContext.getResponse()
                .jsonPath()
                .getList("members.key", Integer.class);
    }
}
