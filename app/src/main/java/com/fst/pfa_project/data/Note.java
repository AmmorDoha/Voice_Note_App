/*package com.fst.pfa_project.data;
//j`ai ajouter
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;


    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    // Constructeur public requis pour Kotlin
    public Note(String text, long timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }



    // Constructeur vide requis par Room
    public Note() {}
}
*/
package com.fst.pfa_project.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    // ✅ Room utilisera ce constructeur vide
    public Note() {}

    // ✅ Room ignorera ce constructeur secondaire
    @Ignore
    public Note(String text, long timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }
}



