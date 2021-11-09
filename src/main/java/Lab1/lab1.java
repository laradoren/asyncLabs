package Lab1;

import java.util.Arrays;
import java.util.stream.IntStream;

public class lab1 {

    public static void main(String[] args) {
        lab1 customIntArray = new lab1();

        System.out.println(customIntArray);
        //4
        customIntArray.countZeroElements();
        //9
        customIntArray.countDuplicateOfInput();
        //14
        customIntArray.findLastNegative();
        //16
        customIntArray.ifArraySortByDecreasing();
        //11
        System.out.println(customIntArray);
        customIntArray.doAllEvenNumbersZero();
        System.out.println(customIntArray);
    }

    private final int []array = new int[10];

    public lab1() {
        for (int i = 0; i < array.length; i++)
            array[i] = -10  + (int) (Math.random() * 20);
    }

    @Override
    public String toString() {
        return "CustomIntArray{" +
                Arrays.toString(array) +
                '}';
    }
    //4
    // создали поток, фильтранули по всем нулевым елементам и посчитали
    public void countZeroElements() {
        IntStream stream = Arrays.stream(array);
        System.out.println("Количество нулевых элементов: " + stream.filter(n -> n == 0).count());
    }
    //9
    // срандомил индекс числа, повторения которого в масиве я буду искать
    // а потом просто отфильтровал поток по этому числу и посчитал
    public void countDuplicateOfInput() {
        int indexInput = (int) (Math.random() * array.length);
        IntStream stream = Arrays.stream(array);
        System.out.println("Ищем количество повторений числа:" + array[indexInput]);
        int result = (int) stream.filter(n -> n == array[indexInput]).count();
        System.out.println("Ответ: " + result);
    }
    //14
    // развернули масив наоборот, отфильровали его по только отрицательным
    // элементам и по первому найденому индексу взяли элемент масива
    public void findLastNegative() {
        System.out.println("Последний отрицательный элемент: " +
                array[IntStream.iterate(array.length - 1, i -> i - 1).limit(array.length).
                        filter(n -> array[n] < 0).findFirst().getAsInt()]);
    }
    //16
    // просто сортируем массив и сверяемся с текущем
    public void ifArraySortByDecreasing() {
        System.out.println("Массив отсортирован по убыванию: " + IntStream.range(0, array.length - 1).allMatch(i -> array[i] >= array[i + 1]));
    }
    //11
    //просто фильтруем все четные элементы и приравняем их нулю
    public void doAllEvenNumbersZero() {
        IntStream.range(0, array.length).filter(n -> array[n]%2==0).forEach(x-> array[x] = 0);
    }
}
