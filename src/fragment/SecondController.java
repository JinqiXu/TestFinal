package fragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.ResourceBundle;

import fragment.VideoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SecondController implements Initializable {
    @FXML
    private TableView<VideoType> tableView;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_extract;
    @FXML
    private Button btn_Check;

    String[] types = new String[10];
    int count = 0;

    ObservableList<VideoType> list = FXCollections.observableArrayList();

    TableColumn<VideoType, String> videoColumn = new TableColumn<VideoType, String>("视频类别");

    // Event Listener on Button[#btn_add].onAction
    @FXML
    public void event_btn_add(ActionEvent event) {
        // TODO Autogenerated
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("/home/xujinqi/C3D/examples/temp/input"));
        File file = directoryChooser.showDialog(new Stage());
        String path = file.toString();
        String osName = System.getProperty("os.name", "");
        String str = null;
        if (osName.startsWith("Windows")) {
            str = "\\";
        } else {
            str = "/";
        }
        String value = path.substring(path.lastIndexOf(str) + 1);
        types[count] = value;
        count++;
        VideoType videoType1 = new VideoType(value);
        list.add(videoType1);
        videoColumn.setCellValueFactory(cellData -> cellData.getValue().getVideoProperty());
        tableView.setItems(list);
    }

    @FXML
    public void event_btn_extract(ActionEvent event) {

        for (int i = 0; i < types.length; i++) {
            System.out.println(types[i]);
        }

        try {
            for (int i = 0; i < types.length; i++) {
                Process process = Runtime.getRuntime().exec("python /home/xujinqi/C3D/examples/temp/extract.py " + types[i]);
                InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
                LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);
                String line;
                while ((line = lineNumberReader.readLine()) != null) {
                    System.out.println(line);
                }
            }
            Parent root = FXMLLoader.load(getClass().getResource("/model/ProgressBar1.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    public void event_btn_check(ActionEvent event) {

        String osName = System.getProperty("os.name", "");
        if (osName.startsWith("Windows")) {
            // Windows
            if (java.awt.Desktop.isDesktopSupported()) {
                java.net.URI uri = java.net.URI.create("https://localhost:8080");
                java.awt.Desktop dp = java.awt.Desktop.getDesktop();
                if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                    try {
                        dp.browse(uri);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        } else {
            // Ubuntu
            String url = "firefox localhost:8080";
            try {
                Runtime.getRuntime().exec(url);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
//		VideoType videoType1 = new VideoType("fire");
//		VideoType videoType2 = new VideoType("gun");
//		VideoType videoType3 = new VideoType("human");
//		VideoType videoType4 = new VideoType("time");
//
//		ObservableList<VideoType> list = FXCollections.observableArrayList();
//
//		list.addAll(videoType1, videoType2, videoType3, videoType4);
//
//		videoColumn.setCellValueFactory(cellData -> cellData.getValue().getVideoProperty());
//		tableView.setItems(list);

        tableView.getColumns().add(videoColumn);
    }

}
