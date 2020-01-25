package list;

public class LoanList {

    public LoanList(String CARD_ID,String LOAN_ID, String ISBN13,String DUE_DATE) {
        this.CARD_ID = CARD_ID;
        this.LOAN_ID = LOAN_ID;
        this.ISBN13 = ISBN13;
        this.DUE_DATE = DUE_DATE;
    }

    private String CARD_ID;
    private String LOAN_ID;
    private String ISBN13;
    private String DUE_DATE;


    

    public String getISBN13() {
        return ISBN13 ;
    }

    public void setISBN13(String ISBN13) {
        this.ISBN13 = ISBN13;
    }
    public String getLOAN_ID() {
        return LOAN_ID;
    }

    public void setLOAN_ID(String LOAN_ID) {
        this.LOAN_ID = LOAN_ID;
    }
    public String getCARD_ID() {
        return CARD_ID;
    }

    public void setCARD_ID(String CARD_ID) {
        this.CARD_ID = CARD_ID;
    }

    public String getDUE_DATE() {
        return DUE_DATE;
    }

    public void setDUE_DATE(String DUE_DATE) {
        this.DUE_DATE = DUE_DATE;
    }
}

