package net.avgl.ekz.onleihe;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public class Person {
    @NotNull
    private String name;
    @NotNull
    private String lastname;
    private Instant birthday;
    private Integer salary;

    public Person(@NotNull String name, @NotNull String lastname, Instant birthday, int salary) {
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(@NotNull String lastname) {
        this.lastname = lastname;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
