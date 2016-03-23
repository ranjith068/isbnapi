package whatnowtravel.com.isbndb.presenters;


import java.util.List;

import whatnowtravel.com.isbndb.Book;

public interface SearchPresenter{
    void searchBook(String book);
    void onSearchSucceed(List<Book> response);
    void onSearchFailure(String mess);
}
