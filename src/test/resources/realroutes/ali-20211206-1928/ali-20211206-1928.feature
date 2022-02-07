Feature: Session of ali on 2021-12-06 ended at 18:28

  Background:
    Given "ali" drove session on 2021-12-06 with end time 18:28
      | restaurant  | customer	| address                           | longitude     | latitude    | timestamp	     |
      |       	    |			| Diestsestaat, 3000 Leuven      	| 4.7056198     | 50.880499   | 2021-12-06 00:00 |
      |             | An     	| 's-Meiersstraat, 3000 Leuven      | 4.702201      | 50.8778237  | 2021-12-06 00:00 |
      |             |           | Muntstraat, 3000 Leuven           | 4.70158491    | 50.878495   | 2021-12-06 00:00 |
      |             | Ben       | Pensstraat, 3000 Leuven	        | 4.6999508     | 50.8798027  | 2021-12-06 00:00 |
      |             |   		| Savoyestraat, 3000 Leuven         | 4.7036307     | 50.8788736  | 2021-12-06 00:00 |
      |             | Chris 	| Pater Damiaanplein, 3000 Leuven   | 4.6975568     | 50.8763557  | 2021-12-06 00:00 |
      |             |   		| Tiensestraat, 3000 Leuven         | 4.7045802     | 50.8773862  | 2021-12-06 00:00 |
      |             | David  	| Parijsstraat, 3000 Leuven         | 4.7022106     | 50.8795489  | 2021-12-06 00:00 |
      |             |   		| Rector de Somerplein, 3000 Leuven | 4.6982497     | 50.8774887  | 2021-12-06 00:00 |
      |             | Eline  	| Kapucijnenvoer, 3000 Leuven       | 4.6906556     | 50.8759876  | 2021-12-06 00:00 |

  Scenario: The number of restaurant stops of ali's session on 2021-12-06 at 18:28 is 5
    When "ali" wants to know how many pickups were done at restaurants
    Then the number of pickups at restaurants should be 5
    And the restaurants detected should be within a perimeter of 25 meters of the following coordinates
      | longitude	       | latitude	        | timestamp 	     |
      | 4.701056899894168  | 50.87849805751138  | 2021-12-06 17:15   |
      | 4.700196269211182  | 50.87583098815853  | 2021-12-06 00:00   |
      | 4.701056899894168  | 50.87849805751138  | 2021-12-06 00:00   |
      | 4.701056899894168  | 50.87849805751138  | 2021-12-06 00:00   |
      | 4.701056899894168  | 50.87849805751138  | 2021-12-06 00:00   |

  Scenario: The number of deliveries at customers of ali's session on 2021-12-06 at 18:28 is 5
    When "ali" wants to know how many deliveries were done at customers
    Then the number of deliveries at customers should be 5
    And the deliveries detected should be within a perimeter of 25 meters of the following coordinates
      | longitude	       | latitude	         | timestamp 	      |
      | 4.692236911540382  | 50.87395684331401   | 2021-12-06 17:22   |
      | 4.7128252562784665 | 50.87880129472542   | 2021-12-06 00:00   |
      | 4.692236911540382  | 50.87395684331401   | 2021-12-06 00:00   |
      | 4.7128252562784665 | 50.87880129472542   | 2021-12-06 00:00   |
      | 4.692236911540382  | 50.87395684331401   | 2021-12-06 00:00   |

  Scenario: The details of ali's session on 20211206 at 18:28 with 5 deliveries are correctly calculated
    When "ali" wants to know all details of the session
    Then the session details should be
      | startTime          | endTime            | deliveries  | kilometers  | paidTime  | unpaidTime  |
      | 2021-12-06 17:00   | 2021-12-06 18:28   | 5           | 2.5         | 00:00:00  | 00:00:00  |