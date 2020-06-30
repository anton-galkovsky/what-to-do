package galanton.whattodo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AdjustActionParamsActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private int oldColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adjust_action_params_dialog);
        findViewById(R.id.button_cancel).setOnClickListener(this);
        findViewById(R.id.button_delete).setOnClickListener(this);
        findViewById(R.id.button_ok).setOnClickListener(this);

        ((EditText) findViewById(R.id.edit_color)).addTextChangedListener(this);

        Intent intent = getIntent();
        oldColor = intent.getIntExtra("color", 0);
        findViewById(R.id.image_color).setBackgroundColor(oldColor);
        ((TextView) findViewById(R.id.text_counter)).setText("" + intent.getLongExtra("counter", 0));
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
            boolean newColor = false;
            try {
                String colorStr = "#" + ((EditText) findViewById(R.id.edit_color)).getText();
                if (colorStr.length() == 7) {
                    color = Color.parseColor(colorStr);
                    newColor = true;
                }
            } catch (Exception ignored) {
            }

            long counterInc = 0;
            try {
                counterInc = Long.valueOf("" + ((EditText) findViewById(R.id.inc_counter)).getText());
            } catch (Exception ignored) {
            }

            if (newColor) {
                intent.putExtra("color", color);
            } else {
                intent.putExtra("color", oldColor);
            }
            intent.putExtra("counter_inc", counterInc);

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
