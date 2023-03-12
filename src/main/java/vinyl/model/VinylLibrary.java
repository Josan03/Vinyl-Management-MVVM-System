package vinyl.model;

import java.util.ArrayList;

public class VinylLibrary
{
  private ArrayList<Vinyl> vinyls;

  public VinylLibrary()
  {
    vinyls = new ArrayList<>();
  }

  public void add(Vinyl vinyl)
  {
    vinyls.add(vinyl);
  }

  public void remove(int index)
  {
    vinyls.get(index).getState().remove();
    vinyls.remove(index);
  }

  public void borrow(int index, User user)
  {

    vinyls.get(index).borrow(user);
  }

  public void reserve(int index, User user)
  {
    vinyls.get(index).reserve(user);
  }

  public void onReturn(int index, User user)
  {
    vinyls.get(index).onReturn(user);
  }

  public ArrayList<Vinyl> getAllVinyls()
  {
    return vinyls;
  }

}
