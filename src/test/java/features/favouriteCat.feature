Feature: the purpose is to have no discrimination between the cats

  Scenario Outline: We need to mark our favourite cats for auction
    Given user "<operates>" their favourite cat ID
    Examples:
      | operates |
      | GET      |
      | POST     |
      | DELETE   |

