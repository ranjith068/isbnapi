package whatnowtravel.com.isbndb;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Book implements Parcelable{

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn) {
        this.isbn10 = isbn;
    }
    public void setIsbn13(String isbn) {
        this.isbn13 = isbn;
    }

    private String name;
    private List<String> author = new ArrayList<>();;
    private String summary;

    public static Creator<Book> getCreator() {
        return CREATOR;
    }

    private String isbn13;
    private String isbn10;
    private String publisher;

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public Book(String title){
        this.name = title;
    }

    public Book(Parcel in){
        String[] book = new String[3];
        in.readStringArray(book);
        this.name = book[0];
        this.isbn10 = book[1];
        this.summary = book[2];
        final int authorNum = in.readInt();
        String[] authors = new String[authorNum];
        in.readStringArray(authors);
        for(int i=0;i<authorNum; i++){
            this.author.add(authors[i]);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {


        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeStringArray(new String[]{
                this.name,
                this.isbn10,
                this.summary,

        });
        String[] aut = author.toArray(new String[author.size()]);
        dest.writeInt(author.size());
        dest.writeStringArray(aut);
    }

    public void setPublisher(String pubblishertext) {
        this.publisher = pubblishertext;
    }
}
