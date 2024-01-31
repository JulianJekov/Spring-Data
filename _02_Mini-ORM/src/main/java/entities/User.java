package entities;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.time.LocalDate;
import java.util.Date;

@Entity(name = "users")
public class User {
    @Id(name = "id")
    private long id;
    @Column(name = "user_name")
    private String username;
    @Column(name = "age")
    private int age;
    @Column(name = "registration_date")
    private Date registration;
    @Column(name = "salary")
    private int salary;

    public User() {
    }


    public User(String username, int age, Date registration, int salary) {
        this.username = username;
        this.age = age;
        this.registration = registration;
        this.salary = salary;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getRegistration() {
        return registration;
    }

    public void setRegistration(Date registration) {
        this.registration = registration;
    }
}
