package orders;

public class OrderDetails {
	
	private double quantity;
	private String product;
	
	public OrderDetails(double q, String p) {
		this.quantity = q;
		this.product = p;
	}

	public double getQuantity() {
		return quantity;
	}

	public String getProduct() {
		return product;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	

}
