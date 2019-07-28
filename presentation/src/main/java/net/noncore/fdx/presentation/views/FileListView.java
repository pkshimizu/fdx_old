package net.noncore.fdx.presentation.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.noncore.fdx.domain.usecases.filelist.load.FileDto;
import net.noncore.fdx.presentation.viewmodel.FileListViewModel;
import net.noncore.fdx.presentation.viewmodel.FileListViewOperator;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FileListView extends View<AnchorPane> implements FileListViewOperator {
    @NonNull
    private FileListViewModel viewModel;
    @FXML
    private ListView<FileListItem> listView;
    private ObservableList<FileListItem> items = FXCollections.observableArrayList();

    @Override
    protected String getFxmlName() {
        return "FileListView.fxml";
    }

    @Override
    protected void initialize(AnchorPane root) {
        listView.setCellFactory(param -> new FileListCell());
        listView.itemsProperty().set(items);
        listView.setOnMouseClicked(viewModel::onMouseClicked);
        listView.setOnKeyReleased(viewModel::onKeyReleased);
        viewModel.initialize(this);
    }

    @Override
    public FileDto getSelectedItem() {
        return listView.getSelectionModel().getSelectedItem().getFile();
    }

    @Override
    public void updateFileList(List<FileDto> files) {
        List<FileListItem> fileProperties = files.stream().map(FileListItem::new).collect(Collectors.toList());
        items.clear();
        items.addAll(fileProperties);
    }

    private static class FileListCell extends ListCell<FileListItem> {
        private FileListCellView view;

        FileListCell() {
            view = new FileListCellView();
            view.getRoot();
        }

        @Override
        protected void updateItem(FileListItem item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                view.getName().textProperty().bind(item.getName());
                view.getSize().textProperty().bind(item.getSize());
                view.getDate().textProperty().bind(item.getDate());
                setGraphic(view.getRoot());
            }
        }
    }

    @Getter
    private static class FileListItem {
        private FileDto file;
        private StringProperty name;
        private StringProperty size;
        private StringProperty date;

        FileListItem(FileDto file) {
            this.file = file;
            name = new SimpleStringProperty(file.getName());
            size = new SimpleStringProperty(file.getSize().map(size -> NumberFormat.getInstance().format(size.getBites())).orElse("<DIR>"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh.mm.ss");
            date = new SimpleStringProperty(file.getDateTime().format(formatter));
        }
    }
}
