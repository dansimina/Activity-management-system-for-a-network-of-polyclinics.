package com.example.source.controller.Asistent;

import com.example.source.Model;
import com.example.source.claseTabele.DataConcediu;
import com.example.source.claseTabele.OrarAngajat;
import com.example.source.claseTabele.Programare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SceneAsistentMedical implements Initializable {
    @FXML
    private Label labelSalariuNegociat;
    @FXML
    private Label labelNumarOreNegociate;
    @FXML
    private Label labelNumarOre;
    @FXML
    private Label labelSalariuCalculat;
    @FXML
    private Label label1;
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
    private Label labelTip;
    @FXML
    private Label labelGrad;
    @FXML
    private Label labelLocatie;
    @FXML
    private Button buttonLogOut;
    @FXML
    private TextField cautaPacientProgramatTextField;
    @FXML
    private ChoiceBox<String> alegeLuna;
    @FXML
    private ChoiceBox<String> alegeAn;
    @FXML
    private TableView<OrarAngajat> tabelOrar;
    @FXML
    private TableColumn<OrarAngajat, Integer> coloanaZi;
    @FXML
    private TableColumn<OrarAngajat, String> coloanaInterval;
    @FXML
    private TableView<Programare> tabelPacienti;
    @FXML
    private TableColumn<Programare, String> numePacient;
    @FXML
    private TableColumn<Programare, String> prenumePacient;
    @FXML
    private TableColumn<Programare, String> dataProgramare;
    @FXML
    private TableColumn<Programare, String> oraProgramare;
    private String[] luni = new String[]{"Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie",
            "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie"};
    ObservableList<OrarAngajat> orar = FXCollections.observableArrayList();
    ObservableList<Programare> programari = FXCollections.observableArrayList();

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
        labelTip.setText(Model.getAsistentCurent().getTip());
        labelGrad.setText(Model.getAsistentCurent().getGrad());
        labelLocatie.setText(Model.getUtilizatorCurent().getAdresa());
        setAlegeLunaAn();
        afiseazaPacientiProgramati();
    }

    private void populateTabelOrar() {
        coloanaZi.setCellValueFactory(new PropertyValueFactory<>("zi"));
        coloanaInterval.setCellValueFactory(new PropertyValueFactory<>("interval"));
        tabelOrar.setItems(orar);
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

    public void afiseazaPacientiProgramatiAzi() {
        programari.clear();
        label1.setText("");
        programari = Model.pacientiProgramatAziLaPoliclinica(Model.getAngajatCurent().getId_policlinica());
        populateTabelPacienti();
    }

    public void afiseazaPacientiProgramati() {
        programari.clear();
        label1.setText("");
        String text = cautaPacientProgramatTextField.getText();
        if (text.isEmpty())
            programari = Model.pacientiProgramati(Model.getAngajatCurent().getId_policlinica());
        else
            switch (text.split(" ").length) {
                case 1:
                    programari = Model.pacientiProgramati(Model.getAngajatCurent().getId_policlinica(), text.split(" ")[0]);
                    break;
                case 2:
                    programari = Model.pacientiProgramati(Model.getAngajatCurent().getId_policlinica(), text.split(" ")[0], text.split(" ")[1]);
                    break;
                default:
                    System.out.println("DOAR DOUA CUVINTE");
                    break;
            }
        populateTabelPacienti();
    }

    public void selecteazaPacient(ActionEvent event) throws IOException, SQLException {
        Programare p = tabelPacienti.getSelectionModel().getSelectedItem();
        System.out.println(p);
        if (p != null && p.isInregistrat()) {
            label1.setText("");
            switchToSceneRaport(event);
        } else
            label1.setText("ALEGE UN PACIENT INREGISTRAT");
    }

    private void populateTabelPacienti() {
        numePacient.setCellValueFactory(new PropertyValueFactory<>("nume"));
        prenumePacient.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        dataProgramare.setCellValueFactory(new PropertyValueFactory<>("_data"));
        oraProgramare.setCellValueFactory(new PropertyValueFactory<>("ora_inceput"));
        tabelPacienti.setItems(programari);
    }

    public void switchToSceneRaport(ActionEvent event) throws IOException, SQLException {
        label1.setText("");
        String scene = "/com.example.source/scene-raport-view.fxml";
        Model.switchToWindowRaport(event, tabelPacienti.getSelectionModel().getSelectedItem());
    }

    public void switchToSceneLogin(ActionEvent event) throws IOException {
        String scene = "/com.example.source/scene-login-view.fxml";
        Model.logOut(event, scene);
    }
}
