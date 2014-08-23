Feature: Run Sprint
  Scenario: Run A Sprint
  	Given sprint data
  	When I run the sprint
  	Then I get sprint artifacts
  	
  	#flake
  Scenario: Sprint is too big
  	Given over-committed sprint data
  	When I run the sprint
  	Then I not all cards are verified
  	
  Scenario: Sprint is very small
  	Given under-committed sprint data
  	When I run the sprint
  	Then I see many idle days for developers
  
  Scenario: Sprint creates defects
  	Given sprint data
  	And there are defects created
  	When I run the sprint
  	Then I get defect creation notifications