package bd.org.basis.abtabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class FragmentA extends Fragment implements OnClickListener{
	
	private Button btnTest;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.frag_a, null, false);
		btnTest=(Button)view.findViewById(R.id.btnTest);
		btnTest.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		Toast.makeText(getActivity(), "Test Button Clicked", Toast.LENGTH_LONG).show();
	}
}
