Feature: Session of Ali on 2021-12-06 ended at 21:55

  Background:
    Given "ali" drove session on 2021-12-06 with end time 21:55
      | restaurant  | customer	| address                           | longitude          | latitude            | timestamp	             |
      |             |   		| Parijsstraat, 3000 Leuven         | 4.7128252562784665 | 50.87880129472542   | 2021-12-16 00:00:00.646 |
      |             | Fien  	| Maria-Theresiastraat 63B, 3000 Leuven | 4.7128252562784665 | 50.87880129472542   | 2021-12-16 00:00:00.646 |
      |             |   		| Tiensevest, 3000 Leuven           | 4.7128252562784665 | 50.87880129472542   | 2021-12-16 00:00:00.646 |
      |             | Gert   	| Fonteinstraat 100, 3000 Leuven    | 4.7128252562784665 | 50.87880129472542   | 2021-12-16 00:00:00.646 |
      |             |   		| Rector de Somerplein, 3000 Leuven | 4.7128252562784665 | 50.87880129472542   | 2021-12-16 00:00:00.646 |
      |             | Hans  	| Windmolenveldstraat, 3000 Leuven       | 4.7128252562784665 | 50.87880129472542   | 2021-12-16 00:00:00.646 |

  Scenario: The number of restaurant stops of a simple session with 2 deliveries is 2
    When "ali" wants to know how many pickups were done at restaurants
    Then the number of pickups at restaurants should be 2
    And the restaurants detected should be within a perimeter of 25 meters of the following coordinates
      | longitude	       | latitude	        | timestamp 	            |
      | 4.701056899894168  | 50.87849805751138  | 2021-12-16 00:00:00.646   |
      | 4.700196269211182  | 50.87583098815853  | 2021-12-16 00:00:00.646   |

  Scenario: The number of deliveries at customers of a simple session with 2 deliveries is 2
    When "ali" wants to know how many deliveries were done at customers
    Then the number of deliveries at customers should be 2
    And the deliveries detected should be within a perimeter of 25 meters of the following coordinates
      | longitude	       | latitude	         | timestamp 	             |
      | 4.692236911540382  | 50.87395684331401   | 2021-12-16 00:00:00.646   |
      | 4.7128252562784665 | 50.87880129472542   | 2021-12-16 00:00:00.646   |

  Scenario: The details of a simple session with 2 deliveries are correctly calculated
    When "ali" wants to know all details of the session
    Then the session details should be
      | startTime          | endTime            | paidTime  | unpaidTime  | kilometers  |  deliveries |
      | 2021-12-16 00:00   | 2021-12-16 00:00   | 00:00:00  | 00:00:00    | 2,5         |  2          |