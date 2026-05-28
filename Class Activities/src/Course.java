//class Course {
//    String name;
//
//    public Course(String name) {
//        this.name = name;
//    }
//    public void show() {
//        System.out.println(name);
//    }
//}
//// aggregation fix
//class Student {
//    Course course;
//    public void setCourse(Course course) {
//        this.course = course;
//    }
//    public void showCourse() {
//        course.show();
//    }
//}
//
//
//// aggregation fix
////class Main {
//    public static void main(String[] args) {
//
//        // Course created outside Student — exists independently
//        Course course = new Course("Math");
//
//        // Assigned directly to the field from outside
//        Student student = new Student();
//        student.setCourse(course);
//
//        student.showCourse();
//
//    }
//}
//
////class Student {
////    private Course course; // now private — nobody can touch it directly from outside
////
////    // Course is injected through the constructor
////    public Student(Course course) {
////        this.course = course;
////    }
////
////    public void showCourse() {
////        course.show();
////    }
////}
//
////class Main {
////    public static void main(String[] args) {
////
////        Course course = new Course("Math");
////
////        Student student = new Student(course);
////        student.showCourse();
////    }
////}