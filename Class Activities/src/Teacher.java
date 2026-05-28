//
//public class Teacher {
//    private String name;
//    private String subject;
//
//    public Teacher(String name, String subject) {
//        this.name = name;
//        this.subject = subject;
//    }
//
//    public void teach() {
//        System.out.println("Teacher " + name + " is teaching " + subject + ".");
//    }
//}
//
// class Classroom {
//    private int roomNumber;
//
//    public Classroom(int roomNumber) {
//        this.roomNumber = roomNumber;
//    }
//
//    public void openRoom() {
//        System.out.println("Classroom " + roomNumber + " is now open.");
//    }
//}
//
// class School {
//     private Teacher teacher;
//     private Classroom classroom;
//
//     // Teacher and Classroom are now passed in — not created here
//     public School(Teacher teacher, Classroom classroom) {
//         this.teacher   = teacher;
//         this.classroom = classroom;
//     }
//
//     public void startDay() {
//         classroom.openRoom();
//         teacher.teach();
//     }
//}
//
//class Main {
//    public static void main(String[] args) {
//
//        Teacher teacher     = new Teacher("Mr. Smith", "Mathematics");
//        Classroom classroom = new Classroom(101);
//
//        School school = new School(teacher, classroom);
//        school.startDay();
//
//        System.out.println("--- School is closed ---");
//        school = null; // School is gone
//
//        teacher.teach(); // still works perfectly
//    }
//}