package com.lightning.factorymethod;

import com.lightning.bean.ICourse;
import com.lightning.bean.pythonCourse;

public class PythonCourseFactory implements ICourseFactory{
    @Override
    public ICourse create() {
        return new pythonCourse();
    }
}
