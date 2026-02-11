package LibraryManagementSystem.Model;



public class Book  {

    protected int  bookID;
    private boolean  borrow;
    private final String bookName;
    private final String author;
    public Book(String bookName,String author,Boolean isBorrow)
    {

        this.bookName=bookName;
        this.author=author;
        this.borrow=isBorrow;
    }
    public Book(String bookName,String author, Boolean isBorrow, int bookID)
    {
        this.bookID=bookID;
        this.bookName=bookName;
        this.author=author;
        this.borrow=isBorrow;

    }

    public String getBookName()
    {
        return bookName;
    }
    public String getAuthor()
    {
        return author;
    }

    public int getBook_ID()
    {
        return bookID;
    }

    public boolean isBorrow() {
        return borrow;
    }
    public  void setBorrow(boolean borrow)
    {
        this.borrow=borrow;
    }
}
