package mvp.com.zmvp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mvp.com.zmvp.R;
import mvp.com.zmvp.widgets.BezierCurve;

public class EffectDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effect_demo);


        BezierCurve bezierCurve = (BezierCurve) findViewById(R.id.bezier_curbve);
    }
}
