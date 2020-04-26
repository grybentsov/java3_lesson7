package ru.geekbrains.java3_lesson7;

public class MainClass {

    public static void main(String[] args) {
	Class_for_Tests myClass = new Class_for_Tests();
    TestsHandler.start(myClass.getClass());
    }
}
