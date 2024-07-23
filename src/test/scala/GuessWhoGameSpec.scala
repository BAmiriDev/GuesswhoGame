import characters.{Black, Blonde, Blue, Brown, DarkBrown, EyeColour, Female, Gender, Green, HairColour, Hazel, Male, Person, PersonWithBeard, PersonWithGlasses, PersonWithHat, Resources}
import gameengine.GameEngine
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable.ListBuffer


class GuessWhoGameSpec extends AnyWordSpec {

  // Initialize game engine and resources once
  val gameEngine: GameEngine = new GameEngine
  val guessWhoGame: Resources = new Resources

  // Initialize expected values directly
  val expectedCharacters: List[Person] = guessWhoGame.charactersList
  var expectedQuestions: List[String] = guessWhoGame.listOfQuestions

  "GameEngine.selectRandomCharacter" should {
    "select a random character from the list" when {
      "called to select a random character" in {
        val characters = guessWhoGame.charactersList
        val selectedCharacter = gameEngine.selectRandomCharacter(characters)
        assert(characters.contains(selectedCharacter))
      }
    }
  }

  "GameEngine.selectRandomQuestions" should {
    "select a random question from the list" when {
      "called to select a random question" in {
        val questions = guessWhoGame.listOfQuestions
        val selectedQuestion = gameEngine.selectRandomQuestions()
        assert(questions.contains(selectedQuestion))
      }
    }
  }

  "GameEngine.filterCharactersByAnswer" should {
    "filter characters based on the string answer" when {
      "called to filter characters by string answer" in {
        val question = "Is your person male?"
        val answer = false
        val gameEngine = new GameEngine

        val charactersList = List(
          Person("John", Male, DarkBrown, PersonWithGlasses(true), PersonWithHat(false), PersonWithBeard(false), Blue),
          Person("Jane", Female, Blonde, PersonWithGlasses(false), PersonWithHat(true), PersonWithBeard(false), Green),
          Person("Doe", Female, Black, PersonWithGlasses(false), PersonWithHat(false), PersonWithBeard(true), Brown)
        )

        val filteredCharacters = ListBuffer.from(charactersList)
        gameEngine.filterCharacters(filteredCharacters, question, answer)

        val expectedFilteredCharacters = List(
          Person("Jane", Female, Blonde, PersonWithGlasses(false), PersonWithHat(true), PersonWithBeard(false), Green),
          Person("Doe", Female, Black, PersonWithGlasses(false), PersonWithHat(false), PersonWithBeard(true), Brown)
        )

        assert(filteredCharacters.toList == expectedFilteredCharacters)
      }
    }

    "filter characters based on the attribute 'no beard'" in {
      val question = "Does your person have a beard?"
      val answer = false
      val gameEngine = new GameEngine

      val charactersList = List(
        Person("Alex", Male, DarkBrown, PersonWithGlasses(true), PersonWithHat(false), PersonWithBeard(false), Blue),
        Person("Beth", Female, Blonde, PersonWithGlasses(false), PersonWithHat(true), PersonWithBeard(false), Green),
        Person("Chris", Male, Black, PersonWithGlasses(false), PersonWithHat(false), PersonWithBeard(true), Brown)
      )

      val filteredCharacters = ListBuffer.from(charactersList)
      gameEngine.filterCharacters(filteredCharacters, question, answer)

      val expectedFilteredCharacters = List(
        Person("Alex", Male, DarkBrown, PersonWithGlasses(true), PersonWithHat(false), PersonWithBeard(false), Blue),
        Person("Beth", Female, Blonde, PersonWithGlasses(false), PersonWithHat(true), PersonWithBeard(false), Green)
      )

      assert(filteredCharacters.toList == expectedFilteredCharacters)
    }

    "filter characters based on the attribute 'blue eyes'" in {
      val question = "Does your person have blue eyes?"
      val answer = true
      val gameEngine = new GameEngine

      val charactersList = List(
        Person("Alex", Male,  DarkBrown, PersonWithGlasses(true), PersonWithHat(false), PersonWithBeard(false), Blue),
        Person("Beth", Female, Blonde, PersonWithGlasses(false), PersonWithHat(true), PersonWithBeard(false), Green),
        Person("Chris", Male, Black, PersonWithGlasses(false), PersonWithHat(false), PersonWithBeard(true), Brown)
      )

      val filteredCharacters = ListBuffer.from(charactersList)
      gameEngine.filterCharacters(filteredCharacters, question, answer)

      val expectedFilteredCharacters = List(
        Person("Alex", Male, DarkBrown, PersonWithGlasses(true), PersonWithHat(false), PersonWithBeard(false), Blue)
      )

      assert(filteredCharacters.toList == expectedFilteredCharacters)
    }

    "handle edge case where no characters match the criteria" in {
      val question = "Does your person have purple hair?"
      val answer = true
      val filteredCharacters = ListBuffer.from(expectedCharacters)
      val expectedFilteredCharacters = gameEngine.filterCharacters(filteredCharacters, question, answer)
      assert(filteredCharacters.isEmpty == expectedFilteredCharacters.isEmpty)
    }
  }

  "GameEngine.endGame" should {
    "return true when only one character is left" in {
      val singleCharacterList = ListBuffer(guessWhoGame.charactersList.head)
      assert(gameEngine.endGame(singleCharacterList))
    }

    "return false when more than one character is left" in {
      val characters = ListBuffer.from(expectedCharacters)
      assert(!gameEngine.endGame(characters))
    }
  }
}
