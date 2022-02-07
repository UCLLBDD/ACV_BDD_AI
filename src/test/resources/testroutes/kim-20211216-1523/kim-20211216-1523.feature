Feature:  Session of Kim on 2021-12-16 ended at 15:23

  Background:
    Given "kim" drove session on 2021-12-16 with end time 15:23
      | restaurant	                | customer	| address		                        | longitude          | latitude            | timestamp	      |
      | Ellis Leuven	            |			| Naamsestraat 5, 3000 Leuven    	    | 4.701056899894168  | 50.87849805751138   | 2021-12-16 00:00 |
      |         		            | An     	| Redingenhof 13, 3000 Leuven           | 4.692236911540382  | 50.87395684331401   | 2021-12-16 00:00 |
      | De Nijl                     |           | Naamsestraat 66, 3000 Leuven          | 4.700196269211182  | 50.87583098815853   | 2021-12-16 00:00 |
      | Hawaiian Poke Bowl - Leuven |           | Tiensestraat 64, 3000 Leuven	        | 4.704512430544738  | 50.87730226908967   | 2021-12-16 00:00 |
      |             	            | Koen		| Maria Theresiastraat 74, 3000 Leuven  | 4.7128252562784665 | 50.87880129472542   | 2021-12-16 00:00 |

  Scenario: The number of restaurant stops of a simple session with 2 deliveries is 2
    When "kim" wants to know how many pickups were done at restaurants
    Then the number of pickups at restaurants should be 2
    And the restaurants detected should be within a perimeter of 25 meters of the following coordinates
      | longitude	       | latitude	        | timestamp 	        |
      | 4.701056899894168  | 50.87849805751138  | 2021-12-16 00:00:00   |
      | 4.704512430544738  | 50.87730226908967  | 2021-12-16 00:00:00   |

  Scenario: The number of deliveries at customers of a simple session with 2 deliveries is 2
    When "kim" wants to know how many deliveries were done at customers
    Then the number of deliveries at customers should be 2
    And the deliveries detected should be within a perimeter of 25 meters of the following coordinates
      | longitude	       | latitude	         | timestamp 	      |
      | 4.692236911540382  | 50.87395684331401   | 2021-12-16 00:00   |
      | 4.7128252562784665 | 50.87880129472542   | 2021-12-16 00:00   |

  Scenario: The details of a simple session with 2 deliveries are correctly calculated
    When "kim" wants to know all details of the session
    Then the session details should be
      | startTime          | endTime            | paidTime  | unpaidTime  | kilometers  |  deliveries |
      | 2021-12-16 14:58   | 2021-12-16 15:23   | 00:00:00  | 00:00:00    | 2,5         |  2          |

  # https://www.google.com/maps/dir/Zwartzusterklooster,+Leuven/Ellis+Gourmet+Burger,+Naamsestraat,+Leuven/50.8748162,4.6949935/De+Nijl,+Naamsestraat,+Leuven/Hawaiian+Poke+Bowl+-+Leuven,+Tiensestraat,+Leuven/Hulpgevangenis+te+Leuven,+Maria+Theresiastraat+74,+3000+Leuven/Zwartzusterklooster,+Leuven/@50.8753734,4.6998084,16z/data=!3m1!5s0x47c16128135c9867:0xa8cfe25b1c22aa49!4m39!4m38!1m5!1m1!1s0x47c16126fb7e1c83:0x6b382f6119e51b2e!2m2!1d4.6979204!2d50.8733213!1m5!1m1!1s0x47c160d848607bbb:0x10cc0c06cb161e7!2m2!1d4.7009818!2d50.8783153!1m0!1m5!1m1!1s0x47c161e55f214959:0xe6f9567d50487c60!2m2!1d4.7002081!2d50.8756752!1m5!1m1!1s0x47c161784e9b90e7:0x46367030b69b1649!2m2!1d4.7045603!2d50.8771967!1m5!1m1!1s0x47c3df7af4e7360f:0x55ea6e66d022fa6a!2m2!1d4.7129334!2d50.8786708!1m5!1m1!1s0x47c16126fb7e1c83:0x6b382f6119e51b2e!2m2!1d4.6979204!2d50.8733213!3e1