package com.demo.day3;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BookmarkDao {
    @Insert(onConflict = REPLACE)
    long insertBookmark(BookmarkEntity bookmarkEntity);

    @Update
    void updateBookmark(BookmarkEntity bookmarkEntity);

    @Delete
    void deleteBookmark(BookmarkEntity bookmarkEntity);

    @Query("SELECT * FROM Bookmark")
    List<BookmarkEntity> getAllBookmark();

    @Query("SELECT * FROM Bookmark WHERE id = :id")
    BookmarkEntity getBookmark(int id);

    @Query("DELETE FROM Bookmark")
    void deleteAll();
}
