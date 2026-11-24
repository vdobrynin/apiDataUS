@api_automation
Feature: Data USA API automation

  @api
  Scenario: Population by state
    Given I send GET request for population by state in year 2023
    Then I validate that year is the same in all elements
    And I validate that each state name is unique

  @api
  Scenario Outline: Population by state across several years
    Given I send GET request for population by state in year <year>
    Then I validate that year is the same in all elements
    And I validate that every element has year <year>
    And I validate that each state name is unique

    Examples:
      | year |
      | 2017 |
      | 2022 |
      | 2024 |

  @api
  Scenario: Available years in the population cube
    Given I send GET request for available years in the population cube
    Then I validate that the available years are sorted
    And I validate that the year 2024 is available
