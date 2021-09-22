Feature: data source service features

  Scenario: GET json data from any given csv file with /get service endpoint
    When client calls GET /get
    Then receives status code 200
    And The response should contain the json response

  Scenario Outline: GET json data from any given csv file with an undefined service endpoint
    When client calls GET with "<endpoint>"
    Then receives status code <STATUS_CODE>
    And if <STATUS_CODE> is 200 receive a valid array
    Examples:
      | endpoint | STATUS_CODE |
      | fetch    | 404         |
      | get      | 200         |