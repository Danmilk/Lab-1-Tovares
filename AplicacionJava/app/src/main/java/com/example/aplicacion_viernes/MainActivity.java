package com.example.aplicacion_viernes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
     Button buttonToast = null;
     EditText editTextText = null;
     CheckBox checkBox = null;
     CalendarView calendarView = null;
     ImageButton imageButton = null;
     long fechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // References
        buttonToast    = findViewById(R.id.buttonToast);
        editTextText   = findViewById(R.id.editTextText);
        checkBox       = findViewById(R.id.checkBox);
        calendarView   = findViewById(R.id.calendarView);
        imageButton    = findViewById(R.id.imageButton);

        // Inicializar fechaSeleccionada con la fecha actual del CalendarView
        fechaSeleccionada = calendarView.getDate();

        // Listener para actualizar fechaSeleccionada cuando el usuario cambie la fecha
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // month es 0-based, por eso sumamos 1
            String fechaStr = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            try {
                fechaSeleccionada = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr).getTime();
            } catch (Exception e) {
                fechaSeleccionada = view.getDate();
            }
        });

        // Botón Toast
        buttonToast.setOnClickListener(v -> {
            boolean sw = checkBox.isChecked();
            String texto = editTextText.getText().toString();

            // Hora actual
            String horaActual = new SimpleDateFormat("HH:mm:ss").format(new Date());

            // Fecha seleccionada
            String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy")
                    .format(new Date(fechaSeleccionada));

            String mensaje = String.format(
                    "Checkbox: %s Texto: %s  Hora: %s  Fecha: %s",
                    sw ? "Marcado" : "No marcado",
                    texto,
                    horaActual,
                    fechaFormateada
            );

            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
        });

        // ImageButton → segunda actividad
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });

        // Ajuste Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return insets;
        });
    }
}
