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

  Scenario: Player is eliminated after 5 incorrect guesses
    Given I am playing a game
    And the word to guess is "school"
    When I guess "towers"
    And I guess "towers"
    And I guess "towers"
    And I guess "towers"
    And I guess "towers"
    Then I should be eliminated

  Scenario: Cannot start a round if still guessing
    Given I am playing a game
    And I am still guessing a word
    Then I cannot start a new round

  Scenario: Cannot start a round if eliminated
    Given I am playing a game
    And I have been eliminated
    Then I cannot start a new round

  Scenario: Cannot guess word if round not started
    Given I am playing a game
    And the round was won
    Then I cannot guess the word

  Scenario Outline: Score increases based on number of attempts
    Given I am playing a game
    And the score is "<current score>"
    And the word to guess is "school"
    When I guess "school" in "<attempts>" attempts
    Then the score is "<new score>"

    Examples:
      | current score | attempts | new score |
      | 0             | 1        | 25        |
      | 5             | 1        | 30        |
      | 0             | 2        | 20        |
      | 5             | 2        | 25        |
      | 0             | 3        | 15        |
      | 5             | 3        | 20        |
      | 0             | 4        | 10        |
      | 5             | 4        | 15        |
      | 0             | 5        | 5         |
      | 5             | 5        | 10        |


