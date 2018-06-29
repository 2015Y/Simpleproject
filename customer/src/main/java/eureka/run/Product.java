package eureka.run;

import java.io.Serializable;

public class Product implements Serializable {
	/**
	 * serialVersionUID 描述:
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String productName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + "]";
	}

}
