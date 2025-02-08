import kotlin.random.Random

// Hangman Game class to handle game logic
class HangmanGame(val difficulty: String) {

    // Map of words categorized by difficulty levels
    // Easy words, Medium words, and Hard words
    private val words = mapOf(
        "easy" to listOf("cat", "dog", "bat"),
        "medium" to listOf("elephant", "guitar", "piano"),
        "hard" to listOf("hippopotamus", "submarine", "encyclopedia")
    )

    // Set the maximum number of incorrect guesses based on the chosen difficulty
    // Easy -> 7 mistakes, Medium -> 5 mistakes, Hard -> 3 mistakes
    private val maxMistakes = when (difficulty) {
        "easy" -> 7
        "medium" -> 5
        "hard" -> 3

        // Default to 5 if difficulty is not recognized
        else -> 5
    }

    // Randomly pick a word from the list of words for the selected difficulty
    private val word = words[difficulty]?.random() ?: "error"

    // Set to store the letters that have been guessed by the player
    private val guessedLetters = mutableSetOf<Char>()

    // Number of remaining attempts (incorrect guesses)
    private var remainingAttempts = maxMistakes

    // Function to display the word with guessed letters and underscores for unguessed ones
    fun displayWord() {
        var display = ""

        // Loop through each letter in the word
        // If the letter has been guessed, display it, otherwise show an underscore
        for (letter in word) {
            display += if (letter in guessedLetters) "$letter " else "_ "
        }

        // Show the current word with guessed letters and underscores
        println("Current word: $display")
    }

    // Function to handle a player's guess
    fun guessLetter(letter: Char) {

        // Check if the letter has already been guessed
        if (letter in guessedLetters) {

            // If yes, inform the player
            println("You've already guessed '$letter'. Try a different letter!")

            // Stop further execution if the letter was guessed already
            return
        }

        // Add the guessed letter to the set of guessed letters
        guessedLetters.add(letter)

        // If the guessed letter is in the word, it's a correct guess
        if (letter in word) {

            // Inform the player of a correct guess
            println("Good guess!")
        } else {

            // If it's an incorrect guess, decrease the remaining attempts
            remainingAttempts--

            // Inform the player of remaining attempts
            println("Incorrect guess. You have $remainingAttempts attempts left.")
        }
    }

    // Function to check if the game has ended (win or lose)
    fun checkGameStatus(): Boolean {

        // Check if all letters in the word have been guessed
        val wordComplete = word.all { it in guessedLetters }

        // If the word is complete, the player wins
        if (wordComplete) {

            // Player wins
            println("Congratulations! You've guessed the word: $word")

            // End the game
            return true
        }

        // If there are no remaining attempts, the player loses
        if (remainingAttempts <= 0) {

            // Player loses
            println("Game over! You've run out of attempts. The word was: $word")

            // End the game
            return true
        }

        // Game is still ongoing
        return false
    }
}

// Main function to start the game
fun main() {

    // Greet the player
    println("Welcome to Hangman!")

    // Ask the player to choose a difficulty level
    println("Choose difficulty: easy, medium, or hard.")

    // Read player's choice, default to "medium" if input is invalid
    val difficulty = readLine()?.lowercase() ?: "medium"

    // Create a new game object with the chosen difficulty
    val game = HangmanGame(difficulty)

    // Game loop: keep asking the player for guesses until the game ends
    while (true) {

        // Display the current word with guessed letters and underscores
        game.displayWord()

        // Prompt the player for a letter guess
        print("Enter a letter to guess: ")

        // Read the first character entered by the player
        val guess = readLine()?.firstOrNull()

        // Check if the input is a valid letter
        if (guess != null && guess.isLetter()) {

            // Process the guess, convert to lowercase
            game.guessLetter(guess.lowercaseChar())

            // Check if the game has ended (win or lose)
            // End the game if player has won or lost
            if (game.checkGameStatus()) break
        } else {

            // Inform the player if the input is invalid
            println("Please enter a valid letter.")
        }
    }
}
