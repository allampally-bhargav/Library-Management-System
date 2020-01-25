package list;


public class SearchBookList {
	private String ISBN10;
	private String ISBN13;
    private String title;
    private String authors;
    private String TOTAL_COPIES;
    @Override
    public String toString() {
        return "SearchBook{" + "ISBN13=" + ISBN13 + "," + "ISBN10=" + ISBN10 + ", title=" + title + ", authors=" + authors + ", TOTAL_COPIES=" + TOTAL_COPIES + '}';
    }

    public SearchBookList(String ISBN13,String ISBN10, String title, String authors,  String TOTAL_COPIES) {
        this.ISBN13 = ISBN13;
        this.ISBN10 = ISBN10;
        this.title = title;
        this.authors = authors;
        this.TOTAL_COPIES = TOTAL_COPIES;
    }

    public String getISBN13() {
        return ISBN13;
    }

    public void setISBN13(String ISBN13) {
        this.ISBN13 = ISBN13;
    }
    public String getISBN10() {
        return ISBN10;
    }

    public void setISBN10(String ISBN10) {
        this.ISBN10 = ISBN10;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTOTAL_COPIES() {
        return TOTAL_COPIES;
    }

    public void setAvailability(String TOTAL_COPIES) {
        this.TOTAL_COPIES = TOTAL_COPIES;
    }
}


