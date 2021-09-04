package com.example.nisa.reflexgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nisa.reflexgame.HAdapter.HAnswerAdapter;
import com.example.nisa.reflexgame.HAdapter.HSuggestAdapter;
import com.example.nisa.reflexgame.HCommon.HCommon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class HGameActivity extends AppCompatActivity {

    public List<String> suggestSource = new ArrayList<>();

    public HAnswerAdapter answerAdapter;
    public HSuggestAdapter suggestAdapter;

    public Button btnSubmit;

    public GridView gridViewAnswer,gridViewSuggest;

    public ImageView imgViewQuestion;

    int[] image_list={

            R.drawable.cheese
            /*R.drawable.deviantart,
            R.drawable.digg,
            R.drawable.dropbox,
            R.drawable.evernote,
            R.drawable.facebook,
            R.drawable.flickr,
            R.drawable.google,
            R.drawable.googleplus,
            R.drawable.hyves,
            R.drawable.instagram,
            R.drawable.linkedin,
            R.drawable.myspace,
            R.drawable.picasa,
            R.drawable.pinterest,
            R.drawable.reddit,
            R.drawable.rss,
            R.drawable.skype,
            R.drawable.soundcloud,
            R.drawable.stumbleupon,
            R.drawable.twitter,
            R.drawable.vimeo,
            R.drawable.wordpress,
            R.drawable.yahoo,
            R.drawable.youtube*/
    };

    public char[] answer;

    String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hayvanlar);

        //Init View
        initView();


    }

    private void initView() {
        gridViewAnswer = (GridView)findViewById(R.id.gridViewAnswer);
        gridViewSuggest = (GridView)findViewById(R.id.gridViewSuggest);

        imgViewQuestion = (ImageView)findViewById(R.id.imgLogo);

        //Add SetupList Here
        setupList();

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result="";
                for(int i = 0; i< HCommon.user_submit_answer.length; i++)
                    result+=String.valueOf(HCommon.user_submit_answer[i]);
                if(result.equals(correct_answer))
                {
                    Toast.makeText(getApplicationContext(),"Bitti ! Bu bir "+result,Toast.LENGTH_SHORT).show();

                    //Reset
                    HCommon.count = 0;
                    HCommon.user_submit_answer = new char[correct_answer.length()];

                    //Set Adapter
                    HAnswerAdapter answerAdapter = new HAnswerAdapter(setupNullList(),getApplicationContext());
                    gridViewAnswer.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();

                    HSuggestAdapter suggestAdapter = new HSuggestAdapter(suggestSource,getApplicationContext(),HGameActivity.this);
                    gridViewSuggest.setAdapter(suggestAdapter);
                    suggestAdapter.notifyDataSetChanged();

                    setupList();
                }
                else
                {
                    Toast.makeText(HGameActivity.this, "Yanlış Cevap !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupList() {
        //Random logo
        Random random = new Random();
        int imageSelected = image_list[random.nextInt(image_list.length)];
        imgViewQuestion.setImageResource(imageSelected);

        correct_answer = getResources().getResourceName(imageSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/")+1);

        answer = correct_answer.toCharArray();

        HCommon.user_submit_answer = new char[answer.length];

        //Add Answer character to List
        suggestSource.clear();
        for(char item:answer)
        {
            //Add logo name to list
            suggestSource.add(String.valueOf(item));
        }

        //Random add some character to list
        for(int i = answer.length;i<answer.length*2;i++)
            suggestSource.add(HCommon.alphabet_character[random.nextInt(HCommon.alphabet_character.length)]);

        //Sort random
        Collections.shuffle(suggestSource);

        //Set for GridView
        answerAdapter = new HAnswerAdapter(setupNullList(),this);
        suggestAdapter = new HSuggestAdapter(suggestSource,this,this);

        answerAdapter.notifyDataSetChanged();
        suggestAdapter.notifyDataSetChanged();

        gridViewSuggest.setAdapter(suggestAdapter);
        gridViewAnswer.setAdapter(answerAdapter);


    }

    private char[] setupNullList() {
            char result[] = new char[answer.length];
            for(int i=0;i<answer.length;i++)
                result[i]=' ';
        return result;
    }
}
