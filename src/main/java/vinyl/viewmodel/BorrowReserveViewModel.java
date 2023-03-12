package vinyl.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vinyl.model.Borrowed;
import vinyl.model.ModelManager;
import vinyl.model.User;
import vinyl.model.Vinyl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class BorrowReserveViewModel implements PropertyChangeListener
{
  private ModelManager modelManager;
  private SimpleStringProperty name;
  private SimpleStringProperty status;
  private SimpleListProperty<Vinyl> vinyls;
  private SimpleListProperty<User> users;
  private PropertyChangeSupport support;

  public BorrowReserveViewModel(ModelManager modelManager)
  {
    this.modelManager = modelManager;
    this.name = new SimpleStringProperty("-");
    this.status = new SimpleStringProperty("-");
    vinyls = new SimpleListProperty<>(FXCollections.observableArrayList());
    users = new SimpleListProperty<>(FXCollections.observableArrayList());

    this.modelManager.addPropertyChangeListener("vinyls", this);
    this.support = new PropertyChangeSupport(this);
  }

  public void bindName(StringProperty property)
  {
    property.bindBidirectional(name);
  }

  public void bindStatus(StringProperty property)
  {
    property.bindBidirectional(status);
  }

  public void bindVinyl(ObjectProperty<ObservableList<Vinyl>> property)
  {
    property.bindBidirectional(vinyls);
  }

  public void bindUsers(ObjectProperty<ObservableList<User>> property)
  {
    property.bindBidirectional(users);
  }

  public void addPropertyChangeListener(String propertyName,
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(propertyName, listener);
  }

  public void removePropertyChangeListener(String propertyName,
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(propertyName, listener);
  }

  public void borrowVinyl(Vinyl vinyl, User user)
  {
    try
    {
      modelManager.borrow(modelManager.getVinylIndex(vinyl), user);
      support.firePropertyChange("borrow", null, vinyl.getState());
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  public void reserveVinyl(Vinyl vinyl, User user)
  {
    try
    {
      modelManager.reserve(modelManager.getVinylIndex(vinyl), user);
      support.firePropertyChange("borrow", null, vinyl.getState());
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  public void removeVinyl(Vinyl vinyl)
  {
    try
    {
      modelManager.remove(modelManager.getVinylIndex(vinyl));
      support.firePropertyChange("borrow", null, vinyl.getState());
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  public void update()
  {
    vinyls.setAll(modelManager.getVinyls());
    users.setAll(modelManager.getUsers());
  }

  public void reset()
  {
    name.set("-");
    status.set("-");
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(() -> update());
  }
}
