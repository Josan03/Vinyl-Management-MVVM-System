package vinyl.model;

public class Vinyl
{
  private String title;
  private String artist;
  private int year;
  private User user;
  private State state;
  private boolean isRemoved;

  public Vinyl(String title, String artist, int year)
  {
    this.title = title;
    this.artist = artist;
    this.year = year;
    this.user = null;
    this.isRemoved = false;
    this.state = new Available(this, null);
  }

  public String getTitle()
  {
    return title;
  }

  public String getArtist()
  {
    return artist;
  }

  public int getYear()
  {
    return year;
  }

  public User getUser()
  {
    return user;
  }

  public State getState()
  {
    return state;
  }

  public boolean isRemoved()
  {
    return isRemoved;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public void setRemoved(boolean removed)
  {
    isRemoved = removed;
  }

  protected void setState(State state)
  {
    this.state = state;
  }

  public void onReturn(User user)
  {
    this.state.onReturn(user);
  }

  public void borrow(User user)
  {
    this.state.borrow(user);
  }

  public void reserve(User user)
  {
    this.state.reserve(user);
  }

  public boolean equals(Object obj)
  {
    if (obj == null || getClass() != obj.getClass())
    {
      return false;
    }

    Vinyl other = (Vinyl) obj;

    return this.title.equals(other.title) && this.artist.equals(other.artist)
        && this.year == other.year && this.isRemoved == other.isRemoved;
  }

  @Override public String toString()
  {
    return "\"" + title + "\"" + "by " + artist + " " + year;
  }
}
