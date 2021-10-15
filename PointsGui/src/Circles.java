//import package
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Circles extends Application {
	// variable declaration
	double x1, y1, x2, y2;
	// creating object for text function
	Text text = new Text();
	// creating array
	Circle[] circle;
	int arrayLength = 0;

	@Override
	public void start(Stage stage) throws IOException {

		// variable declaration
		double x = 0, y = 0;
		int i = 0;
		// creating object for the pane
		Pane pane = new Pane();
		text.setFont(Font.font("Arial", 12));
		// reading file process
		File file = new File("points.txt");
		try{
		// calculating length of the lines
		FileInputStream inputStream = new FileInputStream(file);
		//creating byte object
		byte[] length = new byte[(int) file.length()];
		inputStream.read(length);
		String data = new String(length);
		String[] stringArray = data.split("\r\n");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		circle = new Circle[stringArray.length];
		String line = null;
		while ((line = br.readLine()) != null) {
			
			arrayLength = arrayLength + 1;
			//split method
			String[] tokens = line.split("=|;");
			Point pt = new Point();
			pt.setX(Integer.parseInt(tokens[1]));
			pt.setY(Integer.parseInt(tokens[3]));
			double r = 5;
			circle[i] = new Circle(pt.getX(), pt.getY(), r);
			pane.getChildren().add(circle[i]);
			// mouse pressing operation
			circle[i].setOnMousePressed(circleOnMousePressedEventHandler);
			circle[i].setOnMouseMoved(e -> {
				text.setText("X= " + e.getX() + ", Y=" + e.getY());
			});
			i++;
		}}catch(FileNotFoundException ex){
			System.out.println("File not found");
			System.exit(0);
		}


		for (i = 0; i < arrayLength; i++) {
			final int d = i;
			// dragging and coordinate showing process of point 1
			circle[i].setOnMouseDragged(drawPoints -> {
				circle[d].setCenterX(drawPoints.getX());
				circle[d].setCenterY(drawPoints.getY());
				text.setText("X= " + drawPoints.getX() + ", Y=" + drawPoints.getY());

			});

		}
		// setting text size
		text.setX(200);
		text.setY(500);
		// setting pane size
		Scene scene = new Scene(pane, 600, 500);
		pane.getChildren().add(text);
		stage.setTitle("Show Points");
		stage.setScene(scene);
		stage.show();
	} 
	   
	// mouse pressed event
	EventHandler<MouseEvent> circleOnMousePressedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			x1 = t.getSceneX();
			y1 = t.getSceneY();
			x2 = ((Circle) (t.getSource())).getTranslateX();
			y2 = ((Circle) (t.getSource())).getTranslateY();

		}
	};
	public static void main(String args[]) {
		launch(args);
	}
	

}
