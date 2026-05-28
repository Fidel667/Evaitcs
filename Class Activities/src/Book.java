public class Book {

    private String title;
    private String author;
    private float price;


    public Book(String title, String author, float price) {
        this.title  = title;
        this.author = author;
        this.price  = price;
    }

    public String getTitle()  {
        return title;  }
    public String getAuthor() {
        return author; }
    public double getPrice()  {
        return price;  }

    public void display() {
        System.out.println("Title  : " + title);
        System.out.println("Author : " + author);
        System.out.println("Price  : $" + price);
    }

    public static void main(String[] args) {
        Book book = new Book("Evaitcs", "Ali", 50);
        book.display();
    }
}
