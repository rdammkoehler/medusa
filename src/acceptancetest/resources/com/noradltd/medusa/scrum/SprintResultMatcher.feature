Feature: SprintResultMatcher can match criteria against Sprint Results

  Scenario: Make an exact match
    Given sprint results
    When exact match criteria are provided
    Then the matcher successfully matches the sprint results
    
  Scenario: Make an exact mis-match
    Given sprint results
    When mis-matched criteria are provided
    Then the matcher fails to match the sprint results
    
  Scenario: Can make fuzzy matches
  	Given sprint results
  	When vague criteria are provided
  	Then the matcher successfully matches the sprint results
  	
  Scenario: Can make fuzzy mis-matches
    Given sprint results
    When vague criteria do not match
    Then the matcher fails to match the sprint results
    
  Scenario: Can detect the absence of something
    Given sprint results
    When  exclusionary criteria are provided
    Then the matcher successfully matches the sprint results