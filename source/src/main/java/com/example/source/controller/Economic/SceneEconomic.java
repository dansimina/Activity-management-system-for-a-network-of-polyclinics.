package com.example.source.controller.Economic;

import com.example.source.Model;
import com.example.source.claseTabele.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SceneEconomic implements Initializable {
    @FXML
    private Label labelSalariuNegociat;
    @FXML
    private Label labelNumarOre;
    @FXML
    private Label labelNumarOreNegociate;
    @FXML
    private Label labelSalariuCalculat;
    @FXML
    private Label labelNume;
    @FXML
    private Label labelPrenume;
    @FXML
    private Label labelDepartament;
    @FXML
    private Label labelAdresa;
    @FXML
    private Label labelCnp;
    @FXML
    private Label labelTelefon;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelIban;
    @FXML
    private Label labelDataAngajarii;
    @FXML
    private Label labelLocatie;
    @FXML
    private Button buttonLogOut;
    @FXML
    private TextField inputTextField;
    @FXML
    private TextField inputTextFieldPoliclinici;
    @FXML
    private TextField inputTextFieldSpecialitati;
    @FXML
    private TableView<Angajat> tabel;
    @FXML
    private TableColumn<Angajat, Integer> id;
    @FXML
    private TableColumn<Angajat, Integer> id_utilizator;
    @FXML
    private TableColumn<Angajat, String> nume;
    @FXML
    private TableColumn<Angajat, String> prenume;
    @FXML
    private TableColumn<Angajat, String> functie;
    @FXML
    private ChoiceBox<String> alegeLuna;
    @FXML
    private ChoiceBox<String> alegeAn;
    @FXML
    private ChoiceBox<String> alegeLunaAngajati;
    @FXML
    private ChoiceBox<String> alegeAnAngajati;
    @FXML
    private ChoiceBox<String> alegeLunaPoliclinici;
    @FXML
    private ChoiceBox<String> alegeAnPoliclinici;
    @FXML
    private ChoiceBox<String> alegeLunaSpecialitati;
    @FXML
    private ChoiceBox<String> alegeAnSpecialitati;
    @FXML
    private TableView<OrarAngajat> tabelOrar;
    @FXML
    private TableColumn<OrarAngajat, Integer> coloanaZi;
    @FXML
    private TableColumn<OrarAngajat, String> coloanaInterval;
    @FXML
    private TableView<Policlinica> tabelPoliclinici;
    @FXML
    private TableColumn<Policlinica, String> denumire;
    @FXML
    private TableColumn<Policlinica, String> adresa;
    @FXML
    private TableView<Specialitati> tabelSpecialitati;
    @FXML
    private TableColumn<Specialitati, String> numeSpecialitate;
    @FXML
    private TableColumn<Specialitati, String> grad;
    private String[] luni = new String[]{"Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie",
            "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie"};
    ObservableList<OrarAngajat> orar = FXCollections.observableArrayList();
    ObservableList<Angajat> angajati = FXCollections.observableArrayList();
    ObservableList<Policlinica> policlinici = FXCollections.observableArrayList();
    ObservableList<Specialitati> specialitati = FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelNume.setText(Model.getUtilizatorCurent().getNume());
        labelPrenume.setText(Model.getUtilizatorCurent().getPrenume());
        labelDepartament.setText(Model.getUtilizatorCurent().getDepartament());
        labelAdresa.setText(Model.getUtilizatorCurent().getAdresa());
        labelCnp.setText(Model.getUtilizatorCurent().getCnp());
        labelTelefon.setText(Model.getUtilizatorCurent().getTelefon());
        labelEmail.setText(Model.getUtilizatorCurent().getEmail());
        labelIban.setText(Model.getUtilizatorCurent().getIban());
        labelDataAngajarii.setText(Model.getUtilizatorCurent().getData_angajarii());
        labelLocatie.setText(Model.getUtilizatorCurent().getAdresa());
        setAlegeLunaAn();
        setAlegeLunaAnAngajati();
        setAlegeLunaAnPoliclinici();
        setAlegeLunaAnSpecialitati();
        try {
            angajati = Model.listaAngajati();
            populateTabel();
            policlinici = Model.listPoliclinici();
            populateTabelPoliclinici();
            specialitati = Model.listaSpecialitati();
            populateTabelSpecialitati();
        } catch (SQLException e) {
            System.out.println("EROARE IN SCENERESURSEUMANE LA INITIALIZARE");
            throw new RuntimeException(e);
        }
    }

    private void setAlegeLunaAnSpecialitati() {
        alegeLunaSpecialitati.setValue(luni[LocalDate.now().getMonth().getValue() - 1]);
        alegeLunaSpecialitati.getItems().addAll(luni);
        int anCurent = LocalDate.now().getYear();
        alegeAnSpecialitati.setValue(Integer.toString(anCurent));
        alegeAnSpecialitati.getItems().addAll(new String[]{Integer.toString(anCurent), Integer.toString(anCurent - 1), Integer.toString(anCurent + 1)});
    }

    private void populateTabelSpecialitati() {
        numeSpecialitate.setCellValueFactory(new PropertyValueFactory<>("nume_specialitate"));
        grad.setCellValueFactory(new PropertyValueFactory<>("grad"));
        tabelSpecialitati.setItems(specialitati);
    }

    private void setAlegeLunaAnAngajati() {
        alegeLunaAngajati.setValue(luni[LocalDate.now().getMonth().getValue() - 1]);
        alegeLunaAngajati.getItems().addAll(luni);
        int anCurent = LocalDate.now().getYear();
        alegeAnAngajati.setValue(Integer.toString(anCurent));
        alegeAnAngajati.getItems().addAll(new String[]{Integer.toString(anCurent), Integer.toString(anCurent - 1), Integer.toString(anCurent + 1)});
    }

    private void setAlegeLunaAnPoliclinici() {
        alegeLunaPoliclinici.setValue(luni[LocalDate.now().getMonth().getValue() - 1]);
        alegeLunaPoliclinici.getItems().addAll(luni);
        int anCurent = LocalDate.now().getYear();
        alegeAnPoliclinici.setValue(Integer.toString(anCurent));
        alegeAnPoliclinici.getItems().addAll(new String[]{Integer.toString(anCurent), Integer.toString(anCurent - 1), Integer.toString(anCurent + 1)});
    }

    private void populateTabelPoliclinici() {
        denumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        adresa.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        tabelPoliclinici.setItems(policlinici);
    }

    private void setAlegeLunaAn() {
        alegeLuna.setValue(luni[LocalDate.now().getMonth().getValue() - 1]);
        alegeLuna.getItems().addAll(luni);
        int anCurent = LocalDate.now().getYear();
        alegeAn.setValue(Integer.toString(anCurent));
        alegeAn.getItems().addAll(new String[]{Integer.toString(anCurent), Integer.toString(anCurent - 1), Integer.toString(anCurent + 1)});
        afiseazaOrar();
        calculeazaVenituri();
    }

    private void calculeazaVenituri() {
        int salariuNegociat = Model.getAngajatCurent().getSalariu_negociat();
        labelSalariuNegociat.setText(Integer.toString(salariuNegociat) + " LEI");
        int numarOreContract = Model.getAngajatCurent().getNumar_ore();
        int numarOreLucrate = 0;
        for (int i = 0; i < orar.size(); i++)
            if (orar.get(i).getInterval().equals("CONCEDIU"))
                numarOreLucrate -= (int) orar.get(i).getDiferenta();
            else
                numarOreLucrate += (int) orar.get(i).getDiferenta();

        labelNumarOreNegociate.setText(Integer.toString(numarOreContract));
        labelNumarOre.setText(Integer.toString(numarOreLucrate));
        int salariuCalculat = (numarOreLucrate * salariuNegociat) / numarOreContract;
        labelSalariuCalculat.setText(Integer.toString(salariuCalculat) + " LEI");
    }

    public void afiseazaOrar() {
        int policlinica = Model.getAngajatCurent().getId_policlinica();
        ArrayList<String> orarString = Model.orarPoliclinica(policlinica);
        String luna = alegeLuna.getValue();
        int numarLuna = getNumarLuna(luna);
        int an = Integer.parseInt(alegeAn.getValue());
        LocalDate data = LocalDate.of(an, numarLuna, 1);
        HashMap<String, String> H = faHashMap(orarString);
        orar.clear();
        for (int i = 1; i <= data.lengthOfMonth(); i++)
            orar.add(new OrarAngajat(i, H.get(LocalDate.of(an, numarLuna, i).getDayOfWeek().toString())));
        puneConcediuInOrar(data);
        populateTabelOrar();
        calculeazaVenituri();
    }

    public void cautaAngajat(ActionEvent event) throws IOException, SQLException {
        String input = inputTextField.getText().trim();
        if (!input.isEmpty()) {
            angajati = Model.cautaAngajat(input);
            populateTabel();
        } else {
            angajati = Model.listaAngajati();
            populateTabel();
        }
    }

    private void populateTabel() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id_utilizator.setCellValueFactory(new PropertyValueFactory<>("id_utilizator"));
        nume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        prenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        functie.setCellValueFactory(new PropertyValueFactory<>("functie"));
        tabel.setItems(angajati);
    }

    private void populateTabelOrar() {
        coloanaZi.setCellValueFactory(new PropertyValueFactory<>("zi"));
        coloanaInterval.setCellValueFactory(new PropertyValueFactory<>("interval"));
        tabelOrar.setItems(orar);
    }

    private int getNumarLuna(String luna) {
        for (int i = 0; i < 12; i++)
            if (luni[i].equals(luna))
                return i + 1;
        return 1;
    }

    private void puneConcediuInOrar(LocalDate data) {
        ArrayList<DataConcediu> concedii = Model.angajatInConcediu(Model.getAngajatCurent().getId());
        for (DataConcediu it : concedii) {
            LocalDate data_inceput = it.getData_inceput().toLocalDate();
            LocalDate data_sfarsit = it.getData_sfarsit().toLocalDate();
            if (data_sfarsit.getYear() == data_inceput.getYear()) {
                if (data_sfarsit.getMonth() == data_inceput.getMonth() && data_sfarsit.getMonth() == data.getMonth() && data.getYear() == data_inceput.getYear())
                    for (int i = data_inceput.getDayOfMonth() - 1; i < data_sfarsit.getDayOfMonth(); i++)
                        orar.get(i).setInterval("CONCEDIU");
                else {
                    if (data_inceput.getMonth() != data_sfarsit.getMonth()) {
                        if (data_inceput.getMonth() == data.getMonth())
                            for (int i = data_inceput.getDayOfMonth() - 1; i < data.lengthOfMonth(); i++)
                                orar.get(i).setInterval("CONCEDIU");
                        if (data_sfarsit.getMonth() == data.getMonth())
                            for (int i = 0; i < data_sfarsit.getDayOfMonth(); i++)
                                orar.get(i).setInterval("CONCEDIU");
                    }
                }
            } else {
                System.out.println(data_inceput + " " + data_sfarsit);
                if (data_inceput.getYear() == data.getYear() && data_inceput.getMonth() == data.getMonth())
                    for (int i = data_inceput.getDayOfMonth() - 1; i < data.lengthOfMonth(); i++)
                        orar.get(i).setInterval("CONCEDIU");
                if (data_sfarsit.getYear() == data.getYear() && data_sfarsit.getMonth() == data.getMonth())
                    for (int i = 0; i < data_sfarsit.getDayOfMonth(); i++)
                        orar.get(i).setInterval("CONCEDIU");
//                        orar.set(i, new OrarAngajat(i + 1, "CONCEDIU"));
            }
        }
    }

    public void selecteazaAngajat(ActionEvent event) throws IOException, SQLException {
        Angajat angajat = tabel.getSelectionModel().getSelectedItem();
        if(angajat.getFunctie().equals(Model.MEDIC)) {
            Medic medic = Model.cautaMedic(angajat.getId());
            ArrayList<Bon> bonuri = Model.extrageBonuriMedic(medic.getId());
            afiseazaVenitMedic(calculeazaVenitDupaOrarMedic(alegeLunaAngajati, alegeAnAngajati, angajat, medic), medic, sumaBonuriLunaAleasa(alegeLunaAngajati, alegeAnAngajati,bonuri));
        }
        else
            afiseazaVenitAngajat(calculeazaVenitDupaOrarAngajat(alegeLunaAngajati, alegeAnAngajati,angajat));
    }

    private HashMap<String, String> faHashMap(ArrayList<String> orar) {
        HashMap<String, String> H = new HashMap<>();
        H.put("SUNDAY", orar.get(0));
        H.put("MONDAY", orar.get(1));
        H.put("TUESDAY", orar.get(2));
        H.put("WEDNESDAY", orar.get(3));
        H.put("THURSDAY", orar.get(4));
        H.put("FRIDAY", orar.get(5));
        H.put("SATURDAY", orar.get(6));
        return H;
    }

    public void switchToSceneLogin(ActionEvent event) throws IOException {
        String scene = "/com.example.source/scene-login-view.fxml";
        Model.logOut(event, scene);
    }

    private void afiseazaVenitAngajat(int venit) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("VENIT ANGAJAT");
        a.setHeaderText("Venit: " + venit + " LEI");
        a.showAndWait();
    }

    private void afiseazaVenitMedic(int venit, Medic medic, int sumaBonuri) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("VENIT ANGAJAT");
        a.setHeaderText("Venit total: " + (venit + medic.getVenit_aditional() * sumaBonuri) + " LEI\nTotal consultatii: " + sumaBonuri + " LEI\nProfit: " + (sumaBonuri - (venit + medic.getVenit_aditional() * sumaBonuri)) + " LEI");
        a.setContentText("Venit: " + venit + " LEI\nProcent venit aditional: " + medic.getVenit_aditional() + " LEI\nVenit aditional calculat: " + (medic.getVenit_aditional() * sumaBonuri) + " LEI");
        a.showAndWait();
    }

    public int calculeazaVenitDupaOrarAngajat(ChoiceBox<String> alegeLuna, ChoiceBox<String> alegeAn, Angajat angajat) {
        ObservableList<OrarAngajat> orar = FXCollections.observableArrayList();
        int policlinica = angajat.getId_policlinica();
        ArrayList<String> orarString = Model.orarPoliclinica(policlinica);
        String luna = alegeLuna.getValue();
        int numarLuna = getNumarLuna(luna);
        int an = Integer.parseInt(alegeAn.getValue());
        LocalDate data = LocalDate.of(an, numarLuna, 1);
        HashMap<String, String> H = faHashMap(orarString);
        orar.clear();
        for (int i = 1; i <= data.lengthOfMonth(); i++)
            orar.add(new OrarAngajat(i, H.get(LocalDate.of(an, numarLuna, i).getDayOfWeek().toString())));

        if(orar.isEmpty())
            return -1;

        orar = puneConcediuInOrarAngajat(data, orar, angajat);
        return calculeazaVenitOrar(orar, angajat);
    }

    public int calculeazaVenitDupaOrarMedic(ChoiceBox<String> alegeLuna, ChoiceBox<String> alegeAn, Angajat angajat, Medic medic) {
        ObservableList<OrarAngajat> orar = FXCollections.observableArrayList();
        int policlinica = angajat.getId_policlinica();
        ArrayList<String> orarString = Model.orarPoliclinica(policlinica);
        String luna = alegeLuna.getValue();
        int numarLuna = getNumarLuna(luna);
        int an = Integer.parseInt(alegeAn.getValue());
        LocalDate data = LocalDate.of(an, numarLuna, 1);
        HashMap<String, String> H = faHashMap(orarString);
        orar.clear();
        for (int i = 1; i <= data.lengthOfMonth(); i++) {
            data = LocalDate.of(an, numarLuna, i);
            String interval = Model.programMedicZi(medic.getId(), Date.valueOf(data));
            orar.add(new OrarAngajat(i, interval));
        }
        orar = puneConcediuInOrarAngajat(data, orar, angajat);
        return calculeazaVenitOrar(orar, angajat);
    }

    private int calculeazaVenitOrar(ObservableList<OrarAngajat> orar, Angajat angajat) {
        int salariuNegociat = angajat.getSalariu_negociat();
        int numarOreContract = angajat.getNumar_ore() > 0 ? angajat.getNumar_ore() : 1;
        int numarOreLucrate = 0;
        for (int i = 0; i < orar.size(); i++)
            if (orar.get(i).getInterval().equals("CONCEDIU"))
                numarOreLucrate -= (int) orar.get(i).getDiferenta();
            else
                numarOreLucrate += (int) orar.get(i).getDiferenta();

        return (numarOreLucrate * salariuNegociat) / numarOreContract;
    }

    private ObservableList<OrarAngajat> puneConcediuInOrarAngajat(LocalDate data, ObservableList<OrarAngajat> orar, Angajat angajat) {
        ArrayList<DataConcediu> concedii = Model.angajatInConcediu(angajat.getId());
        for (DataConcediu it : concedii) {
            LocalDate data_inceput = it.getData_inceput().toLocalDate();
            LocalDate data_sfarsit = it.getData_sfarsit().toLocalDate();
            if (data_sfarsit.getYear() == data_inceput.getYear()) {
                if (data_sfarsit.getMonth() == data_inceput.getMonth() && data_sfarsit.getMonth() == data.getMonth() && data.getYear() == data_inceput.getYear())
                    for (int i = data_inceput.getDayOfMonth() - 1; i < data_sfarsit.getDayOfMonth(); i++)
                        orar.get(i).setInterval("CONCEDIU");
                else {
                    if (data_inceput.getMonth() != data_sfarsit.getMonth()) {
                        if (data_inceput.getMonth() == data.getMonth())
                            for (int i = data_inceput.getDayOfMonth() - 1; i < data.lengthOfMonth(); i++)
                                orar.get(i).setInterval("CONCEDIU");
                        if (data_sfarsit.getMonth() == data.getMonth())
                            for (int i = 0; i < data_sfarsit.getDayOfMonth(); i++)
                                orar.get(i).setInterval("CONCEDIU");
                    }
                }
            } else {
                if (data_inceput.getYear() == data.getYear() && data_inceput.getMonth() == data.getMonth())
                    for (int i = data_inceput.getDayOfMonth() - 1; i < data.lengthOfMonth(); i++)
                        orar.get(i).setInterval("CONCEDIU");
                if (data_sfarsit.getYear() == data.getYear() && data_sfarsit.getMonth() == data.getMonth())
                    for (int i = 0; i < data_sfarsit.getDayOfMonth(); i++)
                        orar.get(i).setInterval("CONCEDIU");
            }
        }
        return orar;
    }

    private int sumaBonuriLunaAleasa(ChoiceBox<String> alegeLuna, ChoiceBox<String> alegeAn, ArrayList<Bon> bonuri) {
        int suma = 0;
        for(Bon bon: bonuri) {
            if(getNumarLuna(alegeLuna.getValue()) == bon.getData_emitere().getMonthValue() && alegeAn.getValue().compareToIgnoreCase("" + bon.getData_emitere().getYear()) == 0) {
                suma += bon.getTotal();
            }

        }
        return suma;
    }

    public void selecteazaPoliclinica(ActionEvent event) throws IOException, SQLException {
        Policlinica policlinica = tabelPoliclinici.getSelectionModel().getSelectedItem();
        ArrayList<Bon> bonuri = Model.extrageBonuriPoliclinica(policlinica.getId());
        ObservableList<Angajat> angajati = Model.listaAngajatiPoliclinica(policlinica.getId());

        System.out.println(angajati);

        int totalBonuri = sumaBonuriLunaAleasa(alegeLunaPoliclinici, alegeAnPoliclinici, bonuri);
        double totalSalariiAngajati = sumaSalariiAngajatiPoliclinica(alegeLunaPoliclinici, alegeAnPoliclinici, angajati);
        afiseazaVenitPoliclinica(totalBonuri, totalSalariiAngajati);
    }

    private void afiseazaVenitPoliclinica(int totalBonuri, double totalSalariiAngajati) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("VENIT POLICLINICA");
        a.setHeaderText("Profit: " + (totalBonuri - totalSalariiAngajati) + " LEI");
        a.setContentText("Total incasari: " + totalBonuri + " LEI" + "\nTotal plati salarii: " + totalSalariiAngajati + " LEI");
        a.showAndWait();
    }

    private double sumaSalariiAngajatiPoliclinica(ChoiceBox<String> alegeLunaPoliclinici, ChoiceBox<String> alegeAnPoliclinici, ObservableList<Angajat> angajati) throws SQLException {
        double suma = 0;
        for(Angajat angajat: angajati) {
            if(angajat.getFunctie().equals(Model.MEDIC)) {
                Medic medic = Model.cautaMedic(angajat.getId());
                ArrayList<Bon> bonuri = Model.extrageBonuriMedic(medic.getId());
                suma += calculeazaVenitDupaOrarMedic(alegeLunaPoliclinici, alegeAnPoliclinici, angajat, medic);
                suma += medic.getVenit_aditional() * sumaBonuriLunaAleasa(alegeLunaPoliclinici, alegeAnPoliclinici, bonuri);
            }
            else {
                suma += calculeazaVenitDupaOrarAngajat(alegeLunaPoliclinici, alegeAnPoliclinici, angajat);
            }
        }

        return suma;
    }

    private void afiseazaVenitSpecialitate(int totalServicii, double totalSalariiAngajati) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("VENIT SPECIALITATE");
        a.setHeaderText("Profit: " + (totalServicii - totalSalariiAngajati) + " LEI");
        a.setContentText("Total incasari: " + totalServicii + " LEI" + "\nTotal plati salarii: " + totalSalariiAngajati + " LEI");
        a.showAndWait();
    }

    public void selecteazaSpecialitate(ActionEvent event) throws IOException, SQLException {
        Specialitati specialitati = tabelSpecialitati.getSelectionModel().getSelectedItem();
        ObservableList<Angajat> angajati = Model.listaAngajatiSpecialitati(specialitati.getId());
        String data = alegeAnSpecialitati.getValue() + "-" + getNumarLuna(alegeLunaSpecialitati.getValue()) + "-1";
        System.out.println(data);
        int venitServicii = Model.totalVenitSpecialitate(specialitati.getId(), data);
        double sumaSalariiMedici = sumaSalariiAngajatiPoliclinica(alegeLunaSpecialitati, alegeAnSpecialitati, angajati);
        System.out.println(angajati);
        afiseazaVenitSpecialitate(venitServicii, sumaSalariiMedici);
    }

    public void cautaPoliclinica(ActionEvent event) throws IOException, SQLException {
        String input = inputTextFieldPoliclinici.getText().trim();
        if (!input.isEmpty()) {
            policlinici = Model.cautaPoliclinica(input);
            populateTabelPoliclinici();
        } else {
            policlinici = Model.listPoliclinici();
            populateTabelPoliclinici();
        }
    }

    public void cautaSpecialitati(ActionEvent event) throws IOException, SQLException {
        String input = inputTextFieldSpecialitati.getText().trim();
        if (!input.isEmpty()) {
            specialitati = Model.cautaSpecialitati(input);
            populateTabelSpecialitati();
        } else {
            specialitati = Model.listaSpecialitati();
            populateTabelSpecialitati();
        }
    }

    public void afiseazaConcediu(ActionEvent actionEvent) throws SQLException, IOException {
        Angajat a = tabel.getSelectionModel().getSelectedItem();
        Model.setAngajatSelectat(a);
        Model.switchToWindowConcedii(actionEvent, a);
    }
}
