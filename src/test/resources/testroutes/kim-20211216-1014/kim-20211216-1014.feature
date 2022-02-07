Feature:  Session of Kim on 2021-12-16 ended at 10:14

  Background:
    Given "kim" drove session on 2021-12-16 with end time 10:14
      | restaurant	    | customer	| address		                      | longitude         | latitude            | timestamp	        |
      | Ellis Leuven	|			| Naamsestraat 5, 3000 Leuven    	  | 4.701056899894168 | 50.87849805751138   | 2021-12-16 00:00  |
      |         		| Jan     	| Prosper Poulletlaan 5, 3000 Leuven  | 4.692236911540382 | 50.87395684331401   | 2021-12-16 00:00  |

  Scenario: The number of restaurant stops of a simple session with 1 delivery is 1
    When "kim" wants to know how many pickups were done at restaurants
    Then the number of pickups at restaurants should be 1
    And the restaurants detected should be within a perimeter of 25 meters of the following coordinates
      | longitude	       | latitude	        | timestamp 	     |
      | 4.701056899894168  | 50.87849805751138  | 2021-12-16 00:00   |

  Scenario: The number of deliveries at customers of a simple session with only 1 delivery is 1
    When "kim" wants to know how many deliveries were done at customers
    Then the number of deliveries at customers should be 1
    And the deliveries detected should be within a perimeter of 25 meters of the following coordinates
      | longitude	      | latitude	        | timestamp 	     |
      | 4.692236911540382 | 50.87395684331401   | 2021-12-16 00:00   |

  Scenario: The details of a simple session with only 1 delivery are correctly calculated
    When "kim" wants to know all details of the session
    Then the session details should be
      | startTime          | endTime            | paidTime  | unpaidTime  | kilometers  |  deliveries |
      | 2021-12-16 10:02   | 2021-12-16 10:14   | 00:08:36  | 00:02:56    | 2,5         |  1          |

#  https://www.google.com/maps/dir/Zwartzusterklooster,+Leuven/Ellis+Gourmet+Burger,+Naamsestraat,+Leuven/50.8740306,4.692206/Zwartzusterklooster,+Leuven/@50.8754954,4.697326,16z/data=!4m21!4m20!1m5!1m1!1s0x47c16126fb7e1c83:0x6b382f6119e51b2e!2m2!1d4.6979204!2d50.8733213!1m5!1m1!1s0x47c160d848607bbb:0x10cc0c06cb161e7!2m2!1d4.7009818!2d50.8783153!1m0!1m5!1m1!1s0x47c16126fb7e1c83:0x6b382f6119e51b2e!2m2!1d4.6979204!2d50.8733213!3e1