Feature: Data Consumer Features

  Scenario : The consumer consumes the data produces onto a topic and persists it onto database.

    Given The producer is producing the data
    When Data is produced onto "water-potability-events" topic pipeline
    Then Data is persisted onto Oracle database.
