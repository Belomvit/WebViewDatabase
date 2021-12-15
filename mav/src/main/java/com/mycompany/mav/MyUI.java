package com.mycompany.mav;

import com.mycompany.sp6.Books;
import com.mycompany.sp6.Lidi;
import com.mycompany.sp6.Own;
import static com.sun.tools.javac.tree.TreeInfo.name;
import jers.LidiNJC;
import jers.BooksNJC;
import jers.OwnNJC;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.SingleSelectionModel;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        
    final VerticalLayout layout = new VerticalLayout();
    layout.setMargin(true);
    setContent(layout);
    LidiNJC restClient = new LidiNJC();
    BooksNJC restClientBooks = new BooksNJC();
    OwnNJC restClientOwn = new OwnNJC();    


    Grid<Lidi> gridLidi = new Grid<>();
    gridLidi.addColumn(Lidi::getId).setCaption("Id");
    gridLidi.addColumn(Lidi::getJmeno).setCaption("Name");
    gridLidi.addColumn(Lidi::getPrijmeni).setCaption("Prijm");
    
gridLidi.setSelectionMode(SelectionMode.SINGLE );
SingleSelectionModel<Lidi> singleSelect =
      (SingleSelectionModel<Lidi>) gridLidi.getSelectionModel();

    
    Grid<Books> gridBooks = new Grid<>();
    gridBooks.addColumn(Books::getId).setCaption("Id");
    gridBooks.addColumn(Books::getName).setCaption("Name");
    gridBooks.addColumn(Books::getAuthor).setCaption("Author");
    
    Grid<Own> gridOwn = new Grid<>();
    gridOwn.addColumn(Own::getId).setCaption("Id");
    gridOwn.addColumn(Own::getIdLidi).setCaption("Id cloveka");
    gridOwn.addColumn(Own::getIdBook).setCaption("Id book");
    gridOwn.addColumn(Own::getDate).setCaption("Date");

    HorizontalLayout hLayot = new HorizontalLayout();
    
    final TextField addidLidi = new TextField();
            addidLidi.setCaption("Type id:");
    final TextField addnameLidi = new TextField();
            addnameLidi.setCaption("Type name:");
    final TextField addprijmeniLidi = new TextField();
            addprijmeniLidi.setCaption("Type prijmeni:");
    final TextField addAuthor = new TextField();
        addAuthor.setCaption("Type Author:");

    final TextField addidOwnLidi = new TextField();
            addidOwnLidi.setCaption("Type id cloveka");
    final TextField addidOwnBook = new TextField();
            addidOwnBook.setCaption("Type id book :");
    final TextField addidOwnDate = new TextField();
            addidOwnDate.setCaption("Type date:");

        
