/**
 * 
 */
package smc.pay;

/**
 * @author siva
 *
 */
public class PayMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Pay SMC initiated");
		System.out.println("******************");
		Pay pay = new Pay("1234", 000.0);
		pay.init();

	}

}
