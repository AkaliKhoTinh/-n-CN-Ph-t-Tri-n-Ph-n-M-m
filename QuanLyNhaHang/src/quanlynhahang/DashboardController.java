package quanlynhahang;

import java.lang.Long;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController implements Initializable {

    @FXML
    private TextField availableID_ID;

    @FXML
    private TextField availableID_Name;

    @FXML
    private TextField availableID_Price;

    @FXML
    private ComboBox<?> availableID_Size;

    @FXML
    private ComboBox<?> availableID_Status;

    @FXML
    private Button availableID_addBtn;

    @FXML
    private TableColumn<categories, String> availableID_colFd;

    @FXML
    private TableColumn<categories, String> availableID_colID;

    @FXML
    private TableColumn<categories, String> availableID_colPrice;

    @FXML
    private TableColumn<categories, String> availableID_col_Status;

    @FXML
    private TableColumn<categories, String> availableID_col_size;

    @FXML
    private Button availableID_delBtn;

    @FXML
    private AnchorPane availableID_form;

    @FXML
    private Button availableID_removeBtn;

    @FXML
    private TextField availableID_search;

    @FXML
    private TableView<categories> availableID_tableview;

    @FXML
    private Button availableID_updateBtn;

    @FXML
    private Button available_btn;

    @FXML
    private Button close;

    @FXML
    private BarChart<?, ?> dashboard_ICChart;

    @FXML
    private Label dashboard_NC;

    @FXML
    private BarChart<?, ?> dashboard_NCChart;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label order_balance;

    @FXML
    private Label dashboard_Tincome;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minisize;

    @FXML
    private Button order_addBtn;

    @FXML
    private TextField order_amount;

    @FXML
    private Button order_btn;

    @FXML
    private TableColumn<product, String> order_col_FD;

    @FXML
    private TableColumn<product, String> order_col_ID;

    @FXML
    private TableColumn<product, String> order_col_price;

    @FXML
    private TableColumn<product, String> order_col_quantity;

    @FXML
    private TableColumn<product, String> order_col_size;

    @FXML
    private AnchorPane order_form;

    @FXML
    private Button order_payBtn;

    @FXML
    private ComboBox<?> order_productID;

    @FXML
    private ComboBox<?> order_productName;

    @FXML
    private Spinner<Integer> order_quanitity;

    @FXML
    private Button order_receiptBtn;

    @FXML
    private Button order_removeBtn;

    @FXML
    private TableView<product> order_tableview;

    @FXML
    private Label order_total;

    @FXML
    private Label username;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void dashboardNC() {

        String sql = "SELECT COUNT(id) FROM product_info";

        int nc = 0;

        connect = Database.connectDb();

        try {

            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }

            dashboard_NC.setText(String.valueOf(nc));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardTI() {

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM product_info WHERE date = '" + sqlDate + "'";

        connect = Database.connectDb();

        double ti = 0;

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }

            dashboard_TI.setText("$" + String.valueOf(ti));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardTIncome() {

        String sql = "SELECT SUM(total) FROM product_info";

        connect = Database.connectDb();

        double ti = 0;

        try {

            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }
            dashboard_Tincome.setText("$" + String.valueOf(ti));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboard_NCChart(){
         try {

            dashboard_NCChart.getData().clear();

            String sql = "SELECT date, COUNT(id) FROM product_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 5";

            connect = Database.connectDb();

            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_NCChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void dashboard_ICChart(){
        dashboard_ICChart.getData().clear();

        String sql = "SELECT date, SUM(total) FROM product_info GROUP BY date ORDER BY TIMESTAMP(total) ASC LIMIT 7";

        connect = Database.connectDb();

        try {

            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {

                chart.getData().add(new XYChart.Data(result.getString(1), result.getDouble(2)));

            }

            dashboard_ICChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void availableIDAdd() {

        String sql = "INSERT INTO categories(product_id, product_name, type,price,status)"
                + "VALUE(?,?,?,?,?)";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, availableID_ID.getText());
            prepare.setString(2, availableID_Name.getText());
            prepare.setString(3, (String) availableID_Size.getSelectionModel().getSelectedItem());
            prepare.setString(4, availableID_Price.getText());
            prepare.setString(5, (String) availableID_Status.getSelectionModel().getSelectedItem());

            Alert alert;

            if (availableID_ID.getText().isEmpty()
                    || availableID_Name.getText().isEmpty()
                    || availableID_Size.getSelectionModel() == null
                    || availableID_Price.getText().isEmpty()
                    || availableID_Status.getSelectionModel() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi!!!");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập đầy đủ thông tin");
                alert.showAndWait();
            } else {

                String checkData = "SELECT product_id FROM  categories WHERE product_id='"
                        + availableID_ID.getText() + "'";

                connect = Database.connectDb();
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Lỗi!!!");
                    alert.setHeaderText(null);
                    alert.setContentText("ID sản phẩm: " + availableID_ID.getText() + " đã tồn taị!");
                    alert.showAndWait();
                } else {
                    prepare.executeUpdate();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Thành Công!!");
                    alert.setHeaderText(null);
                    alert.setContentText("Thêm thành công!!");
                    alert.showAndWait();

                    //Hien Data 
                    availableIDShowData();
                    //Xoa Data
                    avalibleFClear();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void avalibleDelete() {
        String sql = "DELETE FROM categories WHERE product_id = '"
                + availableID_ID.getText() + "'";
        connect = Database.connectDb();
        try {
            Alert alert;
            if (availableID_ID.getText().isEmpty()
                    || availableID_Name.getText().isEmpty()
                    || availableID_Size.getSelectionModel() == null
                    || availableID_Price.getText().isEmpty()
                    || availableID_Status.getSelectionModel() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi!!!");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập đầy đủ thông tin");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Update?");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc là muốn xoá sản phẩm: " + availableID_ID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Thành Công!!");
                    alert.setHeaderText(null);
                    alert.setContentText("Xóa thành công!");
                    alert.showAndWait();

                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    availableIDShowData();

                    avalibleFClear();

                } else {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Đã hủy thành công!");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void avalibleUpadate() {
        String sql = "UPDATE categories SET product_name = '"
                + availableID_Name.getText() + "', type = '"
                + availableID_Size.getSelectionModel().getSelectedItem() + "', price = '"
                + availableID_Price.getText() + "', status = '"
                + availableID_Status.getSelectionModel().getSelectedItem()
                + "' WHERE product_id = '" + availableID_ID.getText() + "'";

        connect = Database.connectDb();
        try {
            Alert alert;
            if (availableID_ID.getText().isEmpty()
                    || availableID_Name.getText().isEmpty()
                    || availableID_Size.getSelectionModel() == null
                    || availableID_Price.getText().isEmpty()
                    || availableID_Status.getSelectionModel() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi!!!");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập đầy đủ thông tin");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Update?");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc là muốn cập nhật sản phẩm: " + availableID_ID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Thành Công!!");
                    alert.setHeaderText(null);
                    alert.setContentText("Cập nhật thành công!");
                    alert.showAndWait();

                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    availableIDShowData();

                    avalibleFClear();

                } else {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Đã hủy thành công!");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void avalibleFClear() {
        availableID_ID.setText("");
        availableID_Name.setText("");
        availableID_Size.getSelectionModel().clearSelection();
        availableID_Price.setText("");
        availableID_Status.getSelectionModel().clearSelection();

    }

    public ObservableList<categories> avalibleIDListData() {
        ObservableList<categories> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM categories";
        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            categories cat;

            while (result.next()) {
                cat = new categories(result.getString("product_id"),
                        result.getString("product_name"),
                        result.getString("type"),
                        result.getDouble("price"),
                        result.getString("status"));

                listData.add(cat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void avalibleSearch() {
        FilteredList<categories> filter = new FilteredList<>(availableIDList, e -> true);
        availableID_search.textProperty().addListener((observabl, newValue, oldValue) -> {

            filter.setPredicate(predicateCategories -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (predicateCategories.getProductID().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCategories.getName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCategories.getType().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCategories.getPrice().toString().contains(searchKey)) {
                    return true;
                } else if (predicateCategories.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<categories> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(availableID_tableview.comparatorProperty());
        availableID_tableview.setItems(sortList);

    }

    private ObservableList<categories> availableIDList;

    public void availableIDShowData() {

        availableIDList = avalibleIDListData();

        availableID_colID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        availableID_colFd.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableID_col_size.setCellValueFactory(new PropertyValueFactory<>("type"));
        availableID_colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableID_col_Status.setCellValueFactory(new PropertyValueFactory<>("status"));

        availableID_tableview.setItems(availableIDList);
    }

    public void avalableFDSelect() {

        categories catData = availableID_tableview.getSelectionModel().getSelectedItem();

        int num = availableID_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        availableID_ID.setText(catData.getProductID());
        availableID_Name.setText(catData.getName());
        availableID_Price.setText(String.valueOf(catData.getPrice()));

    }

    private String[] categories = {"Đồ ăn", "Nước uống"};
    private String[] status = {"Còn hàng ", "Hết hàng"};
    private String[] size = {"S", "M", "L"};

    public void availableIDCategories() {
        List<String> listCategories = new ArrayList<>();

        for (String data : categories) {
            listCategories.add(data);
        }
    }

    public void availableIDStatus() {
        List<String> listStatus = new ArrayList<>();

        for (String data : status) {
            listStatus.add(data);
        }

        ObservableList listdata = FXCollections.observableArrayList(listStatus);
        availableID_Status.setItems(listdata);

    }

    public void availableID_Size() {
        List<String> listSize = new ArrayList<>();

        for (String data : size) {
            listSize.add(data);
        }
        ObservableList listdata = FXCollections.observableArrayList(listSize);
        availableID_Size.setItems(listdata);

    }

    public void orderAdd() {

        orderCustomerId();
        orderTotal();

        String sql = "INSERT INTO product "
                + "(customer_id, product_id, product_name, type, price, quantity, date) "
                + "VALUES(?,?,?,?,?,?,?)";

        connect = Database.connectDb();

        try {
            String orderType = "";
            double orderPrice = 0;

            String checkData = "SELECT * FROM categories WHERE product_id = '"
                    + order_productID.getSelectionModel().getSelectedItem() + "'";

            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (result.next()) {
                orderType = result.getString("type");
                orderPrice = result.getDouble("price");
            }

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(customerId));
            prepare.setString(2, (String) order_productID.getSelectionModel().getSelectedItem());
            prepare.setString(3, (String) order_productName.getSelectionModel().getSelectedItem());
            prepare.setString(4, orderType);

            double totalPrice = orderPrice * qty;

            prepare.setString(5, String.valueOf(totalPrice));

            prepare.setString(6, String.valueOf(qty));

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            prepare.setString(7, String.valueOf(sqlDate));

            prepare.executeUpdate();

            orderDisplayTotal();
            orderDisplayData();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void orderPay() {
        orderCustomerId();
        orderTotal();

        String sql = "INSERT INTO product_info (customer_id, total, date) VALUES(?,?,?)";

        connect = Database.connectDb();

        try {

            Alert alert;

            if (balance == 0 || String.valueOf(balance) == "$0.0" || String.valueOf(balance) == null
                    || totalP == 0 || String.valueOf(totalP) == "$0.0" || String.valueOf(totalP) == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Bạn cần nhập số tiền khách đưa");
                alert.showAndWait();
            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Thanh toán");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc thanh toán?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(totalP));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(3, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Thành công");
                    alert.setHeaderText(null);
                    alert.setContentText("Thanh toán thành công!");
                    alert.showAndWait();

                    order_total.setText("$0.0");
                    order_balance.setText("$0.0");
                    order_amount.setText("");

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Hủy");
                    alert.setHeaderText(null);
                    alert.setContentText("Đã hủy thành công!");
                    alert.showAndWait();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private double totalP = 0;

    public void orderTotal() {
        orderCustomerId();

        String sql = "SELECT SUM(price) FROM product WHERE customer_id = " + customerId;

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private double amount;
    private double balance;

    public void orderAmount() {
        orderTotal();

        Alert alert;

        if (order_amount.getText().isEmpty() || order_amount.getText() == null
                || order_amount.getText() == "") {
            alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please type the amount!");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(order_amount.getText());

            if (amount < totalP) {
                order_amount.setText("");
            } else {
                balance = (amount - totalP);
                order_balance.setText("$" + String.valueOf(balance));
            }
        }
    }

    public void orderDisplayTotal() {
        orderTotal();
        order_total.setText("$" + String.valueOf(totalP));

    }

    public ObservableList<product> orderListData() {
        orderCustomerId();

        ObservableList<product> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product WHERE customer_id = " + customerId;

        connect = Database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            product prod;

            while (result.next()) {
                prod = new product(result.getInt("id"),
                        result.getString("product_id"),
                        result.getString("product_name"),
                        result.getString("type"),
                        result.getDouble("price"),
                        result.getInt("quantity"));

                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;

    }

    public void orderRecipt(){
        
    }
     
    public void orderRemove() {

        String sql = "DELETE FROM product WHERE id = " + item;

        connect = Database.connectDb();

        try {
            Alert alert;

            if (item == 0 || String.valueOf(item) == null || String.valueOf(item) == "") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng chọn vật phẩm trước!!");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muốn xóa : " + item + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Thành công");
                    alert.setHeaderText(null);
                    alert.setContentText("Đã xóa thành công!");
                    alert.showAndWait();

                    orderDisplayData();
                    orderDisplayTotal();

                    order_amount.setText("");
                    order_balance.setText("$0.0");

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Hủy");
                    alert.setHeaderText(null);
                    alert.setContentText("Đã hủy thành công!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int item;

    public void orderSelectData() {

        product prod = order_tableview.getSelectionModel().getSelectedItem();
        int num = order_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        item = prod.getId();
    }

    private ObservableList<product> orderData;

    public void orderDisplayData() {
        orderData = orderListData();
        order_col_ID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        order_col_FD.setCellValueFactory(new PropertyValueFactory<>("name"));
        order_col_size.setCellValueFactory(new PropertyValueFactory<>("type"));
        order_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        order_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        order_tableview.setItems(orderData);
    }

    private int customerId;

    public void orderCustomerId() {

        String sql = "SELECT customer_id FROM product";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                customerId = result.getInt("customer_id");
            }

            String checkData = "SELECT customer_id FROM product_info";

            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            int customerInfoId = 0;

            while (result.next()) {
                customerInfoId = result.getInt("customer_id");
            }

            if (customerId == 0) {
                customerId += 1;
            } else if (customerId == customerInfoId) {
                customerId += 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void orderProductID() {
        String sql = "SELECT product_id FROM categories WHERE status = 'Còn hàng '";
        connect = Database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("product_id"));
            }

            order_productID.setItems(listData);

            orderProductName();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void orderProductName() {
        String sql = "SELECT product_name FROM categories WHERE product_id = '"
                + order_productID.getSelectionModel().getSelectedItem() + "'";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("product_name"));
            }

            order_productName.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SpinnerValueFactory<Integer> spinner;

    public void orderSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);

        order_quanitity.setValueFactory(spinner);
    }

    private int qty;

    public void orderQuantity() {
        qty = order_quanitity.getValue();
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            availableID_form.setVisible(false);
            order_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color:#25bcbf; -fx-text-fill: #fff; -fx-border-width: 0px;");
            available_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");
            order_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");

            dashboardNC();
            dashboardTI();
            dashboardTIncome();
            dashboard_NCChart();
            dashboard_ICChart();

        } else if (event.getSource() == available_btn) {
            dashboard_form.setVisible(false);
            availableID_form.setVisible(true);
            order_form.setVisible(false);

            available_btn.setStyle("-fx-background-color:#25bcbf; -fx-text-fill: #fff; -fx-border-width: 0px;");
            dashboard_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");
            order_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");

            availableIDShowData();
            avalibleSearch();

        } else if (event.getSource() == order_btn) {
            dashboard_form.setVisible(false);
            availableID_form.setVisible(false);
            order_form.setVisible(true);

            order_btn.setStyle("-fx-background-color:#25bcbf; -fx-text-fill: #fff; -fx-border-width: 0px;");
            available_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");
            dashboard_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");

            orderProductID();
            orderProductName();
            orderSpinner();
            orderDisplayData();
            orderDisplayTotal();
        }

    }

    private double x = 0;
    private double y = 0;

    public void logout() {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText(null);
            alert.setContentText("Bạn muốn đăng xuất?");
            Optional<ButtonType> option = alert.showAndWait();
            //Trả về màn hình đăng nhập
            if (option.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.7f);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(0.9);
                });
                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayUsername() {
        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);

    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dashboardNC();
        dashboardTI();
        dashboardTIncome();

        displayUsername();
        availableIDStatus();
        availableID_Size();
        availableIDShowData();
        avalibleFClear();

        orderProductID();
        orderProductName();
        orderSpinner();

        orderDisplayData();
        orderDisplayTotal();
    }

}
