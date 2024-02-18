Feature: The feature deals with the images related to CAT api
  Scenario: I am able to CRUD on server
    Given user is able to "GET" the random "10" images from the server
    When user is able to "POST" the image on server
    Then user is able to "GET" the image from the server
    And user is able to "DELETE" the image from the server
