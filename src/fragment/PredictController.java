package fragment;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PredictController implements Initializable {
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnChoose;
    @FXML
    private Button btnPredict;
    @FXML
    private Button btnCheck;
    @FXML
    private TableView<VideoType> tableViewVideo;
    @FXML
    private TableView<ModelType> tableViewModel;

    TableColumn<VideoType, String> videoColumn = new TableColumn<>("视频类型");
    TableColumn<ModelType, String> modelColumn = new TableColumn<>("当前模型");

    ObservableList<VideoType> list1 = FXCollections.observableArrayList();
    ObservableList<ModelType> list2 = FXCollections.observableArrayList();

    // Event Listener on Button[#btnAdd].onAction
    @FXML
    public void event_add(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("/home/xujinqi/C3D/examples/temp/out/test"));
        File file = fileChooser.showOpenDialog(new Stage());
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Videos", "*.*"),
                new FileChooser.ExtensionFilter("AVI", "*.avi"), new FileChooser.ExtensionFilter("MOV", "*.mov"),
                new FileChooser.ExtensionFilter("MP4", "*.mp4"), new FileChooser.ExtensionFilter("FLV", "*.flv"));

        String path = file.toString();
        String osName = System.getProperty("os.name", "");
        String str = null;
        if (osName.startsWith("Windows")) {
            str = "\\";
        } else {
            str = "/";
        }
        String value = path.substring(path.lastIndexOf(str) + 1);
        VideoType videoType1 = new VideoType(value);
        list1.add(videoType1);
        videoColumn.setCellValueFactory(cellData -> cellData.getValue().getVideoProperty());
        tableViewVideo.setItems(list1);
    }

    // Event Listener on Button[#btnChoose].onAction
    @FXML
    public void event_choose(ActionEvent event) {
        VBox anchorPane = new VBox();
        anchorPane.setMinSize(200, 100);
        Label text = new Label("训练模型已经被选择，正在加入训练.....\n成功加入训练，请选择添加的视频类型");
        Button button_Close = new Button("关闭");

        anchorPane.getChildren().addAll(text, button_Close);

        Stage stage = new Stage();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        button_Close.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
                stage.close();
            }
        });
        stage.show();
    }

    // Event Listener on Button[#btnPredict].onAction
    @FXML
    public void event_predict(ActionEvent event) {
        setEnabledCheck();
    }

    // Event Listener on Button[#btnCheck].onAction
    @FXML
    public void event_check(ActionEvent event) throws IOException {
        // TODO Autogenerated
        Parent root = FXMLLoader.load(getClass().getResource("/model/result.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        ModelType model1 = new ModelType("Model1");
        ModelType model2 = new ModelType("Model2");
        ModelType model3 = new ModelType("Model3");

        list2.addAll(model1);
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().getModelProperty());
        tableViewModel.setItems(list2);
        tableViewModel.getColumns().add(modelColumn);
        tableViewVideo.getColumns().add(videoColumn);

        btnCheck.setVisible(false);

    }

    public void setEnabledCheck() {
        btnCheck.setVisible(true);
    }
}
