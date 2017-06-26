package xyz.fnplus.clientproject.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.fnplus.clientproject.R;
import xyz.fnplus.clientproject.models.FirebaseDataModel;

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
    @BindView(R.id.txt_input_emp_name)
    TextInputLayout mTxtinputEmpname;
    @BindView(R.id.textView_day_Openreading)
    EditText mTextViewDayOpenreading;
    @BindView(R.id.text_input_day_open_reading)
    TextInputLayout mTextInputDayOpenreading;
    @BindView(R.id.textView_day_Closereading)
    EditText mTextViewDayClosereading;
    @BindView(R.id.text_input_day_close_reading)
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
    @BindView(R.id.shiftdate)
    EditText mShiftdate;
    @BindView(R.id.rb_day)
    RadioButton mRbDay;
    @BindView(R.id.rb_night)
    RadioButton mRbNight;
    @BindView(R.id.rg_shift_details)
    RadioGroup mRgShiftDetails;
    @BindView(R.id.form_layout)
    LinearLayout mFormLayout;
    @BindView(R.id.txt_last_reading_date)
    TextView mTxtLastReadingDate;
    @BindView(R.id.txt_last_reading_shift)
    TextView mTxtLastReadingShift;
    private DatabaseReference mFirebaseDatabaseReference;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFormLayout.setVisibility(View.GONE);

        // Declare Firebase Database reference
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

    }

    @OnClick(R.id.btn_loom_submit)
    public void onMBtnLoomSubmitClicked(final View view) {
        if (TextUtils.isEmpty(mEditTxtLoom.getText().toString())) {
            mEditTxtLoom.setError("Enter Loom No");
            Toast.makeText(this, "Enter Loom No", Toast.LENGTH_SHORT).show();
        } else {
            mFirebaseDatabaseReference.child("LOOMS").child(mEditTxtLoom.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FirebaseDataModel dm = dataSnapshot.getValue(FirebaseDataModel.class);
                    if (dm == null) {
                        clearDataInView();
                        mFormLayout.setVisibility(View.VISIBLE);
                        Snackbar.make(view, "No Loom Found, fill the details for that Loom", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        clearDataInView();
                        Snackbar.make(view, "Last Record Of the Loom Fetched!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        mFormLayout.setVisibility(View.VISIBLE);
                        setDataToView(dm);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @OnClick(R.id.btn_details_submit)
    public void onMBtnDetailsSubmitClicked(View v) {
        if (validateDetails()) {
            RadioButton shift = (RadioButton) findViewById(mRgShiftDetails.getCheckedRadioButtonId());
            RadioButton messSize = (RadioButton) findViewById(mRgMess.getCheckedRadioButtonId());
            RadioButton status = (RadioButton) findViewById(mRgStatus.getCheckedRadioButtonId());
            FirebaseDataModel model = new FirebaseDataModel(mEditTxtLoom.getText().toString(), mTextViewDayOpenreading.getText().toString(), mShiftdate.getText().toString(), shift.getText().toString(), messSize.getText().toString(), status.getText().toString(), mTxtEmpCode.getText().toString(), mTxtEmpName.getText().toString(), mTextViewDayOpenreading.getText().toString(), mTextViewDayClosereading.getText().toString(), mEditTxtRemark.getText().toString(), mTextView.getText().toString());
            mFirebaseDatabaseReference.child("LOOMS").child(mEditTxtLoom.getText().toString()).setValue(model);
            Snackbar.make(v, "Loom Updated", Snackbar.LENGTH_LONG).show();
            mFormLayout.setVisibility(View.GONE);
        }

    }

    boolean validateDetails() {
        boolean validate = true;
        if (TextUtils.isEmpty(mEditTxtLoom.getText().toString())) {
            validate = false;
            mEditTxtLoom.setError("Enter Loom No");
            Toast.makeText(this, "Enter Loom No", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(mShiftdate.getText().toString())) {
            validate = false;
            mShiftdate.setError("Enter Shift Date");
            Toast.makeText(this, "Enter Shift Date", Toast.LENGTH_SHORT).show();
        }
        if (mRgShiftDetails.getCheckedRadioButtonId() == -1) {
            validate = false;
            Toast.makeText(this, "Select Mess Size or Status", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(mTextView.getText().toString())) {
            validate = false;
            mTextView.setError("Enter Quality Name/Code");
            Toast.makeText(this, "Enter Quality Name/Code", Toast.LENGTH_SHORT).show();
        }
        if (mRgMess.getCheckedRadioButtonId() == -1 || mRgStatus.getCheckedRadioButtonId() == -1) {
            validate = false;
            Toast.makeText(this, "Select Mess Size or Status", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(mTxtEmpCode.getText().toString()) || TextUtils.isEmpty(mTxtEmpName.getText().toString())) {
            validate = false;
            mTxtEmpCode.setError("Enter Employee Name/Code");
            Toast.makeText(this, "Enter Employee Name/Code", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(mTextViewDayOpenreading.getText().toString()) || TextUtils.isEmpty(mTextViewDayClosereading.getText().toString())) {
            validate = false;
            mTextViewDayOpenreading.setError("Enter Day Close/Open Reading");
            Toast.makeText(this, "Enter Day Close/Open Reading", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(mEditTxtRemark.getText().toString())) {
            validate = false;
            mEditTxtRemark.setError("Enter Remark");
            Toast.makeText(this, "Enter Remark", Toast.LENGTH_SHORT).show();
        }
        return validate;

    }

    @OnClick(R.id.shiftdate)
    public void onViewClicked() {

        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.UK);

                mShiftdate.setText(sdf.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        StartTime.show();
    }

    void setDataToView(FirebaseDataModel model) {
        mDbLastReading.setText(model.getLastReading());
        mTxtLastReadingDate.setText(model.getDate());
        mTxtLastReadingShift.setText(model.getShift());
        mShiftdate.setText(model.getDate());
        mTxtEmpCode.setText(model.getEmpCode());
        mTxtEmpName.setText(model.getEmpName());
        mTextViewDayClosereading.setText(model.getDayEndReading());
        mTextViewDayOpenreading.setText(model.getDayOpenReading());
        mEditTxtRemark.setText(model.getRemark());
        mTextView.setText(model.getQuality());
        switch (model.getShift()) {
            case "Day":
                mRbDay.setChecked(true);
                break;
            case "Night":
                mRbNight.setChecked(true);
                break;

        }
        switch (model.getMessSize()) {
            case "10*10":
                mRbMess1.setChecked(true);
                break;
            case "11*11":
                mRbMess2.setChecked(true);
                break;
            case "12*12":
                mRbMess3.setChecked(true);
                break;
            case "Other":
                mRbMess4.setChecked(true);
                break;
        }
        switch (model.getStatus()) {
            case "Running":
                mRbStatus1.setChecked(true);
                break;
            case "Drawing":
                mRbStatus2.setChecked(true);
                break;
            case "Loading":
                mRbStatus3.setChecked(true);
                break;
            case "R&M":
                mRbStatus4.setChecked(true);
                break;
            case "Other":
                mRbStatus5.setChecked(true);
                break;
        }

    }

    void clearDataInView() {
        mDbLastReading.setText("N/A");
        mTxtLastReadingDate.setText("N/A");
        mTxtLastReadingShift.setText("N/A");
        mShiftdate.setText(null);
        mTxtEmpCode.setText(null);
        mTxtEmpName.setText(null);
        mTextViewDayClosereading.setText(null);
        mTextViewDayOpenreading.setText(null);
        mEditTxtRemark.setText(null);
        mTextView.setText(null);
        mRgStatus.clearCheck();
        mRgMess.clearCheck();
        mRgShiftDetails.clearCheck();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
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

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

