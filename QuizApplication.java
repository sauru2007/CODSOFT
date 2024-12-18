import java.util.Scanner;
import java.util.concurrent.*;

class Question {
    private String questionText;
    private String[] options;
    private int correctOption;

    public Question(String questionText, String[] options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}

public class QuizApplication {

    private static final int TIME_LIMIT_SECONDS = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Question[] questions = {
            new Question("What is the capital of France?", new String[]{"1. Berlin", "2. Paris", "3. Madrid", "4. Rome"}, 2),
            new Question("Which programming language is used for Android development?", new String[]{"1. Java", "2. Python", "3. C++", "4. Ruby"}, 1),
            new Question("Who wrote 'Hamlet'?", new String[]{"1. Charles Dickens", "2. Mark Twain", "3. William Shakespeare", "4. J.K. Rowling"}, 3)
        };

        int score = 0;
        for (Question question : questions) {
            System.out.println("\n" + question.getQuestionText());
            for (String option : question.getOptions()) {
                System.out.println(option);
            }

            Integer userAnswer = getAnswerWithinTimeLimit(scanner);

            if (userAnswer == null) {
                System.out.println("Time's up! Moving to the next question.");
            } else if (userAnswer == question.getCorrectOption()) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong answer. The correct option was " + question.getCorrectOption() + ".");
            }
        }

        System.out.println("\nQuiz Over!");
        System.out.println("Your final score is: " + score + " out of " + questions.length);

        scanner.close();
    }

    private static Integer getAnswerWithinTimeLimit(Scanner scanner) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            System.out.print("Your answer: ");
            return scanner.nextInt();
        });

        try {
            return future.get(TIME_LIMIT_SECONDS, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            return null;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        } finally {
            executor.shutdownNow();
        }
    }
}

