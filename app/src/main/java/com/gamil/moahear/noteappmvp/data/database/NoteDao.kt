package com.gamil.moahear.noteappmvp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gamil.moahear.noteappmvp.data.model.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNote(noteEntity: NoteEntity): Completable

    @Delete
    fun deleteNote(noteEntity: NoteEntity): Completable

    @Update
    fun updateNote(noteEntity: NoteEntity): Completable

    @Query("SELECT * FROM NOTES_TABLE")
    fun getNotes(): Observable<List<NoteEntity>>

    @Query("SELECT * FROM NOTES_TABLE WHERE id == :noteId")
    fun getNote(noteId: Int): Observable<NoteEntity>
}