package com.example.source.controller.ResurseUmane;

import com.example.source.Model;
import com.example.source.claseTabele.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SceneMain implements Initializable {
    @FXML
    private Label infoServiciu;
    @FXML
    private TextArea infoPoliclinica;
    @FXML
    private Label labelSalariuNegociat;
    @FXML
    private Label labelOreNegociate;
    @FXML
    private Label labelNumarOre;
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
    private Label eror13;
    @FXML
    private Label labelMedic;
    @FXML
    private TextField specialitateMedic;
    @FXML
    private TextField gradMedic;
    @FXML
    private TextField numeServiciuTf;
    @FXML
    private TextField pretServiciuTf;
    @FXML
    private TextField durataServiciuTf1;
    @FXML
    private TextField inputTextField;
    @FXML
    private TableView<Policlinica> tabelPoliclinici;
    @FXML
    private TableColumn<Policlinica, String> denumireCol;
    @FXML
    private TableColumn<Policlinica, String> adresaCol;
    @FXML
    private TableView<Medic> tabelMedici;
    @FXML
    private TableColumn<Medic, String> numeMedic;
    @FXML
    private TableColumn<Medic, String> prenumeMedic;
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
    private TableView<Serviciu> tabelServicii;
    @FXML
    private TableColumn<Serviciu, String> nume_serviciuCol;
    @FXML
    private TableColumn<Serviciu, Double> pretServiciuCol;
    @FXML
    private TableColumn<Serviciu, Time> durataServiciuCol;
    @FXML
    private Label labelLocatie;
    @FXML
    private ChoiceBox<String> alegeLuna;
    @FXML
    private ChoiceBox<String> alegeAn;
    @FXML
    private ChoiceBox<String> serviciiCB;
    @FXML
    private ChoiceBox<String> specialitatiCB;
    @FXML
    private TableView<OrarAngajat> tabelOrar;
    @FXML
    private TableColumn<OrarAngajat, Integer> coloanaZi;
    @FXML
    private TableColumn<OrarAngajat, String> coloanaInterval;
    private String[] luni = new String[]{"Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie",
            "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie"};
    ObservableList<OrarAngajat> orar = FXCollections.observableArrayList();
    ObservableList<Angajat> angajati = FXCollections.observableArrayList();
    ObservableList<Serviciu> servicii = FXCollections.observableArrayList();
    ObservableList<Policlinica> policlinici = FXCollections.observableArrayList();
    ObservableList<Medic> medici = FXCollections.observableArrayList();

    @Override
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
        try {
            setSpecialitatiServicii();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            medici = Model.listaMediciObs();
            angajati = Model.listaAngajati();
            servicii = Model.listaServicii();
            policlinici = Model.listPoliclinici();
            populateTabelServicii();
            populateTabelMedici();
            populateTabel();
            populateTabelPoliclinici();
        } catch (SQLException e) {
            System.out.println("EROARE IN SCENERESURSEUMANE LA INITIALIZARE");
            throw new RuntimeException(e);
        }
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

    public void selecteazaAngajat(ActionEvent event) throws IOException {
        Angajat a = tabel.getSelectionModel().getSelectedItem();
        System.out.println(a);
        try {
            Model.switchToWindowOrare(event, a);
        } catch (NullPointerException e) {
            System.out.println(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private void setSpecialitatiServicii() throws SQLException {
        ObservableList<Serviciu> ser = Model.listaServicii();
        ObservableList<Specialitati> sp = Model.listaSpecialitati();
        String[] stringSer = new String[ser.size()];
        String[] stringSp = new String[sp.size()];
        for (int i = 0; i < ser.size(); i++)
            stringSer[i] = ser.get(i).getNume_serviciu() + "\n\nPRET: " + ser.get(i).getPret() + "   DURATA: " + ser.get(i).getDurata();
        serviciiCB.setValue(stringSer[0]);
        serviciiCB.getItems().addAll(stringSer);

        for (int i = 0; i < sp.size(); i++)
            stringSp[i] = sp.get(i).getNume_specialitate();
        specialitatiCB.setValue(stringSp[0]);
        specialitatiCB.getItems().addAll(stringSp);
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

        labelOreNegociate.setText(Integer.toString(numarOreContract));
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

    private void populateTabelOrar() {
        coloanaZi.setCellValueFactory(new PropertyValueFactory<>("zi"));
        coloanaInterval.setCellValueFactory(new PropertyValueFactory<>("interval"));
        tabelOrar.setItems(orar);
    }

    private void populateTabelServicii() {
        nume_serviciuCol.setCellValueFactory(new PropertyValueFactory<>("nume_serviciu"));
        pretServiciuCol.setCellValueFactory(new PropertyValueFactory<>("pret"));
        durataServiciuCol.setCellValueFactory(new PropertyValueFactory<>("durata"));
        tabelServicii.setItems(servicii);
    }

    private void populateTabelPoliclinici() {
        denumireCol.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        adresaCol.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        tabelPoliclinici.setItems(policlinici);
    }

    private void populateTabelMedici() {
        numeMedic.setCellValueFactory(new PropertyValueFactory<>("nume"));
        prenumeMedic.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        tabelMedici.setItems(medici);
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
            }
        }
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


    public void adaugaServiciuNou(ActionEvent actionEvent) throws SQLException {
        if (numeServiciuTf.getText().isEmpty() || pretServiciuTf.getText().isEmpty() || durataServiciuTf1.getText().isEmpty())
            eror13.setVisible(true);
        else {
            String numeS = numeServiciuTf.getText();
            Double pretS = Double.parseDouble(pretServiciuTf.getText());
            Time durataS = Time.valueOf(durataServiciuTf1.getText());
            eror13.setVisible(false);
            Model.adaugaServiciuNou(numeS, pretS, durataS);
            servicii = Model.listaServicii();
            setSpecialitatiServicii();
            populateTabelServicii();
        }
    }

    public void schimbaS(Event event) {
        eror13.setVisible(false);
    }

    public void adaugaServiciuSpecialitate(ActionEvent actionEvent) throws SQLException {
        ObservableList<Specialitati> sp = Model.listaSpecialitati();
        ObservableList<Serviciu> ser = Model.listaServicii();
        int id_serviciu = 0, id_specialitate = 0;
        ObservableList<String> serObs = serviciiCB.getItems();
        ObservableList<String> spObs = specialitatiCB.getItems();
        for (int i = 0; i < serObs.size(); i++)
            if (serObs.get(i).equals(serviciiCB.getValue()))
                id_serviciu = i + 1;
        for (int i = 0; i < spObs.size(); i++)
            if (spObs.get(i).equals(specialitatiCB.getValue()))
                id_specialitate = i + 1;
        Model.adaugaServiciuSpecialitate(id_specialitate, id_serviciu);
    }

    public void switchToSceneLogin(ActionEvent event) throws IOException {
        String scene = "/com.example.source/scene-login-view.fxml";
        Model.logOut(event, scene);
    }

    public void adaugaSpecialitatePoliclinica(ActionEvent actionEvent) {
        if (tabelServicii.getSelectionModel().getSelectedItem() != null && tabelPoliclinici.getSelectionModel().getSelectedItem() != null) {
            int id_serviciu = tabelServicii.getSelectionModel().getSelectedItem().getId();
            int id_policlinica = tabelPoliclinici.getSelectionModel().getSelectedItem().getId();
            Model.adaugaServiciuPoliclinica(id_serviciu, id_policlinica);
        }
    }

    public void setLabelInfo(MouseEvent mouseEvent) {
        infoServiciu.setText(tabelServicii.getSelectionModel().getSelectedItem().getNume_serviciu());
    }

    public void setLabelPInfo(MouseEvent mouseEvent) {
        Policlinica pol = tabelPoliclinici.getSelectionModel().getSelectedItem();
        String text = pol.getDenumire() + "\n";
        ArrayList <Serviciu> ser = Model.cautaServiciiPoliclinica(pol.getId());
        for (Serviciu it : ser)
            text+= it.getNume_serviciu() + " " + it.getPret() + "LEI  " + it.getDurata() + " ORE\n";
        infoPoliclinica.setText(text);
    }

    public void adaugaSpecialitateMedic(ActionEvent actionEvent) throws SQLException {
        if (specialitateMedic.getText() != null && gradMedic != null) {
            String specialitate = specialitateMedic.getText();
            String grad = gradMedic.getText();
            Model.inserareSpecialitate(tabelMedici.getSelectionModel().getSelectedItem().getId(), specialitate, grad);
            setSpecialitatiServicii();
            populateTabelServicii();
        }
    }

    public void medicSelectat(MouseEvent mouseEvent) throws SQLException {
        Medic medic = tabelMedici.getSelectionModel().getSelectedItem();
        String text = medic.getNume() + " " + medic.getPrenume() + "\n";
        ObservableList<Specialitati> sp = Model.listaSpecialitatiMedic(medic.getId());
        for (Specialitati it:sp)
            text += it.getNume_specialitate() + "\n";
        labelMedic.setText(text);
    }
}