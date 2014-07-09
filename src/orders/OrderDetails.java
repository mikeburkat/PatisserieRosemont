package orders;

public class OrderDetails {
	
	private double quantity;
	private String product;
	private String pid;
	
	public OrderDetails(double q, String p, String id) {
		quantity = q;
		product = p;
		pid = id;
	}

	public double getQuantity() {
		return quantity;
	}

	public String getProduct() {
		return product;
	}
	
	public String getPid() {
		return pid;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
}
