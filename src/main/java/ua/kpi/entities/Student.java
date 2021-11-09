package ua.kpi.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Student {
    private String name;
    private double rating;
    private List<Exam> exams = new ArrayList<>();

    public OptionalDouble averageForTest() {
        double sum = 0;
        for (Exam exam:exams) {
            sum+=exam.getScore();
        }
        return (exams.size() > 0) ? OptionalDouble.of(sum / exams.size()): OptionalDouble.empty();
    }
    public double sumForTest() {
        double sum = 0;
        for (Exam exam:exams) {
            sum+=exam.getScore();
        }
        return sum;
    }

}
