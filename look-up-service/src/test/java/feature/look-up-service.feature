Feature: look-up service feature

Scenario: I want to fetch all data
When The client calls GET /fetch
Then receives status code 200
And The response should contain

  Scenario Outline: I want to fetch specific water-portability data
    When The client calls GET /fetch
    Then for a particular id: <id>
    Then receives status code <STATUS_CODE>
    And The response should contain water-potability object
    Examples:
      | id   | STATUS_CODE |
      | 3202 | 200         |
      | 3223 | 200         |
      | 1    | 200         |

