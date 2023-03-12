package vinyl.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vinyl.model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ReturnViewModel implements PropertyChangeListener
{
  private ModelManager modelManager;
  private StringProperty name;
  private StringProperty status;
  private SimpleListProperty<Vinyl> vinyls;

  public ReturnViewModel(ModelManager modelManager)
  {
    this.modelManager = modelManager;
    this.name = new SimpleStringProperty("-");
    this.status = new SimpleStringProperty("-");
    this.vinyls = new SimpleListProperty<>(FXCollections.observableArrayList());

    this.modelManager.addPropertyChangeListener("vinyls", this);
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

  public void returnVinyl(Vinyl vinyl, User user)
  {
    try
    {
      modelManager.onReturn(modelManager.getVinylIndex(vinyl), user);
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
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  public void update()
  {
    ArrayList<Vinyl> list = new ArrayList<>();
    for (int i = 0; i < modelManager.getVinyls().size(); i++)
    {
      if (modelManager.getVinyls().get(i).getState() instanceof Borrowed || modelManager.getVinyls().get(i).getState() instanceof BorrowedAndReserved)
      {
        list.add(modelManager.getVinyls().get(i));
      }
    }
    vinyls.setAll(list);
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
