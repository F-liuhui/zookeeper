package com.cnpc.curator.innerclass;

/**
 * @ClassName InnerClassTest
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 */
public class InnerClassTest {

    private static String name;
    private String addr;
    private static int age;
    public static void test(){
        name="斩杀";
        age=19;
        Person person=new InnerClassTest().new Person();
        Student student= new InnerClassTest.Student();
        System.out.println(person.getAge());
        System.out.println(student.getName());

    }

    public static void main(String[] args) {
        test();
    }
    public class Person{

        public int getAge(){
            return age;
        }

    }
    public static class Student{

        public String getName(){
            return name;
        }
    }
}
