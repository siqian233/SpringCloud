package com.lightning.factorymethod;

import com.lightning.bean.ICourse;

public class TestFactoryMethod {
    public static void main(String[] args) {
        ICourseFactory factory = new JavaCourseFactory();
        ICourse course = factory.create();
        course.record();

        factory = new PythonCourseFactory();
        course = factory.create();
        course.record();

        factory = new CCourseFactory();
        course = factory.create();
        course.record();
    }
}
