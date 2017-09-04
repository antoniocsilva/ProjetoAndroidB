package br.com.devjs.projetoandroidb;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by antonios on 07/08/17.
 */

public class PessoaAdapter extends ArrayAdapter<Pessoas> {

    public PessoaAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Pessoas> objects) {
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Pessoas pessoas = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_main,parent, false);

        }
        String str = getContext().getString(R.string.sobrepeso);

        TextView textNome = convertView.findViewById(R.id.text_nome);
        TextView textPesoAtual = convertView.findViewById(R.id.text_peso_atual);
        TextView textPesoIdeal = convertView.findViewById(R.id.text_peso_ideal);
        ImageView imagePessoa = convertView.findViewById(R.id.image_pessoa);
        TextView textIMC = convertView.findViewById(R.id.text_imc);

        textNome.setText(getContext().getString(R.string.nome_pessoa) + " " + pessoas.getNome());
        textPesoAtual.setText(String.valueOf(getContext().getString(R.string.peso_atual_pessoa) + " " + pessoas.getPeso() + " Kg."));
        textPesoIdeal.setText(String.valueOf(getContext().getString(R.string.peso_ideal_pessoa) + " " + String.format("%.1f", pessoas.getPesoIdeal()) + " Kg."));
        textIMC.setText(getContext().getString(R.string.voce) + " " + pessoas.getImc());

        /**ImageView view = (ImageView) convertView;
        if(view == null){
            view = new ImageView(getContext());
        }
        Picasso.with(getContext()).load(pessoas.getUrl()).into(view);*/

        return convertView;
    }

}
