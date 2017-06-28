package xyz.fnplus.clientproject.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    @BindView(R.id.btn_add_emp)
    ImageButton mBtnAddEmp;
    @BindView(R.id.list_view)
    ListView mListView;
    private DatabaseReference mFirebaseDatabaseReference;
    private ProgressDialog mProgressDialog;
    private HashMap<String, String> mEmployeeHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Set visibility of the form as NULL
        mFormLayout.setVisibility(View.GONE);
        // New Employee
        mEmployeeHashMap = new HashMap<>();

        // Declare Firebase Database reference
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String selectedItem = (String) mListView.getItemAtPosition(i);
                final Dialog d = new Dialog(MainActivity.this);
                d.setContentView(R.layout.card_view_emp_details_dialog);
                d.setTitle("Enter Employee Details");
                final EditText mTxtEmpCode = (EditText) d.findViewById(R.id.txt_emp_code);
                mTxtEmpCode.setEnabled(false);
                mTxtEmpCode.setText(selectedItem.substring(0, selectedItem.indexOf(" ")).substring(2));
                final EditText mTxtEmpName = (EditText) d.findViewById(R.id.txt_emp_name);
                mTxtEmpName.setText(mEmployeeHashMap.get(selectedItem.substring(0, selectedItem.indexOf(" "))));
                Button mConfirm = (Button) d.findViewById(R.id.btn_dialog_confirm);

                Button mCancel = (Button) d.findViewById(R.id.btn_dialog_cancel);
                mCancel.setText("Delete");
                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mEmployeeHashMap.remove(selectedItem.substring(0, selectedItem.indexOf(" ")));
                        updateListView();
                        d.dismiss();
                    }
                });
                d.show();
                mConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean validate = true;

                        if (TextUtils.isEmpty(mTxtEmpName.getText().toString())) {
                            validate = false;
                            mTxtEmpCode.setError("Enter Employee Name");
                            Toast.makeText(MainActivity.this, "Enter Employee Name", Toast.LENGTH_SHORT).show();
                        }
                        if (validate) {
                            mEmployeeHashMap.put("ID" + mTxtEmpCode.getText().toString(), mTxtEmpName.getText().toString());
                            updateListView();
                            d.dismiss();
                        }
                    }
                });
            }
        });
    }

    @OnClick(R.id.btn_loom_submit)
    public void onMBtnLoomSubmitClicked(final View view) {
        // show changes in UI
        showProgressDialog();
        // Logic
        if (TextUtils.isEmpty(mEditTxtLoom.getText().toString())) {
            mEditTxtLoom.setError("Enter Loom No");
            // UI
            hideProgressDialog();
            Toast.makeText(this, "Enter Loom No", Toast.LENGTH_SHORT).show();
        } else {
            // UI
            hideProgressDialog();
            // Logic
            mFirebaseDatabaseReference.child("LOOMS").child(mEditTxtLoom.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FirebaseDataModel dm = dataSnapshot.getValue(FirebaseDataModel.class);
                    if (dm == null) {
                        clearDataInView();
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.add(Calendar.DATE, 1);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.UK);
                        mShiftdate.setText(sdf.format(newCalendar.getTime()));

                        mFormLayout.setVisibility(View.VISIBLE);
                        setShiftFromCurrentTime();
                        Snackbar.make(view, "No Loom Found, fill the details for that Loom", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        clearDataInView();
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.add(Calendar.DATE, 1);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.UK);
                        mShiftdate.setText(sdf.format(newCalendar.getTime()));

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
        // show changes in UI
        showProgressDialog();
        // Logic
        if (validateDetails()) {
            // Declaring Buttons
            RadioButton shift = (RadioButton) findViewById(mRgShiftDetails.getCheckedRadioButtonId());
            RadioButton messSize = (RadioButton) findViewById(mRgMess.getCheckedRadioButtonId());
            RadioButton status = (RadioButton) findViewById(mRgStatus.getCheckedRadioButtonId());
            String LastReading = "";

            if (!TextUtils.isEmpty(mTextViewDayClosereading.getText().toString())) {
                LastReading = mTextViewDayClosereading.getText().toString();
                mTextViewDayClosereading.setText(null);
                mTextViewDayOpenreading.setText(null);
                Snackbar.make(v, "Loom Readings Reset for the Next Day", Snackbar.LENGTH_LONG).show();

            } else if (!TextUtils.isEmpty(mTextViewDayOpenreading.getText().toString())) {
                LastReading = mTextViewDayOpenreading.getText().toString();
                Snackbar.make(v, "Loom Is Now Active", Snackbar.LENGTH_LONG).show();

            } else LastReading = null;

            FirebaseDataModel model = new FirebaseDataModel(mEditTxtLoom.getText().toString(), LastReading, mShiftdate.getText().toString(), shift.getText().toString(), messSize.getText().toString(), status.getText().toString(), mEmployeeHashMap, mTextViewDayOpenreading.getText().toString(), mTextViewDayClosereading.getText().toString(), mEditTxtRemark.getText().toString(), mTextView.getText().toString());
            // sending to Firebase
            mFirebaseDatabaseReference.child("LOOMS").child(mEditTxtLoom.getText().toString()).setValue(model);
            // UI
            hideProgressDialog();
            Snackbar.make(v, "Data sent! Loom Updated", Snackbar.LENGTH_LONG).show();
            mFormLayout.setVisibility(View.GONE);
        } else {
            // UI
            hideProgressDialog();
            Toast.makeText(this, "Something went wrong. Try Again.", Toast.LENGTH_SHORT).show();
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

        if (mRgMess.getCheckedRadioButtonId() == -1 || mRgStatus.getCheckedRadioButtonId() == -1) {
            validate = false;
            Toast.makeText(this, "Select Mess Size or Status", Toast.LENGTH_SHORT).show();
        }
        if (mEmployeeHashMap.isEmpty()) {
            validate = false;

            Toast.makeText(this, "Add at least One employee", Toast.LENGTH_SHORT).show();
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

        if (model.getLastReading() == null)
            mDbLastReading.setText("N/A");
        else
            mDbLastReading.setText(model.getLastReading());

        mTxtLastReadingDate.setText(model.getDate());
        mTxtLastReadingShift.setText(model.getShift());
        mShiftdate.setText(model.getDate());
        mEmployeeHashMap.putAll(model.getEmplist());
        updateListView();
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
        // Setting the date to next day
        setShiftFromCurrentTime();

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
        // Update UI
        mDbLastReading.setText("N/A");
        mTxtLastReadingDate.setText("N/A");
        mTxtLastReadingShift.setText("N/A");
        mShiftdate.setText(null);
        mEmployeeHashMap.clear();
        updateListView();
        mTextViewDayClosereading.setText(null);
        mTextViewDayOpenreading.setText(null);
        mEditTxtRemark.setText(null);
        mTextView.setText(null);
        mRgStatus.clearCheck();
        mRgMess.clearCheck();
        mRgShiftDetails.clearCheck();
    }

    @OnClick(R.id.btn_add_emp)
    public void onAddEmployeeClicked() {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.card_view_emp_details_dialog);
        d.setTitle("Enter Employee Details");
        // Declaring Views
        final EditText mTxtEmpCode = (EditText) d.findViewById(R.id.txt_emp_code);
        final EditText mTxtEmpName = (EditText) d.findViewById(R.id.txt_emp_name);
        Button mConfirm = (Button) d.findViewById(R.id.btn_dialog_confirm);
        Button mCancel = (Button) d.findViewById(R.id.btn_dialog_cancel);
        // On Click
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        d.show();
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validate = true;
                if (TextUtils.isEmpty(mTxtEmpCode.getText().toString())) {
                    validate = false;
                    mTxtEmpCode.setError("Enter Employee Code");
                    Toast.makeText(MainActivity.this, "Enter Employee Code", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(mTxtEmpName.getText().toString())) {
                    validate = false;
                    mTxtEmpCode.setError("Enter Employee Name");
                    Toast.makeText(MainActivity.this, "Enter Employee Name", Toast.LENGTH_SHORT).show();
                }
                if (validate) {
                    if (!mEmployeeHashMap.containsKey(mTxtEmpCode.getText().toString())) {
                        mEmployeeHashMap.put("ID" + mTxtEmpCode.getText().toString(), mTxtEmpName.getText().toString());
                        updateListView();
                        d.dismiss();
                    } else
                        Toast.makeText(MainActivity.this, "Employee Code Already Exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateListView() {

        ArrayList<String> items = new ArrayList<>();
        if (mEmployeeHashMap.isEmpty()) {
            items.add("No Data To Display");
        } else {
            for (Map.Entry<String, String> e : mEmployeeHashMap.entrySet())
                items.add(String.format("%-10s %30s", e.getKey(), e.getValue()));
        }
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        mListView.setAdapter(itemsAdapter);
        setListViewHeightBasedOnChildren();
    }

    private void setListViewHeightBasedOnChildren() {
        ListAdapter listAdapter = mListView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, mListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = totalHeight + (mListView.getDividerHeight() * (listAdapter.getCount() - 1));
        mListView.setLayoutParams(params);
    }

    private void setShiftFromCurrentTime() {
        Calendar cal = Calendar.getInstance();
        int hours = cal.get(Calendar.HOUR_OF_DAY);

        if (hours >= 6 || hours <= 16) {
            mRbDay.setChecked(true);
        } else if (hours >= 17 || hours <= 5) {
            mRbNight.setChecked(true);
        }
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
        if (id == R.id.action_about) {
            Toast.makeText(this, "Thanks for using the app!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_logout) {
            // SignOut from Firebase
            FirebaseAuth.getInstance().signOut();
            // Launch the intro activity
            Intent intent = new Intent(this, SplashActivity.class);
            // Closing all the Activities & Add new Flag to start new Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

