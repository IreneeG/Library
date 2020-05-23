import java.util.Objects;
import java.util.Arrays;

/**
 * Immutable class encapsulating data for a single book entry.
 */
public final class BookEntry {

    /** Title of the book. */
    private final String title;
    /** Array of authors of the book. */
    private final String[] authors;
    /** Float value of the book's rating between 0 and 5. */
    private final float rating;
    /** A unique book's identifier. */
    private final String ISBN;
    /** Number of pages in the book. */
    private final int pages;

    /**
     * Create a BookEntry class instance.
     * Checks for input validity and initializes corresponding instance fields.
     *
     * @param title title of the book.
     * @param authors String array of all the book's authors.
     * @param rating book's rating expected to be between 0 and 5.
     * @param ISBN a unique book's numerical identifier.
     * @param pages number of pages in the book must not be negative.
     * @throws IllegalArgumentException if at least one of the given arguments is invalid
     * @throws NullPointerException if even one of the given arguments is null.
     */
    public BookEntry(String title, String[] authors, float rating, String ISBN, int pages) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException(("Rating value should be a number between 0 and 5"));
        }
        if (pages < 0) {
            throw new IllegalArgumentException(("Pages parameter must not be negative"));
        }

        this.title = Objects.requireNonNull(title);
        this.authors = Objects.requireNonNull(authors);
        this.ISBN = Objects.requireNonNull(ISBN);
        this.rating = rating;
        this.pages = pages;
    }

    /**
     * Returns the title of the book.
     *
     *  @return title of the book
     */
    public String getTitle() {
        return title;
    }
    /**
     * Returns authors of the book.
     *
     *  @return String array of all the authors
     */
    public String[] getAuthors() {
        return authors;
    }
    /**
     * Returns book's rating.
     *
     *  @return float point book's rating between 0 and 5
     */
    public float getRating() {
        return rating;
    }
    /**
     * Returns book's ISBN.
     *
     *  @return a unique book's code consisting of digits
     */
    public String getISBN() {
        return ISBN;
    }
    /**
     * Returns number of pages in the book.
     *
     *  @return integer number of pages (always more than 0)
     */
    public int getPages() {
        return pages;
    }

    /**
     * Returns a string representation of a BookEntry instance
     * suitable to display to a user. Uses all 5 instance fields
     * of the object: states them and their values.
     *
     * @return a string representation of a BookEntry instance
     */
    @Override
    public String toString() {
        final String padding = "\n";
        StringBuilder bld = new StringBuilder();

        String ratingStr = String.format("%.2f", rating);

        bld.append(title).append(padding);
        bld.append("by ").append(authorsToString()).append(padding);
        bld.append("Rating: ").append(ratingStr).append(padding);
        bld.append("ISBN: ").append(ISBN).append(padding);
        bld.append(pages).append(" pages").append(padding);

        return bld.toString();
    }

    /**
     * Formats authors for visual string representation.
     * Uses coma as a separator of each author.
     *
     * @return a string representation of authors array
     */
    private String authorsToString() {
        StringBuilder bld = new StringBuilder();
        final String authorsSeparator = ",";

        int numOfAuthors = authors.length;
        for (String author : authors) {
            bld.append(author);
            if (!author.equals(authors[numOfAuthors - 1])) {
                bld.append(authorsSeparator);
            }
        }

        return bld.toString();
    }

    /**
     * Checks if one BookEntry instance is equal to the other.
     *
     *  @return true if all 5 instance fields of both objects
     *  contain same values (whether they reference same object
     *  or not), false otherwise
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (that == null || getClass() != that.getClass()) {
            return false;
        }

        BookEntry anotherBookEntry = (BookEntry) that;

        return title.equals(anotherBookEntry.title) &&
                Arrays.equals(authors, anotherBookEntry.authors) &&
                Float.compare(anotherBookEntry.rating, rating) == 0 &&
                ISBN.equals(anotherBookEntry.ISBN) &&
                pages == anotherBookEntry.pages;
    }

    /**
     * Creates a hash code for BookEntry instance based on all
     * five instance fields: title, rating, ISBN, pages and authors.
     *
     *  @return integer numerical hash code
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(title, rating, ISBN, pages);
        result = 31 * result + Arrays.hashCode(authors);
        return result;
    }
}
