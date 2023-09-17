package com.filip.library;

import static com.filip.library.LibraryCreator.props;
import static com.filip.library.LibraryCreator.url;
import com.filip.library.beans.Grad;
import com.filip.library.beans.Role;
import com.filip.library.beans.User;
import com.filip.library.beans.Zupanija;
import com.filip.tools.Password;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class EmployeeCreator {

    static String url = "jdbc:postgresql://localhost:5432/library";
    static Properties props = new Properties();
    public static String str = "";

    static {

        props.setProperty("user", "postgres");
        props.setProperty("password", "2808Filip");
        props.setProperty("ssl", "false");
    }

    static void generatePassword(String username, String password) {
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {
            ResultSet rs = st.executeQuery("update users set password = '" + Password.hashPassword(password) + "' where username = '" + username + "'");

            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not generate password");
        }
        LibraryApiApplication.getLogger().info("Password generated");
    }

    static void generateUsername(String email) {
        String username = "";
        int counter = 0;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                counter++;
            }
            if (counter == 0) {
                username += email.charAt(i);
            }
        }
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery("update users set username = '" + username + "' where id = (select id from users where email = '" + email + "')");

            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not generate username");
        }
        LibraryApiApplication.getLogger().info("Username generated");
    }

    static User getEmployeeByUserName(String username) {
        User temp = new User();
        try ( Connection con = DriverManager.getConnection(url, props);  Statement st = con.createStatement();) {
            ResultSet rs = st.executeQuery("select * from users_everything where username = '" + username + "'");

            while (rs.next()) {
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

            }
            rs.close();
        } catch (Exception e) {
            LibraryApiApplication.getLogger().severe("Could not get employees");
        }
        LibraryApiApplication.getLogger().info("Employees accessed");
        return temp;
    }
}
