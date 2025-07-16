package com.lightning.factorymethod;

import com.lightning.bean.CCourse;
import com.lightning.bean.ICourse;

public class CCourseFactory implements ICourseFactory{
    public ICourse create() {
        return new CCourse();
    }
}
