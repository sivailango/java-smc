/**
 * 
 */
package smc.pay;

/**
 * @author siva
 *
 */
public class Pay {
	
	public PayContext ctxt;
	
	private String cardId;
	private Double amount;

	private final Double BALANCE = 1000.0;
	private final String CARD_ID = "1234";
	
	private boolean isValidCard;
	private boolean hasBalance = false;
	
	
	public Pay(String cardId, Double amount) {
		ctxt = new PayContext(this);
		this.cardId = cardId;
		this.amount = amount;
	}
	
	public void init() {
		ctxt.ReadyForPay();
		ctxt.IsValidCard();
		
		if(isValidCard) {
			ctxt.CheckCardBalance();
			if(hasBalance) {
				ctxt.SendToMerchant();
			} else {
				ctxt.LowBalance();
			}
		} else {
			ctxt.CardNotValid();
		}
	}
	
	public void initiated() {
		System.out.println("SMC initiated");
	}
	
	public void checkingBalance() {
		System.out.println("Checking Balance");
	}
	
	public void updateBalance() {
		System.out.println("Balance updated");
	}
	
	public void sendToMerchant() {
		System.out.println("Amount sent to Merchant");
	}
	
	public void cardAuthSuccess() {
		System.out.println("Card auth passed");
	}
	
	public void cardAuthFailed() {
		System.out.println("Card auth failed");
	}
	
	public void invalidCard() {
		System.out.println("Card is invalid");
	}

	public void noBalance() {
		System.out.println("Insufficient Balance");
	}
	
	public void stopped() {
		System.out.println("Transaction Stopped");
	}
	
	public boolean isValidCard() {
		System.out.println("Checking whether the card is valid......");
		isValidCard = CARD_ID.equals(cardId) ? true : false;
		return isValidCard;
	}
	
	public boolean checkBalance() {	
		System.out.println("Checking the balance of the card........");
		hasBalance = BALANCE > amount ? true : false;
		return hasBalance;
	}

}
