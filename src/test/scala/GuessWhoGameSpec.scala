import characters.{Person, Resources}
import gameengine.GameEngine
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable.ListBuffer

class GuessWhoGameSpec extends AnyWordSpec {

  // Initialize game engine and resources once
  val gameEngine: GameEngine = new GameEngine
  val guessWhoGame: Resources = new Resources

  // Initialize expected values directly
  val expectedCharacters: List[Person] = guessWhoGame.charactersList
  val expectedQuestions: List[String] = guessWhoGame.listOfQuestions

  "Resources.returnListOfCharacters" should {
    "return a list of characters" when {
      "called to retrieve the characters list" in {
        val characters = guessWhoGame.charactersList
        assert(characters == expectedCharacters)
      }
    }
  }

  "Resources.returnListOfQuestions" should {
    "return a list of questions" when {
      "called to retrieve the questions list" in {
        val questions = guessWhoGame.listOfQuestions
        assert(questions == expectedQuestions)
      }
    }
  }

  "GameEngine.selectRandomCharacter" should {
    "select a random character from the list" when {
      "called to select a random character" in {
        val characters = guessWhoGame.charactersList
        val selectedCharacter = gameEngine.selectRandomCharacter(characters)
        assert(characters.contains(selectedCharacter))
      }
    }
  }

  "GameEngine.selectRandomQuestion" should {
    "select a random question from the list" when {
      "called to select a random question" in {
        val questions = guessWhoGame.listOfQuestions
        val selectedQuestion = gameEngine.selectRandomQuestions()
        assert(questions.contains(selectedQuestion))
      }
    }
  }

  "GameEngine.filterCharacters" should {
    "filter characters based on the question and answer" when {
      "called to filter characters" in {
        val question = "Is your character male"
        val answer = true
        val filteredCharacters = ListBuffer.from(expectedCharacters)
        gameEngine.filterCharacters(filteredCharacters, question, answer)
        val expectedFilteredCharacters = expectedCharacters.filter(_.gender == "Male")
        assert(filteredCharacters.toList == expectedFilteredCharacters)
      }
    }
  }

  "GameEngine.filterCharactersByAnswer" should {
    "filter characters based on the string answer" when {
      "called to filter characters by string answer" in {
        val question = "Is your character male"
        val answer = false
        val filteredCharacters = ListBuffer.from(expectedCharacters)
        gameEngine.filterCharacters(filteredCharacters, question, answer)
        val expectedFilteredCharacters = expectedCharacters.filter(_.gender == "Female")
        assert(filteredCharacters.toList == expectedFilteredCharacters)
      }
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