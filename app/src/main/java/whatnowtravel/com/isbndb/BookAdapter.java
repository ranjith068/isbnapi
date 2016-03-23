package whatnowtravel.com.isbndb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {


    private Context context;
    private int layoutResourceId;
    private List<Book> books;

    public BookAdapter(Context context, int layoutResourceId, List<Book> books) {
        super(context, layoutResourceId, books);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        BookHolder holder = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new BookHolder();
            holder.bookName = (TextView)row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (BookHolder)row.getTag();
        }

        Book book = books.get(position);
        holder.bookName.setText(book.getName());

        return row;
    }

    static class BookHolder{
        TextView bookName;
    }
}
