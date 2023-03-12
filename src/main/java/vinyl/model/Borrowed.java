package vinyl.model;

public class Borrowed implements State
{
  private Vinyl vinyl;
  private User user;

  public Borrowed(Vinyl vinyl, User user)
  {
    this.vinyl = vinyl;
    this.user = user;
  }

  @Override public void onReturn(User user)
  {
    vinyl.setState(new Available(vinyl, user));
  }

  @Override public void borrow(User user)
  {

  }

  @Override public void reserve(User user)
  {
    vinyl.setState(new BorrowedAndReserved(vinyl, this.user, user));
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

    Borrowed other = (Borrowed) obj;

    return this.vinyl.equals(other.vinyl)
        && this.vinyl.getState() instanceof Borrowed;
  }
}
