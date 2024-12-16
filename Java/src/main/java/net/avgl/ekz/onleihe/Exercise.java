package net.avgl.ekz.onleihe;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;



class Exercise {

//    private final String[][] persons = new String[10][3];

    private final HashSet<Person> persons = new HashSet<>();

    public static void main(String[] args) {
        String[] person1 = {"John", "Doe", "2000-01-01"};
        String[] person2 = {"Jane", "Doe", "2000-01-01"};
        String[] person3 = {"Max", "Mustermann", "2000-01-01"};
        String[] person4 = {"Erika", "Mustermann", "2000-01-01"};

        Exercise exercise = new Exercise();
        exercise.addPersonToList(person1);
        exercise.addPersonToList(person2);
        exercise.addPersonToList(person3);
        exercise.addPersonToList(person4);

        String[][] personsArray = {
                {"John", "Doe", "2000-01-01", "5"},
                {"Jane", "Doe", "2000-01-01", "10"},
                {"Max", "Mustermann", "2000-01-01", "15"},
                {"Erika", "Mustermann", "2000-01-01", "20"}
        };
        String[] p = exercise.getResult(personsArray);
System.out.println("Person with highest salary: " + p[0] + " " + p[1] + " " + p[2] + " " + p[3]);

    }

    /**
     * Method is used to add a person entry to a personList
     * Do not Change Method Signature
     *
     * @param person: firstname (@NotNull String), lastName (@NotNull String), BirthDate (String), salary (Integer)
     *                Example: ["John", "Doe", "2000-01-01"]
     *                Example: ["John", "Doe", "2000-01-01", "5"]
     *                Example: ["John", "Doe", "", "5"]
     *                Example: ["John", "Doe"]
     */
    public void addPersonToList(String[] person) {
//        // please implement
//        Instant birthday = null;
//        System.out.println("add persons: " + person);
//        if (person.length < 2) {
//            throw new IllegalArgumentException("Person must have at least a first and last name");
//        }
//        if (person.length > 4) {
//            throw new IllegalArgumentException("Person must have at most a first and last name, birthday and salary");
//        }
//        if (person[2] != null) {
//            birthday = person[2].isBlank() ? Instant.parse(person[2]) : null;
//        }
//
//        Person newPerson = new Person(person[0], person[1], birthday, (person[3] != null) ? Integer.parseInt(person[3]) : 0);
//        persons.add(newPerson);

    }


    /**
     * Get person with highest salary. Persons without a salary do not count.
     */
//    String[] getResult() {
        // please implement
        // regex to get the salary -> 03 position
//        Regex regex = new Regex();
//        return this.persons.stream()
//                .filter(p -> p.getSalary() != null)
//                .max((p1, p2) -> Integer.compare(p1.getSalary(), p2.getSalary()));

//    }


    /**
     * Get person with highest salary. Persons without a salary do not count.
     * @param person: firstname (@NotNull String), lastName (@NotNull String), BirthDate (String), salary (Integer)
     *      *                Example: ["John", "Doe", "2000-01-01"]
     *      *                Example: ["John", "Doe", "2000-01-01", "5"]
     *      *                Example: ["John", "Doe", "", "5"]
     *      *                Example: ["John", "Doe"]
     */
    String[] getResult(String[][] persons) {
        // please implement
        // check incomming person
        if (persons == null || persons.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty");
        }
//        ArrayList<String[]> filteredPersons = new ArrayList<String[]>();
//        String[] personWithHighestSalary = null;
//
//
//        // has person salary? filter all persons with salary
//        for (String[] p: persons) {
//            if(p.length > 3) {
//                filteredPersons.add(p);
//            }
//        }
//
//        // sort the persons by salary
//        personWithHighestSalary = filteredPersons.stream()
//                .sorted()
//                .max((p1, p2) -> Integer.compare(Integer.parseInt(p1[3]), Integer.parseInt(p2[3])))
//                .orElse(null);
//
//       return personWithHighestSalary;
        return Arrays.stream(persons)
                .filter(p -> p.length > 3)
                .filter(p -> isValidSalary(p[3]))
                .max(Comparator.comparingInt(p -> Integer.parseInt(p[3])))
                .orElse(null);

    }

    private boolean isValidSalary(String salary) {
        try {
            Integer.parseInt(salary);
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }
}
add