package ua.kpi.services;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;

import org.junit.jupiter.api.Test;
import ua.kpi.entities.Exam;
import ua.kpi.entities.Exam.Type;
import ua.kpi.entities.Student;
import ua.kpi.repositories.StudentRepository;

class StudentServiceTest {

    private Student firstStudent = Student.builder()
            .name("1")
            .rating(10)
            .exams(Arrays.asList(Exam.of(Exam.Type.ENGLISH, 181)))
            .build();
    private Student secondStudent = Student.builder()
            .name("2")
            .rating(11)
            .exams(Arrays.asList(Exam.of(Exam.Type.ENGLISH, 182),
                    Exam.of(Exam.Type.MATH, 190)))
            .build();
    private Student thirdStudent =
            Student.builder()
                    .name("3")
                    .rating(11)
                    .exams(Arrays.asList(Exam.of(Exam.Type.ENGLISH, 183),
                            Exam.of(Exam.Type.MATH, 191)))
                    .build();
    private Student fourthStudent = Student.builder()
            .name("4")
            .rating(11)
            .exams(Arrays.asList())
            .build();

    private StudentRepository createStudentRepositoryWithAllStudents() {
        StudentRepository studentRepository = mock(StudentRepository.class);
        List<Student> allStudents = Arrays.asList(firstStudent, secondStudent,
                thirdStudent, fourthStudent);
        when(studentRepository.findAll()).thenReturn(allStudents);
        return studentRepository;
    }

    @Test
    void should_find_student_with_max_english() {
        StudentRepository studentRepository = createStudentRepositoryWithAllStudents();
        StudentService studentService = new StudentService(studentRepository);
        Optional<Student> studentOpt = studentService.findWithMaxExam(Type.ENGLISH);
        assertEquals(Optional.of(thirdStudent), studentOpt);
    }

    @Test
    void should_not_find_student_with_max_math() {
        StudentRepository studentRepository = mock(StudentRepository.class);
        when(studentRepository.findAll()).thenReturn(Arrays.asList(firstStudent, fourthStudent));
        StudentService studentService = new StudentService(studentRepository);
        Optional<Student> studentOpt = studentService.findWithMaxExam(Type.MATH);
        assertEquals(Optional.empty(), studentOpt);
    }

    @Test
    void should_find_students_who_have_enough_math_grade() {
        StudentRepository studentRepository = createStudentRepositoryWithAllStudents();
        StudentService studentService = new StudentService(studentRepository);
        final double mathPassRate = 190.0;
        List<Student> studentsWithMath = studentService.findWithEnoughExam(Type.MATH, mathPassRate);
        assertThat(studentsWithMath, containsInAnyOrder(secondStudent, thirdStudent));
    }

    @Test
    void should_not_find_students_who_have_enough_english_grade() {
        StudentRepository studentRepository = createStudentRepositoryWithAllStudents();
        StudentService studentService = new StudentService(studentRepository);
        final double englishPassRate = 190.0;
        List<Student> studentsWithEnglish = studentService.findWithEnoughExam(Type.ENGLISH, englishPassRate);
        assertThat(studentsWithEnglish, hasSize(0));
    }


    //4
    @Test
    void should_find_student_with_max_average() {
        StudentRepository studentRepository = createStudentRepositoryWithAllStudents();
        StudentService studentService = new StudentService(studentRepository);
        Optional<Student> studentOpt = studentService.findWithMaxExamAverage();
        assertEquals(Optional.of(thirdStudent), studentOpt);
    }
    //4

    /**
     *
     */
    //5
    @Test
    void should_find_student_with_two_exam() {
        StudentRepository studentRepository = createStudentRepositoryWithAllStudents();
        StudentService studentService = new StudentService(studentRepository);
        List<Student> students = studentService.findWithTwoExam();
        assertThat(students, containsInAnyOrder(secondStudent, thirdStudent));
    }

    @Test
    void should_not_find_student_with_two_exam() {
        StudentRepository studentRepository = createStudentRepositoryWithAllStudents();
        when(studentRepository.findAll()).thenReturn(Arrays.asList(firstStudent, fourthStudent));
        StudentService studentService = new StudentService(studentRepository);
        List<Student> students = studentService.findWithTwoExam();
        assertThat(students, hasSize(0));
    }
    //5

    /**
     *
     */
    //6
    @Test
    void should_find_student_with_math_and_one_more_exam() {
        StudentRepository studentRepository = createStudentRepositoryWithAllStudents();
        StudentService studentService = new StudentService(studentRepository);
        List<Student> students = studentService.findWithMathExamAndOneMore();
        assertThat(students, containsInAnyOrder(secondStudent, thirdStudent));
    }

    @Test
    void should_not_find_student_with_math_and_one_more_exam() {
        StudentRepository studentRepository = createStudentRepositoryWithAllStudents();
        when(studentRepository.findAll()).thenReturn(Arrays.asList(firstStudent, fourthStudent));
        StudentService studentService = new StudentService(studentRepository);
        List<Student> students = studentService.findWithMathExamAndOneMore();
        assertThat(students, hasSize(0));
    }

    //9
    @Test
    void should_find_average_math_exam_result() {
        StudentRepository studentRepository = createStudentRepositoryWithAllStudents();
        StudentService studentService = new StudentService(studentRepository);
        OptionalDouble result = studentService.findAverageMathExamResult();
        assertEquals(190.5, result.getAsDouble());
    }
    //11
    @Test
    void should_find_student_with_rating_more_than_average_and_pass_math() {
        StudentRepository studentRepository = createStudentRepositoryWithAllStudents();
        StudentService studentService = new StudentService(studentRepository);
        List<Student> students = studentService.findRatingMoreThanAverageAndPassMath();
        assertThat(students, containsInAnyOrder(thirdStudent, secondStudent));
    }

    @Test
    void should_not_find_student_with_rating_more_than_average_and_pass_math() {
        StudentRepository studentRepository = createStudentRepositoryWithAllStudents();
        when(studentRepository.findAll()).thenReturn(Arrays.asList(firstStudent, fourthStudent));
        StudentService studentService = new StudentService(studentRepository);
        List<Student> students = studentService.findRatingMoreThanAverageAndPassMath();
        assertThat(students, hasSize(0));
    }

    //14
    @Test
    void should_find_average_with_name() {
        StudentRepository studentRepository = createStudentRepositoryWithAllStudents();
        StudentService studentService = new StudentService(studentRepository);
        List<String> studentOpt = studentService.findAverageWithName();
        List<String> expected = new LinkedList<>();
        expected.add(firstStudent.getName() + " " + firstStudent.averageForTest());
        expected.add(secondStudent.getName() + " " + secondStudent.averageForTest());
        expected.add(thirdStudent.getName() + " " + thirdStudent.averageForTest());
        expected.add(fourthStudent.getName() + " " + fourthStudent.averageForTest());
        assertEquals(expected, studentOpt);
    }
}