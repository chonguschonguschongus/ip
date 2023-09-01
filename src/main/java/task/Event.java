package task;

import dukeutilities.TimeFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Event class represents a task that occurs within a specified time range.
 * It provides methods to create an Event task and generate file and display strings.
 */
public class Event extends Task {
    private String title;
    private TimeFormatter start;
    private TimeFormatter end;

    /**
     * Constructs an Event object with the specified response string, parsing title and time
     *
     * @param response The user's input representing the event task.
     */
    public Event(String response) {
        super(false);
        int toTrim = response.indexOf(" ");
        int info = response.indexOf("/");
        this.title = response.substring(toTrim + 1, info - 1);
        Pattern startPattern = Pattern.compile("/from (\\d+/\\d+/\\d+ \\d+)");
        Pattern endPattern = Pattern.compile("/to (\\d+/\\d+/\\d+ \\d+)");
        Matcher startMatcher = startPattern.matcher(response);
        Matcher endMatcher = endPattern.matcher(response);
        if (startMatcher.find() && endMatcher.find()) {
            String startDateTime = startMatcher.group(1);
            String endDateTime = endMatcher.group(1);
            this.start = new TimeFormatter(startDateTime);
            this.end = new TimeFormatter(endDateTime);
        } else {
            System.out.println("Incorrect format of date and time inputted");
        }
    }

    /**
     * Generates a string representation of the Event task for storage in a file.
     *
     * @return A formatted string representing the Event task.
     */
    @Override
    public Boolean compareTitle(String query) {
        return this.title.contains(query);
    }

    @Override
    public String toFileString() {
        if (this.done == true) {
            return "D | 1 | " + this.title + " | " + this.start.formatDate() +
                    " - " + this.end.formatDate();
        }
        return "D | 0 | " + this.title + " | " + this.start.formatDate() +
                " - " + this.end.formatDate();
    }


    /**
     * Generates a string representation of the Event task for display purposes.
     *
     * @return A formatted string representing the Event task.
     */
    @Override
    public String toString() {
        String s = String.format("| FROM: %s TO: %s |", start.formatDate(), end.formatDate());
        if (this.done == true) {
            return "[E] " + "[X] " + this.title + " " + s;
        }
        return "[E] " + "[ ] " + this.title + " " + s;
    }
}


