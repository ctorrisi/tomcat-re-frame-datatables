package ctorrisi.tomcatbackend.entity;

public class Person {

    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void incrementAge() {
        ++this.age;
    }

}
