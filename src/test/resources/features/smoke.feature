@api_automation @smoke
Feature: Data USA API smoke check

  @api
  Scenario: API is up and the population cube is registered
    Given I send GET request to the cubes endpoint
    Then I validate that the cube "acs_yg_total_population_5" is available

  @api
  Scenario: Population cube schema is valid
    Given I send GET request for the schema of cube "acs_yg_total_population_5"
    Then I validate that the schema has measure "Population"
