package com.filip.library;

import com.filip.library.beans.Book;
import com.filip.library.beans.Grad;
import com.filip.library.beans.Role;
import com.filip.library.beans.Status;
import com.filip.library.beans.User;
import com.filip.library.beans.Zupanija;
import com.filip.tools.MyFormatter;
import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class LibraryApiApplication {

    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        LibraryApiApplication.logger = logger;
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(LibraryApiApplication.class, args);
        setLogging();
    }

    @GetMapping(path = "api/addnewbook")
    public void addNewBook(String bookName, String author, String releaseYear, String pageNumber, String isbn, String copyNumber) {
        LibraryCreator.addNewBook(bookName, author, releaseYear, pageNumber, isbn, copyNumber);
    }

    @GetMapping(path = "api/borrowabook")
    public void borrowABook(int id) {
        LibraryCreator.borrowABook(id);
    }

    @GetMapping(path = "api/returnabook")
    public void returnABook(int id) {
        LibraryCreator.returnABook(id);
    }

    @PostMapping(path = "api/addauser2")
    public void addUser(String firstName, String lastName, String email, String phoneNumber, String adress, String zupanija, String grad, String role) {
        UserCreator.addUser(firstName, lastName, email, phoneNumber, adress, zupanija, grad, role);
    }

    @GetMapping(path = "api/adddebt")
    public void addDebt(int id, float debt) {
        UserCreator.addDebt(id, debt);
    }

    @GetMapping(path = "api/cleardebt")
    public void clearDebt(int id) {
        UserCreator.clearDebt(id);
    }

    @GetMapping(path = "api/addimage")
    public void addImage(String isbn) {
        LibraryCreator.addImage(isbn);
    }

    @GetMapping(path = "api/generatepassword")
    public void generatePassword(String username, String password) {
        EmployeeCreator.generatePassword(username, password);
    }

    @GetMapping(path = "api/generateusername")
    public void generateUsername(String email) {
        EmployeeCreator.generateUsername(email);
    }

    @GetMapping(path = "api/getemployeebyusername")
    public User getUserByUsername(String username) {
        return EmployeeCreator.getEmployeeByUserName(username);
    }

    @GetMapping(path = "api/getusers")
    public List<User> getUsers() {
        return UserCreator.getUsers();
    }

    @GetMapping(path = "api/getcities")
    public List<Grad> getCities() {
        return UserCreator.getCities();
    }

    @GetMapping(path = "api/getcitiesbyzupanija")
    public List<Grad> getCitiesByZupanija(String zupanijaid) {
        return UserCreator.getCitiesByZupanija(zupanijaid);
    }

    @GetMapping(path = "api/getzupanije")
    public List<Zupanija> getZupanije() {
        return UserCreator.getZupanije();
    }

    @GetMapping(path = "api/getroles")
    public List<Role> getRoles() {
        return UserCreator.getRoles();
    }
    
    @GetMapping(path = "api/getbooks")
    public List<Book> getBooks(){
        return LibraryCreator.getBooks();
    }
    
    @GetMapping(path = "api/getstatuses")
    public List<Status> getStatuses(){
        return LibraryCreator.getStatuses();
    }

    private static void setLogging() throws IOException {
        MyFormatter formatter = new MyFormatter();
        FileHandler mh = new FileHandler("logs.log", 1024 * 1024 * 100, 30, true);
        mh.setFormatter(formatter);
        LibraryApiApplication.setLogger(Logger.getLogger(LibraryApiApplication.class.getName()));
        LibraryApiApplication.getLogger().addHandler(mh);
        LibraryApiApplication.getLogger().info("Program started");
    }

}
