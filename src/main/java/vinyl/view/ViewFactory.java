package vinyl.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import vinyl.viewmodel.BorrowReserveViewModel;
import vinyl.viewmodel.ReturnViewModel;
import vinyl.viewmodel.ViewModelFactory;

import java.io.IOError;
import java.io.IOException;

public class ViewFactory
{
  private ViewHandler viewHandler;
  private ViewModelFactory viewModelFactory;
  private BorrowReserveViewController borrowReserveViewController;
  private ReturnViewController returnViewController;

  public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
  {
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
    this.borrowReserveViewController = null;
    this.returnViewController = null;
  }

  public Region loadBorrowView()
  {
    if (borrowReserveViewController == null)
    {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/GUI/GUI1.fxml"));
      try
      {
        Region root = loader.load();
        borrowReserveViewController = loader.getController();
        borrowReserveViewController.init(viewHandler,
            viewModelFactory.getBorrowReserveViewModel(), root);
      }
      catch (IOException e)
      {
        throw new IOError(e);
      }
    }
    borrowReserveViewController.update();
    return borrowReserveViewController.getRoot();
  }

  public Region loadReturnView()
  {
    if (returnViewController == null)
    {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/GUI/GUI2.fxml"));
      try
      {
        Region root = loader.load();
        returnViewController = loader.getController();
        returnViewController.init(viewHandler,
            viewModelFactory.getReturnViewModel(), root);
      }
      catch (IOException e)
      {
        throw new IOError(e);
      }
    }
    returnViewController.update();
    return returnViewController.getRoot();
  }
}
