package br.java.projeto.poo.controller;
import br.java.projeto.poo.exceptions.ErroDeAuthenticacaoException;
import br.java.projeto.poo.models.BO.FuncionarioBO;
import br.java.projeto.poo.models.VO.FuncionarioVO;
import br.java.projeto.poo.src.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private FuncionarioBO<FuncionarioVO> funcBO = new FuncionarioBO<FuncionarioVO>();

    @FXML
    private Button logar;

    @FXML
    private TextField nomeUsuario;

    @FXML
    private PasswordField password;

    @FXML
    void logarUsuario(ActionEvent event) throws Exception {
        try {
            FuncionarioVO usuario = new FuncionarioVO();
            usuario.setCpf(nomeUsuario.getText());
            usuario.setSenha(password.getText());

            funcBO.authenticar(usuario);
            App.navegarEntreTelas("orcamentos");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroDeAuthenticacaoException(e.getMessage());
        }
    }

}
