import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;

public class Library {
    private Map<String, User> users;
    private Map<String, LibraryItem> libraryItems;
    private Map<String, LibraryItem> borrowedItems;
    private double overdueFinePerDay;
    private List<BookRequest> bookRequests;
    

    public Library() {
        users = new HashMap<>();
        libraryItems = new HashMap<>();
        borrowedItems = new HashMap<>();
        overdueFinePerDay = 0.10;
        addReferenceBooks();
        bookRequests = new ArrayList<>();
    }

    private void addReferenceBooks() {
        Book book1 = new Book("Encyclopedia of Science", "REF001", 0, 0.0, true);
        Book book2 = new Book("Historical Atlas", "REF002", 0, 0.0, true);
        Book book3 = new Book("Thesaurus", "REF003", 0, 0.0, true);
        Book book4 = new Book("magazine1", "MAZ001", 0, 0.0, true);
        Book book5 = new Book("magazine2", "MAZ002", 0, 0.0, true);
        Book book6 = new Book("magazine3", "MAZ003", 0, 0.0, true);
        Book book7 = new Book("Book1", "1", 0, 0.0, true);
        Book book8 = new Book("Book2", "2", 0, 0.0, true);
        Book book9 = new Book("Book3", "3", 0, 0.0, true);
        Book book10 = new Book("Book1", "4", 0, 0.0, true);
        Book book11 = new Book("Book2", "5", 0, 0.0, true);
        Book book12 = new Book("Book3", "6", 0, 0.0, true);
        Book book13 = new Book("Book1", "7", 0, 0.0, true);
        Book book14 = new Book("Book2", "8", 0, 0.0, true);
        Book book15 = new Book("Book3", "9", 0, 0.0, true);
        
        libraryItems.put(book1.getItemCode(), book1);
        libraryItems.put(book2.getItemCode(), book2);
        libraryItems.put(book3.getItemCode(), book3);
        libraryItems.put(book4.getItemCode(), book4);
        libraryItems.put(book5.getItemCode(), book5);
        libraryItems.put(book6.getItemCode(), book6);
        libraryItems.put(book7.getItemCode(), book7);
        libraryItems.put(book8.getItemCode(), book8);
        libraryItems.put(book9.getItemCode(), book9);
        libraryItems.put(book10.getItemCode(), book10);
        libraryItems.put(book11.getItemCode(), book11);
        libraryItems.put(book12.getItemCode(), book12);
        libraryItems.put(book13.getItemCode(), book13);
        libraryItems.put(book14.getItemCode(), book14);
        libraryItems.put(book15.getItemCode(), book15);
    }
    
    public User createUser(String name, String address, String phoneNumber, int age) {
        String libraryCardNumber = "LIBCARD" + (users.size() + 1);
        User newUser = new User(name, address, phoneNumber, libraryCardNumber, age);
        users.put(libraryCardNumber, newUser);
        return newUser;
    }

    public User createChildUser(String name, String address, String phoneNumber, int age) {
        String libraryCardNumber = "CHILD" + (users.size() + 1);
        User newChild = new User(name, address, phoneNumber, libraryCardNumber, age);
        users.put(libraryCardNumber, newChild);
        return newChild;
    }

    public List<BookRequest> getBookRequests() {
        return bookRequests;
    }

    public void raiseBookRequest(User user, LibraryItem item) {
        bookRequests.add(new BookRequest(user, item));
        System.out.println("Request for " + item.getItemName() + " has been raised by " + user.getName());
    }

    public User getUserById(String libraryCardNumber) {
        return users.get(libraryCardNumber);
    }

