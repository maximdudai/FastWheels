package pt.ipleiria.estg.dei.fastwheels;

import android.Manifest;
import android.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
import pt.ipleiria.estg.dei.fastwheels.model.VehiclePhoto;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class UserVehicleFormFragment extends Fragment {

    private EditText etMarca, etModelo, etAno, etNumPortas, etMorada,
            etCodigoPostal, etCidade, etDisponivelDe,etDisponivelAte, etPrecoDia;
    private Button btnGuardarVeiculo;

    private static final int MIN_ETANO = 2000;
    private static final int CURRENT_YEAR_ETANO = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    private static final int MIN_ETPORTA = 3;
    private static final int MAX_ETPORTA = 9;

    private Calendar startDateDisponivelDe, startDateDisponivelAte;

    private GridLayout selectedImagesContainer;
    private ImageView ivOpenGallery;
    private ArrayList<Uri> selectedImages;
    private static final int REQUEST_MEDIA_PERMISSION = 100;
    private static final int MAX_IMAGES = 3;

    public UserVehicleFormFragment() {
        // Required empty public constructor
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_vehicle_form, container, false);

        etMarca = view.findViewById(R.id.etMarca);
        etModelo = view.findViewById(R.id.etModelo);
        etAno = view.findViewById(R.id.etnAno);
        etNumPortas = view.findViewById(R.id.etnNumPortas);
        etMorada = view.findViewById(R.id.etMorada);
        etCodigoPostal = view.findViewById(R.id.etpaCodigoPostal);
        etCidade = view.findViewById(R.id.etCidade);
        etDisponivelDe = view.findViewById(R.id.etdDisponivelDe);
        etDisponivelAte = view.findViewById(R.id.etdDisponivelAte);
        etPrecoDia = view.findViewById(R.id.etdPriceDay);
        selectedImagesContainer = view.findViewById(R.id.selectedImagesContainer);
        ivOpenGallery = view.findViewById(R.id.ivIcon);
        selectedImages = new ArrayList<>();
        btnGuardarVeiculo = view.findViewById(R.id.btnGuardar);

        setupDisponivelDe_Ate();
        setupOpenGallery();

        btnGuardarVeiculo.setOnClickListener(v -> saveVehicle());

        return view;
    }

    private void setupDisponivelDe_Ate() {
        //Definir datas iniciais
        startDateDisponivelDe = Calendar.getInstance();
        startDateDisponivelDe.add(Calendar.DAY_OF_MONTH, 1); //data sistema + 1 dia
        startDateDisponivelAte = (Calendar) startDateDisponivelDe.clone();
        startDateDisponivelAte.add(Calendar.DAY_OF_MONTH, 1); //etDisponivelDe + 1 dia

        // Para etDisponivelDe
        etDisponivelDe.setOnClickListener(v ->
                Helpers.showDatePickerDialog(requireContext(),
                        etDisponivelDe, startDateDisponivelDe, (year, month, day) -> {
                            Calendar newDateDisponivelDe = Calendar.getInstance();
                            newDateDisponivelDe.set(year, month, day);

                            etDisponivelDe.setText(String.format(Locale.forLanguageTag("pt-PT"),
                                    "%02d/%02d/%04d", day, month + 1, year));


                            // Ajustar o limite mínimo para o calendário de etDisponivelAte
                            // Se novo DisponivelDe >= DisponivelAte -> ajusta data etDisponivelAte
                            if (newDateDisponivelDe.compareTo(startDateDisponivelAte) >= 0) {
                                startDateDisponivelAte.set(year, month, day);
                                startDateDisponivelAte.add(Calendar.DAY_OF_MONTH, 1);
                                etDisponivelAte.setText(String.format(Locale.forLanguageTag("pt-PT"),
                                        "%02d/%02d/%04d", startDateDisponivelAte.get(Calendar.DAY_OF_MONTH),
                                        startDateDisponivelAte.get(Calendar.MONTH) + 1,
                                        startDateDisponivelAte.get(Calendar.YEAR)));
                            } else {
                                // Nova data < data atual de etDisponivelAte, manter data atual
                                startDateDisponivelAte.set(year, month, day);
                                startDateDisponivelAte.add(Calendar.DAY_OF_MONTH, 1);
                            }
                        })
        );

        // Para etDisponivelAte
        etDisponivelAte.setOnClickListener(v ->
                Helpers.showDatePickerDialog(requireContext(),
                        etDisponivelAte, startDateDisponivelAte, (year, month, day) -> {
                            Calendar newDateDisponivelAte = Calendar.getInstance();
                            newDateDisponivelAte.set(year, month, day);

                            etDisponivelAte.setText(String.format(Locale.forLanguageTag("pt-PT"),
                                    "%02d/%02d/%04d", day, month + 1, year));
                        }
                )
        );
    }

    private void setupOpenGallery() {
        ivOpenGallery.setOnClickListener(v -> {
            if (selectedImages.size() >= MAX_IMAGES) {
                Helpers.showMessage(getContext(), "Execedeu o limite de " + MAX_IMAGES + " fotografias!");
            } else {
                checkAndroidPermissionsUser();
            }
        });
    }

    //region PERMISSOES ACESSO GALERIA
    // Verificar ou solicitar permissões
    private void checkAndroidPermissionsUser() {
        String permission;

        // Permissão conforme versão android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = Manifest.permission.READ_MEDIA_IMAGES; // Android 13+
        } else {
            permission = Manifest.permission.READ_EXTERNAL_STORAGE; // Android 12 ou inferior
        }

        // Verificar se permissão já foi autorizada
        if (ContextCompat.checkSelfPermission(requireContext(), permission) ==
                PackageManager.PERMISSION_GRANTED) {
            //Autorizado -> abrir galeria
            openGallery.launch("image/*");
        } else {
            // Solicitar permissão
            requestPermissions(new String[]{permission}, REQUEST_MEDIA_PERMISSION);
        }
    }

    //Metodo chamado automaticamente pelo sistema para tratar a resposta da permissão
    //Se não implementado o código não saberá o que fazer após a resposta
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_MEDIA_PERMISSION) {
            //Autorizado -> abrir galeria
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery.launch("image/*");
            } else {
                //Negado -> abrir mensagem erro
                Helpers.showMessage(getContext(), "Permissão para aceder a galeria negada!");
            }
        }
    }
    //endregion

    //region GALERIA -> ADICIONAR/ REMOVER FOTOGRAFIAS
    // Abrir a galeria (1 foto cada vez)
    private final ActivityResultLauncher<String> openGallery = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    if (selectedImages.size() < MAX_IMAGES) {
                        selectedImages.add(uri);
                        addImageToContainer(uri);
                    }
                }
            }
    );

    // Adicionar fotografia ao container
    private void addImageToContainer(Uri uri) {
        ImageView imageView = new ImageView(getContext());
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = 600;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.setMargins(8, 8, 8, 8);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageURI(uri);

        // Ação ao tocar na fotografia
        imageView.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setMessage("Pretende remover a fotografia?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        selectedImages.remove(uri);
                        selectedImagesContainer.removeView(imageView);
                        Helpers.showMessage(getContext(), "Fotografia removida");
                    })
                    .setNegativeButton("Não", null) // Não faz nada
                    .show();
        });
        selectedImagesContainer.addView(imageView);
    }
    //endregion

    //region VALIDACAO CAMPOS FORM
    private boolean validateFields() {
        boolean isValid = Helpers.validateFieldIsNotEmpty(etMarca, "Campo obrigatório");

        if (!Helpers.validateFieldIsNotEmpty(etModelo, "Campo obrigatório")) isValid = false;
        if (!Helpers.validateFieldAno(etAno, MIN_ETANO, CURRENT_YEAR_ETANO)) isValid = false;
        if (!Helpers.validateNumPortas(etNumPortas, MIN_ETPORTA, MAX_ETPORTA)) isValid = false;
        if (!Helpers.validateFieldIsNotEmpty(etNumPortas, "Campo obrigatório")) isValid = false;
        if (!Helpers.validateFieldIsNotEmpty(etMorada, "Campo obrigatório")) isValid = false;
        if (!Helpers.validateCodigoPostal(etCodigoPostal)) isValid = false;
        if (!Helpers.validateFieldIsNotEmpty(etCidade, "Campo obrigatório")) isValid = false;
        if (!Helpers.validateFieldDisponivel(etDisponivelDe, startDateDisponivelDe, getContext())) isValid = false;
        if (!Helpers.validateFieldDisponivel(etDisponivelAte, startDateDisponivelAte, getContext())) isValid = false;
        if (!Helpers.validateFieldPrice(etPrecoDia)) isValid = false;

        return isValid;
    }

    private boolean validatePhotos() {
        if (selectedImages.isEmpty()) {
            Helpers.showMessage(getContext(), "Adicione pelo menos uma fotografia");
            return false;
        }
        return true;
    }
    //endregion

    private void saveVehicle() {
        if (!validateFields() || !validatePhotos()) {
            return;
        }
        try{
            String marca = etMarca.getText().toString();
            String modelo = etModelo.getText().toString();
            int ano = Integer.parseInt(etAno.getText().toString());
            int numPortas = Integer.parseInt(etNumPortas.getText().toString());
            String morada = etMorada.getText().toString();
            String codigoPostal = etCodigoPostal.getText().toString();
            String cidade = etCidade.getText().toString();
            BigDecimal precoDia = new BigDecimal(etPrecoDia.getText().toString());

            // Variáveis de Timestamp
            Timestamp disponivelDe;
            Timestamp disponivelAte;

            //region Formatar Timestamp da etDisponivelDe e etDisponivelAte
            try {
                // Configurar formatador para data de entrada (dd/MM/yyyy) e saída (yyyy-MM-dd)
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-PT"));
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("pt-PT"));

                // Converter e formatar as datas
                Date disponivelDeDate = inputFormat.parse(etDisponivelDe.getText().toString());
                Date disponivelAteDate = inputFormat.parse(etDisponivelAte.getText().toString());

                String disponivelDeFormatted = outputFormat.format(disponivelDeDate);
                String disponivelAteFormatted = outputFormat.format(disponivelAteDate);

                // Criar objetos Timestamp com o formato correto
                disponivelDe = Timestamp.valueOf(disponivelDeFormatted + " 00:00:00");
                disponivelAte = Timestamp.valueOf(disponivelAteFormatted + " 00:00:00");
            }catch (Exception e) {
                Log.e("DateConversionError", "Erro ao converter datas: " + e.getMessage());
                return;
            }
            //endregion

            ArrayList<VehiclePhoto> vehiclePhotos = new ArrayList<>();
            for (Uri uri : selectedImages) {
                vehiclePhotos.add(new VehiclePhoto(0, 0, uri.toString()));
            }

            Vehicle newVehicle = new Vehicle(
                    0, // ID gerado automaticamente
                    1, // TODO clientId ALTERAR
                    marca,
                    modelo,
                    ano,
                    numPortas,
                    true, // status -> ativo
                    disponivelDe,
                    disponivelAte,
                    morada,
                    codigoPostal,
                    cidade,
                    precoDia,
                    vehiclePhotos
            );

            // Adicionar o veículo à BD através do Singleton
            SingletonFastWheels.getInstance(requireContext()).addVehicleDb(newVehicle);

            Helpers.showMessage (getContext(),"Veículo adicionado com sucesso!");

            if (getActivity() instanceof UserVehicles) {
                getActivity().getSupportFragmentManager().popBackStack(); // Fecha UserVehicleFormFragment
                ((UserVehicles) getActivity()).loadFragment(new UserVehicleListFragment()); // Chama UserVehicleListFragment
            }

        } catch (Exception e) {
        Log.e("SaveVehicleError", "Erro ao salvar veículo: " + e.getMessage());
        }
    }
}