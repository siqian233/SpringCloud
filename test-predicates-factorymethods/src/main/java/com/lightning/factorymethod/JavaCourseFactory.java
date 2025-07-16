package com.lightning.factorymethod;

import com.lightning.bean.ICourse;
import com.lightning.bean.JavaCourse;

public class JavaCourseFactory implements ICourseFactory{
    @Override
    public ICourse create() {
        return new JavaCourse();
    }
}
