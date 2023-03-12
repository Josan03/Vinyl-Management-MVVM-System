package vinyl.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ModelManager
{
  private ArrayList<User> users;
  private VinylLibrary vinyls;
  private PropertyChangeSupport support;

  public ModelManager()
  {
    this.users = new ArrayList<>();
    this.vinyls = new VinylLibrary();
    this.support = new PropertyChangeSupport(this);
    initCollection();
    initUsers();
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

  public int getVinylIndex(Vinyl vinyl)
  {
    int pos = -1;
    for (int i = 0; i < vinyls.getAllVinyls().size(); i++)
    {
      if (vinyls.getAllVinyls().get(i).equals(vinyl))
      {
        pos = i;
      }
    }

    return pos;
  }

  public void remove(int index)
  {
    vinyls.remove(index);
    support.firePropertyChange("vinyls", null, vinyls);
  }

  public void borrow(int vinylIndex, User user)
  {
    vinyls.borrow(vinylIndex, user);
    support.firePropertyChange("vinyls", null, vinyls);
  }

  public void reserve(int vinylIndex, User user)
  {
    vinyls.reserve(vinylIndex, user);
    support.firePropertyChange("vinyls", null, vinyls);
  }

  public void onReturn(int vinylIndex, User user)
  {
    vinyls.onReturn(vinylIndex, user);
    support.firePropertyChange("vinyls", null, vinyls);
  }

  private void initUsers()
  {
    users.add(new User("Cristian", "Josan", vinyls, this));
    users.add(new User("Mihai", "Mihaila", vinyls, this));
  }

  public ArrayList<Vinyl> getVinyls()
  {
    return vinyls.getAllVinyls();
  }

  public ArrayList<User> getUsers()
  {
    return users;
  }

  private void initCollection()
  {
    vinyls.add(new Vinyl("The Hounds Of Love", "Kate Bush", 1980));
    vinyls.add(new Vinyl("Dark Side Of The Moon", "Pink Floyd", 1973));
    vinyls.add(new Vinyl("Kind of Blue", "Miles Davis", 1959));
    vinyls.add(new Vinyl("The Doors", "The Doors", 1967));
    vinyls.add(new Vinyl("Fleetwood Mac", "Rumours", 1977));
    vinyls.add(new Vinyl("Thriller", "Michael Jackson", 1982));
    vinyls.add(new Vinyl("What’s The Story Morning Glory", "Oasis", 1995));
    vinyls.add(new Vinyl("Elephant", "The White Stripes", 2003));
    vinyls.add(new Vinyl("After The Gold Rush", "Neil Young", 1970));
    vinyls.add(new Vinyl("Sticky Fingers", "The Rolling Stones", 1971));
    vinyls.add(new Vinyl("Electric Warrior", "T-Rex", 1971));
  }
}
