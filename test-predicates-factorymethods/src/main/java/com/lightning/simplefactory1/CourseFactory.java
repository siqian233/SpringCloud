package com.lightning.simplefactory1;

import com.lightning.bean.ICourse;
import com.lightning.bean.JavaCourse;
import com.lightning.bean.pythonCourse;

public class CourseFactory {
    // open-close principle 开闭原则

    // 方案一： 通过if-else
    public ICourse create(String name)
    {
        if("java".equals(name))
        {
            return new JavaCourse();
        }
        else if("python".equals(name))
        {
            return new pythonCourse();
        }
        return null;
    }

    // 方案二： 通过反射
    public ICourse create2(String className){
        try{
            if(!"".equals(className)&& className!=null){
                return (ICourse) Class.forName(className).newInstance();
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // 方案三： 通过工厂方法
    public ICourse create3(Class<? extends ICourse> clazz){
        try{
            if(clazz!=null){
                return clazz.newInstance();
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
