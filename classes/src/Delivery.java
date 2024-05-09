public class Delivery {
    private String address;
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Delivery(String address, Order order) {
        this.address = address;
        this.order = order;
    }
}
