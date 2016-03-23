package whatnowtravel.com.isbndb.views;

import android.content.Context;

import java.util.List;

import whatnowtravel.com.isbndb.Book;

public interface SearchView {

    public void showSearchResult(List<Book> books);
    public void makeToast(String message);
    public void showProgressBar();
    public void hideProgress();
    public Context getContext();
}
