package com.instance.mancala2;


import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.instance.mancala2.gluonViews.GameView;
import com.instance.mancala2.gluonViews.MainMenuView;
import com.instance.mancala2.gluonViews.PreferencesView;
import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

public class Main extends Application {
    private final AppManager appManager = AppManager.initialize(this::postInit);
    public static final String END_GAME_VIEW = "END_GAME_VIEW";
    public static final String MAIN_MENU_VIEW = "MAIN_MENU_VIEW";
    public static final String GAME_VIEW = "GAME_VIEW";
    public static final String PREFERENCES_VIEW = "PREFERENCES_VIEW";
    @Override
    public void init() {
        appManager.addViewFactory(HOME_VIEW, MainMenuView::new);
        appManager.addViewFactory(END_GAME_VIEW, () -> {
            GamePreferences preferences = GamePreferences.getInstance(); // Or however you obtain preferences
            EndGameScreen endGameScreen = new EndGameScreen(preferences);
            return endGameScreen;
        });
        appManager.addViewFactory(GAME_VIEW, GameView::new);
        appManager.addViewFactory(PREFERENCES_VIEW, PreferencesView::new);
        appManager.addViewFactory(MAIN_MENU_VIEW, MainMenuView::new);

        //() -> {

//            FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.SEARCH.text,
//                    e -> System.out.println("Search"));
//
//            ImageView imageView = new ImageView(new Image(Main.class.getResourceAsStream("openduke.png")));
//            imageView.setFitHeight(200);
//            imageView.setPreserveRatio(true);
//
//            Label label = new Label("Hello, Mancala2!");
//
//            VBox root = new VBox(20, imageView, label);
//            root.setAlignment(Pos.CENTER);
//
//            View view = new View(root) {
//                @Override
//                protected void updateAppBar(AppBar appBar) {
//                    appBar.setTitleText("Mancala2");
//                }
//            };
//
//            fab.showOn(view);
//
//            return view;
//                    MainMenu mainMenu = new MainMenu(); // Adjust constructor as needed
//                    return mainMenu;

       //});
    }
//    @Override
//    public void start(Stage primaryStage) {
//
//        // Initial scene setup
//        MainMenu menu = new MainMenu(primaryStage);
//        Scene scene = new Scene(menu.getView(), 800, 600);
//
//        primaryStage.setTitle("Mancala Game");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }



    // Implement your logic to decide whether a window should be closed

    @Override
    public void start(Stage stage) {
        try {

            appManager.start(stage);

            clickCloseButtonOnPopup();

        } catch (Exception e) {
            // Catch other exceptions. It's a good practice to catch specific exceptions if possible.
            System.err.println("Unexpected exception encountered during appManager.start: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void postInit(Scene scene) {
        Swatch.LIGHT_GREEN.assignTo(scene);
//        Swatch.

        scene.getStylesheets().add(Main.class.getResource("styles.css").toExternalForm());

        ((Stage) scene.getWindow()).getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));

        if (Platform.isDesktop()) {
            Dimension2D dimension2D = DisplayService.create()
                    .map(DisplayService::getDefaultDimensions)
                    .orElse(new Dimension2D(640, 480));
            scene.getWindow().setWidth(dimension2D.getWidth());
            scene.getWindow().setHeight(dimension2D.getHeight());
        }
        Parent root = (Parent) scene.getRoot();
        root.setStyle("-fx-background-color: grey;"); // Set background color to grey
    }


    private void clickCloseButtonOnPopup() {
        javafx.application.Platform.runLater(() -> {
            for (Window window : Window.getWindows()) {
                if (window instanceof Stage) {
                    Stage stage = (Stage) window;
                    Scene scene = stage.getScene();
                    if (scene != null && scene.getRoot() != null) {
                        // Search for the button within this window's scene graph
                        List<Button> buttons = findNodesByType(scene.getRoot(), Button.class);
                        for (Button button : buttons) {

                            if ("CLOSE".equals(button.getText())) {
                                // Simulate a button click
                                button.fire();
                                //javafx.application.Platform.runLater(button::fire);

                                return; // Stop after clicking the first matching button
                            }
                        }
                    }
                }
            }
        });
    }

    private <T extends Node> List<T> findNodesByType(Node root, Class<T> type) {
        List<T> nodes = new ArrayList<>();
        if (type.isInstance(root)) {
            nodes.add(type.cast(root));
        }
        if (root instanceof Parent) {
            for (Node child : ((Parent) root).getChildrenUnmodifiable()) {
                nodes.addAll(findNodesByType(child, type));
            }
        }
        return nodes;
    }
    public static void main(String[] args) {
        launch(args);
    }

}
