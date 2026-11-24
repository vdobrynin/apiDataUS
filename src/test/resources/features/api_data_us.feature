@api_automation
Feature: Data USA API automation

  @api
  Scenario: Population by state
    Given I send GET request for population by state in year 2023
    Then I validate that year is the same in all elements
    And I validate that each state name is unique
