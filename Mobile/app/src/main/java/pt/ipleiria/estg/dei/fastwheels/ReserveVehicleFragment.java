package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pt.ipleiria.estg.dei.fastwheels.listeners.VehicleListener;

public class ReserveVehicleFragment extends Fragment implements VehicleListener {

    private EditText etNomeCompleto, etEmail, etContacto, etLocalizacao, etDataInicio, etDataFim;

    private RadioGroup rgOpcaoSeguro, rgOpcaoPagamento;
    private Button btnConfirmReservation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserve_vehicle, container, false);

        // Initialize views
        etNomeCompleto = view.findViewById(R.id.et_nome_completo);
        etEmail = view.findViewById(R.id.et_email);
        etContacto = view.findViewById(R.id.et_contacto);
        etLocalizacao = view.findViewById(R.id.et_localizacao);
        etDataInicio = view.findViewById(R.id.et_data_inicio);
        etDataFim = view.findViewById(R.id.et_data_fim);
        rgOpcaoSeguro = view.findViewById(R.id.rg_opcao_seguro);
        rgOpcaoPagamento = view.findViewById(R.id.rg_opcao_pagamento);
        btnConfirmReservation = view.findViewById(R.id.btnConfirmReservation);

        // Set up button click listener
        btnConfirmReservation.setOnClickListener(v -> handleConfirmReservation());

        return view;
    }

    private void handleConfirmReservation() {
        String nome = etNomeCompleto.getText().toString();
        String email = etEmail.getText().toString();
        String contacto = etContacto.getText().toString();
        String localizacao = etLocalizacao.getText().toString();
        String dataInicio = etDataInicio.getText().toString();
        String dataFim = etDataFim.getText().toString();

        int selectedSeguroId = rgOpcaoSeguro.getCheckedRadioButtonId();
        int selectedPagamentoId = rgOpcaoPagamento.getCheckedRadioButtonId();

        if (selectedSeguroId == -1 || selectedPagamentoId == -1 || nome.isEmpty() || email.isEmpty()) {
            Toast.makeText(getContext(), "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedSeguro = rgOpcaoSeguro.findViewById(selectedSeguroId);
        RadioButton selectedPagamento = rgOpcaoPagamento.findViewById(selectedPagamentoId);

        String seguro = selectedSeguro.getText().toString();
        String pagamento = selectedPagamento.getText().toString();

        Toast.makeText(getContext(), "Reserva confirmada!\n" +
                "Nome: " + nome + "\n" +
                "Email: " + email + "\n" +
                "Seguro: " + seguro + "\n" +
                "Pagamento: " + pagamento, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefreshVehicle() {

    }
}
