package vinyl.model;

import java.util.Random;

public class User implements Runnable
{
  private String firstName;
  private String lastName;
  private VinylLibrary vinyls;
  private ModelManager model;

  public User(String firstName, String lastName, VinylLibrary vinyls, ModelManager model)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.vinyls = vinyls;
    this.model = model;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  @Override public String toString()
  {
    return firstName + ' ' + lastName;
  }

  @Override public void run()
  {
    Random random = new Random();
    int cases, vinylIndex;

    while(true)
    {
      cases = random.nextInt(1, 4);
      vinylIndex = random.nextInt(1,10);

      try
      {
        Thread.sleep(5000);
        switch (cases)
        {
          case 1 ->
          {
            if (vinyls.getAllVinyls().get(vinylIndex).getState() instanceof Available)
            {
              model.borrow(vinylIndex, this);
              System.out.println("Borrow: " + vinyls.getAllVinyls().get(vinylIndex).getTitle() + " by " + this);
              Thread.sleep(2000);
            }
            if (vinyls.getAllVinyls().get(vinylIndex).getState() instanceof Reserved && this.equals(vinyls.getAllVinyls().get(vinylIndex).getUser()))
            {
              model.borrow(vinylIndex, this);
              System.out.println("Borrow: " + vinyls.getAllVinyls().get(vinylIndex).getTitle()  + " by " + this);
              Thread.sleep(2000);
            }
          }
          case 2 ->
          {
            if (vinyls.getAllVinyls().get(vinylIndex).getState() instanceof Available || vinyls.getAllVinyls().get(vinylIndex).getState() instanceof Borrowed)
            {
              model.reserve(vinylIndex, this);
              System.out.println(
                  "Reserve: " + vinyls.getAllVinyls().get(vinylIndex).getTitle()  + " by " + this);
              Thread.sleep(2000);
            }
          }
          case 3 ->
          {
            if (vinyls.getAllVinyls().get(vinylIndex).getState() instanceof Borrowed || vinyls.getAllVinyls().get(vinylIndex).getState() instanceof BorrowedAndReserved)
            {
              model.onReturn(vinylIndex, this);
              System.out.println("Return: " + vinyls.getAllVinyls().get(vinylIndex).getTitle() + " by " + this);
              Thread.sleep(2000);
            }
          }
          case 4 ->
          {
            model.remove(vinylIndex);
            System.out.println("Remove: " + vinyls.getAllVinyls().get(vinylIndex).getTitle());
            Thread.sleep(2000);
          }
        }
      }
      catch (InterruptedException e)
      {
        throw new RuntimeException(e);
      }
    }

  }
}
