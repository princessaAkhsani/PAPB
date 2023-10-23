package prncsa.mobile.slotgacor;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView msg;
    private ImageView img1, img2, img3;
    private Wheel wheel1, wheel2, wheel3;
    private Button btn;
    private boolean isStarted;

    public static final Random RANDOM = new Random();
    private static final long FRAME_DURATION = 1000;
    private static final long START_IN = 1000;

    private Handler handler;
    private boolean isWheel1Stopped, isWheel2Stopped, isWheel3Stopped;

    public static long randomLong(long lower, long upper) {
        return lower + (long) (RANDOM.nextDouble() * (upper - lower));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        btn = findViewById(R.id.btn);
        msg = findViewById(R.id.msg);

        handler = new Handler();

        isWheel1Stopped = false;
        isWheel2Stopped = false;
        isWheel3Stopped = false;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStarted) {
                    if (!isWheel1Stopped) {
                        isWheel1Stopped = true;
                        wheel1.stopWheel();
                    } else if (!isWheel2Stopped) {
                        isWheel2Stopped = true;
                        wheel2.stopWheel();
                    } else if (!isWheel3Stopped) {
                        isWheel3Stopped = true;
                        wheel3.stopWheel();
                        determineResult();
                    } else {
                        resetGame();
                        startWheels(); // Mulai permainan lagi
                    }
                } else {
                    startWheels();
                }
            }
        });
    }

    private void startWheels() {
        isWheel1Stopped = false;
        isWheel2Stopped = false;
        isWheel3Stopped = false;

        wheel1 = new Wheel(new Wheel.WheelListener() {
            @Override
            public void newImage(final int img) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        img1.setImageResource(img);
                    }
                });
            }
        }, FRAME_DURATION, randomLong(0, START_IN));

        wheel2 = new Wheel(new Wheel.WheelListener() {
            @Override
            public void newImage(final int img) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        img2.setImageResource(img);
                    }
                });
            }
        }, FRAME_DURATION, randomLong(150, START_IN));

        wheel3 = new Wheel(new Wheel.WheelListener() {
            @Override
            public void newImage(final int img) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        img3.setImageResource(img);
                    }
                });
            }
        }, FRAME_DURATION, randomLong(150, START_IN));

        wheel1.start();
        wheel2.start();
        wheel3.start();

        btn.setText("Stop");
        msg.setText("");
        isStarted = true;
    }

    private void determineResult() {
        if (wheel1.getCurrentIndex()== wheel2.getCurrentIndex() && wheel3.getCurrentIndex() == wheel3.getCurrentIndex()) {
            msg.setText("JACKPOT");
        } else {
            msg.setText("You Lose");
        }
    }



    private void resetGame() {
        btn.setText("Start");
        msg.setText("");
        isStarted = false;
    }
}
