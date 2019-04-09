package blockchain.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sg.edu.ntu.sce.sands.crypto.dcpabe.ac.Attribute;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;
    private static AnchorPane mainLayout;


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        showPrimaryView();
    }

    public static void showPrimaryView()throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/sample.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, 400, 400);
        primaryStage.setTitle("File-Sharing-Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void showCreateNodeView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/addNode.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, 400, 400);
        primaryStage.setTitle("Register Node");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void showCreateAttributeview() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/addAttributeAuthority.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, 400, 400);
        primaryStage.setTitle("Register Attribute Authority");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showNode(Node node)throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/node.fxml"));
        mainLayout = loader.load();

        NodeController nc = loader.getController();
        nc.initialize(node);

        Scene scene = new Scene(mainLayout, 450, 600);
        primaryStage.setTitle("Node");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    public static void showAttributeAuthority(AttributeAuthority authority) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/attributeAuthority.fxml"));
        mainLayout = loader.load();

        AttributeAuthorityController ac = loader.getController();
        ac.initialize(authority);

        Scene scene = new Scene(mainLayout, 450, 500);
        primaryStage.setTitle("Attribute Authority");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
