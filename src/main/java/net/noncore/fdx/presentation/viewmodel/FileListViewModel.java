package net.noncore.fdx.presentation.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.noncore.fdx.domain.dtos.FileDto;
import net.noncore.fdx.domain.usecases.filelist.load.LoadFileListRequest;
import net.noncore.fdx.domain.usecases.filelist.load.LoadFileListResponse;
import net.noncore.fdx.domain.usecases.filelist.load.LoadFileListUsecase;
import net.noncore.fdx.presentation.views.FileListCellView;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FileListViewModel {
    private ListView listView;
    @NonNull
    private LoadFileListUsecase loadFileListUsecase;

    public void initialize(ListView listView) {
        this.listView = listView;
        listView.setCellFactory(param -> new FileListCell());

        LoadFileListResponse response = loadFileListUsecase.doIt(new LoadFileListRequest("/path/to/folder"));
        List<FileProperty> fileProperties = response.getFiles().stream().map(FileProperty::new).collect(Collectors.toList());

        listView.itemsProperty().set(FXCollections.observableArrayList(fileProperties));
    }

    @Getter
    private static class FileProperty {
        private StringProperty name;
        private StringProperty size;
        private StringProperty date;

        public FileProperty(FileDto file) {
            name = new SimpleStringProperty(file.getName());
            size = new SimpleStringProperty(file.getSize().map(size -> NumberFormat.getInstance().format(size)).orElse("<DIR>"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh.mm.ss");
            date = new SimpleStringProperty(file.getDateTime().format(formatter));
        }
    }

    private static class FileListCell extends ListCell<FileProperty> {
        private FileListCellView view;

        FileListCell() {
            view = new FileListCellView();
            view.getRoot();
        }

        @Override
        protected void updateItem(FileProperty item, boolean empty) {
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
}
