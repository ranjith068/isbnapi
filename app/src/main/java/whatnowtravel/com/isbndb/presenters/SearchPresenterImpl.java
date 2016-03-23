package whatnowtravel.com.isbndb.presenters;

import android.content.Context;

import java.util.List;

import whatnowtravel.com.isbndb.Book;
import whatnowtravel.com.isbndb.contentprovider.ITransactionProcess;
import whatnowtravel.com.isbndb.views.SearchView;
import whatnowtravel.com.isbndb.models.SearchModel;
import whatnowtravel.com.isbndb.models.SearchModelImp;

public class SearchPresenterImpl implements SearchPresenter {


    private SearchView _view;
    private ITransactionProcess transactionProcess;
    private SearchModel model;

    public SearchPresenterImpl(SearchView _view, ITransactionProcess transactionProcess) {
        this._view = _view;
        this.transactionProcess = transactionProcess;
        model = new SearchModelImp(this, _view.getContext(), transactionProcess);
    }

    @Override
    public void searchBook(String book) {
        _view.showProgressBar();
        model.search(book);
    }

    @Override
    public void onSearchSucceed(List<Book> response) {
        _view.hideProgress();
        _view.showSearchResult(response);
    }

    @Override
    public void onSearchFailure(String message) {
        _view.hideProgress();
        _view.makeToast(message);
    }

}
