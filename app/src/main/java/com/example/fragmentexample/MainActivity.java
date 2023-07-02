package com.example.fragmentexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SimpleFragment.OnFragmentInteractionListener {
    static final String STATE_FRAGMENT = "state_of_fragment";

    private Button mButton;
    private boolean isFragmentDisplayed = false;

    private int mRadioButtonChoice = 2;

    public void displayFragment(){
        SimpleFragment simpleFragment = SimpleFragment.newInstance(mRadioButtonChoice);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_container, simpleFragment).addToBackStack(null).commit();
        mButton.setText(R.string.close);
        isFragmentDisplayed = true;
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if (simpleFragment != null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        mButton.setText(R.string.open);
        isFragmentDisplayed = false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
            if(isFragmentDisplayed){
                mButton.setText(R.string.close);
            }
        }

        mButton = findViewById(R.id.open_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFragmentDisplayed){
                    displayFragment();;
                } else {
                    closeFragment();
                }
            }
        });
    }

    @Override
    public void onRadioButtonChoice(int choice) {
        mRadioButtonChoice = choice;
        Toast.makeText(this, "Choice is " + Integer.toString(choice),
                Toast.LENGTH_SHORT).show();
    }
}