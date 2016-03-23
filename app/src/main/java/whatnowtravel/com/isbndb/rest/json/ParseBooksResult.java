package whatnowtravel.com.isbndb.rest.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import whatnowtravel.com.isbndb.Book;
import whatnowtravel.com.isbndb.BookBuilder;

public class ParseBooksResult {

    public static final String DATA = "data";
    public static final String TITLE = "title";
    public static final String PUBLISHER_NAME = "publisher_name";
    public static final String ISBN_10 = "isbn10";
    public static final String ISBN_13 = "isbn13";
    public static final String SUMMARY = "summary";
    public static final String AUTHOR_DATA = "author_data";
    public static final String NAME = "name";
    private String result;

    public ParseBooksResult(String result){

        this.result = result;
    }

    public List<Book> parse(){
        JSONObject data = null;
        List<Book> books = new ArrayList<>();
        try {
            data = new JSONObject(result);
            JSONArray arr = data.getJSONArray(DATA);
            for (int i = 0; i < arr.length(); i++)
            {
                String title = arr.getJSONObject(i).getString(TITLE);
                BookBuilder builder = new BookBuilder(title);
                String publisher_name = arr.getJSONObject(i).getString(PUBLISHER_NAME);
                builder.addPubblisher(publisher_name);
                String isbn10 = arr.getJSONObject(i).getString(ISBN_10);
                builder.addIsbn10(isbn10);
                String isbn13 = arr.getJSONObject(i).getString(ISBN_13);
                builder.addIsbn13(isbn13);
                String summary = arr.getJSONObject(i).getString(SUMMARY);
                builder.addSummary(summary);
                JSONArray author_data = arr.getJSONObject(i).getJSONArray(AUTHOR_DATA);
                for(int j =0; j< author_data.length(); j++) {
                    String name_author = author_data.getJSONObject(j).getString(NAME);
                    builder.addAuthor(name_author);
                }
                books.add(builder.build());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }
}
