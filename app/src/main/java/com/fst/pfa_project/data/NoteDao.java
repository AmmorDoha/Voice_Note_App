/*package com.fst.pfa_project.data;
 // j`ai ajouter

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    List<Note> getAllNotes();
}
*/
package com.fst.pfa_project.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    List<Note> getAllNotes();

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
