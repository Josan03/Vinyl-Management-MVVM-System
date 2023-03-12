package vinyl.model;

public interface State
{
  void onReturn(User user);
  void borrow(User user);
  void reserve(User user);
  void remove();
}
