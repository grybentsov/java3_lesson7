package ru.geekbrains.java3_lesson7;

public class Class_for_Tests {
    @BeforeSuite
    public void methodBeforeSuite() {
        System.out.println("This is BeforeSuite method.");
    }
    @Test
    public void testingMethodOne() {
        System.out.println("Testing method with priority = 7.");
    }

    @Test(priority = 1)
    public void testingMethodTwo() {
        System.out.println("Testing method with priority = 2.");
    }

    @Test
    public void testingMethodThree() {
        System.out.println("Testing method with default priority.");
    }

    @AfterSuite
    public void methodAfterSuite() {
        System.out.println("This is AfterSuite method.");
    }
}
