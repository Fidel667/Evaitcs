abstract class Payment {
    public abstract void pay(double amount);
}

class CashPayment extends Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + String.format("%.2f", amount) + " in cash.");
    }
}

class CardPayment extends Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + String.format("%.2f", amount) + " by card.");
    }
}

// INHERITANCE
class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName()  { return name; }
    public String getEmail() { return email; }
    public void setName(String name)   { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}

//  POLYMORPHISM
class Customer extends User {
    private int id;

    public Customer(int id, String name, String email) {
        super(name, email);
        this.id = id;
    }

    public int getId() { return id; }

    public double calculateDiscount(double price) {
        return price * 0.05; // 5%
    }
}

class VIPCustomer extends Customer {
    public VIPCustomer(int id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public double calculateDiscount(double price) {
        return price * 0.10; // 10%
    }
}

class Employee extends User {
    private String role;

    public Employee(String name, String email, String role) {
        super(name, email);
        this.role = role;
    }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

//  ENCAPSULATION
class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId()       { return id; }
    public String getName()  { return name; }
    public double getPrice() { return price; }
    public void setName(String name)   { this.name = name; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "[" + id + "] " + name + " - $" + String.format("%.2f", price);
    }
}

// COMPOSITION
class Order {
    private Product product;
    private Customer customer;
    private Payment payment;

    public Order(Product product, Customer customer, Payment payment) {
        this.product = product;
        this.customer = customer;
        this.payment = payment;
    }

    public void processOrder() {
        double price    = product.getPrice();
        double discount = customer.calculateDiscount(price);
        double total    = price - discount;

        System.out.println("\n===== Order Summary =====");
        System.out.println("Customer : " + customer.getName());
        System.out.println("Product  : " + product.getName());
        System.out.printf ("Price    : $%.2f%n", price);
        System.out.printf ("Discount : $%.2f%n", discount);
        System.out.printf ("Total    : $%.2f%n", total);
        payment.pay(total);
        System.out.println("=========================");
    }
}

//  BIG O
class ProductSearch {

    // O(n) — linear search
    public static Product linearSearch(Product[] products, int targetId) {
        for (Product p : products) {
            if (p.getId() == targetId) return p;
        }
        return null;
    }

    // O(log n) — binary search (array must be sorted by id)
    public static Product binarySearch(Product[] products, int targetId) {
        int low = 0, high = products.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if      (products[mid].getId() == targetId) return products[mid];
            else if (products[mid].getId() <  targetId) low  = mid + 1;
            else                                         high = mid - 1;
        }
        return null;
    }
}

public class smartStore {
    public static void main(String[] args) {

        // 1. Create products (sorted by id for binary search)
        Product[] products = {
                new Product(1, "Laptop",  999.99),
                new Product(2, "Mouse",    29.99),
                new Product(3, "Keyboard", 59.99),
                new Product(4, "Monitor", 299.99),
        };

        System.out.println("=== Product Catalog ===");
        for (Product p : products) System.out.println(p);

        // 2. Search demos
        System.out.println("\n-- Linear Search (O(n)) for id=3 --");
        Product found = ProductSearch.linearSearch(products, 3);
        System.out.println(found != null ? "Found: " + found : "Not found");

        System.out.println("\n-- Binary Search (O(log n)) for id=3 --");
        found = ProductSearch.binarySearch(products, 3);
        System.out.println(found != null ? "Found: " + found : "Not found");

        // 3. Create customers
        Customer    regular = new Customer(101,    "Alice", "alice@email.com");
        VIPCustomer vip     = new VIPCustomer(102, "Bob",   "bob@email.com");
        Employee    emp     = new Employee("Carol", "carol@store.com", "Manager");

        System.out.println("\n=== Customers ===");
        System.out.println("Regular : " + regular.getName());
        System.out.println("VIP     : " + vip.getName());
        System.out.println("Employee: " + emp.getName() + " (" + emp.getRole() + ")");

        // 4 & 5. Create orders, apply discount, process payment
        Order order1 = new Order(products[0], regular, new CardPayment());
        Order order2 = new Order(products[3], vip,     new CashPayment());

        order1.processOrder();
        order2.processOrder();
    }
}