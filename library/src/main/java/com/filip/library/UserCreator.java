package com.filip.library;

import static com.filip.library.LibraryCreator.props;
import static com.filip.library.LibraryCreator.url;
import com.filip.library.beans.*;
import com.filip.library.beans.Zupanija;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserCreator {

    static void addUser(String firstName, String lastName, String email, String phoneNumber, String adress, String zupanija, String grad, String role) {
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {
            ResultSet rs = st.executeQuery("insert into users(firstName, lastName, email, phoneNumber, adress, zupanija, grad, role) values ('" + firstName + "', '" + lastName + "', '" + email + "', '" + phoneNumber + "', '" + adress + "', " +zupanija+ ", " +grad+ ", " +role+ ");");

            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not add a user");
        }
        LibraryApiApplication.getLogger().info("User was added");
    }

    static void addDebt(int id, float debt) {
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {
            ResultSet rs = st.executeQuery("update users set debt = debt + " + debt + " where users.id = " + id);

            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not add debt");
        }
        LibraryApiApplication.getLogger().info("Debt was added");
    }

    static void clearDebt(int id) {
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {
            ResultSet rs = st.executeQuery("update users set debt = 0 where users.id = " + id);

            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not clear debt");
        }
        LibraryApiApplication.getLogger().info("Debt was cleared");
    }

    static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {
            ResultSet rs = st.executeQuery("select * from users_everything");

            while (rs.next()) {
                User temp = new User();
                temp.setId(rs.getInt("id"));
                temp.setFirstName(rs.getString("firstName"));
                temp.setLastName(rs.getString("lastName"));
                temp.setAdress(rs.getString("adress"));
                temp.setEmail(rs.getString("email"));
                temp.setPhoneNumber(rs.getString("phoneNumber"));
                temp.setUsername(rs.getString("username"));
                temp.setPassword(rs.getString("password"));

                Zupanija ztemp = new Zupanija();
                ztemp.setId(rs.getInt("zupanijaid"));
                ztemp.setNazivzupanije(rs.getString("nazivzupanije"));
                ztemp.setPozivni(rs.getString("pozivni"));

                temp.setZupanija(ztemp);

                Grad gtemp = new Grad();
                gtemp.setId(rs.getInt("gradid"));
                gtemp.setNazivgrada(rs.getString("nazivgrada"));
                gtemp.setPostbroj(rs.getString("postbroj"));
                gtemp.setZupanija(ztemp);

                temp.setGrad(gtemp);

                Role rtemp = new Role();
                rtemp.setId(rs.getInt("roleid"));
                rtemp.setLevel(rs.getInt("level"));
                rtemp.setRoleName(rs.getString("roleName"));

                temp.setRole(rtemp);
                users.add(temp);
            }
            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not get users");
        }
        LibraryApiApplication.getLogger().info("Users were accessed");

        return users;
    }

    static ArrayList<Grad> getCities() {
        ArrayList<Grad> cities = new ArrayList<>();
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery("select * from gradovi_sa_zupanijama");

            while (rs.next()) {
                Grad temp = new Grad();
                temp.setId(rs.getInt("id"));
                temp.setNazivgrada(rs.getString("nazivgrada"));
                temp.setPostbroj(rs.getString("postbroj"));

                Zupanija ztemp = new Zupanija();
                ztemp.setId(rs.getInt("zupanijaid"));
                ztemp.setNazivzupanije(rs.getString("nazivzupanije"));
                ztemp.setPozivni(rs.getString("pozivni"));

                temp.setZupanija(ztemp);

                cities.add(temp);
            }
            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not get cities");
        }
        LibraryApiApplication.getLogger().info("Cities were accessed");

        return cities;
    }

    static List<Zupanija> getZupanije() {
        ArrayList<Zupanija> zupanije = new ArrayList<>();
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery("select * from zupanije");

            while (rs.next()) {
                Zupanija temp = new Zupanija();

                temp.setId(rs.getInt("id"));
                temp.setNazivzupanije(rs.getString("nazivzupanije"));
                temp.setPozivni(rs.getString("pozivni"));

                zupanije.add(temp);
            }
            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not get zupanije");
        }
        LibraryApiApplication.getLogger().info("zupanije were accessed");

        return zupanije;
    }

    static List<Grad> getCitiesByZupanija(String id) {
        ArrayList<Grad> cities = new ArrayList<>();
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {
            String query = "select * from gradovi_sa_zupanijama where zupanijaid=" + id;
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Grad temp = new Grad();
                temp.setId(rs.getInt("id"));
                temp.setNazivgrada(rs.getString("nazivgrada"));
                temp.setPostbroj(rs.getString("postbroj"));

                Zupanija ztemp = new Zupanija();
                ztemp.setId(rs.getInt("zupanijaid"));
                ztemp.setNazivzupanije(rs.getString("nazivzupanije"));
                ztemp.setPozivni(rs.getString("pozivni"));

                temp.setZupanija(ztemp);

                cities.add(temp);
            }
            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not get cities");
        }
        LibraryApiApplication.getLogger().info("Cities were accessed");

        return cities;
    }

    static List<Role> getRoles() {
        ArrayList<Role> roles = new ArrayList<>();
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery("select * from roles");

            while (rs.next()) {
                Role temp = new Role();
                temp.setId(rs.getInt("id"));
                temp.setLevel(rs.getInt("level"));
                temp.setRoleName(rs.getString("roleName"));

                roles.add(temp);
            }
            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not get roles");
        }
        LibraryApiApplication.getLogger().info("roles were accessed");

        return roles;
    }
}
