package no.webbo.handleliste;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

	TextView mtextOutput;
	EditText mtextInput;
	LinearLayout layout;
	ScrollView sv;
	
	final static String FILENAME = "list.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWidgets();
    	loadTextFromFile();
    }
    

    /**
     * Sets up the widget in the app
     */
    private void setupWidgets() {
    	Button save = (Button)findViewById(R.id.save);
    	Button clear = (Button) findViewById(R.id.clear);
    	save.setOnClickListener(this);
    	clear.setOnClickListener(this);
    	//mtextOutput = (TextView)findViewById(R.id.textView);
    	mtextInput = (EditText)findViewById(R.id.editText);
		//mtextOutput.setMovementMethod(new ScrollingMovementMethod());
		
		layout = (LinearLayout)findViewById(R.id.layout);
		sv = (ScrollView)findViewById(R.id.scrollView);
		
		setTitle("Handleliste");
		
	}


	private void loadTextFromFile() {
		File f = new File(getFilesDir(), FILENAME);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			ArrayList<String> wordlist = new ArrayList<String>();
			String line;
			CheckBox ch;
			//put all the words inside a list
			while((line = br.readLine())!= null){
				wordlist.add(line);
			}
			br.close();
			
			//Reverse them so that they come in the right order
			for(int i = 0; i<wordlist.size(); i++){
				ch = new CheckBox(getApplicationContext());
				ch.setText(wordlist.get(i));
				ch.setTextColor(getResources().getColor(R.color.grey));
				ch.setTextSize(24);
				layout.addView(ch);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/


	/**
	 * Handles clicks for HandleListe
	 * OnClick save-button, write textInput to textOutput and save text to file
	 */
	@Override
	public void onClick(View v) {
		//if the save button is clicked
		if(v.getId() == R.id.save){
			String text = mtextInput.getText().toString();
			mtextInput.setText("");
			CheckBox ch = new CheckBox(getApplicationContext());
			ch.setText(text);
			ch.setTextColor(getResources().getColor(R.color.grey));
			ch.setTextSize(24);
			layout.addView(ch);
			try {
				FileOutputStream fo = openFileOutput(FILENAME, Context.MODE_APPEND);
				fo.write(text.getBytes());
				fo.write("\n".getBytes());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//if the clear button is clicked
		else if(v.getId() == R.id.clear){
			
			AlertDialog alert = buildClearDialog();
			alert.show();
			
		}
		
	}


	/**
	 * Created a dialog to use when the clear button is clicked
	 * @return The alertdialog that is created
	 */
	private AlertDialog buildClearDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setCancelable(true);
		builder.setTitle("Er du sikker på at du vil tømme handlelisten?");
		//Adding buttons
		builder.setPositiveButton(R.string.clear,new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//mtextOutput.setText("");
				//layout = new LinearLayout(this);
				try {
					FileOutputStream fo = openFileOutput(FILENAME, Context.MODE_PRIVATE);
					fo.write("".getBytes());
					layout.removeAllViews();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		builder.setNegativeButton(R.string.neutral, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		return builder.create();
	}
    
}
