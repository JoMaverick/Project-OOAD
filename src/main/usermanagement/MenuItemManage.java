package main.usermanagement;

import java.util.List;
import java.util.Random;

import controller.MenuItemController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MenuItem;

public class MenuItemManage extends Application {

	private MenuItemController menuItemController = new MenuItemController();
	private TextField idInput = new TextField();
	private TextField nameInput = new TextField();
	private TextField priceInput = new TextField();
	private TextField descInput = new TextField();
	private ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();
	private TableView<MenuItem> table;
	
	private void loadMenuItemData() {
		List<MenuItem> data = menuItemController.getAllMenuItemController();
	    table.getItems().setAll(data);
	}
	
	private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idInput.setText(String.valueOf(newSelection.getMenuItemId()));
                nameInput.setText(String.valueOf(newSelection.getMenuItemName()));
                priceInput.setText(String.valueOf(newSelection.getMenuItemPrice()));
                descInput.setText(String.valueOf(newSelection.getMenuItemDescription()));
            }
        });
    }

	public void start(Stage primaryStage) {
        VBox root = new VBox(); 

        table = createProductTable();
        loadMenuItemData();  
        
        
        setupTableSelectionListener();
        GridPane form = createProductForm(table);
        VBox.setMargin(form, new Insets(20));
        root.getChildren().addAll(table, form);  

        Scene scene = new Scene(root, 800, 600); 
        primaryStage.setScene(scene);
        primaryStage.setTitle("MenuItem");
        primaryStage.show();
    }
	
	private TableView<MenuItem> createProductTable() {
        TableView<MenuItem> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       
        TableColumn<MenuItem, String> idColumn = new TableColumn<>("Menu Item ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("MenuItemID"));

        TableColumn<MenuItem, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("MenuItemName"));

        TableColumn<MenuItem, Number> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("MenuItemPrice"));

        TableColumn<MenuItem, Number> descColumn = new TableColumn<>("Description");
        descColumn.setCellValueFactory(new PropertyValueFactory<>("MenuItemDescription"));

        table.getColumns().add(idColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(priceColumn);
        table.getColumns().add(descColumn);

        return table;
    }
	
	 private GridPane createProductForm(TableView<MenuItem> table) {
	        GridPane form = new GridPane();
	        form.setVgap(20);
	        form.setHgap(50);
	        
	        Button addButton = new Button("Add");
	        Button deleteButton = new Button("Delete");
	        //[Step 1] Add update button
	        Button updateButton = new Button("Update");

	        form.add(new Label("MenuItem ID:"), 0, 0);
	        idInput.setDisable(true);
	        form.add(idInput, 1, 0);
	        form.add(new Label("Name:"), 0, 1);
	        form.add(nameInput, 1, 1);
	        form.add(new Label("Price:"), 0, 2);
	        form.add(priceInput, 1, 2);
	        form.add(new Label("Description:"), 0, 3);
	        form.add(descInput, 1, 3);
	        form.add(addButton, 0, 4);
	        form.add(deleteButton, 1, 4);
	        //[Step 2] Add update button to form
	        form.add(updateButton, 2, 4);
	        
	        addButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	try {
	            		int id = 0;
	                    String name = nameInput.getText();
	                    String desc = descInput.getText();
	                    int price = Integer.parseInt(priceInput.getText()); 
	                    MenuItem menuItem = new MenuItem(id, name, desc, price);
	              
//	                    productController.insertProduct(product);
	                    MenuItem.createMenuItem(name, desc, price);
	                    menuItems.add(menuItem); 
	                    loadMenuItemData();
	                    
	                    idInput.clear();
	                    nameInput.clear();
	                    priceInput.clear();
	                    descInput.clear();
	                } catch (NumberFormatException e) {
	                    Alert alert = new Alert(Alert.AlertType.ERROR);
	                    alert.setTitle("Input Error");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Price and Quantity must be integers.");
	                    alert.showAndWait();
	                }
	            }
	        });

	        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                MenuItem selectedMenuItem = table.getSelectionModel().getSelectedItem();
	                if (selectedMenuItem != null) {
//	                    productController.deleteProduct(selectedProduct.getProductID()); 
	                	MenuItem.deleteMenuItem(selectedMenuItem.getMenuItemId());
	                	menuItems.remove(selectedMenuItem);
	                    
	                	loadMenuItemData();
	                    
	                    idInput.clear();
	                    nameInput.clear();
	                    priceInput.clear();
	                    descInput.clear();
	                }
	            }
	        });
	        
	        //[Step 3] Set update button action to update the product
	        updateButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						int id = 0;
	                    String name = nameInput.getText();
	                    String desc = descInput.getText();
	                    int price = Integer.parseInt(priceInput.getText()); 
	                    MenuItem menuItem = new MenuItem(id, name, desc, price);
//	                    productController.updateProduct(product);  
	                    MenuItem.updateMenuItem(id,name, desc, price);
	                    menuItems.add(menuItem); 
	                    loadMenuItemData();
	                    
	                    idInput.clear();
	                    nameInput.clear();
	                    priceInput.clear();
	                    descInput.clear();
	                } catch (NumberFormatException e) {
	                    Alert alert = new Alert(Alert.AlertType.ERROR);
	                    alert.setTitle("Input Error");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Price and Quantity must be integers.");
	                    alert.showAndWait();
	                }
				}
	        	
			});

	        return form;
	    }
	
    public static void main(String[] args) {
        launch(args);
    }
}
