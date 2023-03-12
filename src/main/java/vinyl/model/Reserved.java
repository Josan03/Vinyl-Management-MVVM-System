package vinyl.model;

public class Reserved implements State
{
  private Vinyl vinyl;
  private User user;

  public Reserved(Vinyl vinyl, User user)
  {
    this.vinyl = vinyl;
    this.user = user;
  }

  @Override public void onReturn(User user)
  {

  }

  @Override public void borrow(User user)
  {
    vinyl.setState(new Borrowed(vinyl, user));
  }

  @Override public void reserve(User user)
  {

  }

  @Override public void remove()
  {
    vinyl.setRemoved(true);
  }

  public User getUser()
  {
    return user;
  }

  public boolean equals(Object obj)
  {
    if (obj == null || getClass() != obj.getClass())
    {
      return false;
    }

    Reserved other = (Reserved) obj;

    return this.vinyl.equals(other.vinyl)
        && this.vinyl.getState() instanceof Reserved;
  }
}
