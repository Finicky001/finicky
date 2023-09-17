package com.filip.library;

import com.filip.library.beans.Book;
import com.filip.library.beans.Grad;
import com.filip.library.beans.Status;
import com.filip.library.beans.Zupanija;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LibraryCreator {

    static String url = "jdbc:postgresql://localhost:5432/library";
    static Properties props = new Properties();
    public static String str = "";

    static {

        props.setProperty("user", "postgres");
        props.setProperty("password", "2808Filip");
        props.setProperty("ssl", "false");
    }

    public static void addNewBook(String bookName, String author, String releaseYear, String pageNumber, String isbn, String copyNumber) {
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery("insert into books(bookName, author, releaseYear, PageNumber, isbn, copyNumber) values ('" + bookName + "', '" + author + "', '" + releaseYear + "', " + pageNumber + ", '" + isbn + "', " + copyNumber + ");");

            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not add a new book");
        }
        LibraryApiApplication.getLogger().info("Book added");

    }

    static void borrowABook(int id) {
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery("update books set status = 2 where books.id = " + id);

            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not borrow a book");
        }
        LibraryApiApplication.getLogger().info("Book borrowed");
    }

    static void returnABook(int id) {
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery("update books set status = 1 where books.id = " + id);

            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not return the book");
        }
        LibraryApiApplication.getLogger().info("Book returned");
    }

    static void addImage(String isbn) {

        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery("update books set image = 'https://covers.openlibrary.org/b/isbn/" + isbn + "-L.jpg' where books.isbn = '" + isbn + "'");

            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not add an image");

        }
        LibraryApiApplication.getLogger().info("Image added");
    }

    static List<Book> getBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery("select * from books_everything order by id");

            while (rs.next()) {
                Book temp = new Book();
                temp.setId(rs.getInt("id"));
                temp.setBookName(rs.getString("bookName"));
                temp.setAuthor(rs.getString("author"));
                temp.setReleaseYear(rs.getString("releaseYear"));
                temp.setIsbn(rs.getString("isbn"));
                temp.setPageNumber(rs.getInt("pageNumber"));
                temp.setCopyNumber(rs.getInt("copyNumber"));
                temp.setImage(rs.getString("image"));
                temp.setTimestamp(rs.getTimestamp("dateborrowed"));

                Status stemp = new Status();
                stemp.setId(rs.getInt("statusid"));
                stemp.setStatus(rs.getString("status"));

                temp.setStatus(stemp);

                books.add(temp);
            }
            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not get books");
        }
        LibraryApiApplication.getLogger().info("Books were accessed");

        return books;
    }

    static List<Status> getStatuses() {
        ArrayList<Status> statuses = new ArrayList<>();
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery("select * from statuses");

            while (rs.next()) {
                Status temp = new Status();
                temp.setId(rs.getInt("id"));
                temp.setStatus(rs.getString("status"));
            }
            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not get statuses");
        }
        LibraryApiApplication.getLogger().info("Statuses were accessed");

        return statuses;
    }

}
