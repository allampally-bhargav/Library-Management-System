package list;

public class FineList {
	    private String CARD_ID;
	    private String FINE_AMOUNT;
	    private boolean PAID;
	    
	    public FineList(String CARD_ID,String FINE_AMOUNT, boolean PAID) {
	        this.CARD_ID= CARD_ID;
	        this.FINE_AMOUNT = FINE_AMOUNT;
	        this.PAID = PAID;
	    }


	    public String getCARD_ID() {
	        return CARD_ID;
	    }

	    public void setLOAN_ID(String CARD_ID) {
	        this.CARD_ID = CARD_ID;
	    }

	    public String getFINE_AMOUNT() {
	        return FINE_AMOUNT;
	    }

	    public void setFINE_AMOUNT(String FINE_AMOUNT) {
	        this.FINE_AMOUNT = FINE_AMOUNT;
	    }

	    public boolean isPAID() {
	        return PAID;
	    }

	    public void setIsPaid(boolean PAID) {
	        this.PAID = PAID;
	    }
}
