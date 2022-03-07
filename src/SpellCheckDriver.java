
/***********************
Alex Furmuzan
Daniel Perez
Lab 7 - Spell Check Game
Professor Mukarram
***********************/
import java.util.*;
import java.io.*;

public class SpellCheckDriver { // this is the main class that runs the program.

	public static boolean allNull(String[] arr) { // This nulls the word if it has already been chosen.
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null) {
				return false;
			}
		}

		return true;

	}

	public static String getGuess(String[] correctArray, String[] wrongArray) { // This obtains the random word from
																				// either of the two arrays.
		Random rand = new Random();
		int ranArray = rand.nextInt(2) + 1;

		if (ranArray == 1) { // This picks the word if the correct array is chosen.
			if (allNull(correctArray)) {
				return "";
			}

			int correctWord = rand.nextInt(10);

			while (correctArray[correctWord] == null) {
				correctWord = rand.nextInt(10);
			}

			String word = correctArray[correctWord];

			return word;

		} else { // This picks the word if the wrong array is chosen.

			if (allNull(wrongArray)) {
				return "";
			}

			int wrongWord = rand.nextInt(15);

			while (wrongArray[wrongWord] == null) {
				wrongWord = rand.nextInt(15);
			}

			String word = wrongArray[wrongWord];

			return word;

		}
	}

	public static String[] inputArrays(Scanner file, int size) { // this constructs the arrays and sends them to the
																	// file.
		String[] newArray = new String[size];
		int i = 0;

		while (file.hasNext()) {
			newArray[i] = file.next();
			i++;
		}

		return newArray;
	}

	public static void main(String[] args) throws FileNotFoundException { // this main method declares the scanners for
																			// the files and calls the play method to
																			// run the program.
		Scanner correctfile = new Scanner(new File("correct.txt"));
		Scanner wrongfile = new Scanner(new File("wrong.txt"));
		Play(correctfile, wrongfile); // this calls the method to run the game.
	}

	public static void Play(Scanner correctfile, Scanner wrongfile) { // this method runs the game.
		Scanner console = new Scanner(System.in);
		String correct[] = inputArrays(correctfile, 10);
		String wrong[] = inputArrays(wrongfile, 15);
		String again;
		int score = 0;
		int count = 0;

		do { // this loops the game if the user wishes to play again.
			count++;
			String word = getGuess(correct, wrong); // this calls the getGuess method to obtain the word.
			System.out.println("Your magic word is: " + word + ". Is it correct? (Type \"y\" for yes or \"n\" for no)");
			String input = console.nextLine();
			validateInput(input, console); // this validates the user's input.

			if (wordFinder(correct, wrong, word) == 1) { // This determines the score if the word from the correct file
															// was chosen.

				if (input.equalsIgnoreCase("y")) {
					System.out.println("You are Correct!");
					score++;
				}

				else {
					System.out.println("That was wrong, sorry!");
					score--;
				}

				correct[wordNuller(correct, wrong, word)] = null;

			} else if (wordFinder(correct, wrong, word) == 2) { // This determines the score if the word from the wrong
																// file was chosen.

				if (input.equalsIgnoreCase("n")) {
					System.out.println("You are Correct!");
					score++;

				} else {
					System.out.println("Sorry, wrong answer!");
					score--;
				}

				wrong[wordNuller(correct, wrong, word)] = null;
			}

			System.out.println("Would you like to play again? (Type \"y\" for yes or \"n\" for no)");
			again = console.nextLine();

			while (!again.equalsIgnoreCase("Y") && !again.equalsIgnoreCase("N")) { // This validates the user input of
																					// whether or not they want to play
																					// again.
				System.out.println("Sorry! I don't understand. Type \"y\" or \"n\"");
				again = console.nextLine();
			}

			if (count == 25) {
				System.out.println("Actually, you ran out of tries! Thanks for playing.");
				again = "n";
			}

		} while (again.equalsIgnoreCase("Y") && count <= 25); // this repeats the game if the user desires to.

		System.out.println("Your total score was: " + score);
	}

	public static void validateInput(String input, Scanner console) { // This validates the user's input.
		while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
			System.out.println("Sorry! Invalid input, please input \"y\" or \"n\"");
			input = console.nextLine();
		}
	}

	public static int wordFinder(String[] correct, String[] wrong, String word) { // This finds the word whether it was
																					// in the correct or wrong array.
		for (int i = 0; i < correct.length; i++) {
			if (word == correct[i]) {
				return 1;
			}
		}
		for (int i = 0; i < wrong.length; i++) {
			if (word == wrong[i]) {
				return 2;
			}
		}

		return 0;
	}

	public static int wordNuller(String[] correct, String[] wrong, String word) { // This throws away the word after we
																					// use it.
		for (int i = 0; i < correct.length; i++) {
			if (word == correct[i]) {
				return i;
			}
		}
		for (int i = 0; i < wrong.length; i++) {
			if (word == wrong[i]) {
				return i;
			}
		}

		return 0;
	}
}