package br.java.projeto.poo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import br.java.projeto.poo.models.BO.ClienteBO;
import br.java.projeto.poo.models.BO.EnderecoBO;
import br.java.projeto.poo.models.BO.VeiculoBO;
import br.java.projeto.poo.models.VO.ClienteVO;
import br.java.projeto.poo.models.VO.EnderecoVO;
import br.java.projeto.poo.models.VO.FuncionarioVO;
import br.java.projeto.poo.models.VO.VeiculoVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;


public class ClienteController extends BaseController{

    // ====== campos da tela principal de clientes ======
    private ClienteBO clienteBO = new ClienteBO();
    static ArrayList<ClienteVO> listaClientes;
    static ObservableList<ClienteVO> clientesDisponiveis;

    @FXML protected Button novoCliente;
    @FXML private TextField campoBusca;
    @FXML private TableView<ClienteVO> tabelaClientes;
    @FXML private TableColumn<ClienteVO, String>  columnBut;
    @FXML private TableColumn<ClienteVO, String>  columnCPF;
    @FXML private TableColumn<ClienteVO, EnderecoVO>  columnEnd;
    @FXML private TableColumn<ClienteVO, Integer> columnIdCliente;
    @FXML private TableColumn<ClienteVO, String>  columnNome;
    @FXML private TableColumn<ClienteVO, VeiculoVO>  columnVeic;
    // ==================================================


    @Override
    public void initialize() throws Exception{
        
        
        listaClientes = this.clienteBO.listar();
        clientesDisponiveis = FXCollections.observableArrayList(listaClientes);
        this.inicializarTabela(); 
        
    }




    @FXML
    void abrirCadastro() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Clientes/CadastrarCliente.fxml"));
        Parent root = loader.load();
        Scene janelaCad = new Scene(root);
        Stage palco = new Stage();
        palco.setResizable(false);
        palco.setScene(janelaCad);
        palco.initModality(Modality.APPLICATION_MODAL);
        palco.initStyle(StageStyle.UNDECORATED);
        Window wNC = novoCliente.getScene().getWindow();
        palco.setX((wNC.getX() + wNC.getWidth()/2) - 250);
        palco.setY((wNC.getY() + wNC.getHeight()/2) - 325);
        palco.show();
        
    }




    
    void abrirEdicao(ClienteVO cliente, int i) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Clientes/EditarCliente.fxml"));
        Parent root = loader.load();

        ClienteSubController controller = loader.getController();
        controller.initialize(cliente, i);

        Scene janelaEdit = new Scene(root);
        Stage palco = new Stage();
        palco.setResizable(false);
        palco.setScene(janelaEdit);
        palco.initModality(Modality.APPLICATION_MODAL);
        palco.initStyle(StageStyle.UNDECORATED);
        Window wNC = novoCliente.getScene().getWindow();
        double centralizarEixoX, centralizarEixoY;
        centralizarEixoX = (wNC.getX() + wNC.getWidth()/2) - 250;
        centralizarEixoY = (wNC.getY() + wNC.getHeight()/2) - 225;
        palco.setX(centralizarEixoX);
        palco.setY(centralizarEixoY);
        palco.show();
    }



    void abrirExclusao(ClienteVO cliente, int index) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Modals/ModalExcluir.fxml"));
        Parent root = loader.load();

        Scene janelaEdit = new Scene(root);
        Stage palco = new Stage();
        palco.setResizable(false);
        palco.setScene(janelaEdit);
        palco.initModality(Modality.APPLICATION_MODAL);
        palco.initStyle(StageStyle.UNDECORATED);
        Window wNC = novoCliente.getScene().getWindow();
        double centralizarEixoX, centralizarEixoY;
        centralizarEixoX = (wNC.getX() + wNC.getWidth()/2) - 225;
        centralizarEixoY = (wNC.getY() + wNC.getHeight()/2) - 150;
        palco.setX(centralizarEixoX);
        palco.setY(centralizarEixoY);
        palco.showAndWait();

        ModalsController modalExc = loader.getController();

        if(modalExc.getExclusaoValid()){
            ClienteBO clienteExcluido = new ClienteBO();
            clienteExcluido.deletar(cliente);
        }
    }


    @FXML
    void buscarCliente(){
        try {
            ArrayList<ClienteVO> clienteVOs;
            if (this.campoBusca.getText().length() > 2) {
                if (this.campoBusca.getText().matches("^\\d{3}.*")) {
                    clienteVOs = clienteBO.buscarPorCPF(this.campoBusca.getText());
                    clientesDisponiveis.setAll(clienteVOs);
                } else {
                    clienteVOs = clienteBO.buscarPorNome(this.campoBusca.getText());
                    clientesDisponiveis.setAll(clienteVOs);
                }
            } else {
               clientesDisponiveis.setAll(listaClientes);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void inicializarTabela() throws SQLException {
        columnNome.setCellValueFactory(new PropertyValueFactory<ClienteVO, String>("nome"));
        columnCPF.setCellValueFactory(new PropertyValueFactory<ClienteVO, String>("cpf"));
        columnIdCliente.setCellValueFactory(new PropertyValueFactory<ClienteVO, Integer>("id"));
        //columnEnd.setCellValueFactory(new PropertyValueFactory<ClienteVO, EnderecoVO>("endereco"));
        columnVeic.setCellValueFactory(new PropertyValueFactory<ClienteVO, VeiculoVO>("veiculo"));
        tabelaClientes.setItems(clientesDisponiveis);
        this.inicializarBotoesDeAcao(clientesDisponiveis);
    }

    private void inicializarBotoesDeAcao (ObservableList<ClienteVO> funcs) {
        columnBut.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button();
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnEdit, btnDelete);

            {
                btnEdit.getStyleClass().add("btn-edit");
                btnDelete.getStyleClass().add("btn-delete");
                btnEdit.setOnAction(event -> {
                    try {
                        ClienteVO cliente = getTableView().getItems().get(getIndex());
                        abrirEdicao(cliente, getIndex());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

                btnDelete.setOnAction(event -> {
                    try{
                        ClienteVO cliente = getTableView().getItems().get(getIndex());
                        if (clienteBO.deletar(cliente)) {
                            funcs.remove(cliente);
                            //abrirExclusao(cliente, getIndex());
                        }
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                });
            }

            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btnContainer.setStyle("-fx-padding: 0 20 0 20;");
                    btnContainer.setSpacing(10);
                    setGraphic(btnContainer);
                }
            }
        });
    }




    void realizarExclusao() throws Exception {

    }

}
