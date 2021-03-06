package gui.controller;

import business.exceptions.ObjectNotFoundException;
import business.facade.CategoryFacade;
import business.facade.FavoryFacade;
import business.facade.OfferFacade;
import business.system.Category;
import business.system.Favory;
import business.system.offer.Offer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gui.LoadView;
import gui.ViewPath;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.FloatStringConverter;
import util.MapRessourceBundle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    private final FavoryController favoryController = new FavoryController();

    @FXML
    private TableView<Offer> table;
    @FXML
    private TableColumn<Offer,String> title,category,city;
    @FXML
    private TableColumn<Offer,Integer> offerButton,favoryButton;
    @FXML
    private TableColumn<Offer,Float> price;
    @FXML
    private ChoiceBox<Category> categoryBox;
    @FXML
    private JFXTextField textField;


    /**
     * Initialize the view corresponding to what the user wanted,
     * it can be the home page or only the favorties offers of the user
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FavoryFacade favoryFacade = FavoryFacade.getInstance();
        //Check if we are on the homepage or the favory page
        FilteredList<Offer> offerFilteredList;
        if(((MapRessourceBundle)resources).size() != 0){
            offerFilteredList = new FilteredList<>(FXCollections.observableArrayList(favoryFacade.getAllFavories()));
        }else{
            offerFilteredList = new FilteredList<>(FXCollections.observableArrayList(OfferFacade.getInstance().getAllOffers()), p -> true);
        }
        //add All categories to the choiceBox
        Category noCategory = new Category("No Category",false);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(noCategory);
        categoryList.addAll(CategoryFacade.getInstance().getAllValidatedCategories());
        categoryBox.getItems().addAll(categoryList);

        categoryBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> offerFilteredList.setPredicate(offer -> {
            Category selectedCategory = categoryList.get((Integer) newValue);
            if (selectedCategory.getCategoryName().equals("No Category")) {
                return true;
            } else {
                // We compare what he entered (in lower case)
                String lowerCaseFilter = selectedCategory.getCategoryName().toLowerCase();
                // If the filter matches the name of the category
                // The category is displayed
                // The name doesn't match, not displayed
                return offer.getCategory().getCategoryName().toLowerCase().contains(lowerCaseFilter);
            }
        }));

        //allow the user to type in the search bar
        textField.textProperty().addListener((observable, oldValue, newValue) -> this.filterTitle(observable,oldValue,newValue,offerFilteredList));

        SortedList<Offer> sortedData = new SortedList<>(offerFilteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        title.setCellFactory(TextFieldTableCell.forTableColumn());

        category.setCellValueFactory(param -> {
            Category category = param.getValue().getCategory();
            String name = category.getCategoryName();

            if(name==null || !category.getIsValidated()){
                name="No Category";
            }
            return new SimpleObjectProperty<>(name);
        });
        category.setCellFactory(TextFieldTableCell.forTableColumn());

        city.setCellValueFactory(cellData -> Bindings.select(cellData.getValue(),"user","role","userCity"));
        city.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        price.setCellValueFactory(new PropertyValueFactory<>("pricePerDay") );
        price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        offerButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
        favoryButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));


        buttonSeeOffer(offerButton);

        favoryButton.setCellFactory(param -> new TableCell<>() {
            Image img;
            ImageView iv;

            private final JFXButton favoryButton = new JFXButton();

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                }
                else {
                    Offer offer=null;
                    try {
                       offer= OfferFacade.getInstance().getOffer(item);
                    } catch (ObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    assert offer != null;
                    Favory fav = favoryFacade.findFavory(offer);

                    if(fav==null){
                        img= new Image("img/favory.png") ;
                        iv= new ImageView(img);
                        favoryButton.setOnAction(event -> addToFavory(event, item,favoryButton));

                    }else{
                        img= new Image("img/favoryFilled.png") ;
                        iv= new ImageView(img);
                        favoryButton.setOnAction(event -> deleteFromFavory(event, item,favoryButton));
                    }
                    iv.setFitHeight(35);
                    iv.setFitWidth(35);
                    favoryButton.setGraphic(iv);

                    setGraphic(favoryButton);
                }
            }
        });
        table.setItems(sortedData);
    }

    /**
     * Display the view of the offer when clicked on "see Offer"
     * @param actionEvent
     * @param offer
     */
    public static void seeOfferPage(ActionEvent actionEvent,Offer offer){

        LoadView.changeScreen(actionEvent, ViewPath.OFFER_VIEW,offer);
    }

    /**
     * Add the Offer to the favory list
     * @param actionEvent
     * @param offerID
     * @param button
     */
    public void addToFavory(ActionEvent actionEvent,int offerID,JFXButton button){
       favoryController.addToFavory(actionEvent,offerID,button);
    }

    /**
     * Delete the offer from the favory list
     * @param actionEvent
     * @param offerID
     * @param button
     */
    public void deleteFromFavory(ActionEvent actionEvent, int offerID,JFXButton button){
        favoryController.deleteFromFavory(actionEvent,offerID,button);
    }
    private void filterTitle(ObservableValue<? extends String> observable, String oldValue, String newValue, FilteredList<Offer> filteredData){
        // We check for each category
        filteredData.setPredicate(offer -> {

                // If the text field is empty, we display all the categories
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // We compare what he entered (in lower case)
                String lowerCaseFilter = newValue.toLowerCase();
                // If the filter matches the name of the category
            // The category is displayed
            // The name doesn't match, not displayed
            return offer.getTitle().toLowerCase().contains(lowerCaseFilter);
        });
    }

    public static void buttonSeeOffer(TableColumn<Offer,Integer> offerButton){
        offerButton.setCellFactory(param -> new TableCell<>() {

            private final Button seeOfferButton = new Button("See Offer");
            {
                seeOfferButton.setOnAction(event -> seeOfferPage(event, param.getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    setGraphic(seeOfferButton);
                }
            }
        });
    }
}
