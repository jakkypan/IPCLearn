package aidl;

import aidl.Book;
import aidl.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();

    void addBook(in Book book);

    void registerListener(in IOnNewBookArrivedListener listener);

    void unregisterListener(in IOnNewBookArrivedListener listener);
}