package com.example.age_in_days_calculator.activities;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.example.age_in_days_calculator.lib.Utils.showInfoDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.age_in_days_calculator.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Snackbar mSnackBar;

    private EditText mEditTypeText;
    private TextView mResult;
    private String result;

    /*@Override
    protected void onStop() {
        super.onStop();
        saveOrDeleteGameInSharedPrefs();
    }

    private void saveOrDeleteGameInSharedPrefs() {
        SharedPreferences defaultSharedPreferences = getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();

        editor.apply();
    }*/


    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        setupToolbar();
        setupFAB();
        setupFields();
    }

    private void setupFields() {
        mEditTypeText = findViewById(R.id.et_text);
        mResult = findViewById(R.id.tv_result);
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick (View v) {
                handleFABClick();
            }
        });
    }

    private void handleFABClick() {
        String data = mEditTypeText.getText().toString();
        if(!data.isEmpty())
        {
            long ageInDays = calculateAgeInDays(data);
            mResult.setText((int) ageInDays);
            mResult.setVisibility(View.VISIBLE);
        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    R.string.error_msg,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private long calculateAgeInDays(String birthdayString)
    {
        long ageInDays = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date birthdayDate = sdf.parse(birthdayString);
            Date currentDate = new Date();
            long diff = currentDate.getTime() - birthdayDate.getTime();
            ageInDays = diff / (1000 * 60 * 60 * 24);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return ageInDays;
    }



    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            showSettings();
            return true;
        } else if (itemId == R.id.action_about) {
            showAbout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSettings() {
//        dismissSnackBarIfShown();
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        settingsLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> settingsLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> restoreOrSetFromPreferences_AllAppAndGameSettings());

    private void restoreOrSetFromPreferences_AllAppAndGameSettings() {
        SharedPreferences sp = getDefaultSharedPreferences(this);
//        mUseAutoSave = sp.getBoolean(mKEY_AUTO_SAVE, true);
//        mGame.setWinnerIsLastPlayerToPick(sp.getBoolean(mKEY_WIN_ON_LAST_PICK, false));
    }

    private void showAbout() {
//        dismissSnackBarIfShown();
        showInfoDialog(MainActivity.this, "About this calculator",
                "Enter your birthday to see how many days old you are!\n" +
                        "\nAndroid app by RB.");
    }

    /*private void dismissSnackBarIfShown() {
        if (mSnackBar.isShown()) {
            mSnackBar.dismiss();
        }
    }*/


}