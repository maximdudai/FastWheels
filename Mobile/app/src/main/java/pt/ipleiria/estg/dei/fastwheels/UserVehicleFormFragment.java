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

import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
import pt.ipleiria.estg.dei.fastwheels.model.VehiclePhoto;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class UserVehicleFormFragment extends Fragment {

    private EditText etMarca, etModelo, etAno, etNumPortas, etMorada,
            etCodigoPostal, etCidade, etDisponivelDe,etDisponivelAte, etPrecoDia;
    private Button btnGuardarVeiculo;
    private ImageView ivEliminarVeiculo;

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

    private List<String> displayedImages = new ArrayList<>();

    public UserVehicleFormFragment() {
        // Required empty public constructor
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_vehicle_form, container, false);

        ivEliminarVeiculo = view.findViewById(R.id.ivEliminar);
        ivEliminarVeiculo.setVisibility(View.GONE); // Oculta

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

        // Buscar o ID do veículo do argumento
        Bundle args = getArguments();
        if (args != null) {
            int vehicleId = args.getInt("VEHICLE_ID", -1); // -1 é o valor padrão caso não encontre o ID
            if (vehicleId != -1) {
                loadVehicleDataByID(vehicleId);
                ivEliminarVeiculo.setOnClickListener(v -> removeVehicle(vehicleId));
            }
        }

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
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery.launch("image/*");
            } else {
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
                        addImage(uri);
                    }
                }
            }
    );

    //region GESTAO IMAGECONTAINER
    private void refreshImageContainer() {
        System.out.println("--->DEBUG refreshImageContainer called");

        displayedImages.clear();
        selectedImagesContainer.removeAllViews(); // Clear the container

        for (Uri uri : selectedImages) {
            if (!displayedImages.contains(uri.toString())) {
                displayedImages.add(uri.toString());

                ImageView imageView = new ImageView(getContext());
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 600;
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                params.setMargins(8, 8, 8, 8);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                // Load the image using Glide
                Glide.with(this)
                        .load(uri)
                        .error(R.drawable.gallery_icon) // Error icon
                        .into(imageView);

                // Configure click to remove image
                imageView.setOnClickListener(v -> removeImage(uri));

                selectedImagesContainer.addView(imageView);  // Add the image to the UI
            } else {
                System.out.println("--->DEBUG nothing to show");
            }
        }
    }

    private void addImage(Uri uri) {
        if (!selectedImages.contains(uri)) {
            selectedImages.add(uri); // Adiciona a imagem à lista
            refreshImageContainer(); // Atualiza a interface
        } else {
            Helpers.showMessage(getContext(), "Esta imagem já foi adicionada!");
        }
    }

    private void removeImage(Uri uri) {
        if (selectedImages.contains(uri)) {
            new AlertDialog.Builder(getContext())
                    .setMessage("Pretende remover a fotografia?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        System.out.println("--->DEBUG URI: " + uri);
                        System.out.println("--->DEBUG SELECTEDIMAGES ANTES: " + selectedImages.toString());
                        selectedImages.removeIf(photUri -> photUri.toString().contains(uri.toString()));

                        System.out.println("--->DEBUG SELECTEDIMAGES DEPOIS: " + selectedImages.toString());


                        refreshImageContainer(); // Atualiza a interface
                    })
                    .setNegativeButton("Não", null)
                    .show();
        } else {
            Helpers.showMessage(getContext(), "Erro ao remover: Imagem não encontrada!");
        }
    }
    //endregion


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

    private void loadVehicleDataByID(int vehicleId) {
        Vehicle vehicle = SingletonFastWheels.getInstance(getContext()).getVehicleById(vehicleId);

        if (vehicle != null) {
            ivEliminarVeiculo.setVisibility(View.VISIBLE);
            etMarca.setText(vehicle.getCarBrand());
            etModelo.setText(vehicle.getCarModel());
            etAno.setText(String.valueOf(vehicle.getCarYear()));
            etNumPortas.setText(String.valueOf(vehicle.getCarDoors()));
            etMorada.setText(vehicle.getAddress());
            etCodigoPostal.setText(vehicle.getPostalCode());
            etCidade.setText(vehicle.getCity());
            etPrecoDia.setText(vehicle.getPriceDay().toPlainString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-PT"));
            if (vehicle.getAvailableFrom() != null) {
                etDisponivelDe.setText(dateFormat.format(vehicle.getAvailableFrom()));
            }
            if (vehicle.getAvailableTo() != null) {
                etDisponivelAte.setText(dateFormat.format(vehicle.getAvailableTo()));
            }

            // Limpar e recarregar as fotos
            selectedImages.clear();  // Limpa a lista local
            for (VehiclePhoto photo : vehicle.getVehiclePhotos()) {
                String photoUrl = photo.getPhotoUrl();
                selectedImages.add(Uri.parse(photoUrl)); // Adiciona cada URI à lista
            }
            refreshImageContainer(); // Atualiza o conteiner visual
        } else {
            Helpers.showMessage(getContext(), "Veículo não encontrado!");
        }
    }


    private void saveVehicle() {
        if (!validateFields() || !validatePhotos()) {
            return;
        }
        try {
            //region componentes
            String marca = etMarca.getText().toString();
            String modelo = etModelo.getText().toString();
            int ano = Integer.parseInt(etAno.getText().toString());
            int numPortas = Integer.parseInt(etNumPortas.getText().toString());
            String morada = etMorada.getText().toString();
            String codigoPostal = etCodigoPostal.getText().toString();
            String cidade = etCidade.getText().toString();
            BigDecimal precoDia = new BigDecimal(etPrecoDia.getText().toString());

            Timestamp disponivelDe, disponivelAte;
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-PT"));
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("pt-PT"));

            Date disponivelDeDate = inputFormat.parse(etDisponivelDe.getText().toString());
            Date disponivelAteDate = inputFormat.parse(etDisponivelAte.getText().toString());

            String disponivelDeFormatted = outputFormat.format(disponivelDeDate);
            String disponivelAteFormatted = outputFormat.format(disponivelAteDate);

            disponivelDe = Timestamp.valueOf(disponivelDeFormatted + " 00:00:00");
            disponivelAte = Timestamp.valueOf(disponivelAteFormatted + " 00:00:00");
            //endregion


            SingletonFastWheels singleton = SingletonFastWheels.getInstance(getContext());
            Bundle args = getArguments();
            int vehicleId = args != null ? args.getInt("VEHICLE_ID", -1) : -1;

            ArrayList<VehiclePhoto> vehiclePhotosList = new ArrayList<>();

            Vehicle vehicle = new Vehicle(vehicleId, 1, marca, modelo, ano, numPortas, true,
                    disponivelDe, disponivelAte, morada, codigoPostal, cidade, precoDia, vehiclePhotosList);

            singleton.removeAllVehiclePhotosBD(vehicleId);

            if(vehicleId == -1) {
                singleton.addVehicleDb(vehicle);
                Helpers.showMessage(getContext(), "Veículo adicionado com sucesso!");

            } else {
                boolean updatedVehicle = singleton.editVehicleDb(vehicle);
                if(updatedVehicle) {
                    Helpers.showMessage(getContext(), "Veículo atualizado com sucesso!");
                }
            }

            for (Uri uri : selectedImages) {
                boolean alreadyExists = false;

                for (VehiclePhoto carPhoto : vehicle.getVehiclePhotos()) {
                    if (uri.toString().equals(carPhoto.toString())) {

                        alreadyExists = true;
                        break;
                    }
                }

                if (!alreadyExists) {
                    singleton.addVehiclePhoto(vehicle.getId(), uri.toString());
                    VehiclePhoto vehiclePhoto = new VehiclePhoto(0, vehicleId, uri.toString());
                    vehiclePhotosList.add(vehiclePhoto);

                }
            }
            vehicle.setVehiclePhotos(vehiclePhotosList);

            // Navegar de volta para a Lista de Veículos
            if (getActivity() instanceof UserVehicles) {
                ((UserVehicles) getActivity()).loadFragment(new UserVehicleListFragment(), "UserVehicleListFragment");
            }
        } catch (Exception e) {
            Log.e("Error", "Erro ao salvar veículo: " + e.getMessage());
        }
    }

    private void removeVehicle(int vehicleId) {
        new AlertDialog.Builder(requireContext())
                .setMessage("Pretende eliminar o registo deste veículo?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    SingletonFastWheels singleton = SingletonFastWheels.getInstance(getContext());
                    singleton.removeVehicleDb(vehicleId);
                    Helpers.showMessage(getContext(), "Veículo e fotos eliminados com sucesso!");

                    if (getActivity() instanceof UserVehicles) {
                        getActivity().getSupportFragmentManager().popBackStack();
                        ((UserVehicles) getActivity()).loadFragment(new UserVehicleListFragment(),"UserVehicleListFragment");
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
}