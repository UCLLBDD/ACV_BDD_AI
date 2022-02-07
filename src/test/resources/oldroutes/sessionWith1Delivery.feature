Feature: Route 1: Simple session with only 1 delivery

  Background:
    Given Elke drove session on 2021-12-14 with end time 07:20:00
      | restaurant	    | customer	| address		                      | longitude         | latitude            |timestamp	            |
      | 't Bieke		|			| Boechoutsesteenweg 19 - 2540 Hove	  | 4.47308734140695  | 51.15474533439376   | 2021-12-08 07:10:00.646 |
      |         		| 1     	| Weldadigheidsstraat 32 - 2540 Hove  | 4.477591113346917 | 51.15435149696615   | 2021-12-08 07:20:00.646 |

  Scenario: The number of restaurant stops of a simple session with only 1 delivery is 1
    When Elke wants to know how many pickups she did at restaurants
    Then the number of pickups at restaurants should be 1
    And the restaurants detected should be within a perimeter of 25 meters of the following coordinates
      | longitude	       | latitude	        | timestamp 	            |
      | 4.47308734140695   | 51.15474533439376  | 2021-12-08 07:10:00.646   |

  Scenario: The number of deliveries at customers of a simple session with only 1 delivery is 1
    When Elke wants to know how many deliveries she did
    Then the number of deliveries at customers should be 1
    And the deliveries detected should be within a perimeter of 25 meters of the following coordinates
      | longitude	      | latitude	        | timestamp 	            |
      | 4.477591113346917 | 51.15435149696615   | 2021-12-08 07:20:00.646   |

  Scenario: The details of a simple session with only 1 delivery are correctly calculated
    When Elke wants to know all details of the session she did
    Then the session details should be
      | startTime          | endTime            | paidTime  | unpaidTime  | kilometers  |  deliveries |
      | 2021-12-14 07:00   | 2021-12-14 07:30   | 00:07:30  | 00:20:00    | 5           |  1          |


#      | restaurant	    | customer	| address		                      | coordinate		  | timestamp	            |
#      | 't Bieke		|			| Boechoutsesteenweg 19 - 2540 Hove	  | [4.47308734140695, 51.15474533439376] | 2021-12-08 07:10:00.646 |
#      |         		| 1     	| Weldadigheidsstraat 32 - 2540 Hove  | [4.4775, 51.1541] | 2021-12-08 07:20:00.646 |
