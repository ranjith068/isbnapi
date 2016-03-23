package whatnowtravel.com.isbndb;

import java.util.ArrayList;
import java.util.List;

public class BookBuilder implements IBuilder<Book>{

    private String bookName;
    private String titleLatin;
    private String isbn10;
    private String isbn13;
    private String pubblishertext;
    private String language;
    private String physicalDesciptionText;
    private String summury;
    private List<String> authors;

    public BookBuilder(String bookName){
        this.bookName = bookName;
        authors = new ArrayList<>();
    }

    public Book build(){
        Book book = new Book(bookName);
        book.setAuthor(authors);
        book.setSummary(summury);
        book.setIsbn10(isbn10);
        book.setIsbn13(isbn13);
        book.setPublisher(pubblishertext);
        return book;
    }

    public BookBuilder addAuthor(String bookAuthor){
        authors.add(bookAuthor);
        return this;
    }

    public BookBuilder addTitleLatin(String titleLatin){
        this.titleLatin = titleLatin;
        return this;
    }
    public BookBuilder addIsbn10(String isbn){
        this.isbn10 = isbn;
        return this;
    }
    public BookBuilder addIsbn13(String isbn){
        this.isbn13 = isbn;
        return this;
    }
    public BookBuilder addPubblisher(String pubblisher){
        this.pubblishertext = pubblisher;
        return this;
    }
    public BookBuilder addLanguage(String language){
        this.language = language;
        return this;
    }
    public BookBuilder addPhysicalDesciptionText(String physicalDesciptionText){
        this.physicalDesciptionText = physicalDesciptionText;
        return this;
    }
    public BookBuilder addSummary(String summury){
        this.summury = summury;
        return this;
    }
}
