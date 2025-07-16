package com.lightning.simplefactory1;

import com.lightning.bean.ICourse;

public class TestSimpleFactory {

    public static void main(String[] args) {

        CourseFactory courseFactory = new CourseFactory();
        ICourse course = courseFactory.create("java");
        course.record();

        ICourse course2 = courseFactory.create("python");
        course2.record();


    }

}
