package org.seckill.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by daojia on 2018-5-31.
 */
public class People implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private short age;
    private School school;
//    private List<Friend> relationShip;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

//    public List<Friend> getRelationShip() {
//        return relationShip;
//    }
//
//    public void setRelationShip(List<Friend> relationShip) {
//        this.relationShip = relationShip;
//    }
//
//    @Override
//    public String toString() {
//        return "People{" +
//                "name='" + name + '\'' +
//                ", age=" + age +
//                ", school=" + school +
//                ", relationShip=" + relationShip +
//                '}';
//    }
}