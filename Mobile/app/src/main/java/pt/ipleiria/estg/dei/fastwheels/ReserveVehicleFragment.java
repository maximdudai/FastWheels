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

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import pt.ipleiria.estg.dei.fastwheels.listeners.VehicleListener;
import pt.ipleiria.estg.dei.fastwheels.model.Reservation;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class ReserveVehicleFragment extends Fragment implements VehicleListener {

    private EditText etNomeCompleto, etEmail, etContacto, etLocalizacao, etDataInicio, etDataFim;

    private RadioGroup rgOpcaoSeguro, rgOpcaoPagamento;
    private Button btnConfirmReservation;

    //calendar for rent from-at
    private Calendar startRentFrom, endRentAt;

    private User loggedUser;
    private int selectedVehicle = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve vehicleId from arguments
        if (getArguments() != null) {
            selectedVehicle = getArguments().getInt("vehicleId", -1);
        }
    }


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

        // set up user data
        loggedUser = SingletonFastWheels.getInstance(getContext()).getUser();
        SingletonFastWheels.getInstance(getContext()).setVehicleListener(this);

        // update UI
        etNomeCompleto.setText(loggedUser.getName());
        etEmail.setText(loggedUser.getEmail());

        if(!Objects.equals(loggedUser.getPhone(), "none"))
            etContacto.setText(loggedUser.getPhone());

        //toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbarCars);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }

        //calendar for pickup start rent end end rent dates
        setupDisponivelDe_Ate();
        return view;
    }
    private void setupDisponivelDe_Ate() {
        // Define initial dates
        startRentFrom = Calendar.getInstance();
        startRentFrom.add(Calendar.DAY_OF_MONTH, 1); // System date + 1 day
        endRentAt = (Calendar) startRentFrom.clone();
        endRentAt.add(Calendar.DAY_OF_MONTH, 1); // startRentFrom + 1 day

        // For etDataInicio
        etDataInicio.setOnClickListener(v ->
                Helpers.showDatePickerDialog(requireContext(),
                        etDataInicio, startRentFrom, (year, month, day) -> {
                            Calendar newDateDisponivelDe = Calendar.getInstance();
                            newDateDisponivelDe.set(year, month, day);

                            etDataInicio.setText(String.format(Locale.getDefault(),
                                    "%04d-%02d-%02d 00:00:00", year, month + 1, day));

                            // Adjust the minimum limit for etDataFim
                            if (newDateDisponivelDe.compareTo(endRentAt) >= 0) {
                                endRentAt.set(year, month, day);
                                endRentAt.add(Calendar.DAY_OF_MONTH, 1);
                                etDataFim.setText(String.format(Locale.getDefault(),
                                        "%04d-%02d-%02d 00:00:00",
                                        endRentAt.get(Calendar.YEAR),
                                        endRentAt.get(Calendar.MONTH) + 1,
                                        endRentAt.get(Calendar.DAY_OF_MONTH)));
                            } else {
                                endRentAt.set(year, month, day);
                                endRentAt.add(Calendar.DAY_OF_MONTH, 1);
                            }
                        })
        );

        // For etDataFim
        etDataFim.setOnClickListener(v ->
                Helpers.showDatePickerDialog(requireContext(),
                        etDataFim, endRentAt, (year, month, day) -> {
                            etDataFim.setText(String.format(Locale.getDefault(),
                                    "%04d-%02d-%02d 00:00:00", year, month + 1, day));
                        }
                )
        );
    }


    private void handleConfirmReservation() {
        String nome = etNomeCompleto.getText().toString();
        String email = etEmail.getText().toString();
        String contacto = etContacto.getText().toString();
        String localizacao = etLocalizacao.getText().toString();
        String dataInicioStr = etDataInicio.getText().toString();
        String dataFimStr = etDataFim.getText().toString();

        int selectedSeguroId = rgOpcaoSeguro.getCheckedRadioButtonId();
        int selectedPagamentoId = rgOpcaoPagamento.getCheckedRadioButtonId();

        if (selectedSeguroId == -1 || selectedPagamentoId == -1 || nome.isEmpty() || email.isEmpty() || dataInicioStr.isEmpty() || dataFimStr.isEmpty()) {
            Toast.makeText(getContext(), "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedSeguro = rgOpcaoSeguro.findViewById(selectedSeguroId);
        RadioButton selectedPagamento = rgOpcaoPagamento.findViewById(selectedPagamentoId);

        String seguro = selectedSeguro.getText().toString();
        String pagamento = selectedPagamento.getText().toString();

        // Convert string dates to Timestamp
        Timestamp dataInicio;
        Timestamp dataFim;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date parsedDataInicio = dateFormat.parse(dataInicioStr);
            Date parsedDataFim = dateFormat.parse(dataFimStr);

            if (parsedDataInicio != null && parsedDataFim != null) {
                dataInicio = new Timestamp(parsedDataInicio.getTime());
                dataFim = new Timestamp(parsedDataFim.getTime());
            } else {
                throw new ParseException("Invalid date format", 0);
            }
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Formato de data inválido! Use 'yyyy-MM-dd HH:mm:ss'.", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getContext(), "Reserva confirmada!\n" +
                "Nome: " + nome + "\n" +
                "Email: " + email + "\n" +
                "Seguro: " + seguro + "\n" +
                "Pagamento: " + pagamento, Toast.LENGTH_LONG).show();

        System.out.println("--API veid: " + selectedVehicle);

        // Create a new Reservation object
        Reservation newReservation = new Reservation(
                0,
                loggedUser.getId(),
                selectedVehicle,
                dataInicio,
                dataFim,
                0,
                24.00,
                4.50,
                15.000
        );

        // Send the reservation to the API
        SingletonFastWheels.getInstance(getContext()).addReservationAPI(newReservation, getContext());

        // End the current fragment and return to the previous page
        requireActivity().getSupportFragmentManager().popBackStack();
    }


    @Override
    public void onRefreshVehicle() {

    }
}
