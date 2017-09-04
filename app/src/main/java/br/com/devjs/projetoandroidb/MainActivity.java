package br.com.devjs.projetoandroidb;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CODIGO_CAMERA = 567;
    private List<Pessoas> listPessoa = new ArrayList<>();
    private static final int IDENTIFICADOR = 1;
    private ImageView imageView;
    private ListView listView;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Testando
         */
        imageView = (ImageView) findViewById(R.id.image_pessoa);
        listView = (ListView) findViewById(R.id.list_pessoas);
        addList("Antonio Silva", 1.70, 86.0, "", getInfo(1.70, 86.0));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_CAMERA && requestCode == Activity.RESULT_OK){
            ImageView foto = (ImageView) findViewById(R.id.image_pessoa);
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            foto.setImageBitmap(bitmapReduzido);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
        }



        if (requestCode == IDENTIFICADOR && resultCode == Activity.RESULT_OK) {
            //Recebe os dados
            String nome = data.getStringExtra("nome");
            Double altura = Double.valueOf(data.getStringExtra("altura"));
            Double peso = Double.valueOf(data.getStringExtra("peso"));
            String url = data.getStringExtra("url");

            //Envia pra lista
            String imc = getInfo(altura, peso);
            addList(nome, altura, peso, url, imc);

        }


    }

    private void addList(String string, Double d1, Double d2, String url, String imc) {
        listPessoa.add(new Pessoas(string, d1, d2, url, imc));
        PessoaAdapter pessoaAdapter = new PessoaAdapter(getApplicationContext(), R.layout.list_main, listPessoa);
        listView.setAdapter(pessoaAdapter);
    }

    private String getInfo(Double altura, Double peso) {

        Double mResultadoIMC = peso / (altura * altura);

        if (mResultadoIMC < 16) {
            return getString(R.string.peso_muito_grave);

        } else if (mResultadoIMC >= 16 && mResultadoIMC < 17) {
            return getString(R.string.peso_grave);

        } else if (mResultadoIMC >= 17 && mResultadoIMC < 18.5) {
            return getString(R.string.peso_baixo);

        } else if (mResultadoIMC >= 18.5 && mResultadoIMC < 25) {
            return getString(R.string.peso_ideal);

        } else if (mResultadoIMC >= 25 && mResultadoIMC < 30) {
            return getString(R.string.sobrepeso);

        } else if (mResultadoIMC >= 30 && mResultadoIMC < 35) {
            return getString(R.string.obesidade_1);

        } else if (mResultadoIMC >= 35 && mResultadoIMC < 40) {
            return getString(R.string.obesidade_2);

        } else {
            return getString(R.string.obesidade_3);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab: {
                Intent intentCadastroActivity = new Intent(MainActivity.this, CadastroActivity.class);
                startActivityForResult(intentCadastroActivity, IDENTIFICADOR);
                Snackbar.make(view, R.string.tela_cadastro, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_pesagem: {
                agendarAlarme();
                break;
            }
            case R.id.action_fotos: {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis()+ ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void agendarAlarme() {
        TimePickerDialog.OnTimeSetListener tratador =
                new TimePickerDialog.OnTimeSetListener() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Intent it = new Intent(MainActivity.this, AlarmeReceiver.class);

                        PendingIntent pit = PendingIntent.getBroadcast(
                                MainActivity.this, 0, it, 0);

                        AlarmManager alarmManager = (AlarmManager)
                                getSystemService(Context.ALARM_SERVICE);

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);

                        alarmManager.set(
                                AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis(),
                                pit);
                    }
                };
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                tratador,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
        dialog.show();
    }

}
