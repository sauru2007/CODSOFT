import java.util.*;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private List<String> schedule;
    private List<Student> registeredStudents;

    public Course(String courseCode, String title, String description, int capacity, List<String> schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.registeredStudents = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSlots() {
        return capacity - registeredStudents.size();
    }

    public List<String> getSchedule() {
        return schedule;
    }

    public boolean registerStudent(Student student) {
        if (registeredStudents.size() < capacity) {
            registeredStudents.add(student);
            return true;
        }
        return false;
    }

    public boolean removeStudent(Student student) {
        return registeredStudents.remove(student);
    }

    public List<Student> getRegisteredStudents() {
        return registeredStudents;
    }
}

class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void addCourse(Course course) {
        registeredCourses.add(course);
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
    }
}

public class CourseRegistrationSystem {

    private static List<Course> courseDatabase = new ArrayList<>();
    private static List<Student> studentDatabase = new ArrayList<>();

    public static void main(String[] args) {
        initializeCourses();
        initializeStudents();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Student Course Registration System ---");
            System.out.println("1. List Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. View Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    listCourses();
                    break;
                case 2:
                    registerForCourse(scanner);
                    break;
                case 3:
                    dropCourse(scanner);
                    break;
                case 4:
                    viewRegisteredCourses(scanner);
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeCourses() {
        courseDatabase.add(new Course("CS101", "Introduction to Programming", "Learn the basics of programming.", 30, Arrays.asList("Mon 10AM", "Wed 10AM")));
        courseDatabase.add(new Course("MATH201", "Calculus I", "An introduction to calculus.", 25, Arrays.asList("Tue 11AM", "Thu 11AM")));
        courseDatabase.add(new Course("PHY301", "Physics for Engineers", "Basic principles of physics for engineering students.", 20, Arrays.asList("Mon 1PM", "Wed 1PM")));
    }

    private static void initializeStudents() {
        studentDatabase.add(new Student("S001", "Alice"));
        studentDatabase.add(new Student("S002", "Bob"));
    }

    private static void listCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courseDatabase) {
            System.out.println(course.getCourseCode() + ": " + course.getTitle());
            System.out.println("   Description: " + course.getDescription());
            System.out.println("   Schedule: " + course.getSchedule());
            System.out.println("   Available Slots: " + course.getAvailableSlots());
        }
    }

    private static void registerForCourse(Scanner scanner) {
        System.out.print("Enter your Student ID: ");
        String studentID = scanner.nextLine();

        Student student = findStudentByID(studentID);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter the Course Code to register: ");
        String courseCode = scanner.nextLine();

        Course course = findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (course.getAvailableSlots() == 0) {
            System.out.println("No available slots in this course.");
            return;
        }

        if (course.registerStudent(student)) {
            student.addCourse(course);
            System.out.println("Successfully registered for the course.");
        } else {
            System.out.println("Registration failed.");
        }
    }

    private static void dropCourse(Scanner scanner) {
        System.out.print("Enter your Student ID: ");
        String studentID = scanner.nextLine();

        Student student = findStudentByID(studentID);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter the Course Code to drop: ");
        String courseCode = scanner.nextLine();

        Course course = findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (course.removeStudent(student)) {
            student.dropCourse(course);
            System.out.println("Successfully dropped the course.");
        } else {
            System.out.println("You are not registered in this course.");
        }
    }

    private static void viewRegisteredCourses(Scanner scanner) {
        System.out.print("Enter your Student ID: ");
        String studentID = scanner.nextLine();

        Student student = findStudentByID(studentID);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("\nRegistered Courses for " + student.getName() + ":");
        for (Course course : student.getRegisteredCourses()) {
            System.out.println(course.getCourseCode() + ": " + course.getTitle());
        }
    }

    private static Student findStudentByID(String studentID) {
        for (Student student : studentDatabase) {
            if (student.getStudentID().equals(studentID)) {
                return student;
            }
        }
        return null;
    }

    private static Course findCourseByCode(String courseCode) {
        for (Course course : courseDatabase) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
}

