package definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import support.DataUsaApi;
import support.TestContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SmokeStepDefs {

    @Given("I send GET request to the cubes endpoint")
    public void iSendGETRequestToTheCubesEndpoint() {
        TestContext.setResponse(DataUsaApi.getCubes());
    }

    @Given("I send GET request for the schema of cube {string}")
    public void iSendGETRequestForTheSchemaOfCube(String cubeName) {
        TestContext.setResponse(DataUsaApi.getCubeSchema(cubeName));
    }

    @Then("I validate that the cube {string} is available")
    public void iValidateThatTheCubeIsAvailable(String cubeName) {
        List<String> cubeNames = TestContext.getResponse()
                .jsonPath()
                .getList("cubes.name", String.class);

        assertThat(cubeNames).as("registered cubes").contains(cubeName);
    }

    @Then("I validate that the schema has measure {string}")
    public void iValidateThatTheSchemaHasMeasure(String measure) {
        List<String> measures = TestContext.getResponse()
                .jsonPath()
                .getList("measures.name", String.class);

        assertThat(measures).as("cube measures").contains(measure);
    }
}
