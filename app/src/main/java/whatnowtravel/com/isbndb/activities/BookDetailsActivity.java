package whatnowtravel.com.isbndb.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import whatnowtravel.com.isbndb.Book;
import whatnowtravel.com.isbndb.R;

public class BookDetailsActivity extends ActionBarActivity {
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        book = getIntent().getExtras().getParcelable("BOOK");

        TextView title = (TextView) findViewById(R.id.booktitle);
        TextView author = (TextView) findViewById(R.id.author);
        TextView isbn = (TextView) findViewById(R.id.isbn);


        title.setText(book.getName());
        List<String> aut = book.getAuthor();
        StringBuilder sb = new StringBuilder();
        for(String a: aut){
            sb.append(a).append("\n");
        }
        author.setText(sb.toString());
        isbn.setText(book.getIsbn10());

        summary();

    }

    private void summary() {
        TextView summaryF = (TextView) findViewById(R.id.summaryF);
        TextView summary = (TextView) findViewById(R.id.summary);
        String sum = book.getSummary();
        if(!sum.equals("")) {
            summary.setText(sum);
        }else{
            summaryF.setVisibility(View.GONE);
            summary.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.book_details, menu);
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
}
