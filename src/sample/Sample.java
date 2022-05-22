package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sample extends Application {

    Scene HomeScene;  //Clinic Reception Scene
    Scene SearchScene;  //Search Scene

    @Override
    public void start(Stage primaryStage) throws Exception {

        //######## Task1: Observablelists
        ObservableList<String> obNames = FXCollections.observableArrayList();
        ObservableList<String> obMobile = FXCollections.observableArrayList();
        ObservableList<String> obClinic = FXCollections.observableArrayList();
        ObservableList<String> obTime = FXCollections.observableArrayList();
        ObservableList<String> obClinicFilter = FXCollections.observableArrayList();

        //The Clinics Reception Scene ------------------------------------------------------------------------------------------
        Label fname = new Label("Patient First Name: ");
        Label lname = new Label("Patient Second Name: ");
        Label mobile = new Label("Patient Mobile Number: ");
        Label ClinicName = new Label("Clinic Name: ");
        Label Date = new Label("Appointment Date: ");
        Label Time = new Label("Appointment Time: ");

        TextField FName = new TextField();
        TextField SName = new TextField();
        TextField Mobile = new TextField();

        ComboBox<String> Clinic = new ComboBox();
        Clinic.getItems().addAll("Dental", "Dermatology", "pediatric", "Eye", "Radiology");
        Clinic.setValue("Dental");

        ComboBox<String> time = new ComboBox();
        time.getItems().addAll("8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM");
        time.setValue("8:00 AM");

        DatePicker date = new DatePicker();
        date.setValue(LocalDate.now());

        GridPane form = new GridPane();
        form.add(fname, 0, 0);
        form.add(FName, 1, 0);

        form.add(lname, 0, 1);
        form.add(SName, 1, 1);

        form.add(mobile, 0, 2);
        form.add(Mobile, 1, 2);

        form.add(ClinicName, 0, 3);
        form.add(Clinic, 1, 3);

        form.add(Date, 0, 4);
        form.add(date, 1, 4);

        form.add(Time, 0, 5);
        form.add(time, 1, 5);

        Button ExportToFile = new Button("ExportToFile");
        
        form.add(ExportToFile, 1, 6);
        
        form.setAlignment(Pos.CENTER);
        form.setHgap(10);
        form.setVgap(10);

        Button Book = new Button("Book");
        Button Delete = new Button("Delete");
        Button Search = new Button("Search");
        Button Clear = new Button("Clear");

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(Book, Delete, Search, Clear);

        Label lblmsg = new Label();

        VBox controls = new VBox(20);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().addAll(form, lblmsg, buttons);

        ListView lvName = new ListView(obNames);

        ListView lvMobile = new ListView(obMobile);

        ListView lvClinic = new ListView(obClinic);

        ListView lvTime = new ListView(obTime);

        HBox lvs = new HBox(10);
        lvs.setAlignment(Pos.CENTER);
        lvs.setMaxWidth(700);
        lvs.setMinWidth(700);

        VBox VBlvName = new VBox(10);

        VBox VBlvMobile = new VBox(10);

        VBox VBlvClinic = new VBox(10);

        VBox VBlvTime = new VBox(10);

        VBlvName.getChildren().addAll(new Label("Full Name: "), new ScrollPane(lvName));
        VBlvMobile.getChildren().addAll(new Label("Mobile Number: "), new ScrollPane(lvMobile));
        VBlvClinic.getChildren().addAll(new Label("Clinic: "), new ScrollPane(lvClinic));
        VBlvTime.getChildren().addAll(new Label("Date and Time: "), new ScrollPane(lvTime));

        lvs.getChildren().addAll(VBlvName, VBlvMobile, VBlvClinic, VBlvTime);

        HBox root = new HBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(controls, lvs);

        HomeScene = new Scene(root, 1100, 500);

        //The Search Scene -----------------------------------------------------------------------------------------
        VBox searchControl = new VBox();
        Label lbltf = new Label("Enter the patient name: ");

        TextField tfSearch = new TextField();

        Button SearchPatient = new Button("Search");

        Label msg = new Label();
        msg.setTextFill(Color.DARKRED);

        HBox paneSearch = new HBox(lbltf, tfSearch, SearchPatient);
        paneSearch.setAlignment(Pos.TOP_LEFT);
        paneSearch.setSpacing(10);

        searchControl.getChildren().addAll(paneSearch, msg);
        searchControl.setAlignment(Pos.TOP_LEFT);
        searchControl.setSpacing(10);

        ComboBox<String> Clinicfilter = new ComboBox();
        Clinicfilter.getItems().addAll("Dental", "Dermatology", "pediatric", "Eye", "Radiology");
        Clinicfilter.setValue("Dental");

        ListView lvPatientbyClinc = new ListView(obClinicFilter);
        lvPatientbyClinc.setPrefWidth(750);

        HBox filter = new HBox(10);
        filter.setAlignment(Pos.TOP_LEFT);
        filter.getChildren().addAll(new Label("Filter patients by clinic: "), Clinicfilter, lvPatientbyClinc);

        Button Back = new Button("Back");
        HBox back = new HBox(10);
        back.setAlignment(Pos.BOTTOM_RIGHT);
        back.getChildren().add(Back);

        VBox root2 = new VBox(10);
        root2.setPadding(new Insets(20));

        root2.getChildren().addAll(searchControl, filter, back);

        SearchScene = new Scene(root2, 1100, 500);

        //Actions of Scene Clinics Reception ------------------------------------------------------------------------------------------
        //###### Task 2.1: Add an ActionEvent on the Book button:
        Book.setOnAction(e -> {
            String Name = FName.getText() + " " + SName.getText();
            if (FName.getText().isEmpty() || SName.getText().isEmpty() || Mobile.getText().equals("")) {
                lblmsg.setText("Enter The patient name and mobile number!");
            } else if (Mobile.getText().length() != 10) {

                lblmsg.setText("The length of Mobile number must be 10 digits");
            } else if (obNames.contains(Name)) {
                lblmsg.setText("The patient name is already exists");
            } else {
                obNames.add(FName.getText() + " " + SName.getText());
                obMobile.add(Mobile.getText());
                obClinic.add(Clinic.getValue());
                obTime.add(date.getValue() + " at " + time.getValue());
            }

        });

        //##### Task 2.3: Add an ActionEvent on the Delete button:
        Delete.setOnAction(e -> {
            if (!lvMobile.getSelectionModel().isEmpty()) {
            String mobilestring = Mobile.getText();
            if (obMobile.contains(mobilestring)) {
                int mobileindex = obMobile.indexOf(mobilestring);
                String Name = FName.getText() + " " + SName.getText();
                lblmsg.setText("Patient " + Name + " was Deleted");
                obMobile.remove(mobileindex);
                obNames.remove(mobileindex);
                obClinic.remove(mobileindex);
                obTime.remove(mobileindex);
            }
            }
        });

        //##### Task 2.4: Add an ActionEvent on the “Search” button:
        Search.setOnAction(e -> {
            primaryStage.setTitle("Search");
            primaryStage.setScene(SearchScene);
        });

        //##### Task 2.5: Add an ActionEvent on the “Clear” button:
        Clear.setOnAction(e -> {
            FName.clear();
            SName.clear();
            Mobile.clear();
        });

        //Actions of Scene Search
        //##### Task 3.1: Add an ActionEvent on the Search button:
        SearchPatient.setOnAction(e -> {
            if (tfSearch.getText().isEmpty()) {
                msg.setText("Enter the patient name to search the information");
            } else if (obNames.size() == 0) {
                msg.setText("No Patient in the list.");
            } else {
                if (obNames.contains(tfSearch.getText())) {
                    int Nameindex = obNames.indexOf(tfSearch.getText());
                    msg.setText("The Phone Number is: " + obMobile.get(Nameindex) + " The Appointment is: " + obTime.get(Nameindex));
                } else {

                    msg.setText("The patient not found.");
                }
            }
        });

        //Task 3.2: Add an ActionEvent on the ClinicFilter combobox:
        Back.setOnAction(e -> {
            primaryStage.setTitle("Address Book");
            primaryStage.setScene(HomeScene);
        });

        //Task 3.3: Add an ActionEvent on the Back button:
        Clinicfilter.setOnAction(e -> {
            obClinicFilter.clear();
            for (int i = 0; i < obClinic.size(); i++) {
                String c = obClinic.get(i);
                if (c.equals(Clinicfilter.getValue())) {
                    obClinicFilter.add("Patient Name: " + obNames.get(i) + " Appointment Date and Time: " + obTime.get(i));
                }
            }
        });
        
        ExportToFile.setOnAction(e -> {
            try {
                PrintWriter writer1 = null;
                writer1 = new PrintWriter(new File("Information.txt"));
                for (int i = 0; i < obClinic.size(); i++) {
                writer1.write("Patient Name: " + obNames.get(i));
                writer1.write(" Phone Number: " + obMobile.get(i));
                writer1.write(" Clinic Name: " + obClinic.get(i));
                writer1.write(" Appointment Date and Time: " + obTime.get(i));
                writer1.write("\n");
                }
                 writer1.flush();
                writer1.close();
            } catch (FileNotFoundException ex) {
               
            }
        });

        //###### Task 2.2: Add Listeners on the lvName, lvMobile, lvClinic and lvTime  ListViews:
        //###### You only need to uncomment the following part
        lvName.getSelectionModel().selectedItemProperty().addListener(e -> {
            lvName.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            int index = lvName.getSelectionModel().getSelectedIndex();
            lvMobile.getSelectionModel().select(index);
            lvClinic.getSelectionModel().select(index);
            lvTime.getSelectionModel().select(index);

            String[] FullName = obNames.get(index).split(" ");
            String FirstName = FullName[0];
            String LastName = FullName[1];
            FName.setText(FirstName);
            SName.setText(LastName);
            Mobile.setText(obMobile.get(index));
            Clinic.setValue(obClinic.get(index));

            String[] DateTime = obTime.get(index).split(" at ");

            date.setValue(LocalDate.parse(DateTime[0]));
            time.setValue(DateTime[1]);

        });

        lvMobile.getSelectionModel().selectedItemProperty().addListener(e -> {
            lvMobile.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            int index = lvMobile.getSelectionModel().getSelectedIndex();
            lvName.getSelectionModel().select(index);
            lvClinic.getSelectionModel().select(index);
            lvTime.getSelectionModel().select(index);

            String[] FullName = obNames.get(index).split(" ");
            String FirstName = FullName[0];
            String LastName = FullName[1];
            FName.setText(FirstName);
            SName.setText(LastName);
            Mobile.setText(obMobile.get(index));
            Clinic.setValue(obClinic.get(index));
            String[] DateTime = obTime.get(index).split(" at ");

            date.setValue(LocalDate.parse(DateTime[0]));
            time.setValue(DateTime[1]);

        });

        lvClinic.getSelectionModel().selectedItemProperty().addListener(e -> {
            lvClinic.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            int index = lvClinic.getSelectionModel().getSelectedIndex();
            System.out.println(index);
            lvName.getSelectionModel().select(index);
            lvMobile.getSelectionModel().select(index);
            lvTime.getSelectionModel().select(index);

            String[] FullName = obNames.get(index).split(" ");
            String FirstName = FullName[0];
            String LastName = FullName[1];
            FName.setText(FirstName);
            SName.setText(LastName);
            Mobile.setText(obMobile.get(index));
            Clinic.setValue(obClinic.get(index));
            String[] DateTime = obTime.get(index).split(" at ");

            date.setValue(LocalDate.parse(DateTime[0]));
            time.setValue(DateTime[1]);

        });

        lvTime.getSelectionModel().selectedItemProperty().addListener(e -> {
            lvTime.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            int index = lvTime.getSelectionModel().getSelectedIndex();
            lvName.getSelectionModel().select(index);
            lvMobile.getSelectionModel().select(index);
            lvClinic.getSelectionModel().select(index);

            String[] FullName = obNames.get(index).split(" ");
            String FirstName = FullName[0];
            String LastName = FullName[1];
            FName.setText(FirstName);
            SName.setText(LastName);
            Mobile.setText(obMobile.get(index));
            Clinic.setValue(obClinic.get(index));
            String[] DateTime = obTime.get(index).split(" at ");

            date.setValue(LocalDate.parse(DateTime[0]));
            time.setValue(DateTime[1]);

        });
        primaryStage.setTitle("Clinics Reception");
        primaryStage.setScene(HomeScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
