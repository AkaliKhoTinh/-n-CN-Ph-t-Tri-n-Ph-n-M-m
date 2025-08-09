
package quanlynhahang;


public class categories {
    private String productID;
    private String name;
    private String type;
    private Double price;
    private String status;

    public String getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public categories(String productID, String name, String type, Double price, String status) {
        this.productID = productID;
        this.name = name;
        this.type = type;
        this.price = price;
        this.status = status;
    }
 
}
