import java.util.Scanner;
import java.io.*;


public class Main {
static final String RESET = "\u001B[0m";
static final String RED = "\u001B[31m";
static final String GREEN = "\u001B[32m";
static final String YELLOW = "\u001B[33m";
static final String BLUE = "\u001B[34m";
static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {

        

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = loginService.login(username, password);

        if (user != null) {

            System.out.println("Login Success");
            System.out.println("Role: " + user.getRole());

            if (user.getRole().equals("ADMIN")) {
                adminMenu();
            } else {
                studentMenu();
            }

        } else {
            System.out.println("Login Failed");
        }
    }

    static void adminMenu() {
    Scanner sc = new Scanner(System.in);

    while (true) {

        System.out.println(CYAN + "\n===== ADMIN MENU =====" + RESET);
        System.out.println(YELLOW + "1. Add Book" + RESET);
        System.out.println(YELLOW + "2. Remove Book" + RESET);
        System.out.println(YELLOW + "3. Issue Book" + RESET);
        System.out.println(YELLOW + "4. View Books" + RESET);
        System.out.println(YELLOW + "5. Return Book" + RESET);
        System.out.println(YELLOW + "6. Show Available Books" + RESET);
        System.out.println(YELLOW + "7. Dashboard" + RESET);
        System.out.println(YELLOW + "8. Fine Report" + RESET);
        System.out.println(YELLOW + "9. Exit" + RESET);

        System.out.print(BLUE + "Enter choice: " + RESET);
        int choice = sc.nextInt();

        switch (choice) {

            case 1: addBook(); break;
            case 2: removeBook(); break;
            case 3: issueBook(); break;
            case 4: viewBooks(); break;
            case 5: returnBook(); break;
            case 6: showAvailableBooks(); break;
            case 7: adminDashboard(); break;
            case 8: fineReport(); break;
            case 9: return;

            default:
                System.out.println(RED + "Invalid choice" + RESET);
        }
    }
}

