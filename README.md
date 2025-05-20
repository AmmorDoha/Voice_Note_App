# 🎙️ VoiceNote AI

**VoiceNote AI** is an intelligent Android application that allows users to **record voice**, **transcribe it into text**, **summarize the content automatically**, and **manage notes locally**. Built for students, professionals, and anyone needing quick and hands-free note-taking, the app runs entirely offline and offers clean, minimalistic UI.

---

## 📱 Features

- 🎤 **Voice to Text Transcription**  
  Use Android’s built-in `SpeechRecognizer` to convert spoken words into text.

- 🧠 **Automatic Summarization**  
  A custom-built `TextSummarizer.java` selects key sentences from the note.

- 💾 **Local Database with Room**  
  Notes are saved on the device using `Room` (SQLite).

- 🗂️ **Full History of Notes**  
  Users can view, edit, delete, and summarize saved notes.

- 🌐 **Multi-language Support**  
  Choose from multiple input languages (English, French, Arabic, Spanish...).

- 📤 **Share Notes Easily**  
  Share via WhatsApp, email, Facebook, or any installed app.

- 🔒 **Works Offline**  
  All functionalities, including summarization, are available without internet.

---

## 🏗️ Architecture

com.fst.pfa_project
├── MainActivity.kt # Handles voice recording and display
├── HistoryActivity.java # Shows history of notes and summaries
├── data/
│ ├── Note.java # Data model
│ ├── NoteDao.java # DAO interface
│ └── AppDatabase.java # Singleton Room DB instance
├── utils/
│ └── TextSummarizer.java # Custom extractive summarization logic


---

## 🛠️ Tech Stack

| Technology        | Purpose                                    |
|-------------------|--------------------------------------------|
| Java              | Application logic and structure            |
| Room (SQLite)     | Local storage                              |
| SpeechRecognizer  | Transcription engine (Android API)         |
| Kotlin KAPT       | Annotation processing for Room             |
| XML               | UI layout                                  |
| Android Studio    | IDE used                                   |

---

## 🧠 How Summarization Works

The summarizer in `TextSummarizer.java` uses a **simple NLP algorithm**:
1. Break text into sentences.
2. Count word frequencies (excluding stopwords).
3. Score sentences by important word density.
4. Select top 1-2 sentences as the summary.

> Fast, lightweight, and fully offline — no AI model or cloud required.

---

## 📸 Screenshots

| Home | History | Edit & Share |
|--![image](https://github.com/user-attachments/assets/d6f888d5-6704-4628-b48f-997e63d5f354)
![image](https://github.com/user-attachments/assets/6f8d16ef-1fe3-401d-915b-c6d96dc9fd14)
![image](https://github.com/user-attachments/assets/6a185dcb-1f3d-48c0-91e9-09069f17c5f7)
![image](https://github.com/user-attachments/assets/6dafea9f-0510-4fe2-9bb9-5e3872be1b8b)
![image](https://github.com/user-attachments/assets/0282cc55-53b0-480a-b585-5a0a7352e935)
![image](https://github.com/user-attachments/assets/0eedda6b-2367-4aaf-bdee-9801cf102d08)








---

## 🚀 Getting Started

1. **Clone the repository**

```bash
git clone https://github.com/AmmorDoha/VoiceNote-AI.git

2 .Open with Android Studio

3.Ensure required dependencies are installed:
implementation "androidx.room:room-runtime:2.6.1"
kapt "androidx.room:room-compiler:2.6.1"
implementation "androidx.appcompat:appcompat:1.6.1"

4.Run the app on an Android device (API 21+)
Roadmap
 Replace extractive summarizer with GPT or T5

 Firebase cloud sync

 Export notes as PDF

 Dark mode and voice commands

 AI-powered multi-language summaries

📄 License
Licensed under the MIT License.
© 2025 Ammor Doha & Erraqi Ihssane — EMSI University

🙏 Acknowledgments
Android Developers

OpenAI Summarization

Room Persistence

Material Design

Whisper Speech-to-Text


---




