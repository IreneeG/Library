import java.util.List;
import java.util.Objects;

/**
 * Search command used search and print book titles containing a given word.
 */
public class SearchCmd extends LibraryCommand {

    /** A string to remember the word to search for in the library. */
    private String wordToSearchFor;

    /**
     * Create a search command.
     *
     * @param argumentInput argument input is expected to be a single word.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException if the given argumentInput is null.
     */
    public SearchCmd(String argumentInput) {
        super(CommandType.SEARCH, argumentInput);
    }

    /**
     * Execute the search command. This searches through and prints
     * all the titles of the books in the library containing
     * the word to search for.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if the given data is null
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given input argument must not be null.");

        boolean isSuccessfulSearch = false; // A flag to determine if any of the entries satisfied the search.
        List<BookEntry> books = data.getBookData();

        for (BookEntry book: books) {
            String lowerTitle = book.getTitle().toLowerCase();
            String lowerStrToSearch = wordToSearchFor.toLowerCase();
            if (lowerTitle.contains(lowerStrToSearch)) {
                System.out.println(book.getTitle());
                isSuccessfulSearch = true;
            }
        }

        if (!isSuccessfulSearch) {
            System.out.println("No hits found for search term: " + wordToSearchFor);
        }
    }

    /**
     * Remembers the command argument input in wordToSearchFor field.
     *
     * @param argumentInput argument input for search command
     * @return true iff the given argument consists of a single word.
     * @throws NullPointerException if the given argumentInput is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");

        wordToSearchFor = argumentInput.trim();

        return !wordToSearchFor.isBlank() && wordToSearchFor.split(" ").length == 1;
    }
}