    public void removeUser(String libraryCardNumber) {
        users.remove(libraryCardNumber);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void addLibraryItem(LibraryItem item) {
        if (isItemCodeUnique(item.getItemCode()) && isItemNameUnique(item.getItemName())) {
            libraryItems.put(item.getItemCode(), item);
            System.out.println("Item added successfully./n/n");
        } else {
            System.out.println("Item code or item name already exists. Item not added./n/n");
        }
    }

    private boolean isItemCodeUnique(String itemCode) {
        return !libraryItems.containsKey(itemCode);
    }

    private boolean isItemNameUnique(String itemName) {
        for (LibraryItem item : libraryItems.values()) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return false;
            }
        }
        return true;
    }

    public void removeLibraryItem(String itemCode) {
        libraryItems.remove(itemCode);
    }

    public List<LibraryItem> getAllLibraryItems() {
        return new ArrayList<>(libraryItems.values());
    }

    public LibraryItem getLibraryItemByCode(String itemCode) {
        return libraryItems.get(itemCode);
    }

    public boolean borrowItem(User user, String itemCode) {
        Scanner scanner = new Scanner(System.in);
        LibraryItem item = libraryItems.get(itemCode);
        if (item != null && item.isAvailable()) {
            if (item instanceof Book) {
                Book book = (Book) item;
                if (book.isBorrowed()) {
                    System.out.println(book.getItemName() + " is already borrowed by another user.");
                    System.out.print("Do you want to request the item? (yes/no): ");
                    String raiseRequest = scanner.nextLine();
                    if (raiseRequest.equalsIgnoreCase("yes")) {
                        bookRequests.add(new BookRequest(user, book));
                        System.out.println("Your request for " + book.getItemName() + " has been added.");
                    }
                } else {
                    if (book.isBestSeller()) {
                        book.setDueDate(LocalDate.now().plusWeeks(2));
                    } else {
                        book.setDueDate(LocalDate.now().plusWeeks(3));
                    }
                    book.setBorrowedBy(user);
                    book.setRenewalsLeft(1); // Assuming 1 renewal allowed for books
                    System.out.println(book.getItemName() + " is borrowed successfully.");
                }
            } else if (item instanceof AudioVideoMaterial) {
                AudioVideoMaterial avMaterial = (AudioVideoMaterial) item;
                if (avMaterial.isBorrowed()) {
                    System.out.println(avMaterial.getItemName() + " is already borrowed by another user.");
                    System.out.print("Do you want to request the item? (yes/no): ");
                    String raiseRequest = scanner.nextLine();
                    if (raiseRequest.equalsIgnoreCase("yes")) {
                        bookRequests.add(new BookRequest(user, avMaterial));
                        System.out.println("Your request for " + avMaterial.getItemName() + " has been added.");
                    }
                } else {
                    avMaterial.setDueDate(LocalDate.now().plusWeeks(2)); // Two weeks for audio/video materials
                    avMaterial.setBorrowedBy(user);
                    avMaterial.setRenewalsLeft(0); // Assuming 0 renewal allowed for audio/video materials
                    System.out.println(avMaterial.getItemName() + " is borrowed successfully.");
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean returnItem(User user, String itemCode) {
        LibraryItem item = borrowedItems.get(itemCode);
        if (item != null && item.getBorrowedBy() == user) {
            item.setAvailable(true);
            item.setDueDate(null);
            item.setBorrowedBy(null);
            borrowedItems.remove(itemCode);
            return true;
        }
        return false;
    }

    private LocalDate calculateDueDate(LibraryItem item) {
        LocalDate currentDate = LocalDate.now();
        int loanDuration;
        if (item instanceof Book) {
            Book book = (Book) item;
            // Check if it's a current bestseller
            if (book.isBestSeller()) {
                loanDuration = 14; // Two weeks (14 days) for bestsellers
            } else {
                loanDuration = 21; // Three weeks (21 days) for regular books
            }
        } else if (item instanceof AudioVideoMaterial) {
            loanDuration = 14; // Two weeks (14 days) for audio/video materials
        } else {
            loanDuration = 0; // Unknown item type, set to 0 days (due immediately)
        }
        return currentDate.plusDays(loanDuration);
    }

    public void displayAvailableItemsForBorrowing() {
        System.out.println("Available Items for Borrowing:");
        for (LibraryItem item : libraryItems.values()) {
            if (item.isAvailable()) {
                String itemType = "";
                if (item instanceof Book) {
                    itemType = "Book";
                } else if (item instanceof AudioVideoMaterial) {
                    itemType = "Audio/Video Material";
                }
                System.out.println(item.getItemCode() + ": " + item.getItemName() + " (" + itemType + ")");
            }
        }
    }

    

    public void showStudentMenu(User student) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nStudent Menu:");
            System.out.println("1- Borrow Items");
            System.out.println("2- Renew Items");
            System.out.println("3- Return Items");
            System.out.println("4. Checkedout items");
            System.out.println("0- Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    borrowItems(student);
                    break;
                case 2:
                    renewItems(student);
                    break;
                case 3:
                    returnItems();
                    break;
                case 4:
                    displayCheckedOutItems(student);
                    break;
                case 0:
                    System.out.println("Exiting Student Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        
        //scanner.close(); // Close the scanner after use
    }
    public double calculateOverdueFine(LibraryItem item) {
        double fine = 0.0;
        LocalDate currentDate = LocalDate.now();
        LocalDate dueDate = item.getDueDate();

        if (item.isBorrowed() && dueDate.isBefore(currentDate)) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate, currentDate);
            double finePerDay = item.getOverdueFinePerDay();
            fine = finePerDay * daysOverdue;
        }

        return fine;
    }
    public void displayCheckedOutItems(User user) {
        System.out.println("Checked-out Items for " + user.getName() + ":");

        for (LibraryItem item : libraryItems.values()) {
            if (item.isBorrowed() && item.getBorrowedBy() == user) {
                System.out.println(item.getItemCode() + ": " + item.getItemName() + " (Due Date: " + item.getDueDate() + ")");
                double fine = calculateOverdueFine(item);
                if (fine > 0.0) {
                    System.out.println("   Overdue Fine: $" + fine);
                }
            }
        }
    }

public void check(User user){
    Scanner scanner = new Scanner(System.in);
    System.out.print("\nEnter the item code to borrow (Enter 0 to stop): ");
    String itemCode;
    while (true) {
        itemCode = scanner.nextLine();

        if (itemCode.equals("0")) {
            break;
        }

        LibraryItem item = getLibraryItemByCode(itemCode);
        if (item != null && item.isAvailable()) {
            // Check if the item code starts with "REF" or "MAZ"
            if (itemCode.startsWith("REF") || itemCode.startsWith("MAZ")) {
                System.out.println("Items Reference books and magazines cannot be borrowed.");
            } else {
                if (item.isBorrowable()) {
                    // Borrow the item
                    if (item instanceof Book) {
                        Book book = (Book) item;
                        if (book.isBorrowed()) {
                            System.out.println(book.getItemName() + " is already borrowed by another user.");
                            System.out.print("Do you want to request the item? (yes/no): ");
                            String raiseRequest = scanner.nextLine();
                            if (raiseRequest.equalsIgnoreCase("yes")) {
                                bookRequests.add(new BookRequest(user, book));
                                System.out.println("Your request for " + book.getItemName() + " has been added.");
                            }
                        } else {
                            if (book.isBestSeller()) {
                                book.setDueDate(LocalDate.now().plusWeeks(2));
                            } else {
                                book.setDueDate(LocalDate.now().plusWeeks(3));
                            }
                            book.setBorrowedBy(user);
                            System.out.println(book.getItemName() + " is borrowed successfully.");
                        }
                    } else if (item instanceof AudioVideoMaterial) {
                        AudioVideoMaterial avMaterial = (AudioVideoMaterial) item;
                        if (avMaterial.isBorrowed()) {
                            System.out.println(avMaterial.getItemName() + " is already borrowed by another user.");
                            System.out.print("Do you want to request the item? (yes/no): ");
                            String raiseRequest = scanner.nextLine();
                            if (raiseRequest.equalsIgnoreCase("yes")) {
                                bookRequests.add(new BookRequest(user, avMaterial));
                                System.out.println("Your request for " + avMaterial.getItemName() + " has been added.");
                            }
                        } else {
                            avMaterial.setDueDate(LocalDate.now().plusWeeks(2)); // Two weeks for audio/video materials
                            avMaterial.setBorrowedBy(user);
                            System.out.println(avMaterial.getItemName() + " is borrowed successfully.");
                        }
                    }
                } else {
                    System.out.println(item.getItemName() + " is a reference book and cannot be borrowed.");
                }
            }
        } else {
            System.out.println("Invalid item code or item is not available for borrowing. Please try again.");
            System.out.print("Do you want to raise a request for this item? (yes/no): ");
            String raiseRequest = scanner.nextLine();
            if (raiseRequest.equalsIgnoreCase("yes")) {
                bookRequests.add(new BookRequest(user, item));
                System.out.println("Your request for the item with code " + itemCode + " has been raised.");
            }
        }
    }

    double totalFine = 0.0;
    LocalDate currentDate = LocalDate.now();

    for (LibraryItem item : libraryItems.values()) {
        if (item.isBorrowed() && item.getBorrowedBy() == user) {
            if (item.getDueDate().isBefore(currentDate)) {
                long daysOverdue = ChronoUnit.DAYS.between(item.getDueDate(), currentDate);
                double fine = overdueFinePerDay * daysOverdue;
                totalFine += fine;
            }
        }
    }

    if (totalFine > 0.0) {
        System.out.println("Overdue Fine for " + user.getName() + ": $" + totalFine);
    } else {
        System.out.println("No overdue fine for " + user.getName());
    }
}

    // Borrow items
public void borrowItems(User user) {
    Scanner scanner = new Scanner(System.in);
    int count=0;
    System.out.println("\nAvailable Items for Borrowing:");

    // Convert the values of libraryItems map to a List and then iterate through it
    List<LibraryItem> libraryItemList = new ArrayList<>(libraryItems.values());
    for (LibraryItem item : libraryItemList) {
        if (item.isAvailable()) {
            String itemType = item instanceof Book ? "Book" : "Audio/Video Material";
            System.out.println(item.getItemCode() + ": " + item.getItemName() + " (" + itemType + ")");
        }
    }
    if(user.getAge()<=12){
        if(count<=5){
            check(user);
            count++;
        }
        else{
            System.out.println("you reached your limit to borrow items");
            
        }
    }
    else if(user.getAge()>=13){
        check(user);
    }
    // Ask for item code and borrow items
    
}

    
    
    
    public void displayDueDates() {
        System.out.println("Due Dates for Borrowed Items:");
        for (LibraryItem item : libraryItems.values()) {
            if (item.isBorrowed()) {
                System.out.println(item.getItemName() + ": " + item.getDueDate());
            }
        }
    }
    public void changeDueDate(String itemCode, LocalDate newDueDate) {
        LibraryItem item = libraryItems.get(itemCode);
        if (item != null && item.isBorrowed()) {
            item.setDueDate(newDueDate);
            System.out.println("Due date for " + item.getItemName() + " changed to: " + newDueDate);
        } else {
            System.out.println("Invalid item code or item is not currently borrowed.");
        }
    }
    
    public void renewItems(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nRenew Items:");
        List<LibraryItem> checkedOutItems = getCheckedOutItems(user);
        if (checkedOutItems.isEmpty()) {
            System.out.println("You have no items currently checked out.");
        } else {
            System.out.println("Items currently checked out by you:");
            for (LibraryItem item : checkedOutItems) {
                System.out.println(item.getItemCode() + ": " + item.getItemName() + " (Due Date: " + item.getDueDate() + ")");
            }
            System.out.print("Enter the item code to renew (Enter 0 to stop): ");
            String itemCode;
            while (true) {
                itemCode = scanner.nextLine();

                if (itemCode.equals("0")) {
                    break;
                }

                LibraryItem item = getLibraryItemByCode(itemCode);
                if (item != null && checkedOutItems.contains(item)) {
                    int renewalsLeft = item.getRenewalsLeft();
                    if (renewalsLeft > 0) {
                        System.out.println("You have " + renewalsLeft + " chance(s) to renew this item.");
                        System.out.print("Do you want to renew this item? (yes/no): ");
                        String answer = scanner.nextLine();
                        if (answer.equalsIgnoreCase("yes")) {
                            // Renew the item
                            LocalDate newDueDate;
                            if (item instanceof Book) {
                                Book book = (Book) item;
                                if (book.isBestSeller()) {
                                    newDueDate = LocalDate.now().plusWeeks(2);
                                } else {
                                    newDueDate = LocalDate.now().plusWeeks(3);
                                }
                            } else if (item instanceof AudioVideoMaterial) {
                                newDueDate = LocalDate.now().plusWeeks(2);
                            } else {
                                newDueDate = LocalDate.now(); // Unknown item type, set to today (due immediately)
                            }
                            item.setDueDate(newDueDate);
                            item.setRenewalsLeft(renewalsLeft - 1); // Decrease the renewals left
                            System.out.println("Item renewed: " + item.getItemName() + " (New Due Date: " + newDueDate + ")");
                        } else {
                            System.out.println("Renewal cancelled.");
                        }
                    } else {
                        System.out.println("You don't have any renewals available for this item.");
                    }
                } else {
                    System.out.println("Invalid item code or the item is not currently checked out by you.");
                }

                System.out.print("\nEnter the item code to renew (Enter 0 to stop): ");
            }
        }
    }
    
    public List<LibraryItem> getCheckedOutItems(User user) {
        List<LibraryItem> checkedOutItems = new ArrayList<>();
        for (LibraryItem item : libraryItems.values()) {
            if (item.isBorrowed() && item.getBorrowedBy() == user) {
                checkedOutItems.add(item);
            }
        }
        return checkedOutItems;
    }
    public void returnItems() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("\nEnter the library card number of the student: ");
        String libraryCardNumber = scanner.nextLine();
    
        User student = getUserById(libraryCardNumber);
        if (student == null) {
            System.out.println("Student with library card number " + libraryCardNumber + " not found.");
            return;
        }
    
        List<LibraryItem> checkedOutItems = getCheckedOutItems(student);
        if (checkedOutItems.isEmpty()) {
            System.out.println(student.getName() + " has no items currently checked out.");
            return;
        }
    
        double totalFine = 0.0;
        LocalDate currentDate = LocalDate.now();
    
        for (LibraryItem item : checkedOutItems) {
            if (item.getDueDate() != null && item.getDueDate().isBefore(currentDate)) {
                long daysOverdue = ChronoUnit.DAYS.between(item.getDueDate(), currentDate);
                double fine = overdueFinePerDay * daysOverdue;
                totalFine += fine;
            }
        }
    
        if (totalFine > 0.0) {
            System.out.println("Overdue Fine for " + student.getName() + ": $" + totalFine);
            System.out.print("Do you want to pay the fine? (yes/no): ");
            String payFine = scanner.nextLine();
            if (payFine.equalsIgnoreCase("yes")) {
                student.deductBalance(totalFine);
                System.out.println("Fine paid successfully. Remaining balance: $" + student.getBalance());
            } else {
                System.out.println("Fine not paid. You need to pay the fine before returning the items.");
                return;
            }
        } else {
            System.out.println("No overdue fine for " + student.getName());
        }
    
        System.out.println("\nItems currently checked out by " + student.getName() + ":");
        for (LibraryItem item : checkedOutItems) {
            System.out.println(item.getItemCode() + ": " + item.getItemName());
        }
    
        System.out.print("\nEnter the item code to return (Enter 0 to stop): ");
        String itemCode;
        while (true) {
            itemCode = scanner.nextLine();
    
            if (itemCode.equals("0")) {
                break;
            }
    
            LibraryItem item = getLibraryItemByCode(itemCode);
            if (item != null && checkedOutItems.contains(item)) {
                item.setBorrowedBy(null); // Mark the item as not borrowed by any user
                checkedOutItems.remove(item);
                System.out.println("Returned: " + item.getItemName());
            } else {
                System.out.println("Invalid item code or the item is not currently checked out by " + student.getName() + ". Please try again.");
            }
    
            System.out.print("\nEnter the item code to return (Enter 0 to stop): ");
        }
    }
    
        
    
    
    
    public void showFacultyMenu(User faculty) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display the faculty menu options
            System.out.println("\nFaculty Menu:");
            System.out.println("1. Borrow Items");
            System.out.println("2. Renew Items");
            System.out.println("3. Return Items");
            System.out.println("4. Checkedout items");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");

            // Validate input
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left in the input buffer

            switch (choice) {
                case 1:
                    borrowItems(faculty);
                    break;
                case 2:
                    renewItems(faculty);
                    break;
                case 3:
                    returnItems();
                    break;
                case 4:
                    displayCheckedOutItems(faculty);
                    break;
                case 0:
                    System.out.println("Exiting Student Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public void showChildrenMenu(User child) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display the children menu options
            System.out.println("\nChildren Menu:");
            System.out.println("1. Borrow Items");
            System.out.println("2. Renew Items");
            System.out.println("3. Return Items()");
            System.out.println("4. Checkedout items");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");

            // Validate input
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left in the input buffer

            switch (choice) {
                case 1:
                    borrowItems(child);
                    break;
                case 2:
                    renewItems(child);
                    break;
                case 3:
                    returnItems();
                    break;
                case 4:
                    displayCheckedOutItems(child);
                    break;
                case 0:
                    System.out.println("Exiting Student Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
    // Other methods in the Library class...
}
