package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.flashcards.R;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.helper.Base64Custon;
import com.example.flashcards.model.Baralho;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class CriarBaralhoActivity extends AppCompatActivity {
    private RadioButton rdFcCom;
    private RadioButton rdFcId;
    private RadioButton rdFcIdIng;
    private RadioGroup grupoAlarmes;
    private LinearLayout linearLayoutHora;
    private EditText editTextNome, editTextHora, editTextMinuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_baralho);

        editTextNome = findViewById(R.id.crb_edt_nome_baralho);
        rdFcCom = findViewById(R.id.crb_rdb_fc_com);
        rdFcId = findViewById(R.id.crb_rdb_fc_id);
        rdFcIdIng = findViewById(R.id.crb_rdb_fc_id_ing);
        RadioButton rdAlSem = findViewById(R.id.crb_rdb_al_sem);
        RadioButton rdAlLem = findViewById(R.id.crb_rdb_al_lem);
        RadioButton rdAlAlm = findViewById(R.id.fcrb_rdb_al_alm);
        grupoAlarmes = findViewById(R.id.crb_rgp_alertas);
        linearLayoutHora = findViewById(R.id.crb_lly_hora);
        mostrarHoraAlarme();

    }

    public void mostrarHoraAlarme(){
        /*
        médo ouvinte dos radiosbuttons de alarme
        quando a opção sem alarme é selecionada esconde as aopções de editar as horas para o alarme
        ou lembrete
         */
        grupoAlarmes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.crb_rdb_al_sem){
                    linearLayoutHora.setVisibility(View.INVISIBLE);
                }else if(checkedId == R.id.crb_rdb_al_lem || checkedId == R.id.fcrb_rdb_al_alm){
                    linearLayoutHora.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void crbVoltarTelaPrincipal(View view){
        finish();
        Intent principal = new Intent(CriarBaralhoActivity.this, MainActivity.class);
        startActivity(principal);
    }

    public void criarBaralho(View view){
        String nome = editTextNome.getText().toString();
        if (!nome.isEmpty()){
            Baralho baralho = new Baralho();
            baralho.setNome(nome);
            baralho.setDias(0);
            baralho.setCartas(0);
            baralho.setTextos(0);
            baralho.setPalavrasUnicas(0);
            if (verifiicarNome(nome)) {
                FirebaseAuth autenticacao = ConfiguracaoFirebase.getAuth();
                DatabaseReference firebase = ConfiguracaoFirebase.getDatabase();
                String email = Base64Custon.codificarBase64(autenticacao.getCurrentUser().getEmail());
                SharedPreferences preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("nomeBaralho",nome);

                editor.commit();
                //TODO: Criar método de criar alarme ou lembrete
                if (rdFcCom.isChecked()) {
                    baralho.setTipo("Comum");
                    editor.putString("tipoBaralho","Comum");
                    editor.apply();
                    firebase.child(email).child(nome).setValue(baralho);
                    finish();
                    Intent baralhoComum = new Intent(getApplicationContext(), BaralhoComumActivity.class);
                    startActivity(baralhoComum);
                } else if (rdFcId.isChecked() || rdFcIdIng.isChecked()) {
                    baralho.setTipo("Idiomas");
                    editor.putString("tipoBaralho","Idiomas");
                    editor.apply();
                    if (rdFcId.isChecked()) {
                        firebase.child(email).child(nome).setValue(baralho);
                        finish();
                        Intent infoc = new Intent(getApplicationContext(), InformacaoIdiomasActivity.class);
                        startActivity(infoc);
                    } else if (rdFcIdIng.isChecked()) {
                        firebase.child(email).child(nome).setValue(baralho);
                        //TODO: Criar método para baixar grupos nativos do app
                        Intent infIng = new Intent(getApplicationContext(), InformacaoIdiomasActivity.class);
                        finish();
                        startActivity(infIng);
                    }
                }
            }
        }else{
            Toast.makeText(CriarBaralhoActivity.this,
                    R.string.crb_toast_nomenaoinformado,
                    Toast.LENGTH_LONG).show();
        }
    }

    public boolean verifiicarNome(String nome){
        //TODO: verificar se o nome já existe e se não tem caracteres invalidos
        return true;
    }

    public void criarAlarme(){

    }
    public void criarLembrete(){

    }
    public void cancelarAlarme(){

    }
    public void cancelarLembrete(){

    }

    //TODO: fazer o métódo para criar o objeto baralho e encaminhar par as devias activitys
    //TODO:Não esquecer de fazer o tratamento para adicionar o alarme (futuro)


}