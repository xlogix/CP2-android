package xyz.fnplus.clientproject.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.fnplus.clientproject.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_txt_loom)
    EditText mEditTxtLoom;
    @BindView(R.id.btn_loom_submit)
    AppCompatButton mBtnLoomSubmit;
    @BindView(R.id.txt_last_reading)
    TextView mTxtLastReading;
    @BindView(R.id.db_last_reading)
    TextView mDbLastReading;
    @BindView(R.id.textView)
    AutoCompleteTextView mTextView;
    @BindView(R.id.txt_mess_size)
    TextView mTxtMessSize;
    @BindView(R.id.rb_mess1)
    RadioButton mRbMess1;
    @BindView(R.id.rb_mess2)
    RadioButton mRbMess2;
    @BindView(R.id.rb_mess3)
    RadioButton mRbMess3;
    @BindView(R.id.rb_mess4)
    RadioButton mRbMess4;
    @BindView(R.id.rg_mess)
    RadioGroup mRgMess;
    @BindView(R.id.txt_status)
    TextView mTxtStatus;
    @BindView(R.id.rb_status1)
    RadioButton mRbStatus1;
    @BindView(R.id.rb_status2)
    RadioButton mRbStatus2;
    @BindView(R.id.rb_status3)
    RadioButton mRbStatus3;
    @BindView(R.id.rb_status4)
    RadioButton mRbStatus4;
    @BindView(R.id.rb_status5)
    RadioButton mRbStatus5;
    @BindView(R.id.rg_status)
    RadioGroup mRgStatus;
    @BindView(R.id.txt_emp_code)
    EditText mTxtEmpCode;
    @BindView(R.id.txtinput_emp_code)
    TextInputLayout mTxtinputEmpCode;
    @BindView(R.id.txt_emp_name)
    EditText mTxtEmpName;
    @BindView(R.id.txtinput_empname)
    TextInputLayout mTxtinputEmpname;
    @BindView(R.id.textView_day_Openreading)
    EditText mTextViewDayOpenreading;
    @BindView(R.id.textInput_day_openreading)
    TextInputLayout mTextInputDayOpenreading;
    @BindView(R.id.textView_day_Closereading)
    EditText mTextViewDayClosereading;
    @BindView(R.id.textInput_day_Closereading)
    TextInputLayout mTextInputDayClosereading;
    @BindView(R.id.edit_txt_remark)
    EditText mEditTxtRemark;
    @BindView(R.id.btn_details_submit)
    AppCompatButton mBtnDetailsSubmit;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private DatabaseReference mFirebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sending data to the server", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        String refPath = getIntent().getStringExtra("refPath");


    }

    private void updateDataOnServer() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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

    @OnClick(R.id.btn_loom_submit)
    public void onMBtnLoomSubmitClicked() {


    }

    @OnClick(R.id.btn_details_submit)
    public void onMBtnDetailsSubmitClicked() {

    }
}
