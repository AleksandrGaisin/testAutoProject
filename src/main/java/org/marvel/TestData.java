package org.marvel;

import org.marvel.models.Superhero;


public class TestData {
    public static final Superhero DEFAULT_HERO = new Superhero("Blade", "2002-03-15", "New York",
            "Martial Arts", "M", "+9959550431");
    public static Superhero getUpdatedHero (int id) {
        Superhero superhero = new Superhero();
        superhero.setId(id);
        superhero.setFullName("Updated Blade");
        superhero.setCity("Boston");
        superhero.setBirthDate("1990-01-01");
        superhero.setMainSkill("New Mortal skill");
        superhero.setGender("F");
        superhero.setPhone("88005500050");
        return superhero;
    }

    public static Superhero getUpdatedHero2 (int id) {
        return Superhero.builder()
                .id(id)
                .fullName("Updated Blade")
                .city("Boston")
                .birthDate("1990-01-01")
                .mainSkill("New Mortal skill")
                .gender("F")
                .phone("88005500050")
                .build();
    }
}
