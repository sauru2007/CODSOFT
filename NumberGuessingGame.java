import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        boolean playAgain = true;
        int totalScore = 0;
        
        System.out.println("Welcome to the Number Guessing Game!");

        while (playAgain) {
            int min = 1;
            int max = 100;
            int targetNumber = random.nextInt(max - min + 1) + min;
            int attemptsLeft = 10;
            boolean guessedCorrectly = false;

            System.out.println("\nI have selected a number between " + min + " and " + max + ". Can you guess it?");
            System.out.println("You have " + attemptsLeft + " attempts.");

            while (attemptsLeft > 0) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                
                if (userGuess == targetNumber) {
                    System.out.println("Congratulations! You guessed the number correctly.");
                    totalScore += attemptsLeft;
                    guessedCorrectly = true;
                    break;
                } else if (userGuess > targetNumber) {
                    System.out.println("Your guess is too high.");
                } else {
                    System.out.println("Your guess is too low.");
                }

                attemptsLeft--;
                if (attemptsLeft > 0) {
                    System.out.println("You have " + attemptsLeft + " attempts left.");
                } else {
                    System.out.println("Sorry, you've run out of attempts. The correct number was " + targetNumber + ".");
                }
            }

            if (guessedCorrectly) {
                System.out.println("Great job! Your score for this round is " + attemptsLeft + " points.");
            } else {
                System.out.println("Better luck next time!");
            }

            System.out.print("Do you want to play another round? (yes/no): ");
            String userResponse = scanner.next().toLowerCase();
            playAgain = userResponse.equals("yes");
        }

        System.out.println("\nThank you for playing! Your total score is: " + totalScore + " points.");

        if (totalScore > 50) {
            System.out.println("Amazing performance! You really have a knack for this game.");
        } else if (totalScore > 20) {
            System.out.println("Good job! You did well overall.");
        } else {
            System.out.println("Keep practicing! You'll get better with more tries.");
        }

        scanner.close();
    }
}
