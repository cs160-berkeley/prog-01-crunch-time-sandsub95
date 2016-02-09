package cs160.test_converter;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import java.util.HashMap;
import android.widget.ImageView;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import android.view.View;

public class Empty extends FragmentActivity {

    //Units of Exercises
    HashMap<String, String> units;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initializeTables();
        setContentView(R.layout.activity_empty);
        //Create an array of all 13 of these and update accordingly. Change hashmap of numbers to be by index.
        //Iterate through gridlayout? Update text as well based on array?
        final int[] nums = {100, 350, 200, 225, 25, 25, 10, 100, 12, 20, 12, 13, 15};
        final EditText[] exercises = new EditText[13];
        exercises[0] = (EditText)findViewById(R.id.calorie_entry);
        exercises[1] = (EditText)findViewById(R.id.pushups_id);
        exercises[2] = (EditText)findViewById(R.id.situps_id);
        exercises[3] = (EditText)findViewById(R.id.squats_id);
        exercises[4] = (EditText)findViewById(R.id.leglifts_id);
        exercises[5] = (EditText)findViewById(R.id.planks_id);
        exercises[6] = (EditText)findViewById(R.id.jumpingjacks_id);
        exercises[7] = (EditText)findViewById(R.id.pullups_id);
        exercises[8] = (EditText)findViewById(R.id.cycling_id);
        exercises[9] = (EditText)findViewById(R.id.walking_id);
        exercises[10] = (EditText)findViewById(R.id.jogging_id);
        exercises[11] = (EditText)findViewById(R.id.swimming_id);
        exercises[12] = (EditText)findViewById(R.id.stairclimbing_id);

        final EditText weight = (EditText)findViewById(R.id.weight_entry);
        /* For weight dependence, calories burned is roughly proportional to one's weight:
        http://www.health.harvard.edu/diet-and-weight-loss/calories-burned-in-30-minutes-of-leisure-and-routine-activities
         */

        for (int i = 0; i<=12; i+=1) {
            final int index = i;
            final int num_index = nums[i];
            final EditText exercise = exercises[i];
            exercise.setOnEditorActionListener(
                    new EditText.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                    actionId == EditorInfo.IME_ACTION_DONE ||
                                    event.getAction() == KeyEvent.ACTION_DOWN &&
                                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                                String new_exercise_text = exercise.getText().toString();
                                exercise.setText(new_exercise_text, TextView.BufferType.EDITABLE);
                                Integer new_exercise_num = Integer.parseInt(new_exercise_text);
                                Integer curr_weight = Integer.parseInt(weight.getHint().toString());
                                //calorie_entry.setHint(exercise_text);

                                Integer calorie_num;
                                if (index == 0) {
                                    calorie_num = new_exercise_num;
                                } else {
                                    calorie_num = 100 * new_exercise_num * curr_weight / (150 * num_index);
                                }
                                exercises[0].setHint(calorie_num.toString());

                                for (int j=1; j<=12; j+=1) {
                                    Integer numexercise = calorie_num * nums[j] * 150 / (100 * curr_weight);
                                    exercises[j].setHint(numexercise.toString());
                                }

                                exercise.setText("", TextView.BufferType.EDITABLE);
                                return true;
                            }
                            return false;
                        }
                    });
        }

        weight.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            Integer curr_weight = Integer.parseInt(weight.getHint().toString());
                            String new_weight_text = weight.getText().toString();
                            weight.setText(new_weight_text, TextView.BufferType.EDITABLE);
                            Integer new_weight = Integer.parseInt(new_weight_text);

                            /*Calories don't change, but amount of exercise needed to burn same calories does.
                             According to the Harvard Health article linked above, as the weight of a person increases,
                             they are able to burn a proportionally larger amount of calories for the same amount of exercise.
                             Thus, we would expect that if the number of calories is held fixed, an inverse amount of time
                             would be needed to burn the same number of calories for a given weight. Thus, if my weight increases
                             and I still want to burn the same number of calories, I need less time to do it.
                             Roughly, Weight * Time ~ Calories
                            */
                            for (int j = 1; j <= 12; j += 1) {
                                Integer numweight = Integer.parseInt(exercises[j].getHint().toString()) * curr_weight / new_weight;
                                exercises[j].setHint(numweight.toString());
                            }

                            weight.setHint(new_weight_text);
                            weight.setText("", TextView.BufferType.EDITABLE);
                            return true;
                        }
                        return false;
                    }
                });

    }

    private void initializeTables() {
        units = new HashMap<String, String>();
        units.put("Pushups", "Reps");
        units.put("Situps", "Reps");
        units.put("Squats", "Reps");
        units.put("Leglifts", "Mins");
        units.put("Planks", "Mins");
        units.put("Jumping Jacks", "Mins");
        units.put("Pullups", "Reps");
        units.put("Cycling", "Mins");
        units.put("Walking", "Mins");
        units.put("Jogging", "Mins");
        units.put("Swimming", "Mins");
        units.put("Stairclimbing", "Mins");
    }
}
