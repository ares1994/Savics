package com.arepadeobiri.savics;

public class Patient {
    private final String fullName;
    private final String gender;
    private final int age;

    public Patient(String fullName, String gender, int age) {
        this.fullName = fullName;
        this.gender = gender;
        this.age = age;
    }


    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[fullName=" + fullName + " + gender=" + gender + " age=" + age + "]";
    }


}
