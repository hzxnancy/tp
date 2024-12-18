package seedu.exchangecoursemapper.command;

import seedu.exchangecoursemapper.exception.Exception;
import seedu.exchangecoursemapper.ui.UI;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.exchangecoursemapper.constants.Assertions.EMPTY_JSON_FILE;
import static seedu.exchangecoursemapper.constants.Assertions.NULL_JSON_FILE;
import static seedu.exchangecoursemapper.constants.Assertions.NO_NUS_COURSE_CODE_PARSED;
import static seedu.exchangecoursemapper.constants.Assertions.NO_COURSE_INFORMATION;
import static seedu.exchangecoursemapper.constants.Logs.EXECUTING_COMMAND;
import static seedu.exchangecoursemapper.constants.Logs.SUCCESS_READ_JSON_FILE;
import static seedu.exchangecoursemapper.constants.Logs.FAILURE_READ_JSON_FILE;
import static seedu.exchangecoursemapper.constants.Logs.COMPLETE_EXECUTION;
import static seedu.exchangecoursemapper.constants.Logs.NO_NUS_COURSE_CODE_FILTER;
import static seedu.exchangecoursemapper.constants.Logs.FILTER_COURSES_LIMIT;
import static seedu.exchangecoursemapper.constants.Logs.LIST_MAPPABLE_COURSES;
import static seedu.exchangecoursemapper.constants.Commands.COMMAND_WORD_INDEX;
import static seedu.exchangecoursemapper.constants.Commands.ZERO_INDEX_OFFSET;
import static seedu.exchangecoursemapper.constants.Commands.FILTER_COURSES_MAX_ARGS;
import static seedu.exchangecoursemapper.constants.JsonKey.COURSES_ARRAY_LABEL;
import static seedu.exchangecoursemapper.constants.JsonKey.NUS_COURSE_CODE_KEY;
import static seedu.exchangecoursemapper.constants.Messages.NO_MAPPABLE_COURSES_MESSAGE;
import static seedu.exchangecoursemapper.constants.Regex.REPEATED_SPACES;
import static seedu.exchangecoursemapper.constants.Regex.SPACE;

/**
 * FilterCoursesCommand allows users to search and filter for mappable Partner University (PU) Courses
 * to one NUS course, with the input as the NUS course code.
 */
public class FilterCoursesCommand extends CheckInformationCommand {
    private static final Logger logger = Logger.getLogger(FilterCoursesCommand.class.getName());
    private UI ui;

    /**
     * Class Constructor
     */
    public FilterCoursesCommand() {
        ui = new UI();
    }

    /**
     * Executes the command to filter out possible mappable PU courses
     * to a user specified NUS course.
     *
     * @param userInput NUS course in NUS course code format to find mappable PU courses.
     */
    @Override
    public void execute(String userInput) {
        logger.log(Level.INFO, EXECUTING_COMMAND);
        try {
            JsonObject jsonObject = super.createJsonObject();
            logger.log(Level.INFO, SUCCESS_READ_JSON_FILE);
            assert jsonObject != null : NULL_JSON_FILE;
            assert !jsonObject.isEmpty() : EMPTY_JSON_FILE;
            String courseToFind = getNusCourseCode(userInput);
            displayMappableCourses(jsonObject, courseToFind.toLowerCase());
        } catch (IOException e) {
            logger.log(Level.WARNING, FAILURE_READ_JSON_FILE);
            System.out.println(Exception.fileReadError());
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, e.getMessage());
            System.out.println(e.getMessage());
        }
        logger.log(Level.INFO, COMPLETE_EXECUTION);
    }

    /**
     * Returns the user specified NUS course code as a String to use as a filter.
     *
     * @param userInput A String containing the user's input.
     * @return a String containing the extracted information: NUS course code.
     */
    public String getNusCourseCode(String userInput) throws IllegalArgumentException {
        String input = userInput.trim().replaceAll(REPEATED_SPACES, SPACE);
        String[] inputDetails = input.split(SPACE);
        if (inputDetails.length == COMMAND_WORD_INDEX + ZERO_INDEX_OFFSET) {
            logger.log(Level.WARNING, NO_NUS_COURSE_CODE_FILTER);
            throw new IllegalArgumentException(Exception.missingNusCourseCode());
        }
        if (inputDetails.length > FILTER_COURSES_MAX_ARGS) {
            logger.log(Level.WARNING, FILTER_COURSES_LIMIT);
            throw new IllegalArgumentException(Exception.filterCoursesLimitExceeded());
        }
        assert inputDetails[1] != null : NO_NUS_COURSE_CODE_PARSED;
        return inputDetails[1];
    }

    /**
     * Executes the main logic of going through each PU, filtering out possible mappable courses for the NUS course.
     *
     * @param jsonObject Database containing course information.
     * @param courseToFind a String containing the NUS course code that acts as the filter.
     */
    public void displayMappableCourses(JsonObject jsonObject, String courseToFind) {
        Set<String> universityNames = jsonObject.keySet();
        boolean isCourseFound = false;
        for (String universityName : universityNames) {
            assert universityName != null && !universityName.isEmpty();
            JsonArray courses = jsonObject.getJsonObject(universityName).getJsonArray(COURSES_ARRAY_LABEL);
            int numberOfCourses = courses.size();
            logger.log(Level.INFO, LIST_MAPPABLE_COURSES);
            isCourseFound = isCourseFound(courseToFind, universityName, numberOfCourses, courses, isCourseFound);
        }

        if (!isCourseFound) {
            System.out.println(NO_MAPPABLE_COURSES_MESSAGE);
        }
    }

    /**
     * Returns true if a PU has at least one course that can be mapped to the NUS course, false otherwise.
     * If a course is mappable, a UI function will be called to print out the course information.
     *
     * @param courseToFind a String containing the NUS course code that acts as the filter.
     * @param universityName a String containing the name of the PU we are searching through.
     * @param numberOfCourses total number of courses that the PU offers that are mappable to SoC modules.
     * @param courses a JsonArray that contains all courses that the PU offers that are mappable to SoC modules.
     * @param isCourseFound a boolean value that tracks whether at least one mappable course has been found,
     *                      throughout all PUs
     * @return true if a PU has at least one course that can be mapped to the NUS course, false otherwise.
     */
    public boolean isCourseFound(String courseToFind, String universityName, int numberOfCourses,
                                         JsonArray courses, boolean isCourseFound) {
        for (int i = 0; i < numberOfCourses; i += 1) {
            JsonObject course = courses.getJsonObject(i);
            assert course != null : NO_COURSE_INFORMATION;
            String nusCourseCode = course.getString(NUS_COURSE_CODE_KEY);

            if (nusCourseCode.equalsIgnoreCase(courseToFind)) {
                ui.printMappableCourse(universityName, course);
                isCourseFound = true;
            }
        }
        return isCourseFound;
    }
}