//    Button test = new Button("test");
//    test.addClickListener((ClickEvent event) -> {
//       GenericType<List<Lidi>> gType = new GenericType<List<Lidi>>(){};
//       String a = restClient.find_JSON(gType, "12222");
//       if(a.isEmpty() ){
//           Notification.show("y", Type.ERROR_MESSAGE);
//       }else{
//           Notification.show("n", Type.ERROR_MESSAGE);
//       }
//    });
//    layout.addComponent(test);
        
    Button addButtLidi = new Button("Add");
    addButtLidi.addClickListener((ClickEvent event) -> {
        if(addidLidi.getValue().isEmpty() || addnameLidi.getValue().isEmpty() || addprijmeniLidi.getValue().isEmpty() )
                {Notification.show("FILL ALL", Type.WARNING_MESSAGE);return;}
        try{
            if(!checkID("Lidi",addidLidi.getValue())){
                if(addidLidi.getValue().isEmpty()){
                    Notification.show("FILL ALL", Type.WARNING_MESSAGE);
                    return;
                }
                Notification.show("Id: " + addidLidi.getValue() + " already exist.", Type.ERROR_MESSAGE);
                return;
            }
            Lidi l = new Lidi(Integer.parseInt(addidLidi.getValue()),addnameLidi.getValue(),addprijmeniLidi.getValue());
            restClient.create_JSON(l);
            refreshGridLidi(gridLidi);
        }catch(NumberFormatException e){
             Notification.show("FILL ALL", Type.WARNING_MESSAGE);
        } catch(NotFoundException e2){
            Notification.show("Not found", Type.ERROR_MESSAGE);
        }
    });

    Button editButtLidi = new Button("Edit");
    editButtLidi.addClickListener((ClickEvent event) -> {
        try{
            if(checkID("Lidi",addidLidi.getValue())){
                Notification.show("Id: " + addidLidi.getValue() + " does not exist.", Type.ERROR_MESSAGE);
                return;
            }
            Lidi l = new Lidi(Integer.parseInt(addidLidi.getValue()),addnameLidi.getValue(),addprijmeniLidi.getValue());
            restClient.edit_JSON(l,addidLidi.getValue());
            refreshGridLidi(gridLidi);
        }catch(NumberFormatException e){
             Notification.show("FILL ALL", Type.WARNING_MESSAGE);
        }catch(NotFoundException e2){
            Notification.show("Not found", Type.ERROR_MESSAGE);
        }
    });
    
    Button delButtLidi = new Button("Delete");
    delButtLidi.addClickListener((ClickEvent event) -> {
        if(addidLidi.getValue().isEmpty()){Notification.show("Enter ID to delete", Type.WARNING_MESSAGE);}
        try{
            if(checkID("Lidi",addidLidi.getValue())){
                Notification.show("Id: " + addidLidi.getValue() + " does not exist.", Type.ERROR_MESSAGE);
                return;
            }
            if(!checkDEL(Integer.parseInt(addidLidi.getValue()))){Notification.show("Book Purchased", Type.ERROR_MESSAGE);return;}
            restClient.remove(addidLidi.getValue());
            refreshGridLidi(gridLidi);
        }catch(NotFoundException e2){
            Notification.show("Not found", Type.ERROR_MESSAGE);
        }
    });
    
    
    
    Button addButtBooks = new Button("Add");
    addButtBooks.addClickListener((ClickEvent event) -> {
        if(addidLidi.getValue().isEmpty() || addnameLidi.getValue().isEmpty() || addAuthor.getValue().isEmpty() )
                {Notification.show("FILL ALL", Type.WARNING_MESSAGE);return;}
        try{
            if(!checkID("Books",addidLidi.getValue())){
                if(addidLidi.getValue().isEmpty()){
                    Notification.show("FILL ALL", Type.WARNING_MESSAGE);
                    return;
                }
                Notification.show("Id: " + addidLidi.getValue() + " already exist.", Type.ERROR_MESSAGE);
                return;
            }
            Books l = new Books(Integer.parseInt(addidLidi.getValue()),addnameLidi.getValue(),addAuthor.getValue());
            restClientBooks.create_JSON(l);
            refreshGridBooks(gridBooks);
            Notification.show("Added new book", Type.HUMANIZED_MESSAGE);
        }catch(NumberFormatException e){
             Notification.show("FILL ALL", Type.WARNING_MESSAGE);
        } catch(NotFoundException e2){
            Notification.show("Not found", Type.ERROR_MESSAGE);
        }
    });
    
    Button editButtBooks = new Button("Edit");
    editButtBooks.addClickListener((ClickEvent event) -> {
        try{
            if(checkID("Books",addidLidi.getValue())){
                Notification.show("Id: " + addidLidi.getValue() + " does not exist.", Type.ERROR_MESSAGE);
                return;
            }
            Books l = new Books(Integer.parseInt(addidLidi.getValue()),addnameLidi.getValue(),addAuthor.getValue());
            restClientBooks.edit_JSON(l,addidLidi.getValue());
            refreshGridBooks(gridBooks);
        }catch(NumberFormatException e){
             Notification.show("FILL ALL", Type.WARNING_MESSAGE);
        } catch(NotFoundException e2){
            Notification.show("Not found", Type.ERROR_MESSAGE);
        }
    });
    
    Button delButtBooks = new Button("Delete");
    delButtBooks.addClickListener((ClickEvent event) -> {
        if(addidLidi.getValue().isEmpty()){Notification.show("Enter ID to delete", Type.WARNING_MESSAGE);}
        try{
            if(checkID("Books",addidLidi.getValue())){
                Notification.show("Id: " + addidLidi.getValue() + " does not exist.", Type.ERROR_MESSAGE);
                return;
            }
            restClientBooks.remove(addidLidi.getValue());
            refreshGridBooks(gridBooks);
        }catch(NotFoundException e2){
            Notification.show("Not found", Type.ERROR_MESSAGE);
        }
    });

    
    
    
    Button addButtOwn = new Button("Add");
    addButtOwn.addClickListener((ClickEvent event) -> {
        if(addidLidi.getValue().isEmpty() || addidOwnLidi.getValue().isEmpty() || addidOwnBook.getValue().isEmpty() || addidOwnDate.getValue().isEmpty() )
                {Notification.show("FILL ALL", Type.WARNING_MESSAGE);return;}
        try{
            if(!checkID("Own",addidLidi.getValue())){
                if(addidLidi.getValue().isEmpty()){
                    Notification.show("FILL ALL", Type.WARNING_MESSAGE);
                    return;
                }
                Notification.show("Id: " + addidLidi.getValue() + " already exist.", Type.ERROR_MESSAGE);
                return;
            }
            Own l = new Own(Integer.parseInt(addidLidi.getValue()), Integer.parseInt(addidOwnLidi.getValue()),
                    Integer.parseInt(addidOwnBook.getValue()),addidOwnDate.getValue());
            if(!check(l)){return;}
            restClientOwn.create_JSON(l);
            refreshGridOwn(gridOwn);
        }catch(NumberFormatException e){
            Notification.show("FILL ALL", Type.WARNING_MESSAGE);
        } catch(NotFoundException e2){
            Notification.show("Not found", Type.ERROR_MESSAGE);
        }
    });
    
    Button editButtOwn = new Button("Edit");
    editButtOwn.addClickListener((ClickEvent event) -> {
        try{
            if(checkID("Own",addidLidi.getValue())){
                Notification.show("Id: " + addidLidi.getValue() + " does not exist.", Type.ERROR_MESSAGE);
                return;
            }
            Own l = new Own(Integer.parseInt(addidLidi.getValue()), Integer.parseInt(addidOwnLidi.getValue()),
                    Integer.parseInt(addidOwnBook.getValue()),addidOwnDate.getValue());
            restClientOwn.edit_JSON(l,addidLidi.getValue());
            refreshGridOwn(gridOwn);
        }catch(NumberFormatException e){
             Notification.show("FILL ALL", Type.WARNING_MESSAGE);
        } catch(NotFoundException e2){
            Notification.show("Not found", Type.ERROR_MESSAGE);
        }
    });
    
    Button delButtOwn = new Button("Delete");
    delButtOwn.addClickListener((ClickEvent event) -> {
        if(addidLidi.getValue().isEmpty()){Notification.show("Enter ID to delete", Type.WARNING_MESSAGE);}
        try{
            if(checkID("Own",addidLidi.getValue())){
                Notification.show("Id: " + addidLidi.getValue() + " does not exist.", Type.ERROR_MESSAGE);
                return;
            }
            restClientOwn.remove(addidLidi.getValue());
            refreshGridOwn(gridOwn);
        }catch(NotFoundException e2){
                Notification.show("Not found", Type.ERROR_MESSAGE);
            }
    });
    
    
    
    HorizontalLayout hlayout2 = new HorizontalLayout();
    HorizontalLayout hlayout3 = new HorizontalLayout();
    
    Button LidiSR = new Button("Lidi show");
    LidiSR.addClickListener((ClickEvent event) -> {
      refreshGridLidi(gridLidi);
      clear(hlayout2,hlayout3,layout,gridLidi,gridBooks,gridOwn);
      hlayout2.addComponents(addidLidi, addnameLidi, addprijmeniLidi);
      hlayout3.addComponents(addButtLidi, editButtLidi, delButtLidi);
      layout.addComponent(gridLidi);
     // Notification.show("I HAZ NOTIFICATZ UR DISPLAI!!111", Type.ERROR_MESSAGE);
    });

    Button BooksSR = new Button("Books show");
    BooksSR.addClickListener((ClickEvent event) -> {
      refreshGridBooks(gridBooks);
      clear(hlayout2,hlayout3,layout,gridLidi,gridBooks,gridOwn);
      hlayout2.addComponents(addidLidi, addnameLidi, addAuthor);
      hlayout3.addComponents(addButtBooks, editButtBooks, delButtBooks);
      layout.addComponent(gridBooks);
    });
    
    Button OwnSR = new Button("Purchase show");
    OwnSR.addClickListener((ClickEvent event) -> {
      refreshGridOwn(gridOwn);
      clear(hlayout2,hlayout3,layout,gridLidi,gridBooks,gridOwn);
      hlayout2.addComponents(addidLidi, addidOwnLidi, addidOwnBook, addidOwnDate);
      hlayout3.addComponents(addButtOwn, editButtOwn, delButtOwn);
      layout.addComponent(gridOwn);
    });
    
    hLayot.addComponents(LidiSR,BooksSR,OwnSR);
    layout.addComponent(hLayot);
    layout.addComponent(hlayout2);
    layout.addComponent(hlayout3);


    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    public void clear (HorizontalLayout hlayout2, HorizontalLayout hlayout3, VerticalLayout layout, 
            Grid<Lidi> g1, Grid<Books> g2, Grid<Own> g3){
      hlayout2.removeAllComponents();
      hlayout3.removeAllComponents();
      layout.removeComponent(g1);
      layout.removeComponent(g2);
      layout.removeComponent(g3);
    }
    
    public Boolean checkID(String table,String id){
        String a;
        a = "";
        if("Lidi".equals(table)){
            GenericType<List<Lidi>> gType = new GenericType<List<Lidi>>(){};
            LidiNJC restClient = new LidiNJC();
            a = restClient.find_JSON(gType, id);
        }
        if(table == "Books"){
            GenericType<List<Books>> gType = new GenericType<List<Books>>(){};
            BooksNJC restClient = new BooksNJC();
            a = restClient.find_JSON(gType, id);
        }
        if(table == "Own"){
            GenericType<List<Own>> gType = new GenericType<List<Own>>(){};
            OwnNJC restClient = new OwnNJC();
            a = restClient.find_JSON(gType, id);
        }
        
       if(a.isEmpty() ){
           return true;
       }            
       return false;
    }

    
    public Boolean checkDEL(Integer ID){

        return true;
    }
    
    
    public Boolean check(Own ow){
        LidiNJC restClientL = new LidiNJC();
        BooksNJC restClientB = new BooksNJC();
        GenericType<List<Lidi>> gTypeL = new GenericType<List<Lidi>>(){};
        GenericType<List<Books>> gTypeB = new GenericType<List<Books>>(){};
        String L = restClientL.find_JSON(gTypeL, String.valueOf(ow.getIdLidi()));
        String B = restClientB.find_JSON(gTypeB, String.valueOf(ow.getIdBook()));
        if(L.isEmpty() && B.isEmpty()){Notification.show("Wrong book & lidi id", Type.ERROR_MESSAGE);return false;}
        if(L.isEmpty()){Notification.show("Wrong lidi id", Type.ERROR_MESSAGE);return false;}
        if(B.isEmpty()){Notification.show("Wrong book id", Type.ERROR_MESSAGE);return false;}
        return true;
    }
    
    public Grid<Lidi> refreshGridLidi(Grid<Lidi> grid){
        LidiNJC restClient = new LidiNJC();
        GenericType<List<Lidi>> gType = new GenericType<List<Lidi>>(){};
        List<Lidi> people = restClient.findAll_JSON(gType);
        grid.setItems(people);
        return grid;
    }
    
    public Grid<Books> refreshGridBooks (Grid<Books> grid){
        BooksNJC restClient = new BooksNJC();
        GenericType<List<Books>> gType = new GenericType<List<Books>>(){};
        List<Books> books = restClient.findAll_JSON(gType);
        grid.setItems(books);
        return grid;
    }
    
    public Grid<Own> refreshGridOwn (Grid<Own> grid){
        OwnNJC restClient = new OwnNJC();
        GenericType<List<Own>> gType = new GenericType<List<Own>>(){};
        List<Own> ow = restClient.findAll_JSON(gType);
        grid.setItems(ow);
        return grid;
    }
    
    
}
