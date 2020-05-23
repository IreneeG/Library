import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Remove command used to remove a book with specific title or all books for a given author.
 */
public class RemoveCmd extends LibraryCommand {

    /** Delimiter between command argument keyword (AUTHOR or TITLE) and corresponding string. */
    private static final String ARGUMENT_INPUT_DELIMITER = " ";
    /** An enumerator of possible arguments to remove books by.
     * Extending it will have to be followed by also extending the switch
     * case in execute method of the class.
     */
    private enum RemoveArgumentType { TITLE, AUTHOR }
    /** First part of the command argument initialised in the parseArgument method
     * if the argument is valid, represented as enumerator instance (null if
     * the this part of argument is invalid)
     * */
    private RemoveArgumentType bookRemovalArgument;
    /** Second part of the command argument initialised in the parseArgument method
     * if the argument is valid. */
    private String bookToRemove;

    /**
     * Create a remove command.
     *
     * @param argumentInput argument input is expected to consist of two parts separated by a space.
     *                      1st part - either 'AUTHOR', or 'TITLE'
     *                      2nd part - a full authors name or books title.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException if the given argumentInput is null.
     */
    public RemoveCmd(String argumentInput) { super(CommandType.REMOVE, argumentInput); }

    /**
     * Execute the remove command. This removes either the book
     * with a given title or all the books for a given author and
     * prints if the operation was successful.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if the given data is null
     * @throws UnsupportedOperationException if the enumerator of possible remove command
     * arguments has been extended but the switch case for dealing with them has not
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given input argument must not be null.");

        List<BookEntry> books = data.getBookData();

        switch(bookRemovalArgument) {
            case TITLE:
                removeByTitle(books);
                break;
            case AUTHOR:
                removeByAuthor(books);
                break;
            default:
                throw new UnsupportedOperationException("Removal by argument " + bookRemovalArgument + " is not yet implemented");
        }
    }

    /**
     * Removes all the books form the library for a given author.
     *
     * @param books a list of books in the library
     */
    private void removeByAuthor(List<BookEntry> books) {
        int booksRemoved = 0;
        for (BookEntry book : books) {
            if (Arrays.asList(book.getAuthors()).contains(bookToRemove)) {
                books.remove(book);
                booksRemoved++;
            }
        }
        System.out.println(booksRemoved + " books removed for author: " + bookToRemove);
    }

    /**
     * Removes a book form the library with a given title.
     *
     * @param books a list of books in the library
     */
    private void removeByTitle(List<BookEntry> books) {
        boolean ifAnyRemoved = false; // A flag to check if any books have been removed.;

        for (BookEntry book : books) {
            if (book.getTitle().equals(bookToRemove)) {
                books.remove(book);
                System.out.println(bookToRemove + ": removed successfully.");
                ifAnyRemoved = true;
                break;
            }
        }
        if (!ifAnyRemoved) {
            System.out.println(bookToRemove + ": not found.");
        }
    }

    /**
     * Remembers the command argument input in bookRemovalArgument
     * (expected to be either 'TITLE' or 'AUTHOR') and bookToRemove fields.
     *
     * @param argumentInput argument input for remove command
     * @return true if the given argument consists of 'TITLE' or 'AUTHOR' followed by a string.
     * @throws NullPointerException if the given argumentInput is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");

        String cleanArgument = argumentInput.trim(); // An argument without leading and trailing spaces
        int firstSpaceIdx = cleanArgument.indexOf(ARGUMENT_INPUT_DELIMITER);
        bookRemovalArgument = null;

        // If argument input consists of at least two words
        if (firstSpaceIdx > 0) {
            String firstWord = cleanArgument.substring(0, firstSpaceIdx);

            for (RemoveArgumentType type : RemoveArgumentType.values()) {
                if (type.toString().equals(firstWord)) {
                    bookRemovalArgument = type;
                    bookToRemove = cleanArgument.substring(firstSpaceIdx + 1);
                    break;
                }
            }
        }

        return bookRemovalArgument != null;
    }
}
