package org.erik.timesheets.utils;

import java.util.Random;

public class FakerUtils {

    private static final Random random = new Random();

    private static final String[] FIRST_PARTS = {
            "I", "He", "She", "Someone", "We", "They", "I, myself,"
    };
    private static final String[] SECOND_PARTS = {
            "deleted", "fixed", "worked on", "changed", "sorted",
            "entered", "clicked", "messed up"
    };
    private static final String[] THIRD_PARTS = {
            "some", "all of the", "a few", "one of the", "the first of the",
            "two", "three", "many"
    };
    private static final String[] FOURTH_PARTS = {
            "things", "stuff", "records", "tables", "documents",
            "customers", "contracts", "reports"
    };

    private static final String[] CLIENTS = {
            "WartSilo", "No KIA", "Hard Wall", "WellMet", "Megasoft",
            "Row.io", "Kindman", "Weehoori"
    };

    private static final String[] PROJECTS_PART_ONE = {
            "A new", "The best", "Worlds first", "Super", "Mega",
            "A good", "Our", "THE "
    };

    private static final String[] PROJECTS_PART_TWO = {
            "paper", "document", "payment", "medical", "PoS", "online",
            "workforce", "HR", "CRM"
    };

    private static final String[] PROJECTS_PART_THREE = {
            "app", "application", "software", "webapp", "project", "proof of concept",
            "demo", "prototype", "improvement", "design", "report"
    };

    private static final String[] FIRST_NAMES = {
            "Carl", "Mick", "Mike", "Bob", "Chad", "Luke", "Max", "Ralph",
            "Ann", "Mary", "Carmen", "Britney", "Charlotte", "Leia", "Diana", "Renata"
    };

    private static final String[] SECOND_NAMES = {
            "McTest", "McWork", "Workson", "Trainée", "Bossman", "Testerson",
            "Hardwork", "Fireded", "Screwup", "Failure"
    };

    private static String getEntry(String[] array) {
        return array[random.nextInt(array.length)];
    }

    public static String getComment() {
        return String.format("%s %s %s %s.",
                getEntry(FIRST_PARTS), getEntry(SECOND_PARTS),
                getEntry(THIRD_PARTS), getEntry(FOURTH_PARTS));
    }

    public static String getClient() {
        return getEntry(CLIENTS);
    }

    public static String getProject() {
        return String.format("%s %s %s",
                getEntry(PROJECTS_PART_ONE),
                getEntry(PROJECTS_PART_TWO),
                getEntry(PROJECTS_PART_THREE));
    }

    public static String getFirstName() {
        return getEntry(FIRST_NAMES);
    }

    public static String getLastName() {
        return getEntry(SECOND_NAMES);
    }
}