    static void studentMenu() {
    Scanner sc = new Scanner(System.in);

    while (true) {
        System.out.println("\n===== STUDENT MENU =====");
        System.out.println("1. Search Book");
        System.out.println("2. View Issued Books");
        System.out.println("3. Exit");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                searchBook();
                break;
            case 2:
                System.out.println("View Issued Books");
                break;
            case 3:
                return;
        }
    }
}
    // ADD BOOK
    static void addBook() {
        Scanner sc = new Scanner(System.in);

        try {
            int bookId = BookIdGenerator.getNextId();

            System.out.println("Generated Book ID: " + bookId);

            System.out.print("Enter Book Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Author: ");
            String author = sc.nextLine();

            FileWriter fw = new FileWriter("books.txt", true);
            fw.write(bookId + "," + name + "," + author + "\n");
            fw.close();

            System.out.println(GREEN + "Book Added Successfully" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Error saving book" + RESET);
        }
    }

    // VIEW BOOKS
    static void viewBooks() {
        System.out.println("\n===== BOOK LIST =====");
    try {
        BufferedReader br = new BufferedReader(new FileReader("books.txt"));
        String line;

        System.out.println("\n===== BOOK LIST =====");

        while ((line = br.readLine()) != null) {

            String[] parts = line.split(",", 3);

            System.out.println("ID: " + parts[0]);
            System.out.println("Name: " + parts[1]);
            System.out.println("Author: " + parts[2]);
            System.out.println("------------------");
        }

        br.close();

    } catch (Exception e) {
        System.out.println("Error reading books");
    }
}
    // REMOVE BOOK
    static void removeBook() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Book ID to remove: ");
        int id = sc.nextInt();

        try {
            File inputFile = new File("books.txt");
            File tempFile = new File("temp.txt");

            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (Integer.parseInt(parts[0]) != id) {
                    bw.write(line);
                    bw.newLine();
                }
            }

            br.close();
            bw.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

            System.out.println("Book removed successfully");

        } catch (Exception e) {
            System.out.println("Error removing book");
        }
    }
    static void issueBook() {
    Scanner sc = new Scanner(System.in);

    try {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        sc.nextLine();

        // check already issued
        File file = new File("issued.txt");
        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {

    if (line.startsWith("bookId")) continue; // skip header

    String[] parts = line.split(",", 4);

    if (Integer.parseInt(parts[0]) == bookId) {
        System.out.println("Book already issued!");
        br.close();
        return;
    }
}}

        System.out.print("Enter Student Name: ");
        String student = sc.nextLine();

        java.time.LocalDate issueDate = java.time.LocalDate.now();
        java.time.LocalDate dueDate = issueDate.plusDays(7);

        FileWriter fw = new FileWriter("issued.txt", true);
        fw.write(bookId + "," + student + "," + issueDate + "," + dueDate + "\n");
        fw.close();

        System.out.println("Book Issued Successfully");
        System.out.println("Due Date: " + dueDate);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

static void returnBook() {
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter Book ID to return: ");
    int id = sc.nextInt();

    try {
        File inputFile = new File("issued.txt");
        File tempFile = new File("temp.txt");

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

        String line;
        boolean found = false;

        while ((line = br.readLine()) != null) {

            String[] parts = line.split(",", 4);

            int bookId = Integer.parseInt(parts[0]);

            if (bookId == id) {
                found = true;

                java.time.LocalDate dueDate =
                        java.time.LocalDate.parse(parts[3]);

                java.time.LocalDate today =
                        java.time.LocalDate.now();

                long daysLate =
                        java.time.temporal.ChronoUnit.DAYS
                        .between(dueDate, today);

                if (daysLate > 0) {
                    long fine = daysLate * 5;
                    System.out.println("Late by " + daysLate + " days");
                    System.out.println("Fine: Rs " + fine);
                } else {
                    System.out.println("No fine");
                }if (line.startsWith("bookId")) continue;

            } else {
                bw.write(line);
                bw.newLine();
            }
        }

        br.close();
        bw.close();

        inputFile.delete();
        tempFile.renameTo(inputFile);

        if (found)
            System.out.println("Book Returned Successfully");
        else
            System.out.println("Book not issued");

    } catch (Exception e) {
        e.printStackTrace();
    }
}static void searchBook() {
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter book name to search: ");
    String keyword = sc.nextLine().toLowerCase();

    try {
        BufferedReader br = new BufferedReader(new FileReader("books.txt"));
        String line;
        boolean found = false;

        while ((line = br.readLine()) != null) {

            String[] parts = line.split(",", 3);

          String data = (parts[1] + parts[2]).toLowerCase();

            if (data.contains(keyword)) {

                System.out.println("\nBook Found:");
                System.out.println("ID: " + parts[0]);
                System.out.println("Name: " + parts[1]);
                System.out.println("Author: " + parts[2]);
                System.out.println("----------------");

                found = true;
            }
        }

        br.close();

        if (!found)
            System.out.println("Book not found");

    } catch (Exception e) {
        System.out.println("Error searching book");
    }
    }static void showAvailableBooks() {
    try {
        java.util.Set<String> issuedIds = new java.util.HashSet<>();

        // read issued books
        File issuedFile = new File("issued.txt");
        if (issuedFile.exists()) {
            BufferedReader br1 = new BufferedReader(new FileReader(issuedFile));
            String line;

            while ((line = br1.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",", 4);
                issuedIds.add(parts[0].trim());
            }

            br1.close();
        }

        // read all books
        BufferedReader br2 = new BufferedReader(new FileReader("books.txt"));
        String line;

        System.out.println("\n===== AVAILABLE BOOKS =====");

        boolean found = false;

        while ((line = br2.readLine()) != null) {

            String[] parts = line.split(",", 3);

            String id = parts[0].trim();

            if (!issuedIds.contains(id)) {

                System.out.println("ID: " + parts[0]);
                System.out.println("Name: " + parts[1]);
                System.out.println("Author: " + parts[2]);
                System.out.println("----------------");

                found = true;
            }
        }

        br2.close();

        if (!found)
            System.out.println("No available books");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
static void adminDashboard() {
    try {
        int totalBooks = 0;
        int issuedBooks = 0;
        long totalFine = 0;

        // count total books
        BufferedReader br1 = new BufferedReader(new FileReader("books.txt"));
        while (br1.readLine() != null)
            totalBooks++;
        br1.close();

        // read issued books
        File issuedFile = new File("issued.txt");
        if (issuedFile.exists()) {

            BufferedReader br2 = new BufferedReader(new FileReader(issuedFile));
            String line;

            while ((line = br2.readLine()) != null) {

                String[] parts = line.split(",", 4);

                issuedBooks++;

                java.time.LocalDate dueDate =
                        java.time.LocalDate.parse(parts[3]);

                java.time.LocalDate today =
                        java.time.LocalDate.now();

                long daysLate =
                        java.time.temporal.ChronoUnit.DAYS
                        .between(dueDate, today);

                if (daysLate > 0)
                    totalFine += daysLate * 5;
            }

            br2.close();
        }

        int availableBooks = totalBooks - issuedBooks;

        System.out.println("\n===== ADMIN DASHBOARD =====");
        System.out.println("Total Books      : " + totalBooks);
        System.out.println("Issued Books     : " + issuedBooks);
        System.out.println("Available Books  : " + availableBooks);
        System.out.println("Total Fine (Rs)  : " + totalFine);
        System.out.println("============================");

    } catch (Exception e) {
        e.printStackTrace();
    }
}static void fineReport() {
    try {
        File file = new File("issued.txt");

        if (!file.exists()) {
            System.out.println("No issued books");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        System.out.println("\n===== FINE REPORT =====");

        boolean found = false;

        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty()) continue;

            String[] parts = line.split(",", 4);

            int bookId = Integer.parseInt(parts[0]);
            String student = parts[1];

            java.time.LocalDate dueDate =
                    java.time.LocalDate.parse(parts[3]);

            java.time.LocalDate today =
                    java.time.LocalDate.now();

            long daysLate =
                    java.time.temporal.ChronoUnit.DAYS
                    .between(dueDate, today);

            if (daysLate > 0) {

                long fine = daysLate * 5;

                System.out.println("Book ID: " + bookId);
                System.out.println("Student: " + student);
                System.out.println("Due Date: " + dueDate);
                System.out.println("Late Days: " + daysLate);
                System.out.println("Fine: Rs " + fine);
                System.out.println("----------------------");

                found = true;
            }
        }

        br.close();

        if (!found)
            System.out.println("No pending fines");

    } catch (Exception e) {
        e.printStackTrace();
    }
}}