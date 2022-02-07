Feature: Session of koen on 2022-01-07 ended at 19:44

  Background:
    Given "koen" drove session on 2022-01-07 with end time 19:44
      | restaurant  | customer	| address                           | longitude     | latitude    | timestamp	     |
      |       	    |			|       	                        | 3.7299465     | 51.0592679  | 2022-01-07 18:30 |
      |             | An     	|                                   | 3.7309185     | 51.0546439  | 2022-01-07 18:36 |
      |             | Ben       | 	                                | 3.7435837     | 51.0475162  | 2022-01-07 18:41 |
      |       	    |			|       	                        | 3.7319846     | 51.0497423  | 2022-01-07 18:52 |
      |             | Chris    	|                                   | 3.748158      | 51.0452119  | 2022-01-07 18:58 |
      |       	    |			|       	                        | 3.731024      | 51.0488548  | 2022-01-07 19:15 |
      |             | David    	|                                   | 0.0000000     | 00.000000   | 2022-01-07 19:22 |
      |             | Elke      | 	                                | 0.0000000     | 00.000000   | 2022-01-07 19:33 |
      |       	    |			|       	                        | 0.0000000     | 00.000000   | 2022-01-07 20:00 |
      |             | Fien    	|                                   | 0.0000000     | 00.000000   | 2022-01-07 20:08 |
      |       	    |			|       	                        | 0.0000000     | 00.000000   | 2022-01-07 20:13 |
      |             | Gerben   	|                                   | 0.0000000     | 00.000000   | 2022-01-07 20:25 |
      |       	    |			|       	                        | 0.0000000     | 00.000000   | 2022-01-07 20:25 |
      |             | Helle    	|                                   | 0.0000000     | 00.000000   | 2022-01-07 20:33 |

  Scenario: The number of restaurant stops of koen's session on 2022-01-07 at 19:44 is 6
    When "koen" wants to know how many pickups were done at restaurants
    Then the number of pickups at restaurants should be 6
    And the restaurants detected should be within a perimeter of 25 meters of the following coordinates
      | longitude	       | latitude	        | timestamp 	     |
      |                    |                    |                    |
      |                    |                    |                    |
      |                    |                    |                    |
      |                    |                    |                    |
      |                    |                    |                    |
      |                    |                    |                    |

  Scenario: The number of deliveries at customers of koen's session on 2022-01-07 at 19:44 is 8
    When "koen" wants to know how many deliveries were done at customers
    Then the number of deliveries at customers should be 8
    And the deliveries detected should be within a perimeter of 25 meters of the following coordinates
      | longitude	       | latitude	         | timestamp 	      |
      |                    |                     |                    |
      |                    |                     |                    |
      |                    |                     |                    |
      |                    |                     |                    |
      |                    |                     |                    |
      |                    |                     |                    |
      |                    |                     |                    |
      |                    |                     |                    |

  Scenario: The details of koen's session on 2022-01-07 at 19:44 with 8 deliveries are correctly calculated
    When "koen" wants to know all details of the session
    Then the session details should be
      | startTime          | endTime            | deliveries  | kilometers  | paidTime  | unpaidTime  |
      | 2021-12-06 17:00   | 2021-12-06 18:28   | 8           | 2.5         | 00:00:00  | 00:00:00  |