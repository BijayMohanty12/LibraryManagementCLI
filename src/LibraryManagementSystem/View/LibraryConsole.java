package LibraryManagementSystem.View;

import LibraryManagementSystem.Model.Book;
import LibraryManagementSystem.Model.Library;
import LibraryManagementSystem.Model.User;


import java.util.List;
import java.util.Scanner;



public class LibraryConsole {


    public static synchronized void userInput(Library library, User user) {
        System.out.println("Welcome to the Library ");
        System.out.println("How can we assist you");
        Scanner next= new Scanner(System.in);
        int flag=0;
        int value=0;
        boolean lock1= false;
        boolean lock2= false;
        boolean lock3= false;
        while (true) {
             if (flag == 0){
                System.out.println("\nSelect your choice");
            }
            else{
                System.out.println("\nSelect your next  choice");
            }

            if(!(value ==1)) {
                System.out.println("1 List all books");

                lock1= true;
            }
            if(!(value==2)) {
                System.out.println("2. Borrow a book");

                lock2=true;
            }
            if(!(value==3))
            {
                System.out.println("3 .Return a book");

                lock3=true;
            }
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            value = next.nextInt();
            flag=1;
            switch (value) {

                case 1:
                    if (lock1) {
                        System.out.println("This books are available:");
                        library.listBook();
                        lock1=false;
                    }
                    break;

                case 2:
                    if(lock2) {
                        List<Book> bookList;

                        next.nextLine();
                        System.out.println("Please Enter Book Name");
                        String bookName = next.nextLine();
                        System.out.println("Please Enter Author Name");
                        String bookAuthor = next.nextLine();
                        bookList = library.searchBook(bookName, bookAuthor);
                        if (bookList.isEmpty()) {
                            break;
                        } else {
                            System.out.println(bookList.size() + " book is available");

                        }
                        System.out.println("\nPlease Enter Y/N  :");
                        for (Book book : bookList) {
                            System.out.println("Book name :" + book.getBookName());
                            System.out.println("Book author name: " + book.getAuthor());

                            System.out.println("If you want to Borrow this Book");
                            System.out.println("Enter Y/N  or If you want to Exit Enter 4 :");
                            String check = next.nextLine().trim();
                            if (check.isEmpty()) {
                                System.out.println("Empty input. Please enter Y/N or 4.");
                                break;
                            }
                            char firstChar = check.toLowerCase().charAt(0);

                            if (firstChar == 'y') {
                                user.setBorrow(book);

                                System.out.println(" Book successfully borrowed.");
                            } else if (firstChar == 'n') {
                                continue;

                            } else if (check.equals("4")) {
                                break;
                            } else {
                                System.out.println("Input is invalid.");
                                break;
                            }

                        }
                        lock2=false;
                    }
                    break;
                case 3:
                    if(lock3) {

                        List<Book> books;
                        System.out.println("Hello ," + user.getNameValue());
                        List<Book> borrowedBook = user.borrowedBook();
                        if(borrowedBook.isEmpty())
                        {
                            System.out.println("Book is borrow by "+user.getNameValue());
                            return ;
                        }
                        System.out.println("Which book you want to return :");
                        for (Book book : borrowedBook) {
                            System.out.println("Book Name: " + book.getBookName());
                            System.out.println("Book Author: " + book.getAuthor());
                        }
                        System.out.println("Which book you want to return :");
                        System.out.println("Please Enter a book Name");
                        next.nextLine();
                        String title = next.nextLine();
                        System.out.println("Please Enter a author Name");
                        String authorName = next.nextLine();
                        books = library.searchBook(title, authorName);
                        for (Book book : books) {
                            System.out.println("Please Enter your Choice Y/N or 4 for exit");
                            String check = next.nextLine().trim();
                            if (book.isBorrow()) {
                                char firstChar = check.toLowerCase().charAt(0);
                                if (firstChar == 'y') {

                                    Book bookTemp = user.returnBook(book);
                                    if (bookTemp != null) {
                                        System.out.println("Book successfully return");
                                    }
                                } else if (firstChar == 'n') {
                                    continue;

                                } else if (check.equals("4")) {
                                    break;
                                } else {
                                    System.out.println("Input is invalid.");
                                    break;
                                }

                            }


                        }
                        lock3=false;
                    }
                      break;

                case 4:
                    int star = 0;
                    System.out.println(" Please rate your experience with our library services (1 to 5 stars):");
                    System.out.println("1 Star: Very Dissatisfied");
                    System.out.println("2 Stars: Dissatisfied");
                    System.out.println("3 Stars: Neutral");
                    System.out.println("4 Stars: Satisfied");
                    System.out.println("5 Stars: Very Satisfied");

                    System.out.print("Enter your rating (1-5): ");
                    try{
                        star = next.nextInt();
                    }catch(RuntimeException e)
                    {
                        System.out.println(e.getMessage());

                    }



                    if (star >= 1 && star <= 5) {

                        System.out.println(" Rating: " + star + " stars");
                    }
                    else
                    {
                        System.out.println(" Invalid rating! Please enter a number between 1 and 5.");
                    }
                    next.nextLine();
                    System.out.print(" (Optional) Additional comments: ");
                    String comment = next.nextLine();

                    System.out.println("\nThank you for your feedback! ");

                    if (!comment.isEmpty()) {
                        System.out.println(" Comment: " + comment);
                    } else {
                        System.out.println("No additional comments provided.");
                    }


            }
            if(value==4)
            {
                System.out.println("Thanks for coming!");
                break;
            }


        }
    }


}
