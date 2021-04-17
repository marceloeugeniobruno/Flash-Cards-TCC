package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.flashcards.R;
import com.example.flashcards.model.Baralho;

public class CriarBaralhoActivity extends AppCompatActivity {
    private RadioButton rdFcCom, rdFcId, rdFcIdIng, rdAlSem, rdAlLem, rdAlAlm;
    private RadioGroup grupoAlarmes;
    private LinearLayout linearLayoutHora;
    private Baralho baralho;
    private EditText editTextNome, editTextHora, editTextMinuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_baralho);

        editTextNome = findViewById(R.id.crb_edt_nome_baralho);
        //editTextHora = findViewById(R.id.crb_edt_hora);
        //configurando radio buttons
        rdFcCom = findViewById(R.id.crb_rdb_fc_com);
        rdFcId = findViewById(R.id.crb_rdb_fc_id);
        rdFcIdIng = findViewById(R.id.crb_rdb_fc_id_ing);
        rdAlSem = findViewById(R.id.crb_rdb_al_sem);
        rdAlLem = findViewById(R.id.crb_rdb_al_lem);
        rdAlAlm = findViewById(R.id.fcrb_rdb_al_alm);
        //configurando radio grupo para add um listneer
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
            this.baralho = new Baralho();
            this.baralho.setNome(editTextNome.getText().toString());
            if (rdFcCom.isChecked()){

            }else if(rdFcId.isChecked()||rdFcIdIng.isChecked()){

            }


        }else{
            Toast.makeText(CriarBaralhoActivity.this,
                    "Nome do baralho não Informado",
                    Toast.LENGTH_LONG).show();
        }

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