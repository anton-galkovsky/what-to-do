package galanton.whattodo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class AdjustActionParamsActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_action_params_dialog);
        findViewById(R.id.button_cancel).setOnClickListener(this);
        findViewById(R.id.button_delete).setOnClickListener(this);
        findViewById(R.id.button_ok).setOnClickListener(this);

        EditText edit_color = findViewById(R.id.edit_color);
        edit_color.addTextChangedListener(this);
        EditText edit_counter = findViewById(R.id.edit_counter);

        Intent intent = getIntent();
        findViewById(R.id.image_color).setBackgroundColor(intent.getIntExtra("color", 0));
        edit_counter.setText("" + intent.getLongExtra("counter", 0));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (v.getId() == R.id.button_cancel) {
            setResult(0, intent);
        } else if (v.getId() == R.id.button_delete) {
            setResult(1, intent);
        } else if (v.getId() == R.id.button_ok) {
            int color = Color.BLACK;
            long counter = 0;
            boolean newColor = false;
            try {
                counter = Long.valueOf("" + ((EditText) findViewById(R.id.edit_counter)).getText());   // do not swap lines
                String colorStr = "#" + ((EditText) findViewById(R.id.edit_color)).getText();
                if (colorStr.length() == 7) {
                    color = Color.parseColor(colorStr);
                    newColor = true;
                }
            } catch (Exception ignored) {
            }
            intent.putExtra("color", color);
            intent.putExtra("newColor", newColor);
            intent.putExtra("counter", counter);

            setResult(2, intent);
        }
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 6) {
            try {
                findViewById(R.id.image_color).setBackgroundColor(Color.parseColor("#" + s));
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
