package model;

public class CherryTree implements Tree{
    String name;
    int age;

    public CherryTree(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOlderThan3Years() {
        return this.age > 3;
    }

    public String toString() {
        return "Cherry Tree: " + name + ", age " + age;
    }

    public boolean equals(Object other) {
        if (other instanceof CherryTree) {
            CherryTree o = (CherryTree) other;
            return this.age == o.getAge() && this.name.equals(o.getName());
        } else return false;
    }
}
