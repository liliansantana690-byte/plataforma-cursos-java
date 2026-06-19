package com.plataforma.controller;

import com.plataforma.dao.MatriculaDAO;
import com.plataforma.model.UsuarioRanking;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class RankingController {

    @FXML private TableView<UsuarioRanking> tabelaRanking;
    @FXML private TableColumn<UsuarioRanking, String> colNome;
    @FXML private TableColumn<UsuarioRanking, Integer> colTotal;

    private final MatriculaDAO matriculaDAO = new MatriculaDAO();

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));
        colTotal.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getTotalConcluidos()).asObject());

        List<UsuarioRanking> ranking = matriculaDAO.listarRanking();
        tabelaRanking.setItems(FXCollections.observableArrayList(ranking));
    }
}
