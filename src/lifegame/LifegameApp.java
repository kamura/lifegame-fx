package lifegame;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LifegameApp extends Application {
    private static final int NUM_OF_ROWS = 40;
    private static final int NUM_OF_COLUMNS = 40;
    private Scene scene;
    private Lifegame lifegame;
    private Rectangle[][] cellGridRects;
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LifegameApp.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        //Parent root = FXMLLoader.load(getClass().getResource("LifegameApp.fxml"));
        LifegameController controller = loader.getController();
        this.scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        lifegame = new Lifegame(this, NUM_OF_ROWS, NUM_OF_COLUMNS);
        controller.setLifegame(lifegame);

        initializeCellGridView();
        updateCellGridView();
    }

    private void initializeCellGridView() {
        int numOfRows = lifegame.getCellGrid().getRowSize();
        int numOfColumns = lifegame.getCellGrid().getColumnSize();
        cellGridRects = new Rectangle[numOfRows][numOfColumns];

        Pane pane = (Pane)scene.lookup("#canvas");
        double paneWidth = pane.getWidth();
        double paneHeight = pane.getHeight();
        double xUnit = paneWidth / numOfColumns;
        double yUnit = paneHeight / numOfRows;
        
        List<Rectangle> rectList = new ArrayList<>();
        for (int row = 0; row < numOfRows; row++) {
            for (int column = 0; column < numOfColumns; column++) {
                final Rectangle rect = createCellRect(xUnit, yUnit, row, column);
                rectList.add(rect);
                cellGridRects[row][column] = rect;
            }
        }

        pane.getChildren().addAll(rectList);
    }

    private Rectangle createCellRect(double xUnit, double yUnit, int row, int column) {
        final Rectangle rect = new Rectangle();
        rect.setX(xUnit * column);
        rect.setY(yUnit * row);
        rect.setWidth(xUnit);
        rect.setHeight(yUnit);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setStroke(Color.LIGHTGRAY);
        rect.setFill(Color.WHITE);
        rect.setUserData(new int[]{row, column});
        rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int[] userData = (int[])rect.getUserData();
                int row = userData[0];
                int column = userData[1];
                Cell cell = lifegame.getCellGrid().getCell(row, column);
                cell.setAlive(!cell.isAlive());
                updateCellGridView();
            }
        });
        return rect;
    }

    public void updateCellGridView() {
        int numOfRows = lifegame.getCellGrid().getRowSize();
        int numOfColumns = lifegame.getCellGrid().getColumnSize();

        for (int row = 0; row < numOfRows; row++) {
            for (int column = 0; column < numOfColumns; column++) {
                Rectangle rect = cellGridRects[row][column];
                Cell cell = lifegame.getCellGrid().getCell(row, column);
                if (cell.isAlive()) {
                    rect.setFill(Color.BLUE);
                } else {
                    rect.setFill(Color.WHITE);
                }
                //rect.setStyle("radial 100% stops (0.0,red) (0.50,darkgray) (1.0,black)");
            }
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}