package pt.ipleiria.estg.dei.fastwheels.modules;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import pt.ipleiria.estg.dei.fastwheels.R;

public class InputDialog extends DialogFragment {

    public interface OnInputListener {
        void onInputReceived(String input, String type);
    }

    private OnInputListener inputListener;
    private EditText inputField;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Get the arguments passed to the dialog
        Bundle args = getArguments();
        String title = args.getString("title", "Input");
        String hint = args.getString("hint", "Enter value");
        String type = args.getString("type", "default");

        // Inflate the dialog's layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_generic_input, null);

        inputField = view.findViewById(R.id.edit_input);
        inputField.setHint(hint);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(view)
                .setTitle(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputValue = inputField.getText().toString();
                        if (inputListener != null) {
                            inputListener.onInputReceived(inputValue, type); // Return the value and type to the activity
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Close dialog
                    }
                });

        return builder.create();
    }

    public void setOnInputListener(OnInputListener listener) {
        this.inputListener = listener;
    }

    public static InputDialog newInstance(String title, String hint, String type) {
        InputDialog fragment = new InputDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("hint", hint);
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }
}
