import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.Set;

/**
 * Group command used to group books either alphabetically by title or by author
 * and lexicographically print the grouped books.
 */
public class GroupCmd extends LibraryCommand {

    /** An enumerator of possible arguments to group books by.
     * Extending it will have to be followed by also extending the switch
     * case in execute method of the class, as well as writing grouping
     * functions (such as groupByTitle) for newly added arguments.
     */
    private enum GroupArgumentType { TITLE, AUTHOR }
    /** An enum instance representing the argument input for group command
     * (null if argument input is invalid)
     */
    private GroupArgumentType commandArgument;

    /**
     * Create a group command.
     *
     * @param argumentInput argument input is expected to be either "TITLE" or "AUTHOR".
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException if the given argumentInput is null.
     */
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);
    }

    /**
     * Execute the group command. This prints groups of book titles
     * grouped lexicographically either by title or by author.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if the given data is null
     * @throws UnsupportedOperationException if the enumerator of possible group command
     * arguments has been extended but the switch case for dealing with them has not
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given data must not be null.");

        List<BookEntry> books = data.getBookData();
        if (books.size() == 0) {
            System.out.println("The library has no book entries.");
        }
        else {
            System.out.println("Grouped data by " + commandArgument.toString());
            TreeMap<String, ArrayList<String>> groupedBooks;

            switch (commandArgument) {
                case TITLE:
                    groupedBooks = groupByTitle(books);
                    break;
                case AUTHOR:
                    groupedBooks = groupByAuthor(books);
                    break;
                default:
                    throw new UnsupportedOperationException("Command argument " + commandArgument + " is not yet implemented");
            }

            printGroup(groupedBooks);
        }
    }

    /**
     * Translates given command argument to corresponding GroupArgumentType
     * if it is valid and remembers it in commandArgument field for later use
     * (otherwise leaves argument field null).
     *
     * @param argumentInput argument input for this command
     * @return true if the given argument is part of the valid arguments represented
     * in GroupArgumentType enumerator, false otherwise.
     * @throws NullPointerException if the given argumentInput is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");

        commandArgument = null;

        for (GroupArgumentType type : GroupArgumentType.values()) {
            if (type.toString().equals(argumentInput.trim())) {
                commandArgument = type;
                break;
            }
        }

        return commandArgument != null;
    }

    /**
     * Groups the book data by title.
     *
     * @param books list of all the book entries in the library.
     * @return a TreeMap where keys are either capital letters (+ a key "[0-9]"), and values are book titles
     * starting with the letter represented by the corresponding key (or starting with a number).
     */
    protected TreeMap<String, ArrayList<String>> groupByTitle(List<BookEntry> books) {
        TreeMap<String, ArrayList<String>> groupMap = new TreeMap<>();

        final String numberGroupHeader = "[0-9]"; // The header under which all book titles starting with a number go

        for (BookEntry book : books) {
            String title = book.getTitle();
            String key;

            if (Character.isDigit(title.charAt(0))) {
                key = numberGroupHeader;
            }
            else {
                key = Character.toUpperCase(title.charAt(0)) + "";
            }

            if (!groupMap.containsKey(key)) {
                ArrayList<String> value = new ArrayList<>() {{add(title);}};
                groupMap.put(key, value);
            }
            else {
                ArrayList<String> value = groupMap.get(key);
                value.add(title);
                groupMap.put(key, value);
            }
        }

        return groupMap;
    }

    /**
     * Groups the book data by author.
     *
     * @param books list of all the book entries in the library.
     * @return a TreeMap where keys are full names of authors, and values are book titles
     * of the books written by the corresponding author.
     */
    protected TreeMap<String, ArrayList<String>> groupByAuthor(List<BookEntry> books) {
        TreeMap<String, ArrayList<String>> groupMap = new TreeMap<>();

        for (BookEntry book : books) {
            String title = book.getTitle();

            for (String author : book.getAuthors()) {
                if (!groupMap.containsKey(author)) {
                    ArrayList<String> value = new ArrayList<>() {{
                        add(title);
                    }};
                    groupMap.put(author, value);
                } else {
                    ArrayList<String> value = groupMap.get(author);
                    value.add(title);
                    groupMap.put(author, value);
                }
            }
        }

        return groupMap;
    }

    /**
     * Helper function to print the grouped books.
     *
     * @param group a TreeMap of key-value pairs
     */
    protected void printGroup(TreeMap<String, ArrayList<String>> group) {
        Set<String> keys = group.keySet();
        final String padding = "   ";

        for (String key : keys) {
            System.out.println("## " + key);

            for (String title : group.get(key)) {
                System.out.println(padding + title);
            }
        }
    }
}
