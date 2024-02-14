package com.example.source;

import com.example.source.claseTabele.*;
import com.example.source.controller.Receptioner.SceneProgramare;
import com.example.source.controller.ResurseUmane.SceneConcediu;
import com.example.source.controller.ResurseUmane.SceneOrarConcediu;
import com.example.source.controller.Altele.SceneRaport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class Model {
    public static final String SUPERADMIN = "super_admin";
    public static final String ADMIN = "admin";
    public static final String UTILIZATOR = "utilizator";
    public static final String MEDIC = "medic";
    public static final String ASISTENT_MEDICAL = "asistent_medical";
    public static final String RECEPTIONER = "receptioner";
    public static final String ECONOMIC = "economic";
    public static final String RESURSE_UMANE = "resurse_umane";
    private static ContUtilizator contCurent;
    private static Utilizator utilizatorCurent;
    private static Angajat angajatCurent;
    private static Angajat angajatSelectat;
    private static Medic medicCurent;
    private static AsistentMedical asistentCurent;
    private static Pacient pacientSelectat;
    private static Programare programareSelectata;
    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    private static final String URL = "jdbc:mysql://localhost/policlinica?user=root&password=parola";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static void logOut(ActionEvent event, String scenePath) throws IOException {
        root = FXMLLoader.load(Model.class.getResource(scenePath));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        contCurent = null;
        angajatCurent = null;
        utilizatorCurent = null;
    }

    static public boolean logIn(String username, String password) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        boolean booleanVariable = false;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call CautaCont(?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, username);
            callableStatement.setString(2, password);
            resultSet = callableStatement.executeQuery();

            if (resultSet.next() == false)
                booleanVariable = false;
            else {
                contCurent = new ContUtilizator(Integer.parseInt(resultSet.getString("id")), resultSet.getString("nume_utilizator"), resultSet.getString("parola"));
                System.out.println(contCurent);
                extrageUtilizator(contCurent.getId());
                booleanVariable = true;
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return booleanVariable;
    }

    public static ObservableList<Angajat> cautaAngajat(String input) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Angajat> angajati = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call CautaAngajat(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, input);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_utilizator = resultSet.getInt("id_utilizator");
                int id_policlinica = resultSet.getInt("id_policlinica");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String functie = resultSet.getString("functie");
                int salariu_negociat = resultSet.getInt("salariu_negociat");
                int numar_ore = resultSet.getInt("numar_ore");
                angajati.add(new Angajat(id, id_utilizator, id_policlinica, nume, prenume, functie, salariu_negociat, numar_ore));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return angajati;
    }

    public static ObservableList<Angajat> listaAngajati() throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Angajat> angajati = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call AfiseazaAngajati()}";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_utilizator = resultSet.getInt("id_utilizator");
                int id_policlinica = resultSet.getInt("id_policlinica");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String functie = resultSet.getString("functie");
                int salariu_negociat = resultSet.getInt("salariu_negociat");
                int numar_ore = resultSet.getInt("numar_ore");
                angajati.add(new Angajat(id, id_utilizator, id_policlinica, nume, prenume, functie, salariu_negociat, numar_ore));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return angajati;
    }

    public static ObservableList<Concediu> listaConcedii(int id_angajat) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Concediu> concedii = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call ExtrageConcediiReadOnly(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, Integer.toString(id_angajat));
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date data_inceput = resultSet.getDate("data_inceput");
                Date data_sfarsit = resultSet.getDate("data_sfarsit");
                System.out.println(id + " " + " " + id_angajat + " " + " " + data_inceput + " " + " " + data_sfarsit);
                concedii.add(new Concediu(id, id_angajat, data_inceput, data_sfarsit));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return concedii;
    }

    public static ObservableList<Pacient> listaPacienti() throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Pacient> pacienti = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call ExtragePacienti()}";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                pacienti.add(new Pacient(id, nume, prenume));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return pacienti;
    }

    public static ObservableList<Specialitati> listaSpecialitatiMedic(int id) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Specialitati> specialitati = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call AfiseazaSpecialitati(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, Integer.toString(id));
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id_specialitate = resultSet.getInt("id");
                String nume_specialitate = resultSet.getString("nume_specialitate");
                String grad = resultSet.getString("grad");
                specialitati.add(new Specialitati(id_specialitate, nume_specialitate, grad));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return specialitati;
    }

    public static ArrayList<Medic> listaMedici() throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ArrayList<Medic> medici = new ArrayList<>();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call AfiseazaMedici()}";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_angajat = resultSet.getInt("id_angajat");
                String cod_parafa = resultSet.getString("cod_parafa");
                String titlu_stiintific = resultSet.getString("titlu_stiintific");
                String post_didactic = resultSet.getString("post_didactic");
                Double venit_aditional = resultSet.getDouble("venit_aditional");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                medici.add(new Medic(id, id_angajat, cod_parafa, titlu_stiintific, post_didactic, venit_aditional, nume, prenume));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return medici;
    }

    public static ObservableList<Pacient> cautaPacient(String input) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Pacient> pacienti = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM pacienti " +
                    "WHERE pacienti.nume = " + "'" + input + "'" + " or pacienti.prenume = " + "'" + input + "'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                pacienti.add(new Pacient(id, nume, prenume));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return pacienti;
    }

    public static boolean cautaPacient(String nume, String prenume) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        CallableStatement callableStatement = null;

        boolean exista = false;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM pacienti " +
                    "WHERE pacienti.nume = " + "'" + nume + "'" + " and pacienti.prenume = " + "'" + prenume + "'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            if (resultSet.next())
                exista = true;

        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return exista;
    }

    public static void extrageUtilizator(int id) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call CautaUtilizatorDupaCont(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, Integer.toString(id));
            resultSet = callableStatement.executeQuery();
            if (resultSet.next())
                utilizatorCurent = new Utilizator(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_cont"),
                        resultSet.getString("departament"),
                        resultSet.getString("adresa"),
                        resultSet.getString("cnp"),
                        resultSet.getString("nume"),
                        resultSet.getString("prenume"),
                        resultSet.getString("telefon"),
                        resultSet.getString("email"),
                        resultSet.getString("IBAN"),
                        resultSet.getString("data_angajarii"),
                        resultSet.getString("rol")
                );
            System.out.println(utilizatorCurent);
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static boolean concediuCorect(int id_angajat, Date ziInceput, Date ziSfarsit) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        boolean posibil = true;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT  id, concedii.id_angajat, concedii.data_inceput, concedii.data_sfarsit FROM concedii " +
                    "WHERE concedii.id_angajat = " + id_angajat +
                    " AND id = (SELECT max(id) FROM concedii WHERE id_angajat = " + id_angajat + ")";

            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                id_angajat = resultSet.getInt("id_angajat");
                Date data_inceput = resultSet.getDate("data_inceput");
                Date data_sfarsit = resultSet.getDate("data_sfarsit");
                if (ziInceput.before(data_sfarsit) || ziInceput.equals(data_sfarsit)) {
                    posibil = false;
                    System.out.println(ziInceput + "    " + data_sfarsit);
                }
                System.out.println(id + " " + id_angajat + " " + data_inceput + " " + data_sfarsit);
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return posibil;
    }

    public static void adaugaConcediu(int id_angajat, LocalDate ziInceput, LocalDate ziSfarsit) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call SeteazaConcediu(?, ?, ?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, Integer.toString(id_angajat));
            callableStatement.setString(2, ziInceput.toString());
            callableStatement.setString(3, ziSfarsit.toString());
            resultSet = callableStatement.executeQuery();
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void extrageAngajatDupaUtilizator(int id) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call CautaAngajatDupaUtilizator(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, Integer.toString(id));
            resultSet = callableStatement.executeQuery();
            if (resultSet.next())
                angajatCurent = new Angajat(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_utilizator"),
                        resultSet.getInt("id_policlinica"),
                        utilizatorCurent.getNume(),
                        utilizatorCurent.getPrenume(),
                        resultSet.getString("functie"),
                        resultSet.getInt("salariu_negociat"),
                        resultSet.getInt("numar_ore")
                );
            System.out.println(angajatCurent);
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void extrageAsistentMedical(int id) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call CautaAsistentMedical(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, Integer.toString(id));
            resultSet = callableStatement.executeQuery();
            if (resultSet.next())
                asistentCurent = new AsistentMedical(
                        resultSet.getInt("id"),
                        resultSet.getString("tip"),
                        resultSet.getString("grad")
                );
            System.out.println(asistentCurent);
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void extrageMedic(int id) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call CautaMedic(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, Integer.toString(id));
            resultSet = callableStatement.executeQuery();
            if (resultSet.next())
                medicCurent = new Medic(
                        resultSet.getInt("id"),
                        resultSet.getString("cod_parafa"),
                        resultSet.getString("titlu_stiintific"),
                        resultSet.getString("post_didactic"),
                        resultSet.getDouble("venit_aditional")
                );
            System.out.println(medicCurent);
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void switchToWindowOrare(ActionEvent event, Angajat a) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(Model.class.getResource("/com.example.source/scene-resurse-umane-orar-concediu-view.fxml"));
        root = loader.load();

        SceneOrarConcediu orar = loader.getController();
        orar.setAngajatSelectat(a);
        angajatSelectat = a;

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToWindowProgramare(ActionEvent event, Pacient pacient) throws IOException {
        FXMLLoader loader = new FXMLLoader(Model.class.getResource("/com.example.source/scene-receptioner-programare-view.fxml"));
        root = loader.load();

        SceneProgramare prgramare = loader.getController();
        prgramare.setPacientSelectat(pacient);
        pacientSelectat = pacient;

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToWindowRaport(ActionEvent event, Programare programare) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(Model.class.getResource("/com.example.source/scene-raport-view.fxml"));
        root = loader.load();

        SceneRaport raport = loader.getController();
        raport.setProgramareSelectata(programare);
        programareSelectata = programare;

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToWindowConcedii(ActionEvent event, Angajat a) throws IOException {
        FXMLLoader loader = new FXMLLoader(Model.class.getResource("/com.example.source/scene-resurse-umane-concedii-view.fxml"));
        root = loader.load();

        SceneConcediu concediu = loader.getController();
        concediu.setAngajatSelectat(a);
        angajatSelectat = a;

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void goToMainMenu(ActionEvent event, String scenePath) throws IOException {
        root = FXMLLoader.load(Model.class.getResource(scenePath));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static Utilizator getUtilizatorCurent() {
        return utilizatorCurent;
    }

    public static Angajat getAngajatCurent() {
        return angajatCurent;
    }

    public static Angajat getAngajatSelectat() {
        return angajatSelectat;
    }

    public static Medic getMedicCurent() {
        return medicCurent;
    }

    public static AsistentMedical getAsistentCurent() {
        return asistentCurent;
    }

    public static Pacient getPacientSelectat() {
        return pacientSelectat;
    }

    public static ArrayList<Serviciu> cautaServiciiMedic(int id_medic) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ArrayList<Serviciu> servicii = new ArrayList<>();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "select sss.id, sss.nume_serviciu, sss.pret, sss.durata from specialitati s, servicii_specialitate ss, servicii sss " +
                    "join medici m " +
                    "where s.id_medic = '" + id_medic + "'and m.id = s.id_medic " + "and sss.id = ss.id_serviciu and ss.id_specialitate = s.id";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume_serviciu = resultSet.getString("nume_serviciu");
                Double pret = resultSet.getDouble("pret");
                Time durata = resultSet.getTime("durata");
                servicii.add(new Serviciu(id, nume_serviciu, pret, durata));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return servicii;
    }

    public static void insereazaPacient(String nume, String prenume) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call InserarePacient(?, ?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, nume);
            callableStatement.setString(2, prenume);
            resultSet = callableStatement.executeQuery();
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static ArrayList<DataConcediu> angajatInConcediu(Integer idAngajat) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ArrayList<DataConcediu> concedii = new ArrayList<>();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM policlinica.concedii " +
                    "WHERE concedii.id_angajat = " + "'" + idAngajat + "'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Date data_inceput = resultSet.getDate("data_inceput");
                Date data_sfarsit = resultSet.getDate("data_sfarsit");
//                System.out.println(data_inceput + "->" + data_sfarsit);
                concedii.add(new DataConcediu(data_inceput, data_sfarsit));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return concedii;
    }

    public static IntervalOrar extrageOrarMedicZiProgramare(Integer idAngajat, Date dataSelectata) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;
        IntervalOrar res = new IntervalOrar();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call OrarPentrudataProgramare(?, ?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, idAngajat.toString());
            callableStatement.setString(2, dataSelectata.toString());
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                Time ora_inceput = resultSet.getTime("ora_inceput");
                Time ora_sfarsit = resultSet.getTime("ora_sfarsit");
                res.setOra_inceput(ora_inceput);
                res.setOra_sfarsit(ora_sfarsit);
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static Time extrageFinalProgramari(int id, LocalDate data, Time oraInceput) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        Time res = Time.valueOf("00:00:00");

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call FinalProgramari(?, ?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, Integer.toString(id));
            callableStatement.setString(2, data.toString());
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                res = resultSet.getTime("final_ultima_programare");
            }

            if (res == null)
                res = oraInceput;

            System.out.println(res);
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static int idPoliclinicaDeCareApartineMedic(int id) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        int res = 0;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call PoliclinicaDeCareApartineMedic(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, Integer.toString(id));
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                res = resultSet.getInt("id_policlinica");
            }

            System.out.println(res);
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static void inserareProgramare(int id_policlinica, int id_angajat, int id_pacient, int id_medic, Date data, Time ora_inceput, Time ora_finalizare) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);

            insertStatement = connection.createStatement();
            insertStatement.execute("INSERT INTO programari (id_policlinica, id_angajat, id_pacient, id_medic, _data, ora_inceput, ora_sfarsit, inregistrat) " +
                    "VALUES (" + id_policlinica + " , " + id_angajat + " , " + id_pacient + " , " + id_medic + " , '" + data.toString() + "' , '" + ora_inceput.toString() + "' , '" + ora_finalizare.toString() + "' , false )");

        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static ObservableList<Programare> cautaProgramariMedic(int id_medic) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Programare> programari = FXCollections.observableArrayList();
        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call ExtragePacientiProgramatiMedic(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, Integer.toString(id_medic));
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Programare programare = new Programare(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_policlinica"),
                        resultSet.getInt("id_angajat"),
                        resultSet.getInt("id_pacient"),
                        resultSet.getInt("id_medic"),
                        resultSet.getDate("_data"),
                        resultSet.getTime("ora_inceput"),
                        resultSet.getTime("ora_sfarsit"),
                        resultSet.getBoolean("inregistrat"),
                        resultSet.getString("nume"),
                        resultSet.getString("prenume")
                );
                programari.add(programare);
            }
            System.out.println(programari);
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return programari;
    }

    public static ObservableList<Programare> cautaProgramariMedic(int id_medic, String s) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Programare> programari = FXCollections.observableArrayList();
        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call ExtragePacientiProgramatiMedic(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, Integer.toString(id_medic));
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                if (nume.equalsIgnoreCase(s) || prenume.equalsIgnoreCase(s)) {
                    Programare programare = new Programare(
                            resultSet.getInt("id"),
                            resultSet.getInt("id_policlinica"),
                            resultSet.getInt("id_angajat"),
                            resultSet.getInt("id_pacient"),
                            resultSet.getInt("id_medic"),
                            resultSet.getDate("_data"),
                            resultSet.getTime("ora_inceput"),
                            resultSet.getTime("ora_sfarsit"),
                            resultSet.getBoolean("inregistrat"),
                            resultSet.getString("nume"),
                            resultSet.getString("prenume")
                    );
                    programari.add(programare);
                }
            }
            System.out.println(programari);
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return programari;
    }

    public static ObservableList<Programare> cautaProgramariMedic(int id_medic, String nume_p, String prenume_p) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Programare> programari = FXCollections.observableArrayList();
        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call ExtragePacientiProgramatiMedic(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, Integer.toString(id_medic));
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                if (nume_p.equalsIgnoreCase(nume) && prenume_p.equalsIgnoreCase(prenume)) {
                    Programare programare = new Programare(
                            resultSet.getInt("id"),
                            resultSet.getInt("id_policlinica"),
                            resultSet.getInt("id_angajat"),
                            resultSet.getInt("id_pacient"),
                            resultSet.getInt("id_medic"),
                            resultSet.getDate("_data"),
                            resultSet.getTime("ora_inceput"),
                            resultSet.getTime("ora_sfarsit"),
                            resultSet.getBoolean("inregistrat"),
                            resultSet.getString("nume"),
                            resultSet.getString("prenume")
                    );
                    programari.add(programare);
                }
            }
            System.out.println(programari);
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return programari;
    }

    public static ArrayList<String> orarPoliclinica(int policlinica) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ArrayList<String> orar = new ArrayList<>();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "select duminica as '1', luni as '2', marti as '3', miercuri as '4',"
                    + "joi as '5', vineri as '6', sambata as '7' from program_functionare pf " + "join policlinici p"
                    + " where p.id_program_functionare = pf.id and p.id =" + "'" + policlinica + "'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {

                orar.add(resultSet.getString(1));
                orar.add(resultSet.getString(2));
                orar.add(resultSet.getString(3));
                orar.add(resultSet.getString(4));
                orar.add(resultSet.getString(5));
                orar.add(resultSet.getString(6));
                orar.add(resultSet.getString(7));

            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return orar;
    }

    public static ObservableList<Programare> pacientiProgramatAziLaPoliclinica(int idPoliclinica) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Programare> programari = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT p.id, p.id_policlinica, p.id_angajat, " +
                    "p.id_pacient, p.id_medic, p._data, " +
                    "p.ora_inceput, p.ora_sfarsit, p.inregistrat, pac.nume, pac.prenume FROM pacienti pac " +
                    "INNER JOIN programari p " +
                    "WHERE pac.id = p.id_pacient AND p.id_policlinica =" + "'" + idPoliclinica + "' " +
                    "AND p._data = CURDATE()";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Programare programare = new Programare(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_policlinica"),
                        resultSet.getInt("id_angajat"),
                        resultSet.getInt("id_pacient"),
                        resultSet.getInt("id_medic"),
                        resultSet.getDate("_data"),
                        resultSet.getTime("ora_inceput"),
                        resultSet.getTime("ora_sfarsit"),
                        resultSet.getBoolean("inregistrat"),
                        resultSet.getString("nume"),
                        resultSet.getString("prenume")
                );
                programari.add(programare);
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return programari;
    }

    public static ObservableList<Programare> pacientiProgramatAziLaPoliclinicaM(int idMedic) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Programare> programari = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT p.id, p.id_policlinica, p.id_angajat, " +
                    "p.id_pacient, p.id_medic, p._data, " +
                    "p.ora_inceput, p.ora_sfarsit, p.inregistrat, pac.nume, pac.prenume FROM pacienti pac " +
                    "INNER JOIN programari p " +
                    "WHERE pac.id = p.id_pacient AND p.id_medic = " + "'" + idMedic + "' " +
                    "AND p._data = CURDATE()";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Programare programare = new Programare(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_policlinica"),
                        resultSet.getInt("id_angajat"),
                        resultSet.getInt("id_pacient"),
                        resultSet.getInt("id_medic"),
                        resultSet.getDate("_data"),
                        resultSet.getTime("ora_inceput"),
                        resultSet.getTime("ora_sfarsit"),
                        resultSet.getBoolean("inregistrat"),
                        resultSet.getString("nume"),
                        resultSet.getString("prenume")
                );
                programari.add(programare);
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return programari;
    }

    public static ObservableList<Programare> pacientiProgramati(int idPoliclinica, String s) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Programare> programari = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT p.id, p.id_policlinica, p.id_angajat, " +
                    "p.id_pacient, p.id_medic, p._data, " +
                    "p.ora_inceput, p.ora_sfarsit, p.inregistrat, pac.nume, pac.prenume FROM pacienti pac " +
                    "INNER JOIN programari p " +
                    "WHERE pac.id = p.id_pacient AND p.id_policlinica =" + "'" + idPoliclinica + "' " +
                    "AND (pac.nume = '" + s + "' or pac.prenume = '" + s + "')";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Programare programare = new Programare(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_policlinica"),
                        resultSet.getInt("id_angajat"),
                        resultSet.getInt("id_pacient"),
                        resultSet.getInt("id_medic"),
                        resultSet.getDate("_data"),
                        resultSet.getTime("ora_inceput"),
                        resultSet.getTime("ora_sfarsit"),
                        resultSet.getBoolean("inregistrat"),
                        resultSet.getString("nume"),
                        resultSet.getString("prenume")
                );
                programari.add(programare);
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return programari;
    }

    public static ObservableList<Programare> pacientiProgramati(int idPoliclinica, String nume_p, String prenume_p) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Programare> programari = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT p.id, p.id_policlinica, p.id_angajat, " +
                    "p.id_pacient, p.id_medic, p._data, " +
                    "p.ora_inceput, p.ora_sfarsit, p.inregistrat, pac.nume, pac.prenume FROM pacienti pac " +
                    "INNER JOIN programari p " +
                    "WHERE pac.id = p.id_pacient AND p.id_policlinica =" + "'" + idPoliclinica + "' " +
                    "AND pac.nume = '" + nume_p + "' AND pac.prenume = '" + prenume_p + "'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Programare programare = new Programare(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_policlinica"),
                        resultSet.getInt("id_angajat"),
                        resultSet.getInt("id_pacient"),
                        resultSet.getInt("id_medic"),
                        resultSet.getDate("_data"),
                        resultSet.getTime("ora_inceput"),
                        resultSet.getTime("ora_sfarsit"),
                        resultSet.getBoolean("inregistrat"),
                        resultSet.getString("nume"),
                        resultSet.getString("prenume")
                );
                programari.add(programare);
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return programari;
    }

    public static ObservableList<Programare> pacientiProgramati(int idPoliclinica) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Programare> programari = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT p.id, p.id_policlinica, p.id_angajat, " +
                    "p.id_pacient, p.id_medic, p._data, " +
                    "p.ora_inceput, p.ora_sfarsit, p.inregistrat, pac.nume, pac.prenume FROM pacienti pac " +
                    "INNER JOIN programari p " +
                    "WHERE pac.id = p.id_pacient AND p.id_policlinica =" + "'" + idPoliclinica + "'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Programare programare = new Programare(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_policlinica"),
                        resultSet.getInt("id_angajat"),
                        resultSet.getInt("id_pacient"),
                        resultSet.getInt("id_medic"),
                        resultSet.getDate("_data"),
                        resultSet.getTime("ora_inceput"),
                        resultSet.getTime("ora_sfarsit"),
                        resultSet.getBoolean("inregistrat"),
                        resultSet.getString("nume"),
                        resultSet.getString("prenume")
                );
                programari.add(programare);
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return programari;
    }

    public static String programMedicZi(Integer idAngajat, Date dataSelectata) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        String interval = "";

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call OrarPentrudataProgramare(?, ?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, idAngajat.toString());
            callableStatement.setString(2, dataSelectata.toString());
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                String ora_inceput = resultSet.getString("ora_inceput");
                String ora_sfarsit = resultSet.getString("ora_sfarsit");
                interval = ora_inceput + "-" + ora_sfarsit;
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return interval;
    }

    public static Medic cautaMedic(int idAngajat) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        Medic medic = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call CautaMedic(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setInt(1, idAngajat);
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                medic = new Medic(
                        resultSet.getInt("id"),
                        resultSet.getString("cod_parafa"),
                        resultSet.getString("titlu_stiintific"),
                        resultSet.getString("post_didactic"),
                        resultSet.getDouble("venit_aditional")
                );
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return medic;
    }

    public static void modificaOrarMedic(Integer id, int tip, String zi, Time time, Time time1) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;


        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call Adaugaorarmedic(?, ?, ?, ?, ?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setInt(1, id);
            callableStatement.setInt(2, tip);
            callableStatement.setString(3, zi);
            callableStatement.setTime(4, time);
            callableStatement.setTime(5, time1);
            resultSet = callableStatement.executeQuery();
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void inregistreazaPacientProgramat(int id) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;


        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "update programari " +
                    "set inregistrat = true where id = " + "'" + id + "'";
            callableStatement = connection.prepareCall(query);
            callableStatement.executeUpdate();
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void genereazaRaport(int id, int idMedic) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;


        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "insert into rapoarte(id_programare, id_medic) values " +
                    "('" + id + "', '" + idMedic + "')";
            callableStatement = connection.prepareCall(query);
            callableStatement.executeUpdate();
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static Raport extrageRaport(int id) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        Raport res = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM rapoarte WHERE id_programare = '" + id + "'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                res = new Raport(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_programare"),
                        resultSet.getInt("id_medic"),
                        resultSet.getInt("id_asistent"),
                        resultSet.getString("nume_medic_recomandare"),
                        resultSet.getString("prenume_medic_recomandare"),
                        resultSet.getString("istoric"),
                        resultSet.getString("simptome"),
                        resultSet.getString("diagnostic"),
                        resultSet.getString("recomandari"),
                        resultSet.getBoolean("parafa")
                );
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static void updateRaport(Raport raportPacient) throws SQLException {

        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;


        try {
            Class.forName(DRIVER).newInstance();
        } catch (
                Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            int parafa = raportPacient.isParafa() ? 1 : 0;
            connection = DriverManager.
                    getConnection(URL);
            String query = "UPDATE rapoarte SET " +
                    "nume_medic_recomandare = '" + raportPacient.getNume_medic_recomandare() + "', " +
                    "prenume_medic_recomandare = '" + raportPacient.getPrenume_medic_recomandare() + "', " +
                    "istoric = '" + raportPacient.getIstoric() + "', " +
                    "simptome = '" + raportPacient.getSimptome() + "', " +
                    "diagnostic = '" + raportPacient.getDiagnostic() + "', " +
                    "recomandari = '" + raportPacient.getRecomandari() + "', " +
                    "parafa = '" + parafa + "' " +
                    "WHERE id = '" + raportPacient.getId() + "'";
            callableStatement = connection.prepareCall(query);
            callableStatement.executeUpdate();
        } catch (
                SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static ArrayList<Serviciu> cautaServiciiPoliclinica(int id_policlinica) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ArrayList<Serviciu> servicii = new ArrayList<>();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "select s.id, s.nume_serviciu, s.pret, s.durata from (policlinici p inner join servicii_oferite_policlinica sp) inner join servicii s " +
                    "where p.id = '" + id_policlinica + "' and s.id = sp.id_serviciu and sp.id_policlinica = p.id";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume_serviciu = resultSet.getString("nume_serviciu");
                Double pret = resultSet.getDouble("pret");
                Time durata = resultSet.getTime("durata");
                servicii.add(new Serviciu(id, nume_serviciu, pret, durata));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return servicii;
    }

    public static ObservableList<Serviciu> cautaServiciiRaport(int id_raport) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Serviciu> servicii = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "select s.id, s.nume_serviciu, s.pret, s.durata, sr.investigatii from (rapoarte r inner join servicii_oferite_raport sr) inner join servicii s " +
                    "where r.id = '" + id_raport + "' and s.id = sr.id_serviciu and sr.id_raport = r.id";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume_serviciu = resultSet.getString("nume_serviciu");
                Double pret = resultSet.getDouble("pret");
                Time durata = resultSet.getTime("durata");
                String investigatii = resultSet.getString("investigatii");
                servicii.add(new Serviciu(id, nume_serviciu, pret, durata, investigatii));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return servicii;
    }

    public static void updateServiciiRaport(int id_raport, HashSet<Serviciu> serviciiRaport) throws SQLException {

        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;


        try {
            Class.forName(DRIVER).newInstance();
        } catch (
                Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            for (Serviciu s : serviciiRaport) {
                String query = "INSERT INTO servicii_oferite_raport(id_raport, id_serviciu, investigatii)" +
                        "VALUES ('" + id_raport + "' , '" + s.getId() + "' , '" + s.getInvestigatii() + "')";
                callableStatement = connection.prepareCall(query);
                callableStatement.executeUpdate();
            }
        } catch (
                SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void updateRaportAsistent(Raport raportPacient) throws SQLException {

        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;


        try {
            Class.forName(DRIVER).newInstance();
        } catch (
                Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            int parafa = raportPacient.isParafa() ? 1 : 0;
            connection = DriverManager.
                    getConnection(URL);
            String query = "UPDATE rapoarte SET " +
                    "id_asistent = '" + raportPacient.getId_asistent() + "' " +
                    "WHERE id = '" + raportPacient.getId() + "'";
            callableStatement = connection.prepareCall(query);
            callableStatement.executeUpdate();
        } catch (
                SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static Bon extrageBon(int id_raport) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        Bon res = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM bonuri_fiscale WHERE id_raport = '" + id_raport + "'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                res = new Bon(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_raport"),
                        resultSet.getInt("id_angajat"),
                        resultSet.getDouble("total"),
                        resultSet.getDate("data_emitere").toLocalDate()
                );
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static void inserareBon(Bon bon) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);

            insertStatement = connection.createStatement();
            insertStatement.execute("INSERT INTO bonuri_fiscale (id_raport, id_angajat, total, data_emitere) " +
                    "VALUES ( '" + bon.getId_raport() + "' , '" + bon.getId_angajat() + "' , '" + bon.getTotal() + "' , '" + bon.getData_emitere() + "' )");

        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void udateBon(Bon bon) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;


        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = ("UPDATE bonuri_fiscale " +
                    "SET total = '" + bon.getTotal() + "', data_emitere ='" + bon.getData_emitere() + "' WHERE id = '" + +bon.getId() + "'");
            callableStatement = connection.prepareCall(query);
            callableStatement.executeUpdate();
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static ObservableList<Policlinica> listPoliclinici() {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Policlinica> res = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM policlinici";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                res.add(new Policlinica(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_program_functionare"),
                        resultSet.getString("adresa"),
                        resultSet.getString("denumire")
                ));
            }
            System.out.println(res);
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static ObservableList<Utilizator> listaUtilizatori() {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Utilizator> res = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM utilizatori";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                res.add(new Utilizator(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_cont"),
                        resultSet.getString("departament"),
                        resultSet.getString("adresa"),
                        resultSet.getString("cnp"),
                        resultSet.getString("nume"),
                        resultSet.getString("prenume"),
                        resultSet.getString("telefon"),
                        resultSet.getString("email"),
                        resultSet.getString("iban"),
                        resultSet.getString("data_angajarii"),
                        resultSet.getString("rol")));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static void adaugaUtilizator(String u, String p, String dep, String adr, String cn, String num, String pren, String tel, String em, String ib, Date date, String rol) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;


        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call AdaugaUtilizator(?,?,?,?,?,?,?,?,?,?,?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, u);
            callableStatement.setString(2, p);
            callableStatement.setString(3, dep);
            callableStatement.setString(4, adr);
            callableStatement.setString(5, cn);
            callableStatement.setString(6, num);
            callableStatement.setString(7, pren);
            callableStatement.setString(8, tel);
            callableStatement.setString(9, em);
            callableStatement.setString(10, ib);
            callableStatement.setDate(11, date);
            callableStatement.setString(12, rol);
            callableStatement.executeUpdate();
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void stergeUtilizator(int id, int idCont) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;


        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "DELETE FROM utilizatori WHERE utilizatori.id = '" + id + "'";
            callableStatement = connection.prepareCall(query);
            callableStatement.executeUpdate();
            query = "DELETE FROM conturi WHERE conturi.id = '" + idCont + "'";
            callableStatement = connection.prepareCall(query);
            callableStatement.executeUpdate();
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void actualizeazaUtilizatori(ArrayList<Utilizator> u) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            for (Utilizator it : u) {
                String query = "UPDATE utilizatori " +
                        "SET departament = '" + it.getDepartament() + "', " +
                        "nume = '" + it.getNume() + "', " +
                        "prenume = '" + it.getPrenume() + "', " +
                        "telefon = '" + it.getTelefon() + "', " +
                        "cnp = '" + it.getCnp() + "', " +
                        "rol = '" + it.getRol() + "', " +
                        "email = '" + it.getEmail() + "', " +
                        "iban = '" + it.getIban() + "', " +
                        "data_angajarii = '" + it.getData_angajarii() + "', " +
                        "adresa = '" + it.getAdresa() + "' " +
                        "WHERE id = '" + it.getId() + "'";
                callableStatement = connection.prepareCall(query);
                callableStatement.executeUpdate();
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static ObservableList<Cont> listaConturi() {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Cont> res = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM conturi";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                res.add(new Cont(
                        resultSet.getInt("id"),
                        resultSet.getString("nume_utilizator"),
                        resultSet.getString("parola")
                ));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static void adaugaCont(String user, String pass) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;


        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "INSERT INTO conturi(nume_utilizator, parola) VALUES " +
                    "('" + user + "', '" + pass + "')";
            callableStatement = connection.prepareCall(query);
            callableStatement.executeUpdate();
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void stergeCont(int id) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;


        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "DELETE FROM conturi WHERE conturi.id = '" + id + "'";
            callableStatement = connection.prepareCall(query);
            callableStatement.executeUpdate();
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void actualizeazaConturi(ArrayList<Cont> c) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            for (Cont it : c) {
                String query = "UPDATE conturi " +
                        "SET nume_utilizator = '" + it.getNume_utilizator() + "', " +
                        "parola = '" + it.getParola() + "' " +
                        "WHERE id = '" + it.getId() + "'";
                callableStatement = connection.prepareCall(query);
                callableStatement.executeUpdate();
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static ArrayList<Bon> extrageBonuriMedic(int id_medic) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ArrayList<Bon> res = new ArrayList<>();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT bonuri_fiscale.id, bonuri_fiscale.id_raport, bonuri_fiscale.id_angajat, bonuri_fiscale.total, bonuri_fiscale.data_emitere FROM rapoarte inner join bonuri_fiscale " +
                    "WHERE rapoarte.id = bonuri_fiscale.id_raport and id_medic = '" + id_medic + "'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                res.add(new Bon(
                                resultSet.getInt("id"),
                                resultSet.getInt("id_raport"),
                                resultSet.getInt("id_angajat"),
                                resultSet.getDouble("total"),
                                resultSet.getDate("data_emitere").toLocalDate()
                        )
                );
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static ArrayList<Bon> extrageBonuriPoliclinica(int id_policlinica) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ArrayList<Bon> res = new ArrayList<>();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT bonuri_fiscale.id, bonuri_fiscale.id_raport, bonuri_fiscale.id_angajat, bonuri_fiscale.total, bonuri_fiscale.data_emitere FROM angajati inner join bonuri_fiscale " +
                    "WHERE angajati.id = bonuri_fiscale.id_angajat and angajati.id_policlinica = '" + id_policlinica + "'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                res.add(new Bon(
                                resultSet.getInt("id"),
                                resultSet.getInt("id_raport"),
                                resultSet.getInt("id_angajat"),
                                resultSet.getDouble("total"),
                                resultSet.getDate("data_emitere").toLocalDate()
                        )
                );
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static ObservableList<Angajat> listaAngajatiPoliclinica(int id_poli) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Angajat> angajati = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT a.id, a.id_utilizator, a.id_policlinica, u.nume, u.prenume, a.functie, a.salariu_negociat, a.numar_ore " +
                    "FROM angajati a INNER JOIN utilizatori u ON a.id_utilizator = u.id WHERE a.id_policlinica = '" + id_poli + "'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_utilizator = resultSet.getInt("id_utilizator");
                int id_policlinica = resultSet.getInt("id_policlinica");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String functie = resultSet.getString("functie");
                int salariu_negociat = resultSet.getInt("salariu_negociat");
                int numar_ore = resultSet.getInt("numar_ore");
                angajati.add(new Angajat(id, id_utilizator, id_policlinica, nume, prenume, functie, salariu_negociat, numar_ore));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return angajati;
    }

    public static ObservableList<Specialitati> listaSpecialitati() throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Specialitati> specialitati = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM specialitati";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_medic = resultSet.getInt("id_medic");
                String nume_specialitate = resultSet.getString("nume_specialitate");
                String grad = resultSet.getString("grad");

                specialitati.add(new Specialitati(id, id_medic, nume_specialitate, grad));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return specialitati;
    }

    public static int totalVenitSpecialitate(int idSpecialitate, String data) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        int res = 0;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call TotalServiciiOferiteSpecialitate(?, ?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, Integer.toString(idSpecialitate));
            callableStatement.setString(2, data);
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                res = resultSet.getInt("total_servicii_oferite");
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static ObservableList<Angajat> listaAngajatiSpecialitati(int idSpecialitate) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Angajat> angajati = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT a.id, a.id_utilizator, a.id_policlinica, u.nume, u.prenume, a.functie, a.salariu_negociat, a.numar_ore " +
                    "FROM ((angajati a INNER JOIN utilizatori u ON a.id_utilizator = u.id) " +
                    "INNER JOIN medici ON medici.id_angajat = a.id)" +
                    "INNER JOIN specialitati ON specialitati.id_medic = medici.id" +
                    " WHERE specialitati.id = '" + idSpecialitate + "'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_utilizator = resultSet.getInt("id_utilizator");
                int id_policlinica = resultSet.getInt("id_policlinica");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String functie = resultSet.getString("functie");
                int salariu_negociat = resultSet.getInt("salariu_negociat");
                int numar_ore = resultSet.getInt("numar_ore");
                angajati.add(new Angajat(id, id_utilizator, id_policlinica, nume, prenume, functie, salariu_negociat, numar_ore));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return angajati;
    }

    public static ObservableList<Policlinica> cautaPoliclinica(String input) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Policlinica> res = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM policlinici WHERE policlinici.denumire LIKE '%" + input + "%'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                res.add(new Policlinica(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_program_functionare"),
                        resultSet.getString("adresa"),
                        resultSet.getString("denumire")
                ));
            }
            System.out.println(res);
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static ObservableList<Specialitati> cautaSpecialitati(String input) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Specialitati> specialitati = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM specialitati WHERE nume_specialitate LIKE '%" + input + "%'";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_medic = resultSet.getInt("id_medic");
                String nume_specialitate = resultSet.getString("nume_specialitate");
                String grad = resultSet.getString("grad");

                specialitati.add(new Specialitati(id, id_medic, nume_specialitate, grad));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return specialitati;
    }

    public static ObservableList<ProgramFunctionare> listaProgramFunctionare() {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<ProgramFunctionare> res = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM program_functionare";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String duminica = resultSet.getString("duminica");
                String luni = resultSet.getString("luni");
                String marti = resultSet.getString("marti");
                String miercuri = resultSet.getString("miercuri");
                String joi = resultSet.getString("joi");
                String vineri = resultSet.getString("vineri");
                String sambata = resultSet.getString("sambata");
                res.add(new ProgramFunctionare(id, duminica, luni, marti, miercuri, joi
                        , vineri, sambata));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static void actualizeazaPoliclinici(ArrayList<ProgramFunctionare> pr, ArrayList<Policlinica> po) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            for (ProgramFunctionare it : pr) {
                String query = "UPDATE program_functionare " +
                        "SET duminica = '" + it.getDuminica() + "', " +
                        "luni = '" + it.getLuni() + "', " +
                        "marti = '" + it.getMarti() + "', " +
                        "miercuri = '" + it.getMiercuri() + "', " +
                        "joi = '" + it.getJoi() + "', " +
                        "vineri = '" + it.getVineri() + "', " +
                        "sambata = '" + it.getSambata() + "' " +
                        "WHERE id = '" + it.getId() + "'";
                callableStatement = connection.prepareCall(query);
                callableStatement.executeUpdate();
            }
            for (Policlinica it : po) {
                String query = "UPDATE policlinici " +
                        "SET id_program_functionare = '" + it.getId_program_functionare() + "', " +
                        "denumire = '" + it.getDenumire() + "', " +
                        "adresa = '" + it.getAdresa() + "' " +
                        "WHERE id = '" + it.getId() + "'";
                callableStatement = connection.prepareCall(query);
                callableStatement.executeUpdate();
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void inserareProgramFunctionare(ArrayList<String> pr) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;


        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "INSERT INTO program_functionare(duminica, luni, marti, miercuri, joi, vineri, sambata) VALUES ('" +
                    pr.get(0) + "','" + pr.get(1) + "','" + pr.get(2) + "','" + pr.get(3) + "','" +
                    pr.get(4) + "','" + pr.get(5) + "','" + pr.get(6) + "')";
            callableStatement = connection.prepareCall(query);
            callableStatement.executeUpdate();
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static ObservableList<Serviciu> listaServicii() {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Serviciu> res = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM servicii";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume_serviciu = resultSet.getString("nume_serviciu");
                Double pret = resultSet.getDouble("pret");
                Time durata = resultSet.getTime("durata");
                res.add(new Serviciu(id, nume_serviciu, pret, durata));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static void actualizeazaAngajat(ArrayList<Angajat> a) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            for (Angajat it : a) {
                String query = "UPDATE angajati " +
                        "SET id_policlinica = '" + it.getId_policlinica() + "', " +
                        "functie = '" + it.getFunctie() + "', " +
                        "salariu_negociat = '" + it.getSalariu_negociat() + "', " +
                        "numar_ore = '" + it.getNumar_ore() + "' " +
                        "WHERE id = '" + it.getId() + "'";
                callableStatement = connection.prepareCall(query);
                callableStatement.executeUpdate();
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void inserareAngajatGol(int idUtilizator) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);

            insertStatement = connection.createStatement();
            insertStatement.execute("INSERT INTO angajati (id_utilizator, id_policlinica, functie, salariu_negociat, numar_ore) " +
                    "VALUES ( '" + idUtilizator + "' , 1, ' ', 0, 0 )");

        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static int getUltimulUtilizator() {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        int id = -1;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT  MAX(id) as idMax FROM utilizatori";

            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("idMax");
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return id;
    }

    public static ObservableList<Medic> listaMediciAdmin() throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Medic> medici = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM medici";

            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_angajat = resultSet.getInt("id_angajat");
                String cod_parafa = resultSet.getString("cod_parafa");
                String titlu_stiintific = resultSet.getString("titlu_stiintific");
                String post_didactic = resultSet.getString("post_didactic");
                Double venit_aditional = resultSet.getDouble("venit_aditional");
                medici.add(new Medic(id, id_angajat, cod_parafa, titlu_stiintific, post_didactic, venit_aditional));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return medici;
    }

    public static void actualizeazaMedic(ArrayList<Medic> m) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            for (Medic it : m) {
                String query = "UPDATE medici " +
                        "SET id_angajat = '" + it.getId_angajat() + "', " +
                        "cod_parafa = '" + it.getCod_parafa() + "', " +
                        "titlu_stiintific = '" + it.getTitlu_stiintific() + "', " +
                        "post_didactic = '" + it.getPost_didactic() + "', " +
                        "venit_aditional = '" + it.getVenit_aditional() + "' " +
                        "WHERE id = '" + it.getId() + "'";
                callableStatement = connection.prepareCall(query);
                callableStatement.executeUpdate();
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void inserareMedicGol(int idAngajat) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);

            insertStatement = connection.createStatement();
            insertStatement.execute("INSERT INTO medici (id_angajat, cod_parafa, titlu_stiintific, post_didactic, venit_aditional) " +
                    "VALUES ( '" + idAngajat + "' , ' ', ' ', ' ', 0.0 )");

        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static ObservableList<AsistentMedical> listaAsisteti() throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<AsistentMedical> asistenti = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT  * FROM asistenti_medicali";

            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_angajat = resultSet.getInt("id_angajat");
                String tip = resultSet.getString("tip");
                String grad = resultSet.getString("grad");

                asistenti.add(new AsistentMedical(id, id_angajat, tip, grad));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return asistenti;
    }

    public static void actualizeazaAsistenti(ArrayList<AsistentMedical> a) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            for (AsistentMedical it : a) {
                String query = "UPDATE asistenti_medicali " +
                        "SET id_angajat = '" + it.getId_angajat() + "', " +
                        "tip = '" + it.getTip() + "', " +
                        "grad = '" + it.getGrad() + "' " +
                        "WHERE id = '" + it.getId() + "'";
                callableStatement = connection.prepareCall(query);
                callableStatement.executeUpdate();
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void inserareAsistentGol(int idAngajat) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);

            insertStatement = connection.createStatement();
            insertStatement.execute("INSERT INTO asistenti_medicali (id_angajat, tip, grad) " +
                    "VALUES ( '" + idAngajat + "' , ' ', ' ')");

        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void adaugaServiciuNou(String numeS, Double pretS, Time durataS) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "INSERT INTO servicii(nume_serviciu, pret, durata) VALUES(?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, numeS);
            stmt.setDouble(2, pretS);
            stmt.setTime(3, durataS);
            stmt.executeUpdate();
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void adaugaServiciuSpecialitate(int idSpecialitate, int idServiciu) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM servicii_specialitate WHERE id_specialitate = ? AND id_serviciu = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idSpecialitate);
            stmt.setInt(2, idServiciu);
            resultSet = stmt.executeQuery();
            if (!resultSet.next()) {
                query = "INSERT INTO servicii_specialitate VALUES (?,?)";
                stmt = connection.prepareStatement(query);
                stmt.setInt(1, idSpecialitate);
                stmt.setInt(2, idServiciu);
                stmt.executeUpdate();
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void adaugaServiciuPoliclinica(int idServiciu, int idPoliclinica) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM servicii_oferite_policlinica WHERE id_serviciu = ? AND id_policlinica = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(2, idPoliclinica);
            stmt.setInt(1, idServiciu);
            resultSet = stmt.executeQuery();
            if (!resultSet.next()) {
                query = "INSERT INTO servicii_oferite_policlinica VALUES (?,?)";
                stmt = connection.prepareStatement(query);
                stmt.setInt(1, idPoliclinica);
                stmt.setInt(2, idServiciu);
                stmt.executeUpdate();
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static ObservableList<Medic> listaMediciObs() throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Medic> medici = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call AfiseazaMedici()}";
            callableStatement = connection.prepareCall(query);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_angajat = resultSet.getInt("id_angajat");
                String cod_parafa = resultSet.getString("cod_parafa");
                String titlu_stiintific = resultSet.getString("titlu_stiintific");
                String post_didactic = resultSet.getString("post_didactic");
                Double venit_aditional = resultSet.getDouble("venit_aditional");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                medici.add(new Medic(id, id_angajat, cod_parafa, titlu_stiintific, post_didactic, venit_aditional, nume, prenume));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return medici;
    }

    public static void inserareSpecialitate(Integer id, String specialitate, String grad) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM specialitati WHERE id_medic = ? AND nume_specialitate = ? AND grad = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.setString(2, specialitate);
            stmt.setString(3, grad);
            resultSet = stmt.executeQuery();
            if (!resultSet.next()) {
                query = "INSERT INTO specialitati (id_medic, nume_specialitate, grad) VALUES (?, ?, ?)";
                stmt = connection.prepareStatement(query);
                stmt.setInt(1, id);
                stmt.setString(2, specialitate);
                stmt.setString(3, grad);
                stmt.executeUpdate();
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    static public ObservableList<Policlinica> policliniciCompatibile(int id_medic) throws SQLException {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ObservableList<Policlinica> policlinici = FXCollections.observableArrayList();

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "{call repartizare(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setInt(1, id_medic);
            resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                policlinici.add(new Policlinica(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_program_functionare"),
                        resultSet.getString("adresa"),
                        resultSet.getString("denumire"),
                        resultSet.getInt("nr_servicii_compatibile"),
                        resultSet.getInt("nr_medici")
                ));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return policlinici;
    }

    public static void setAngajatSelectat(Angajat a) {
        angajatSelectat = a;
    }

    public static ArrayList<String> cautaAsistent(int idAsistent) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        ArrayList<String> s = new ArrayList<>();
        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT u.nume, u.prenume FROM utilizatori u " +
                    "INNER JOIN angajati a " +
                    "INNER JOIN asistenti_medicali am " +
                    " WHERE am.id_angajat = a.id AND a.id_utilizator = u.id AND am.id = ?";
            callableStatement = connection.prepareCall(query);
            callableStatement.setInt(1, idAsistent);
            resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                s.add(resultSet.getString("nume"));
                s.add(resultSet.getString("prenume"));
            }
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return s;
    }

    public static boolean verificaAdmin(int id) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        boolean res = false;
        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);
            String query = "SELECT * FROM utilizatori WHERE id_cont = ? AND (rol = 'super_admin' or rol = 'admin')";
            callableStatement = connection.prepareCall(query);
            callableStatement.setInt(1, id);
            resultSet = callableStatement.executeQuery();
            if (resultSet.next())
                res = true;
        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return res;
    }

    public static void inserarePoliclinica(Policlinica policlinica) {
        Connection connection = null;
        Statement selectStatement = null;
        Statement insertStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        CallableStatement callableStatement = null;

        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }

        try {
            connection = DriverManager.
                    getConnection(URL);

            insertStatement = connection.createStatement();
            insertStatement.execute("INSERT INTO policlinici (id_program_functionare, adresa, denumire) " +
                    "VALUES ( '" + policlinica.getId_program_functionare() + "' , '" + policlinica.getAdresa() + "' , '" + policlinica.getDenumire() + "' )");

        } catch (SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}

