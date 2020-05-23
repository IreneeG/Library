import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Add command used to add new books to the library from a given book data file.
 */
public class AddCmd extends LibraryCommand {

    /** A path to the file with book data remembered from user's input. */
    private Path bookDataPath;

    /**
     * Create an add command.
     *
     * @param argumentInput argument input is expected to be a path to a book file.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException if the given argumentInput is null.
     */
    public AddCmd(String argumentInput) {
        super(CommandType.ADD, argumentInput);
    }

    /**
     * Execute the add command. This loads the book data into the library
     * by calling a loadData method of the given LibraryData instance.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if the given data is null
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given data must not be null.");

        data.loadData(bookDataPath);
    }

    /**
     * Remembers the command argument input in bookDataPath field for later use.
     * Does not check if the given input is a valid path or not.
     *
     * @param argumentInput argument input for this command
     * @return true if the given argument ends with '.csv', false otherwise.
     * @throws NullPointerException if the given argumentInput is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");

        bookDataPath = Paths.get(argumentInput.trim());

        return argumentInput.trim().endsWith(".csv");
    }
}
