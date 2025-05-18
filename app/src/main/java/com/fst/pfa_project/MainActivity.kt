package com.fst.pfa_project

import android.animation.ValueAnimator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.fst.pfa_project.data.AppDatabase
import com.fst.pfa_project.data.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var textResult: TextView
    private lateinit var btnSpeak: FloatingActionButton
    private lateinit var btnHistory: Button
    private lateinit var btnCopy: ImageButton
    private lateinit var btnShare: ImageButton
    private lateinit var languageSpinner: Spinner
    private lateinit var recordingStatus: TextView
    private lateinit var recordingIndicator: LinearLayout
    private lateinit var recordingDot: View
    private lateinit var database: AppDatabase

    private var selectedLanguage = Locale.getDefault().language
    private lateinit var pulseAnimator: ValueAnimator

    private val speechRecognizerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            hideRecordingIndicator()

            if (result.resultCode == RESULT_OK && result.data != null) {
                val data = result.data
                val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (!results.isNullOrEmpty()) {
                    val transcription = results[0]
                    textResult.text = transcription
                    recordingStatus.text = "Transcription complète"

                    val note = Note(transcription, System.currentTimeMillis())
                    database.noteDao().insert(note)
                }
            } else {
                recordingStatus.text = "Aucune parole détectée ou annulée"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialisation UI
        textResult = findViewById(R.id.textResult)
        btnSpeak = findViewById(R.id.btnSpeak)
        btnHistory = findViewById(R.id.btnHistory)
        btnCopy = findViewById(R.id.btnCopy)
        btnShare = findViewById(R.id.btnShare)
        languageSpinner = findViewById(R.id.languageSpinner)
        recordingStatus = findViewById(R.id.recordingStatus)
        recordingIndicator = findViewById(R.id.recordingIndicator)
        recordingDot = findViewById(R.id.recordingDot)

        // Configuration de l'animation
        setupPulseAnimation()

        // Base de données
        database = AppDatabase.getInstance(this)

        // Langues disponibles
        val languages = mapOf(
            "Français" to "fr",
            "Anglais" to "en",
            "Arabe" to "ar",
            "Espagnol" to "es",
            "Allemand" to "de"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages.keys.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter

        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selected = parent.getItemAtPosition(position).toString()
                selectedLanguage = languages[selected] ?: "fr"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Boutons
        btnSpeak.setOnClickListener {
            startSpeechToText()
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        btnCopy.setOnClickListener {
            copyTextToClipboard()
        }

        btnShare.setOnClickListener {
            shareText()
        }
    }

    private fun setupPulseAnimation() {
        pulseAnimator = ValueAnimator.ofFloat(0.8f, 1.2f).apply {
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = LinearInterpolator()
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                recordingDot.scaleX = value
                recordingDot.scaleY = value
            }
        }
    }

    private fun startSpeechToText() {
        showRecordingIndicator()
        recordingStatus.text = "Écoute en cours..."

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, selectedLanguage)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Parlez maintenant...")

        try {
            speechRecognizerLauncher.launch(intent)
        } catch (e: Exception) {
            hideRecordingIndicator()
            Toast.makeText(this, "Erreur: ${e.message}", Toast.LENGTH_SHORT).show()
            recordingStatus.text = "Erreur lors du lancement de la reconnaissance vocale"
        }
    }

    private fun showRecordingIndicator() {
        recordingIndicator.visibility = View.VISIBLE
        pulseAnimator.start()
    }

    private fun hideRecordingIndicator() {
        pulseAnimator.cancel()
        recordingIndicator.visibility = View.GONE
    }

    private fun copyTextToClipboard() {
        val text = textResult.text.toString()
        if (text.isNotEmpty() && text != getString(R.string.text_result_hint)) {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Texte transcrit", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Texte copié dans le presse-papiers", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Rien à copier", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareText() {
        val text = textResult.text.toString()
        if (text.isNotEmpty() && text != getString(R.string.text_result_hint)) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(Intent.createChooser(shareIntent, "Partager via"))
        } else {
            Toast.makeText(this, "Rien à partager", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (pulseAnimator.isRunning) {
            pulseAnimator.cancel()
        }
    }
}