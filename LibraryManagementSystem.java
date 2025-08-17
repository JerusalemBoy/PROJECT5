import java.util.ArrayList;
import java.util.Scanner;

// Book class
class Book {
    private String title;
    private String author;
    private String isbn;
    private int quantity;

    public Book(String title, String author, String isbn, int quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.quantity = quantity;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public int getQuantity() { return quantity; }

    public boolean borrowBook() {
        if(quantity > 0) {
            quantity--;
            return true;
        }
        return false;
    }

    public void returnBook() { quantity++; }

    @Override
    public String toString() {
        return title + " by " + author + " | ISBN: " + isbn + " | Available: " + quantity;
    }
}

// Member class
class Member {
    private String name;
    private int memberId;
    private ArrayList<String> borrowedBooks;

    public Member(String name, int memberId) {
        this.name = name;
        this.memberId = memberId;
        borrowedBooks = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getMemberId() { return memberId; }

    public void borrowBook(String bookTitle) { borrowedBooks.add(bookTitle); }
    public void returnBook(String bookTitle) { borrowedBooks.remove(bookTitle); }

    public String getBorrowHistory() {
        if(borrowedBooks.isEmpty()) return "No books borrowed yet.";
        StringBuilder sb = new StringBuilder();
        for(String book : borrowedBooks) sb.append(book).append("\n");
        return sb.toString();
    }
}

// Main Library System
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Member> members = new ArrayList<>();

        // Sample data
        books.add(new Book("1984", "George Orwell", "ISBN001", 3));
        books.add(new Book("The Alchemist", "Paulo Coelho", "ISBN002", 2));
        books.add(new Book("The Hobbit", "J.R.R. Tolkien", "ISBN003", 4));

        members.add(new Member("Alice", 101));
        members.add(new Member("Bob", 102));

        System.out.println("=== Welcome to Library Management System ===");

        while(true){
            System.out.println("\n1. View Books  2. Borrow Book  3. Return Book  4. Member History  5. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch(choice) {
                case 1 -> { // View all books
                    System.out.println("Available Books:");
                    for(Book book : books) System.out.println(book);
                }
                case 2 -> { // Borrow book
                    System.out.print("Enter Member ID: ");
                    int id = sc.nextInt(); sc.nextLine();
                    Member currentMember = null;
                    for(Member m : members) if(m.getMemberId() == id) { currentMember = m; break; }

                    if(currentMember == null) { System.out.println("❌ Member not found!"); break; }

                    System.out.print("Enter Book ISBN to borrow: ");
                    String isbn = sc.nextLine();
                    Book bookToBorrow = null;
                    for(Book b : books) if(b.getIsbn().equals(isbn)) { bookToBorrow = b; break; }

                    if(bookToBorrow == null) System.out.println("❌ Book not found!");
                    else if(bookToBorrow.borrowBook()) {
                        currentMember.borrowBook(bookToBorrow.getTitle());
                        System.out.println("✅ Book borrowed successfully!");
                    } else System.out.println("❌ Book not available!");
                }
                case 3 -> { // Return book
                    System.out.print("Enter Member ID: ");
                    int id = sc.nextInt(); sc.nextLine();
                    Member currentMember = null;
                    for(Member m : members) if(m.getMemberId() == id) { currentMember = m; break; }

                    if(currentMember == null) { System.out.println("❌ Member not found!"); break; }

                    System.out.print("Enter Book Title to return: ");
                    String title = sc.nextLine();
                    Book bookToReturn = null;
                    for(Book b : books) if(b.getTitle().equalsIgnoreCase(title)) { bookToReturn = b; break; }

                    if(bookToReturn == null) System.out.println("❌ Book not found in library!");
                    else {
                        bookToReturn.returnBook();
                        currentMember.returnBook(bookToReturn.getTitle());
                        System.out.println("✅ Book returned successfully!");
                    }
                }
                case 4 -> { // Member borrowing history
                    System.out.print("Enter Member ID: ");
                    int id = sc.nextInt(); sc.nextLine();
                    Member currentMember = null;
                    for(Member m : members) if(m.getMemberId() == id) { currentMember = m; break; }

                    if(currentMember != null) System.out.println("Borrowed Books:\n" + currentMember.getBorrowHistory());
                    else System.out.println("❌ Member not found!");
                }
                case 5 -> { // Exit
                    System.out.println("Thank you for using the Library System!");
                    return;
                }
                default -> System.out.println("❌ Invalid choice!");

               
            }
             sc.close();
        }
    }
    
}
