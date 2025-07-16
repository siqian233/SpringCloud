package com.lightning.bean;

public class JavaCourse implements ICourse{
    @Override
    public void record() {
        System.out.println("java课程");
    }
}
