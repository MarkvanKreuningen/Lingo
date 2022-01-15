# TODO: schets maken van het domein ahv deze scenario's
Feature: new round
  Scenario: Start new game
    When I start a new game
    Then the game should give me a first letter

  Scenario Outline: Start a new round
    Given I am playing a game
    And the round was won
    And the last word had "<previous length>" letters
    When I start a new round
    Then the word to guess has "<next length>" letters

  Examples:
    | previous length | next length |
    | 5               | 6           |
    | 6               | 7           |
    | 7               | 5           |

# Failure path
    Given I am playing a game
    And the round was lost
    Then I cannot start a new round


  Scenario Outline: Guessing a word
    Given I am playing a game
    And the word to guess is "<word>"
    When I am guessing "<guess>"
    Then I should get feedback "<feedback>"

    Examples:
      | word            | guess       | feedback                                              |
      | BAARD           | BERGEN      | INVALID, INVALID, INVALID, INVALID, INVALID, INVALID  |
      | BAARD           | BONJE       | CORRECT, ABSENT, ABSENT, ABSENT, ABSENT               |
      | BAARD           | BARST       | CORRECT, ABSENT, ABSENT, ABSENT, ABSENT               |
      | BAARD           | BEDDE       | CORRECT, ABSENT, PRESENT, ABSENT, ABSENT              |
      | BAARD           | BAARD       | CORRECT, CORRECT, CORRECT, CORRECT, CORRECT           |
