package galanton.whattodo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class NewActionParamsActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_action_params_dialog);
        findViewById(R.id.button_cancel).setOnClickListener(this);
        findViewById(R.id.button_ok).setOnClickListener(this);

        ((EditText) findViewById(R.id.edit_color)).addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (v.getId() == R.id.button_cancel) {
            setResult(0, intent);
        } else if (v.getId() == R.id.button_ok) {
            int color = Color.BLACK;
            try {
                color = Color.parseColor("#" + ((EditText) findViewById(R.id.edit_color)).getText());
            } catch (Exception ignored) {
            }
            intent.putExtra("color", color);

            int id = ((RadioGroup) findViewById(R.id.radio_group)).getCheckedRadioButtonId();
            if (id == R.id.radio_button_time) {
                intent.putExtra("type", "time");
            } else if (id == R.id.radio_button_click) {
                intent.putExtra("type", "click");
            }
            setResult(1, intent);
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
