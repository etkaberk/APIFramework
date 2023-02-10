Feature: Validating Place API's
@AddPlace
  Scenario Outline: Verify if place is being Succesfully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When user calls "addPlaceAPI" with "post" http request
    Then the API call is succes with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "getPlaceAPI"



    Examples:
      | name      |  language  |  address             |
      | AAhouse   |  English   |  World cross center  |
     # | BBhaouse  |  Spanish   |  Sea cross center    |

  @DeletePlace
  Scenario: verify if Delete Place functionality is working
    Given Delete Place payload
    When user calls "deletePlaceAPI" with "post" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"