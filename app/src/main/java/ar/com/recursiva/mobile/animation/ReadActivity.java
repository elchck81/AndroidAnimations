package ar.com.recursiva.mobile.animation;

import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReadActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private int linecount=0;
    private int lineShow=1;
    private int lineSkip=1;
    private int linesXmin=300;
    private TextView[] lineas;
    private TextView speed,numberShowLines,numberSkipLines;
    Button slower,faster,lessShowLines,lessSkipLines,moreShowLines,moreSkipLines,restart;
    Boolean start;
    ToggleButton startButton;
    final ArrayList<String> texto = new ArrayList<>();
    private int charsPerLine = 10;


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
      /* do what you need to do */
            changeLines();
      /* and here comes the "trick" */
            if(start && linecount<texto.size())
            handler.postDelayed(this, 60000/linesXmin);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        readTextFile();

        lineas= new TextView[4];
        lineas[0] = (TextView) findViewById(R.id.linea1);
        lineas[1] = (TextView) findViewById(R.id.linea2);
        lineas[2] = (TextView) findViewById(R.id.linea3);
        lineas[3] = (TextView) findViewById(R.id.linea4);

        speed = (TextView) findViewById(R.id.speed);
        speed.setText(Integer.toString(linesXmin)+" lines/min");

        slower = (Button)findViewById(R.id.slower);
        slower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linesXmin>150){
                    linesXmin-=50;
                    speed.setText(Integer.toString(linesXmin)+" lines/min");
                }
            }
        });
        faster = (Button)findViewById(R.id.faster);
        faster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linesXmin<1000){
                    linesXmin+=50;
                    speed.setText(Integer.toString(linesXmin)+" lines/min");
                }
            }
        });

        numberSkipLines = (TextView) findViewById(R.id.numberSkipLines);
        numberSkipLines.setText(Integer.toString(lineSkip));

        lessSkipLines = (Button)findViewById(R.id.lessSkipLines);
        lessSkipLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lineSkip>1){
                    lineSkip-=1;
                    numberSkipLines.setText(Integer.toString(lineSkip));
                }
            }
        });
        moreSkipLines = (Button)findViewById(R.id.moreSkipLines);
        moreSkipLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lineSkip<lineShow){
                    lineSkip+=1;
                    numberSkipLines.setText(Integer.toString(lineSkip));
                }
            }
        });

        numberShowLines = (TextView) findViewById(R.id.numberShowLines);
        numberShowLines.setText(Integer.toString(lineShow));

        lessShowLines = (Button)findViewById(R.id.lessShowLines);
        lessShowLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lineShow>1){
                    lineShow-=1;
                    numberShowLines.setText(Integer.toString(lineShow));
                }
            }
        });

        moreShowLines = (Button)findViewById(R.id.moreShowLines);
        moreShowLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lineShow<4){
                    lineShow+=1;
                    numberShowLines.setText(Integer.toString(lineShow));
                }
            }
        });

        startButton = (ToggleButton) findViewById(R.id.toggleButton) ;
        startButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                start = b;
                if(start){
                    handler.postDelayed(runnable, 60000/linesXmin);
                }
            }
        });

        restart = (Button)findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0;i<lineas.length;i++){
                    lineas[i].setText("");
                }
                linecount=0;
            }
        });


    }

    public void changeLines(){
        for(int i = 0;i<lineas.length;i++){
            if(i<lineShow && texto.size()<linecount)
                lineas[i].setText(texto.get(linecount+i));
            else
                lineas[i].setText("");
        }
        linecount+=lineSkip;
    }

    public void readTextFile(){
        texto.clear();
        File sdcard = Environment.getExternalStorageDirectory();

//Get the text file
        //File file = new File(sdcard,"file.txt");


        InputStream inputStream = getApplicationContext().getResources().openRawResource(R.raw.file);

        InputStreamReader inputreader = new InputStreamReader(inputStream);


        try {
            //FileReader inputreader= new FileReader(file);
            BufferedReader br = new BufferedReader(inputreader);
            String line;
            String lineToArrayList,aux;

            while ((line = br.readLine()) != null) {
                lineas[0].setText(line);
                //break;
                StringTokenizer st = new StringTokenizer(line);
                lineToArrayList = st.nextToken(" ");
                while (st.hasMoreElements()){
                    aux =  st.nextToken();
                    if(aux.length()+lineToArrayList.length()<=charsPerLine){
                        lineToArrayList+= " "+aux;
                    }else{
                        texto.add(lineToArrayList);
                        lineToArrayList = aux;
                    }
                }
                texto.add(lineToArrayList);
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            e.printStackTrace();
        }

    }
}
