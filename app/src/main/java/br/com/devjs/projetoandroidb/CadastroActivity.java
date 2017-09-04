package br.com.devjs.projetoandroidb;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class CadastroActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewHolder mViewHolder = new ViewHolder();
    private Intent mIntent;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;
    private Bitmap thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        this.mViewHolder.editNome = (EditText)findViewById(R.id.edit_nome);
        this.mViewHolder.editPeso = (EditText)findViewById(R.id.edit_peso);
        this.mViewHolder.editAltura = (EditText)findViewById(R.id.edit_altura);
        this.mViewHolder.imageCadastro = (ImageView)findViewById(R.id.image_cadastro);
        this.mViewHolder.buttonSalvar = (Button)findViewById(R.id.button_salvar);
        this.mViewHolder.buttonFoto = (Button)findViewById(R.id.button_foto);

        //adicionar eventos nos botões
        this.mViewHolder.buttonSalvar.setOnClickListener(this);
        this.mViewHolder.buttonFoto.setOnClickListener(this);
        //this.mViewHolder.buttonPesagem.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {

        //Implementação dos botões
        switch (view.getId()){
            case R.id.button_salvar: {
                String nome = this.mViewHolder.editNome.getText().toString();
                String altura = this.mViewHolder.editAltura.getText().toString();
                String peso = this.mViewHolder.editPeso.getText().toString();
                if((!nome.isEmpty() && !nome.equals(0)) && (!altura.isEmpty() && !altura.equals(0)) && (!peso.isEmpty() && !peso.equals(0))){
                    Intent intentCadastro = new Intent();
                    Bundle dados = new Bundle();
                    dados.putString("nome", nome);
                    dados.putString("altura", altura);
                    dados.putString("peso", peso);
                    dados.putString("url", "");

                    intentCadastro.putExtras(dados);

                    setResult(Activity.RESULT_OK, intentCadastro);
                    finish();
                    break;
                }else {
                    Toast.makeText(this, "valores devem ser diferente de 0 ou vazio", Toast.LENGTH_LONG).show();
                }
                break;
            }
           case R.id.button_foto: {
                //Toast.makeText(this, "ação do botão foto", Toast.LENGTH_LONG).show();
               int hasCamera = checkSelfPermission(Manifest.permission.CAMERA);
               if (hasCamera != PackageManager.PERMISSION_GRANTED) {
                   ActivityCompat.requestPermissions(CadastroActivity.this,
                           new String[]{Manifest.permission.CAMERA},
                           REQUEST_CAMERA_PERMISSION);

               } else {
                   mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   startActivityForResult(mIntent, REQUEST_IMAGE_CAPTURE);

               }
                break;
            }
           /**case R.id.button_pesagem: {
                //Toast.makeText(this, "ação do botão pesagem", Toast.LENGTH_LONG).show();
                Snackbar.make(view, "ação do botão pesagem", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            }*/
        }
    }

    /**
     * Padrão ViewHolder
     */
    private static class ViewHolder{
        private EditText editNome;
        private EditText editPeso;
        private EditText editAltura;
        private Button buttonSalvar;
        private Button buttonFoto;
        private ImageView imageCadastro;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, FotoActivity.class);
            intent.putExtras(data.getExtras());
            startActivity(intent);

            thumbnail = (Bitmap) data.getExtras().get("data");
            this.mViewHolder.imageCadastro.setImageBitmap(thumbnail);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(mIntent, REQUEST_IMAGE_CAPTURE);

        } else {
            Toast.makeText(CadastroActivity.this, "Permission denied to read camera ", Toast.LENGTH_SHORT).show();
        }

    }


}
