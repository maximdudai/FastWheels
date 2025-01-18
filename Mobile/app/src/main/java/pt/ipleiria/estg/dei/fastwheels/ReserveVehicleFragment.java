package pt.ipleiria.estg.dei.fastwheels;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import pt.ipleiria.estg.dei.fastwheels.listeners.VehicleListener;
import pt.ipleiria.estg.dei.fastwheels.model.Reservation;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
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
        etDataFim = view.findViewById(R.id.etdDisponivelAte);
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
                                    "%04d-%02d-%02d", year, month + 1, day));

                            // Adjust the minimum limit for etDataFim
                            if (newDateDisponivelDe.compareTo(endRentAt) >= 0) {
                                endRentAt.set(year, month, day);
                                endRentAt.add(Calendar.DAY_OF_MONTH, 1);
                                etDataFim.setText(String.format(Locale.getDefault(),
                                        "%04d-%02d-%02d",
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
                                    "%04d-%02d-%02d", year, month + 1, day));
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

        Toast.makeText(getContext(), "Reserva confirmada!\n" +
                "Nome: " + nome + "\n" +
                "Email: " + email + "\n" +
                "Seguro: " + seguro + "\n" +
                "Pagamento: " + pagamento, Toast.LENGTH_LONG).show();

        Timestamp disponivelDe = Timestamp.valueOf(dataInicioStr + " 00:00:00");
        Timestamp disponivelAte = Timestamp.valueOf(dataFimStr + " 00:00:00");

        Reservation newReservation = new Reservation(
                0,
                loggedUser.getId(),
                selectedVehicle,
                disponivelDe,
                disponivelAte,
                0,
                24.00,
                4.50,
                15.000
        );

        // Send the reservation to the API
        SingletonFastWheels.getInstance(getContext()).addReservationAPI(newReservation, getContext());

        // Update vehicle
        Vehicle vehData = SingletonFastWheels.getInstance(getContext()).getVehicleByIdBd(selectedVehicle);
        vehData.setStatus(true);

        // set vehicle as rented in database
        SingletonFastWheels.getInstance(getContext()).editVehicleAPI(vehData, getContext());

        // Generate PDF
        generateAndDownloadPDF(nome, email, contacto, localizacao, dataInicioStr, dataFimStr, seguro, pagamento);

        // End the current fragment and return to the previous page
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void generateAndDownloadPDF(String nome, String email, String contacto, String localizacao, String dataInicioStr, String dataFimStr, String seguro, String pagamento) {
        try {
            File pdfFile = new File(requireContext().getExternalFilesDir(null), "ReservationDetails.pdf");

            PdfWriter writer = new PdfWriter(pdfFile);

            Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

            document.add(new Paragraph("Detalhes da Reserva"));
            document.add(new Paragraph("Nome: " + nome));
            document.add(new Paragraph("Email: " + email));
            document.add(new Paragraph("Contacto: " + contacto));
            document.add(new Paragraph("Localização: " + localizacao));
            document.add(new Paragraph("Data de Início: " + dataInicioStr));
            document.add(new Paragraph("Data de Fim: " + dataFimStr));
            document.add(new Paragraph("Seguro: " + seguro));
            document.add(new Paragraph("Pagamento: " + pagamento));

            document.close();
            Toast.makeText(getContext(), "Invoice received successfully!", Toast.LENGTH_SHORT).show();

            openPDF(pdfFile);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RESERVATION:INVOICE", "Erro while creating: " +e.getMessage());
        }
    }

    private void openPDF(File pdfFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".provider", pdfFile);

        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "Nenhum visualizador de PDF encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefreshVehicle() {

    }
}
