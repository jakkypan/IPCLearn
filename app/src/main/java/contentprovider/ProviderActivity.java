/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package contentprovider;

import aidl.Book;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by panhongchao on 15/12/2.
 */
public class ProviderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri bookUri = Uri.parse("content://bainuo.security.provider/book");
//        getContentResolver().query(uri, null, null, null, null, null);
//        getContentResolver().query(uri, null, null, null, null, null);
//        getContentResolver().delete(uri, null, null);

        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "ddddd");
        getContentResolver().insert(BookProvider.BOOK_CONTENT_URI, values);

        Cursor bookCursor = getContentResolver().query(BookProvider.BOOK_CONTENT_URI, new String[]{"_id", "name"},
                null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            Log.e("book", "query book: " + book.toString());
        }

    }
}
