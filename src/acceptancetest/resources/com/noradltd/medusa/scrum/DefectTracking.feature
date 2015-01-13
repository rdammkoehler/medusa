Feature: Defect Tracking

  Scenario: Defects are detected and tracked
    Given a defect occurs in work
    When the sprint concludes
    Then I can find the card associated with each defect
    And I can find the developer who created each card

  Scenario: Defects are not detected
    Given no defects occur
    When the sprint concludes
    Then nothing is reported