package bd.org.basis.onlinelibrary;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BooksAdapter extends ArrayAdapter<Book> {
	
	private Activity context;
	private ArrayList<Book> items;
	private LayoutInflater inflater;

	public BooksAdapter(Context context, ArrayList<Book> items) 
	{
		super(context, R.layout.book_item, items);
		this.context=(Activity)context;
		this.items=items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v=convertView;
		if(v==null)
		{
			// create new view
			inflater=context.getLayoutInflater();
			v=inflater.inflate(R.layout.book_item, null);
			
			TextView tvTitle=(TextView)v.findViewById(R.id.tvTitle);
			TextView tvAuthor=(TextView)v.findViewById(R.id.tvAuthor);
			TextView tvCategory=(TextView)v.findViewById(R.id.tvCategory);
			TextView tvPrice=(TextView)v.findViewById(R.id.tvPrice);
			Book b=items.get(position);
			tvTitle.setText(b.getTitle());
			tvAuthor.setText(b.getAuthorName());
			tvCategory.setText(b.getCategory());
			tvPrice.setText("$"+b.getPrice());
			
			tvTitle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(context, "I am a title", Toast.LENGTH_LONG).show();
					
				}
			});
			
		}
		else
		{
			
		}
		
		return v;
	}

}
