module assignment1sep2 {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;
  requires javafx.graphics;

  opens vinyl to javafx.fxml;
  opens vinyl.view to javafx.fxml;
  opens vinyl.viewmodel to javafx.fxml;

  exports vinyl.view;
  exports vinyl;
  exports vinyl.viewmodel;
}