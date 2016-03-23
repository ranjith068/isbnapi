package whatnowtravel.com.isbndb.activities;
import whatnowtravel.com.isbndb.contentprovider.ITransactionProcess;
import whatnowtravel.com.isbndb.contentprovider.Transaction;
import whatnowtravel.com.isbndb.contentprovider.TransactionDbHelper;
import whatnowtravel.com.isbndb.contentprovider.TransactionProcess;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import whatnowtravel.com.isbndb.Book;
import whatnowtravel.com.isbndb.BookAdapter;
import whatnowtravel.com.isbndb.R;
import whatnowtravel.com.isbndb.presenters.SearchPresenter;
import whatnowtravel.com.isbndb.presenters.SearchPresenterImpl;
import whatnowtravel.com.isbndb.rest.json.ParseBooksResult;
import whatnowtravel.com.isbndb.views.SearchView;


public class SearchActivity extends ActionBarActivity implements SearchView {

    private Button ok;
    private SearchPresenter searchPresenter;
    private TextView search;
    private ListView listView1;
    private BookAdapter bookAdapter;
    public final static String EXTRA_MESSAGE = "BOOK";
    private List<Book> books;
    private static final String LIST_BOOKS = "LIST_BOOKS";
    private ProgressBar progressBar;
    private ITransactionProcess transactionProcess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transactionProcess = new TransactionProcess(this);

        if(savedInstanceState != null){
            books = savedInstanceState.getParcelableArrayList(LIST_BOOKS);
        }else{
            books = new ArrayList<>();
            Transaction lastTransaction = transactionProcess.getLast();
            if(lastTransaction!=null){
                if(lastTransaction.getStatus() == 2){
                    ParseBooksResult lastResearch = new ParseBooksResult(lastTransaction.getSummary());
                    books = lastResearch.parse();
                }
            }
        }
        setContentView(R.layout.activity_main);
        ok = (Button) findViewById(R.id.ok);
        search = (TextView) findViewById(R.id.search);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        searchPresenter = new SearchPresenterImpl(this, transactionProcess);
        listView1 = (ListView)findViewById(R.id.listView1);
        bookAdapter = new BookAdapter(this,R.layout.listview_item_row, books);
        listView1.setAdapter(bookAdapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Book book = bookAdapter.getItem(position);
                Intent intent = new Intent(SearchActivity.this, BookDetailsActivity.class);
                intent.putExtra(EXTRA_MESSAGE, book);
                startActivity(intent);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(search.getText().toString())){
                    makeToast("Please insert a book name");
                }else {
                    searchPresenter.searchBook(search.getText().toString());
                }
            }

        });
    }

    public void makeToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void showProgressBar(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause(){

        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putParcelableArrayList(LIST_BOOKS, (ArrayList<Book>) books);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        books = savedInstanceState.getParcelableArrayList(LIST_BOOKS);


    }

    @Override
    public void showSearchResult(final List<Book> books) {
        bookAdapter.clear();
        bookAdapter.addAll(books);
        bookAdapter.notifyDataSetChanged();
        if(books == null || books.isEmpty()) {
            makeToast("No book matching");
        }
    }
}
