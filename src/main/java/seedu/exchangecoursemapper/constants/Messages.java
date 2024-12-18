package seedu.exchangecoursemapper.constants;


public class Messages {
    /* For UI layout */
    public static final String LINE_SEPARATOR = "-----------------------------------------------------";

    /* Messages for all invalid CLI commands */
    public static final String INVALID_COMMAND_MESSAGE = "Please provide a valid command!" +
            "\nType 'commands' to find out what we can help you with :)";

    /* Messages for FilterCoursesCommand */
    public static final String NO_MAPPABLE_COURSES_MESSAGE = "No courses found for the given course code.";
    public static final String PARTNER_UNIVERSITY_HEADER = "Partner University: ";
    public static final String PARTNER_UNIVERSITY_COURSE_CODE_HEADER = "Partner University Course Code: ";

    /* Messages for DeleteCoursesCommand */
    public static final String DELETE_COURSE_PLAN_HEADER = "You have deleted the course from your plan: ";

    public static final String LIST_RELEVANT_PU = "The relevant universities are (non-case sensitive):\n" +
            "1. The University of Melbourne\n" +
            "2.The Australian National University\n" +
            "3. Victoria University of Wellington\n" +
            "4.The University of Western Australia\n\n" +
            "NOTE: Please indicate the partner universities FULL NAME!\n" +
            "NOTE: Instead of \"Australian National University,\" " +
            "please indicate \"The Australian National University.\"";
    public static final String COMMANDS_LIST =
            """
                    Here are the available commands:
                    filter: filter <subject code> - Filter courses by subject code.
                    set: set <SCHOOL_NAME>     - Set a partner university for course mapping.
                    list schools: list schools          - List all available partner universities.
                    add: add <NUS_COURSE_CODE> /pu <NAME_OF_PU> /coursepu <PU_COURSE_CODE> \
                    - Add mapped courses between NUS and partner universities.
                    obtain: obtain <SCHOOL_NAME> /email  - Obtain partner university contact email.
                    obtain: obtain <SCHOOL_NAME> /number - Obtain partner university contact number.
                    delete: delete <TASK_NUMBER> - Delete a partner university.
                    list mapped: list mapped - List all course mapping saved.
                    compare: compare pu/<uni1> pu/<uni2> - Compare course mappings between 2 universities.
                    bye: bye - End the program.
                    To get more specific information of the commands, please use help <COMMAND>
                    """;
    // ListPersonalTrackerCommand
    public static final String NO_MODULES_MESSAGE = "No modules mapped yet.";
    public static final String MAPPED_MODULES_HEADER = "Mapped Modules:";

    // CompareMappedCommand messages
    public static final String INVALID_INPUT_FORMAT = "Please provide two universities in the format:  " +
            "compare pu/<University 1> pu/<University 2>";
    public static final String COMPARISON_RESULTS_HEADER = "Comparison of Course Mappings between ";
    public static final String COMMON_MAPPINGS_HEADER = "Common Mappings:";
    public static final String NO_COMMON_MAPPINGS = "No common mappings found.";
    public static final String UNIQUE_MAPPINGS_HEADER = "Unique to ";
    public static final String NO_UNIQUE_MAPPINGS = "No unique mappings for ";
    public static final String INVALID_COURSE_MAPPING = "Invalid course mapping!";

    //Obtain contacts messages
    public static final String EMAIL_TAG = "Email for ";
    public static final String NUMBER_TAG = "Phone number for ";
}
