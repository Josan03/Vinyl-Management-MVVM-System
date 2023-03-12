package vinyl;

import javafx.application.Application;
import javafx.stage.Stage;
import vinyl.model.ModelManager;
import vinyl.view.ViewHandler;
import vinyl.viewmodel.ViewModelFactory;

public class Start extends Application
{
  @Override public void start(Stage primaryStage)
  {
    ModelManager model = new ModelManager();
    ViewModelFactory viewModelFactory = new ViewModelFactory(model);
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);
    viewHandler.start(primaryStage);

    Thread user1 = new Thread(model.getUsers().get(0));
    Thread user2 = new Thread(model.getUsers().get(1));

    user1.start();
    user2.start();
  }

  public static void main(String[] args){
    launch();
  }
}
