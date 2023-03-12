package vinyl.viewmodel;

import vinyl.model.ModelManager;

public class ViewModelFactory
{

  private BorrowReserveViewModel borrowReserveViewModel;
  private ReturnViewModel returnViewModel;

  public ViewModelFactory(ModelManager modelManager)
  {
    this.borrowReserveViewModel = new BorrowReserveViewModel(modelManager);
    this.returnViewModel = new ReturnViewModel(modelManager);
  }

  public BorrowReserveViewModel getBorrowReserveViewModel(){
    return borrowReserveViewModel;
  }

  public ReturnViewModel getReturnViewModel()
  {
    return returnViewModel;
  }
}
