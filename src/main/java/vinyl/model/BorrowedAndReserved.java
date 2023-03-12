package vinyl.model;

public class BorrowedAndReserved implements State
{
  private Vinyl vinyl;
  private User user;
  private User user2;

  public BorrowedAndReserved(Vinyl vinyl, User user, User user2)
  {
    this.vinyl = vinyl;
    this.user = user;
    this.user2 = user2;
  }

  @Override public void onReturn(User user)
  {
    vinyl.setState(new Reserved(vinyl, user2));
  }

  @Override public void borrow(User user)
  {

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

  public User getUser2()
  {
    return user2;
  }

  public boolean equals(Object obj)
  {
    if (obj == null || getClass() != obj.getClass())
    {
      return false;
    }

    BorrowedAndReserved other = (BorrowedAndReserved) obj;

    return this.vinyl.equals(other.vinyl)
        && this.vinyl.getState() instanceof BorrowedAndReserved;
  }
}
