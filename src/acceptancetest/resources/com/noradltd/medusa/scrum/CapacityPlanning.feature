Feature: Capacity Planning impacts sprint execution

  Scenario: Developers who are available can work
    Given a developer with 100% capacity
    When work assignments occur
    Then the developer is assigned work

  Scenario: Unavailable workers aren't assigned work
    Given a developer with unavailable time
    When work assignments occur
    Then the developer is not assigned work