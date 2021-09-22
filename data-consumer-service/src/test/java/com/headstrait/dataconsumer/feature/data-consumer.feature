Feature: Data Consumer Features

  Scenario : The consumer consumes the data produces onto a topic and persists it onto database.

    When Data is produced onto "water-potability" topic pipeline
    Then Data is persisted onto Oracle database.
