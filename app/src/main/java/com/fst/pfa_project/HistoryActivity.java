/*package com.fst.pfa_project;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fst.pfa_project.data.AppDatabase;
import com.fst.pfa_project.data.Note;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private LinearLayout notesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        notesContainer = findViewById(R.id.historyContainer);

        List<Note> notes = AppDatabase.getInstance(this).noteDao().getAllNotes();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        for (Note note : notes) {
            TextView noteView = new TextView(this);
            noteView.setText(
                    "📅 " + sdf.format(note.timestamp) + "\n📝 " + note.text + "\n"
            );
            noteView.setTextSize(16);
            noteView.setPadding(0, 0, 0, 32);
            notesContainer.addView(noteView);
        }
    }
}
*/




/*
package com.fst.pfa_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fst.pfa_project.data.AppDatabase;
import com.fst.pfa_project.data.Note;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.fst.pfa_project.utils.TextSummarizer;
import android.util.Log;
import android.widget.Toast;


public class HistoryActivity extends AppCompatActivity {

    private LinearLayout notesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        notesContainer = findViewById(R.id.historyContainer);
        loadNotes();
    }

    private void loadNotes() {
        notesContainer.removeAllViews(); // Rafraîchir

        List<Note> notes = AppDatabase.getInstance(this).noteDao().getAllNotes();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        for (Note note : notes) {
            LinearLayout noteLayout = new LinearLayout(this);
            noteLayout.setOrientation(LinearLayout.VERTICAL);
            noteLayout.setPadding(0, 0, 0, 32);

            TextView noteView = new TextView(this);
            noteView.setText("📅 " + sdf.format(note.timestamp) + "\n📝 " + note.text);
            noteView.setTextSize(16);

            Button btnEdit = new Button(this);
            btnEdit.setText("✏️ Modifier");
            btnEdit.setOnClickListener(v -> showEditDialog(note));

            Button btnDelete = new Button(this);
            btnDelete.setText("🗑 Supprimer");
            btnDelete.setOnClickListener(v -> {
                AppDatabase.getInstance(this).noteDao().delete(note);
                loadNotes(); // Recharger l’affichage
            });

            noteLayout.addView(noteView);
            noteLayout.addView(btnEdit);
            noteLayout.addView(btnDelete);
            notesContainer.addView(noteLayout);
        }
    }

    private void showEditDialog(Note note) {
        EditText input = new EditText(this);
        input.setText(note.text);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        new AlertDialog.Builder(this)
                .setTitle("Modifier la note")
                .setView(input)
                .setPositiveButton("Enregistrer", (dialog, which) -> {
                    note.text = input.getText().toString();
                    AppDatabase.getInstance(this).noteDao().update(note);
                    loadNotes(); // Recharger
                })
                .setNegativeButton("Annuler", null)
                .show();
    }
}

*/
package com.fst.pfa_project;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fst.pfa_project.data.AppDatabase;
import com.fst.pfa_project.data.Note;
import com.fst.pfa_project.utils.TextSummarizer;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private LinearLayout notesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        notesContainer = findViewById(R.id.historyContainer);
        loadNotes();
    }

    private void loadNotes() {
        notesContainer.removeAllViews();

        List<Note> notes = AppDatabase.getInstance(this).noteDao().getAllNotes();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        for (Note note : notes) {
            LinearLayout noteLayout = new LinearLayout(this);
            noteLayout.setOrientation(LinearLayout.VERTICAL);
            noteLayout.setPadding(0, 0, 0, 32);

            // Texte de la note
            TextView noteView = new TextView(this);
            noteView.setText("📅 " + sdf.format(note.timestamp) + "\n📝 " + note.text);
            noteView.setTextSize(16);

            // Zone pour afficher le résumé
            TextView summaryView = new TextView(this);
            summaryView.setTextSize(14);
            summaryView.setPadding(0, 8, 0, 8);

            // 🔘 Bouton "Résumer"
            Button btnSummarize = new Button(this);
            btnSummarize.setText("📄 Résumer");
            btnSummarize.setOnClickListener(v -> {
                String summary = TextSummarizer.summarize(note.text);
                summaryView.setText("📄 Résumé : " + summary);
            });

            // Bouton Modifier
            Button btnEdit = new Button(this);
            btnEdit.setText("✏️ Modifier");
            btnEdit.setOnClickListener(v -> showEditDialog(note));

            // Bouton Supprimer
            Button btnDelete = new Button(this);
            btnDelete.setText("🗑 Supprimer");
            btnDelete.setOnClickListener(v -> {
                AppDatabase.getInstance(this).noteDao().delete(note);
                loadNotes(); // Recharger
            });

            // Ajouter tous les éléments au layout
            noteLayout.addView(noteView);
            noteLayout.addView(btnSummarize);
            noteLayout.addView(summaryView);
            noteLayout.addView(btnEdit);
            noteLayout.addView(btnDelete);

            notesContainer.addView(noteLayout);
        }
    }


    private void showEditDialog(Note note) {
        EditText input = new EditText(this);
        input.setText(note.text);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        new AlertDialog.Builder(this)
                .setTitle("Modifier la note")
                .setView(input)
                .setPositiveButton("Enregistrer", (dialog, which) -> {
                    note.text = input.getText().toString();
                    AppDatabase.getInstance(this).noteDao().update(note);
                    loadNotes();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }
}
