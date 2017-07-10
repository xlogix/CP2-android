package xyz.fnplus.clientproject.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.fnplus.clientproject.R;
import xyz.fnplus.clientproject.helpers.RetrofitBuilder;
import xyz.fnplus.clientproject.helpers.RetrofitInterface;
import xyz.fnplus.clientproject.helpers.SQLiteHandler;
import xyz.fnplus.clientproject.helpers.SessionManager;
import xyz.fnplus.clientproject.models.DataModel;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = MainActivity.class.getSimpleName();

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

  @BindView(R.id.btn_details_submit)
  AppCompatButton mBtnDetailsSubmit;

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

  @BindView(R.id.btn_details_reset)
  AppCompatButton mBtnDetailsReset;

  private String LastReading;
  private String OpenReading;
  private SQLiteHandler db;
  private SessionManager session;
  private ProgressDialog mProgressDialog;
  private HashMap<String, DataModel.EmpRecord> mEmployeeHashMap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // Bind View
    ButterKnife.bind(this);
    // Toolbar
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // SqLite database handler
    db = new SQLiteHandler(getApplicationContext());
    // Session manager
    session = new SessionManager(getApplicationContext());

    if (!session.isLoggedIn()) {
      logoutUser();
    }
    // Set visibility of the form as NULL
    mFormLayout.setVisibility(View.GONE);
    // New Employee
    mEmployeeHashMap = new HashMap<>();

    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final String selectedItem = (String) mListView.getItemAtPosition(i);
        final Dialog d = new Dialog(MainActivity.this);
        d.setContentView(R.layout.card_view_emp_details_dialog);
        d.setTitle("Enter employee details");
        final EditText mTxtEmpCode = (EditText) d.findViewById(R.id.txt_emp_code);
        final EditText mTxtEmpName = (EditText) d.findViewById(R.id.txt_emp_name);

        Button mConfirm = (Button) d.findViewById(R.id.btn_dialog_confirm);
        final EditText mTextViewDayOpenreading =
            (EditText) d.findViewById(R.id.textView_day_Openreading);
        final EditText mTextViewDayClosereading =
            (EditText) d.findViewById(R.id.textView_day_Closereading);
        final EditText mEditTxtRemark = (EditText) d.findViewById(R.id.edit_txt_remark);

        if (!selectedItem.equals("No Data To Display- ")) {
          DataModel.EmpRecord record =
              mEmployeeHashMap.get(selectedItem.substring(0, selectedItem.indexOf(" ")));
          mTxtEmpCode.setText(selectedItem.substring(0, selectedItem.indexOf(" ")).substring(2));
          mEditTxtRemark.setText(record.getRemarks());
          mTxtEmpName.setText(record.getEmpName());
          mTxtEmpCode.setEnabled(false);
          mTextViewDayClosereading.setText(record.getCloseReading());
          mTextViewDayOpenreading.setText(record.getOpenReading());
        }

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

              mTxtEmpCode.setError("Enter employee name");
              Toast.makeText(MainActivity.this, "Enter employee name", Toast.LENGTH_SHORT).show();
            }
            if (!TextUtils.isEmpty(mTextViewDayOpenreading.getText().toString())
                && !TextUtils.isEmpty(mTextViewDayClosereading.getText().toString())) {
              if (Integer.parseInt(mTextViewDayOpenreading.getText().toString()) > Integer.parseInt(
                  mTextViewDayClosereading.getText().toString())) {
                validate = false;
                mTxtEmpCode.setError("Close Reading Should Be Greater than Opening reading");
                Toast.makeText(MainActivity.this, "Enter Employee Name", Toast.LENGTH_SHORT).show();
              }
            }
            if (validate) {
              DataModel.EmpRecord record =
                  new DataModel.EmpRecord("ID" + mTxtEmpCode.getText().toString(),
                      mTxtEmpName.getText().toString(),
                      mTextViewDayOpenreading.getText().toString(),
                      mTextViewDayClosereading.getText().toString(),
                      mEditTxtRemark.getText().toString());
              mEmployeeHashMap.put("ID" + mTxtEmpCode.getText().toString(), record);
              setLastReading(mTextViewDayOpenreading.getText().toString(),
                  mTextViewDayClosereading.getText().toString());
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
    try {
      InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    } catch (Exception e) {
      Log.d(TAG, "Unable to hide the soft keyboard");
    }
    // Logic
    if (TextUtils.isEmpty(mEditTxtLoom.getText().toString())) {
      mEditTxtLoom.setError("Enter Loom No");
      // UI
      hideProgressDialog();
      Toast.makeText(this, "Enter Loom No", Toast.LENGTH_SHORT).show();
    } else {
      // UI
      hideProgressDialog();
      RetrofitInterface client = RetrofitBuilder.createService(RetrofitInterface.class);
      Call<DataModel> call = client.getLoomInfo(mEditTxtLoom.getText().toString());
      call.enqueue(new Callback<DataModel>() {
        @Override
        public void onResponse(Call<DataModel> call, Response<DataModel> response) {
          if (response.isSuccessful()) {

            DataModel dm = response.body();

            if (response.body() == null) {
              // Clear previous data
              clearDataInView();

              Calendar newCalendar = Calendar.getInstance();
              // Get current date
              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.UK);
              mShiftdate.setText(sdf.format(newCalendar.getTime()));
              // Form
              mFormLayout.setVisibility(View.VISIBLE);
              mBtnDetailsReset.setEnabled(false);
              setShiftFromCurrentTime();
              Snackbar.make(view, "No Loom found. Fill the details for that Loom",
                  Snackbar.LENGTH_LONG)
                  .setAction("Action", null).show();
            } else {
              // Clear previous data
              clearDataInView();

              Calendar newCalendar = Calendar.getInstance();
              // Get current date
              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.UK);
              mShiftdate.setText(sdf.format(newCalendar.getTime()));
              // Form
              mFormLayout.setVisibility(View.VISIBLE);
              Snackbar.make(view, "Last record of the Loom fetched!", Snackbar.LENGTH_LONG)
                  .setAction("Action", null).show();
              setDataToView(dm);

              mBtnDetailsReset.setEnabled(true);
            }
          } else {
            Snackbar.make(view, "Server Response Error" + response.errorBody(),
                Snackbar.LENGTH_LONG)
                .show();
          }
        }

        @Override public void onFailure(Call<DataModel> call, Throwable t) {
          Snackbar.make(view, "Server Response Failure" + t.getMessage(), Snackbar.LENGTH_LONG)
              .show();
        }
      });
    }
  }

  @OnClick(R.id.btn_details_submit)
  public void onMBtnDetailsSubmitClicked(final View v) {
    // show changes in UI
    showProgressDialog();
    // Logic
    if (validateDetails()) {
      // Declaring Buttons
      RadioButton shift = (RadioButton) findViewById(mRgShiftDetails.getCheckedRadioButtonId());
      RadioButton messSize = (RadioButton) findViewById(mRgMess.getCheckedRadioButtonId());
      RadioButton status = (RadioButton) findViewById(mRgStatus.getCheckedRadioButtonId());

      RetrofitInterface client = RetrofitBuilder.createService(RetrofitInterface.class);
      Gson gson = new Gson();

      DataModel model = new DataModel(mEditTxtLoom.getText().toString(), LastReading,
          mShiftdate.getText().toString(), shift.getText().toString(),
          messSize.getText().toString(), status.getText().toString(), mEmployeeHashMap, LastReading,
          LastReading, mTextView.getText().toString());
      String json = gson.toJson(model, DataModel.class);
      Call<Void> call = client.setLoomInfo(json);
      call.enqueue(new Callback<Void>() {
        @Override public void onResponse(Call<Void> call, Response<Void> response) {
          if (response.isSuccessful()) {
            hideProgressDialog();
            Snackbar.make(v, "Data sent! Loom Updated", Snackbar.LENGTH_LONG).show();
            mFormLayout.setVisibility(View.GONE);
          } else {
            hideProgressDialog();
            Snackbar.make(v, "Something went wrong with server. Try Again" + response.errorBody(),
                Snackbar.LENGTH_LONG).show();
          }
        }

        @Override public void onFailure(Call<Void> call, Throwable t) {
          // UI
          hideProgressDialog();
          Snackbar.make(v, "Something went wrong with server. Try Again." + t.getMessage(),
              Snackbar.LENGTH_SHORT).show();
        }
      });
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
      Toast.makeText(this, "Select Mess Size", Toast.LENGTH_SHORT).show();
    }
    if (mRgStatus.getCheckedRadioButtonId() == -1) {
      validate = false;
      Toast.makeText(this, "Select Mess Status", Toast.LENGTH_SHORT).show();
    }
    if (mEmployeeHashMap.isEmpty()) {
      validate = false;

      Toast.makeText(this, "Add at least One employee", Toast.LENGTH_SHORT).show();
    }
    return validate;
  }

  @OnClick(R.id.shiftdate)
  public void onViewClicked() {

    Calendar newCalendar = Calendar.getInstance();

    DatePickerDialog StartTime =
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
          public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.UK);

            mShiftdate.setText(sdf.format(newDate.getTime()));
          }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH));
    StartTime.show();
  }

  void setDataToView(DataModel model) {

    if (model.getLastReading() == null) {
      mDbLastReading.setText("N/A");
    } else {
      mDbLastReading.setText(model.getLastReading());
    }
    LastReading = model.getLastReading();
    OpenReading = model.getOpenReading();
    mTxtLastReadingDate.setText(model.getDate());
    mTxtLastReadingShift.setText(model.getShift());
    mShiftdate.setText(model.getDate());
    if (model.getEmplist() != null && !model.getEmplist().isEmpty()) {
      mEmployeeHashMap.putAll(model.getEmplist());
    }
    updateListView();

    mTextView.setText(model.getQuality());
    if (model.getShift() != null) {
      switch (model.getShift()) {
        case "Day":
          mRbDay.setChecked(true);
          break;
        case "Night":
          mRbNight.setChecked(true);
          break;
      }
    }
    // Setting the date to next day
    setShiftFromCurrentTime();
    if (model.getMessSize() != null) {
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
    }
    if (model.getStatus() != null)

    {
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
  }

  void clearDataInView() {
    // Update UI
    LastReading = "";
    OpenReading = "";
    mDbLastReading.setText("N/A");
    mTxtLastReadingDate.setText("N/A");
    mTxtLastReadingShift.setText("N/A");
    mShiftdate.setText(null);
    mEmployeeHashMap.clear();
    updateListView();
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
    final EditText mTextViewDayOpenReading =
        (EditText) d.findViewById(R.id.textView_day_Openreading);
    final EditText mTextViewDayCloseReading =
        (EditText) d.findViewById(R.id.textView_day_Closereading);
    final EditText mEditTxtRemark = (EditText) d.findViewById(R.id.edit_txt_remark);
    mTextViewDayOpenReading.setText(LastReading.substring(0, LastReading.indexOf(',')));
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
        if (!TextUtils.isEmpty(mTextViewDayOpenReading.getText().toString()) && !TextUtils.isEmpty(
            mTextViewDayCloseReading.getText().toString())) {
          if (Integer.parseInt(mTextViewDayOpenReading.getText().toString()) > Integer.parseInt(
              mTextViewDayCloseReading.getText().toString())) {
            validate = false;
            mTextViewDayCloseReading.setError(
                "Close Reading Should Be Greater than Opening reading");
            Toast.makeText(MainActivity.this, "Enter Correct Close reading", Toast.LENGTH_SHORT)
                .show();
          }
        }

        if (validate) {
          if (!mEmployeeHashMap.containsKey("ID" + mTxtEmpCode.getText().toString())) {
            DataModel.EmpRecord record =
                new DataModel.EmpRecord("ID" + mTxtEmpCode.getText().toString(),
                    mTxtEmpName.getText().toString(), mTextViewDayOpenReading.getText().toString(),
                    mTextViewDayCloseReading.getText().toString(),
                    mEditTxtRemark.getText().toString());
            mEmployeeHashMap.put("ID" + mTxtEmpCode.getText().toString(), record);
            setLastReading(mTextViewDayOpenReading.getText().toString(),
                mTextViewDayCloseReading.getText().toString());
            updateListView();
            d.dismiss();
          } else {
            Toast.makeText(MainActivity.this, "Employee Code Already Exists", Toast.LENGTH_SHORT)
                .show();
          }
        }
      }
    });
  }

  private void updateListView() {

    final ArrayList<String> items = new ArrayList<>();
    if (mEmployeeHashMap.isEmpty()) {
      items.add("No Data To Display- ");
    } else {
      for (Map.Entry<String, DataModel.EmpRecord> e : mEmployeeHashMap.entrySet())
        items.add(e.getValue().toString());
    }
    ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1,
            items) {
          @NonNull
          public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
            TextView text2 = (TextView) view.findViewById(android.R.id.text2);

            String[] lines = items.get(position).split("-");
            Log.e(TAG, "getView: " + lines[0] + items.get(position));
            text1.setText(lines[0]);
            text2.setText(lines[1]);
            return view;
          }
        };
    mListView.setAdapter(adapter);
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

  private void setLastReading(String open, String close) {
    Calendar cal = Calendar.getInstance();
    int hours = cal.get(Calendar.HOUR_OF_DAY);
    boolean shift = false;
    if (hours >= 6 || hours <= 16) {
      shift = true;
    } else if (hours >= 17 || hours <= 5) {
      shift = false;
    }
    if (!TextUtils.isEmpty(open) && !TextUtils.isEmpty(close)) {
      LastReading = close + "," + (shift ? "Day" : "Night");
    } else if (!TextUtils.isEmpty(open) && TextUtils.isEmpty(close)) {
      OpenReading = open;
      LastReading = open + "," + (shift ? "Day" : "Night");
    } else if (TextUtils.isEmpty(open) && !TextUtils.isEmpty(close)) {
      LastReading = close + "," + (shift ? "Day" : "Night");
    } else {
      LastReading = "";
    }
  }

  @OnClick(R.id.btn_details_reset)
  public void onResetClicked(final View v) {
    if (mEmployeeHashMap == null || mEmployeeHashMap.isEmpty()) {
      Snackbar.make(v, "You can't reset the loom without emp data", Toast.LENGTH_SHORT).show();
    } else {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DATE, 1);
      SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy", Locale.UK);
      mEmployeeHashMap.clear();
      RetrofitInterface client = RetrofitBuilder.createService(RetrofitInterface.class);
      Gson gson = new Gson();

      RadioButton shift = (RadioButton) findViewById(mRgShiftDetails.getCheckedRadioButtonId());
      RadioButton messSize = (RadioButton) findViewById(mRgMess.getCheckedRadioButtonId());
      RadioButton status = (RadioButton) findViewById(mRgStatus.getCheckedRadioButtonId());
      DataModel model = new DataModel(mEditTxtLoom.getText().toString(), LastReading,
          sdf.format(calendar), shift.getText().toString(),
          messSize.getText().toString(), status.getText().toString(), mEmployeeHashMap, LastReading,
          LastReading, mTextView.getText().toString());
      String json = gson.toJson(model, DataModel.class);
      Call<Void> call = client.setLoomInfo(json);
      call.enqueue(new Callback<Void>() {
        @Override public void onResponse(Call<Void> call, Response<Void> response) {
          if (response.isSuccessful()) {
            hideProgressDialog();
            Snackbar.make(v, "Data sent! Loom Reset", Snackbar.LENGTH_LONG).show();
            mFormLayout.setVisibility(View.GONE);
          } else {
            hideProgressDialog();
            Snackbar.make(v, "Something went wrong with server. Try Again" + response.errorBody(),
                Snackbar.LENGTH_LONG).show();
          }
        }

        @Override public void onFailure(Call<Void> call, Throwable t) {
          // UI
          hideProgressDialog();
          Snackbar.make(v, "Something went wrong with server. Try Again." + t.getMessage(),
              Snackbar.LENGTH_SHORT).show();
        }
      });
      clearDataInView();
      mFormLayout.setVisibility(View.GONE);
      Snackbar.make(v, "Loom Data Reset Successfully!", Snackbar.LENGTH_LONG).show();
    }
  }

  /**
   * Logging out the user. Will set 'isLoggedIn' flag to false in shared
   * preferences. Clears user data from sqlite users table
   */
  private void logoutUser() {
    // Set Login to false
    session.setLogin(false);
    // Delete Database
    db.deleteUsers();
    // Launch the intro activity
    Intent intent = new Intent(this, SplashActivity.class);
    // Closing all the Activities & Add new Flag to start new Activity
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
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
      // Call to logout function
      logoutUser();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}

