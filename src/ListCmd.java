import java.util.List;
import java.util.Objects;

/**
 * List command used to print either a short (only titles) or
 * a long extensive list of the books in the library.
 */
public class ListCmd extends LibraryCommand {

    /** An enumerator of possible arguments to list books by.
     * Extending it will have to be followed by also extending the switch
     * case in execute method of the class.
     */
    private enum ListArgumentType { LONG, SHORT }
    /** An enum instance representing the argument input for list command
     * (null if argument input is invalid)
     */
    private ListArgumentType commandArgument;

    /**
     * Create a list command.
     *
     * @param argumentInput argument input is expected to be either 'long', or 'short', or to be completely blank.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException if the given argumentInput is null.
     */
    public ListCmd(String argumentInput) {
        super(CommandType.LIST, argumentInput);
    }

    /**
     * Execute the list command. This prints the amount of books in the library and
     * a long or a short version of the list of the books.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if the given data is null
     * @throws UnsupportedOperationException if the enumerator of possible list command
     * arguments has been extended but the switch case for dealing with them has not
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given data must not be null.");

        List<BookEntry> books = data.getBookData();
        if (books.size() > 0) {
            System.out.println(books.size() + " books in library:");
        }
        else {
            System.out.println("The library has no book entries.");
        }

        switch(commandArgument) {
            case SHORT:
                for (BookEntry entry : books) {
                    System.out.println(entry.getTitle());
                }
                break;
            case LONG:
                for (BookEntry entry : books) {
                    System.out.println(entry.toString());
                }
                break;
            default:
                throw new UnsupportedOperationException("Command argument " + commandArgument + " is not yet implemented");
        }
    }

    /**
     * Remembers the command argument input in commandArgument field for later use.
     *
     * @param argumentInput argument input for list command
     * @return true if the given argument is either 'long' or 'short', or blank.
     * @throws NullPointerException if the given argumentInput is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");

        commandArgument = null;

        if (argumentInput.isBlank()) {
            commandArgument = ListArgumentType.SHORT;
        }
        else {
            for (ListArgumentType type : ListArgumentType.values()) {
                if (type.toString().toLowerCase().equals(argumentInput.trim())) {
                    commandArgument = type;
                    break;
                }
            }
        }

        return commandArgument != null;
    }
}
