package com.example.alic.modernartui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;


public class MainActivity extends Activity {

    private static final String URL = "http://www.moma.org";
    private static final String TAG = "ModernComplaint";
    private static final int[] COLORS = new int[]
            {Color.RED, Color.YELLOW, Color.GREEN, Color.WHITE, Color.CYAN};

    private DialogFragment mDialog;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeekBar = (SeekBar) findViewById(R.id.SeekBar);

        //initialize colors
        setColor(COLORS);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int mSeekBarValue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSeekBarValue = progress;
                Log.i(TAG,"Changing seekbar's progress to " + mSeekBarValue);
                // TODO change blocks' color
                int[] colors = new int[5];
                System.arraycopy(COLORS, 0, colors, 0, COLORS.length);
                for (int i = 0; i < 5; i ++) {
                    colors[i] += 10 * mSeekBarValue;
                }
                setColor(colors);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i(TAG,"Started tracking seekbar");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i(TAG,"Covered: " + mSeekBarValue + "/" + seekBar.getMax());
                Log.i(TAG,"Stopped tracking seekbar");
            }
        });
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.option_more_info) {
            showDialogFragment();
        }

        return super.onOptionsItemSelected(item);
    }

    // show desired dialog
    void showDialogFragment() {
        // create a new AlertDialogFragment
        mDialog = AlertDialogFragment.newInstance();

        // show AlertDialogFragment
        mDialog.show(getFragmentManager(), "more info");
    }

    void setColor(int[] colors) {
        for (int i = 0; i < 5; i++) {
            // find the view
            int resId = getResources().getIdentifier("rectView" + i, "id", getPackageName());
            View view = (View) findViewById(resId);
            // get the view's background
            GradientDrawable bgShape = (GradientDrawable) view.getBackground();
            bgShape.setColor(colors[i]);
        }
    }


    // create the AlertDialog
    public static class AlertDialogFragment extends DialogFragment {

        public static AlertDialogFragment newInstance() {
            return new AlertDialogFragment();
        }

        // build alertDialog using AlertDialog.Builder
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.dialog_message)

                    .setCancelable(true)

                    // set up visit button
                    .setPositiveButton(R.string.pos_button,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // go to www.moma.org
                                    Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                                    startActivity(urlIntent);
                                }
                            })

                    // set up cancel button
                    .setNegativeButton(R.string.neg_button,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dismiss();
                                }
                            })

                    .create();

        }
    }
}
