package vinyl.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import vinyl.model.*;
import vinyl.viewmodel.BorrowReserveViewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class BorrowReserveViewController implements PropertyChangeListener
{
  @FXML public Label nameLabel;
  @FXML public Label statusLabel;
  @FXML public ListView<Vinyl> vinylList;
  @FXML public Button brmenuButton;
  @FXML public Button rmenuButton;
  @FXML public Button exitButton;
  @FXML public Button reserveButton;
  @FXML public Button borrowButton;
  @FXML public Button removeButton;
  @FXML public ComboBox<User> userSelector;
  private ViewHandler viewHandler;
  private BorrowReserveViewModel borrowReserveViewModel;
  private Region root;
  private Vinyl selectedVinyl;
  private User selectedUser;

  @FXML public void exit()
  {
    viewHandler.closeView();
  }

  @FXML public void switchToReturnMenu()
  {
    viewHandler.openView(ViewHandler.RETURN);
    borrowReserveViewModel.reset();
  }

  @FXML public void borrow()
  {
    selectedVinyl = vinylList.getSelectionModel().getSelectedItem();
    selectedUser = userSelector.getSelectionModel().getSelectedItem();
    borrowReserveViewModel.borrowVinyl(selectedVinyl, selectedUser);
    updateStatus(selectedVinyl);
    update();
  }

  @FXML public void reserve()
  {
    selectedVinyl = vinylList.getSelectionModel().getSelectedItem();
    selectedUser = userSelector.getSelectionModel().getSelectedItem();
    borrowReserveViewModel.reserveVinyl(selectedVinyl, selectedUser);
    updateStatus(selectedVinyl);
    update();
  }

  @FXML public void remove()
  {
    selectedVinyl = vinylList.getSelectionModel().getSelectedItem();

    if (selectedVinyl.getState() instanceof Available)
    {
      borrowReserveViewModel.removeVinyl(selectedVinyl);
      updateStatus(selectedVinyl);
      update();
    }
    else if (selectedVinyl.getState() instanceof Reserved || selectedVinyl.getState() instanceof Borrowed || selectedVinyl.getState() instanceof BorrowedAndReserved)
    {
      selectedVinyl.setRemoved(true);
      updateStatus(selectedVinyl);
      update();
    }
  }

  public void init(ViewHandler viewHandler,
      BorrowReserveViewModel borrowReserveViewModel, Region root)
  {
    this.viewHandler = viewHandler;
    this.borrowReserveViewModel = borrowReserveViewModel;
    this.root = root;

    this.borrowReserveViewModel.bindName(nameLabel.textProperty());
    this.borrowReserveViewModel.bindStatus(statusLabel.textProperty());
    this.borrowReserveViewModel.bindVinyl(vinylList.itemsProperty());
    this.borrowReserveViewModel.bindUsers(userSelector.itemsProperty());

    removeButton.setDisable(true);
    reserveButton.setDisable(true);
    borrowButton.setDisable(true);
    userSelector.setDisable(true);
  }

  public Region getRoot()
  {
    return root;
  }

  public void update()
  {
    borrowReserveViewModel.update();

    vinylList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null)
      {
        userSelector.setDisable(false);
        borrowButton.setDisable(true);
        reserveButton.setDisable(true);
        selectedUser = null;
        nameLabel.setText(newSelection.toString());
        selectedVinyl = newSelection;
        if (newSelection.getState() instanceof Available)
        {
          userSelector.setDisable(false);
          borrowButton.setDisable(false);
          reserveButton.setDisable(false);
          removeButton.setDisable(false);
          statusLabel.setText("Available to be borrowed or reserved");
        }
        else if (newSelection.getState() instanceof Borrowed)
        {
          Borrowed aux = (Borrowed) newSelection.getState();

          userSelector.setDisable(false);
          reserveButton.setDisable(false);
          removeButton.setDisable(false);
          statusLabel.setText("Borrowed by " + aux.getUser());

          if (newSelection.isRemoved())
          {
            userSelector.setDisable(true);
            statusLabel.setText(statusLabel.getText() + "\n Marked for deletion");
            reserveButton.setDisable(true);
          }
        }
        else if (newSelection.getState() instanceof BorrowedAndReserved)
        {
          BorrowedAndReserved aux = (BorrowedAndReserved) newSelection.getState();
          removeButton.setDisable(false);
          userSelector.setDisable(true);
          reserveButton.setDisable(true);
          statusLabel.setText("Borrowed by " + aux.getUser() + " and reserved after that by " + aux.getUser2());

          if (newSelection.isRemoved())
          {
            statusLabel.setText(statusLabel.getText() + "\n Marked for deletion");
          }
        }
        else if (newSelection.getState() instanceof Reserved)
        {
          Reserved aux = (Reserved) newSelection.getState();

          removeButton.setDisable(false);
          reserveButton.setDisable(true);
          borrowButton.setDisable(false);

          statusLabel.setText("Reserved by " + aux.getUser());

          if (newSelection.isRemoved())
          {
            statusLabel.setText(statusLabel.getText() + "\n Marked for deletion");
            borrowButton.setDisable(true);
          }
        }
      }
      else
      {
        userSelector.setDisable(true);
        borrowButton.setDisable(true);
        reserveButton.setDisable(true);
        removeButton.setDisable(true);
      }
    });

    userSelector.valueProperty().addListener((obs, oldVal, newVal) -> {
      this.selectedUser = newVal;
      if (selectedVinyl.getState() instanceof Reserved)
      {
        Reserved aux = (Reserved) selectedVinyl.getState();
        if (aux.getUser() == selectedUser)
        {
          borrowButton.setDisable(false);
        }
        else
        {
          borrowButton.setDisable(true);
        }
      }
    });
  }

  public void updateStatus(Vinyl vinyl)
  {
    if (vinyl.getState() instanceof Available)
    {
      statusLabel.setText("Available to be borrowed or reserved");
    }
    else if (vinyl.getState() instanceof Reserved)
    {
      statusLabel.setText("Reserved by " + ((Reserved) vinyl.getState()).getUser());

      if (vinyl.isRemoved())
      {
        statusLabel.setText(statusLabel.getText() + "\n Marked for deletion");
      }
    }
    else if (vinyl.getState() instanceof Borrowed)
    {
      statusLabel.setText("Borrowed by " + ((Borrowed) vinyl.getState()).getUser());

      if (vinyl.isRemoved())
      {
        statusLabel.setText(statusLabel.getText() + "\n Marked for deletion");
      }
    }
    else if (vinyl.getState() instanceof BorrowedAndReserved)
    {
      statusLabel.setText("Borrowed by " + ((BorrowedAndReserved) vinyl.getState()).getUser() + " and reserved after that by " + ((BorrowedAndReserved) vinyl.getState()).getUser2());

      if (vinyl.isRemoved())
      {
        statusLabel.setText(statusLabel.getText() + "\n Marked for deletion");
      }
    }
  }
  
  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    borrowReserveViewModel = (BorrowReserveViewModel) evt.getSource();
    Vinyl vinyl = (Vinyl) evt.getNewValue();
    updateStatus(vinyl);
  }
}
