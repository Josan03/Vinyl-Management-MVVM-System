package vinyl.view;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import vinyl.viewmodel.ViewModelFactory;

public class ViewHandler
{
  public static final String BORROW = "borrow";
  public static final String RETURN = "return";
  private Scene currentScene;
  private Stage primaryStage;
  private final ViewFactory viewFactory;

  public ViewHandler(ViewModelFactory viewModelFactory)
  {
    this.viewFactory = new ViewFactory(this, viewModelFactory);
    this.currentScene = new Scene(new Region());
  }

  public void start(Stage primaryStage)
  {
    this.primaryStage = primaryStage;
    openView(BORROW);
  }

  public void openView(String id)
  {
    Region root = switch(id) {
      case BORROW -> viewFactory.loadBorrowView();
      case RETURN -> viewFactory.loadReturnView();
      default -> throw new IllegalArgumentException("Unknown view: " + id);
    };
    currentScene.setRoot(root);
    if (root.getUserData() == null)
    {
      primaryStage.setTitle("");
    } else {
      primaryStage.setTitle(root.getUserData().toString());
    }
    primaryStage.setScene(currentScene);
    primaryStage.sizeToScene();
    primaryStage.show();
  }

  public void closeView()
  {
    primaryStage.close();
  }
}
