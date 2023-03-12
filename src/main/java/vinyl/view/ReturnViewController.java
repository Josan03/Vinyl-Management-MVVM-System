package vinyl.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import vinyl.model.*;
import vinyl.viewmodel.ReturnViewModel;

public class ReturnViewController
{
  @FXML public Label nameLabel;
  @FXML public Label statusLabel;
  @FXML public ListView<Vinyl> vinylList;
  @FXML public Button brmenuButton;
  @FXML public Button rmenuButton;
  @FXML public Button exitButton;
  @FXML public Button returnButton;
  private ViewHandler viewHandler;
  private ReturnViewModel returnViewModel;
  private Region root;
  private Vinyl selectedVinyl;

  @FXML public void exit()
  {
    viewHandler.closeView();
  }

  @FXML public void switchToBRMenu()
  {
    viewHandler.openView(ViewHandler.BORROW);
    returnViewModel.reset();
  }

  @FXML public void onReturn()
  {
    selectedVinyl = vinylList.getSelectionModel().getSelectedItem();
    
    if (selectedVinyl.getState() instanceof Borrowed)
    {
      if (selectedVinyl.isRemoved())
      {
        returnViewModel.returnVinyl(selectedVinyl,null);
        returnViewModel.removeVinyl(selectedVinyl);
      }
      else
      {
        returnViewModel.returnVinyl(selectedVinyl, null);
      }
    }
    else if (selectedVinyl.getState() instanceof BorrowedAndReserved)
    {
      returnViewModel.returnVinyl(selectedVinyl, null);
    }
    update();
  }

  public void init(ViewHandler viewHandler, ReturnViewModel returnViewModel,
      Region root)
  {
    this.viewHandler = viewHandler;
    this.returnViewModel = returnViewModel;
    this.root = root;

    this.returnViewModel.bindName(nameLabel.textProperty());
    this.returnViewModel.bindStatus(statusLabel.textProperty());
    this.returnViewModel.bindVinyl(vinylList.itemsProperty());

    rmenuButton.setDisable(true);
    returnButton.setDisable(true);
  }

  public Region getRoot()
  {
    return root;
  }

  public void update()
  {
    returnViewModel.update();

    vinylList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null)
      {
        returnButton.setDisable(false);
        nameLabel.setText(newSelection.toString());

        if (newSelection.getState() instanceof Borrowed)
        {
          Borrowed aux = (Borrowed) newSelection.getState();

          statusLabel.setText("Borrowed by " + aux.getUser());

          if (newSelection.isRemoved())
          {
            statusLabel.setText(statusLabel.getText() + "\n Marked for deletion");
          }
        }
        else if (newSelection.getState() instanceof BorrowedAndReserved)
        {
          BorrowedAndReserved aux = (BorrowedAndReserved) newSelection.getState();

          statusLabel.setText("Borrowed by " + aux.getUser() + " and reserved after that by " + aux.getUser2());

          if (newSelection.isRemoved())
          {
            statusLabel.setText(statusLabel.getText() + "\n Marked for deletion");
          }
        }
      }
      else
      {
        returnButton.setDisable(true);
      }
    });
  }
}
