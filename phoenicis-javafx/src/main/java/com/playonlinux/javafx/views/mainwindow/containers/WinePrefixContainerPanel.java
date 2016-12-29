package com.playonlinux.javafx.views.mainwindow.containers;

import com.playonlinux.containers.dto.WinePrefixDTO;
import com.playonlinux.containers.wine.parameters.*;
import com.playonlinux.javafx.views.common.ColumnConstraintsWithPercentage;
import com.playonlinux.javafx.views.common.TextWithStyle;
import com.sun.javafx.collections.ImmutableObservableList;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static com.playonlinux.configuration.localisation.Localisation.translate;

public class WinePrefixContainerPanel extends AbstractContainerPanel<WinePrefixDTO> {
    private static final String CAPTION_TITLE_CSS_CLASS = "captionTitle";
    private static final String CONFIGURATION_PANE_CSS_CLASS = "containerConfigurationPane";
    private static final String TITLE_CSS_CLASS = "title";
    private final List<Node> lockableElements = new ArrayList<>();

    public WinePrefixContainerPanel(WinePrefixDTO containerEntity) {
        super(containerEntity);
        this.getTabs().add(drawDisplayTab(containerEntity));
        this.getTabs().add(drawInputTab(containerEntity));
        this.getTabs().add(drawToolsTab(containerEntity));
    }

    @Override
    Tab drawInformationTab(WinePrefixDTO container) {
        final Tab informationTab = new Tab(translate("Information"));
        final VBox informationPane = new VBox();
        final Text title = new TextWithStyle(translate("Information"), TITLE_CSS_CLASS);

        informationPane.getStyleClass().add(CONFIGURATION_PANE_CSS_CLASS);
        informationPane.getChildren().add(title);

        final GridPane informationContentPane = new GridPane();
        informationContentPane.getStyleClass().add("grid");

        informationContentPane.add(new TextWithStyle(translate("Name:"), CAPTION_TITLE_CSS_CLASS), 0, 0);
        informationContentPane.add(new Text(container.getName()), 1, 0);

        informationContentPane.add(new TextWithStyle(translate("Path:"), CAPTION_TITLE_CSS_CLASS), 0, 1);
        informationContentPane.add(new Text(container.getPath()), 1, 1);

        informationContentPane.add(new TextWithStyle(translate("Wine version:"), CAPTION_TITLE_CSS_CLASS), 0, 2);
        informationContentPane.add(new Text(container.getVersion()), 1, 2);

        informationContentPane.add(new TextWithStyle(translate("Wine architecture:"), CAPTION_TITLE_CSS_CLASS), 0, 3);
        informationContentPane.add(new Text(container.getArchitecture()), 1, 3);

        informationContentPane.add(new TextWithStyle(translate("Wine distribution:"), CAPTION_TITLE_CSS_CLASS), 0, 4);
        informationContentPane.add(new Text(container.getDistribution()), 1, 4);

        informationContentPane.getRowConstraints().addAll(
                new RowConstraints(20.),
                new RowConstraints(20.),
                new RowConstraints(20.),
                new RowConstraints(20.),
                new RowConstraints(20.)
        );

        informationContentPane.getColumnConstraints().addAll(
                new ColumnConstraintsWithPercentage(20),
                new ColumnConstraintsWithPercentage(80)
        );

        informationPane.getChildren().addAll(informationContentPane);
        informationTab.setContent(informationPane);
        informationTab.setClosable(false);
        return informationTab;
    }


    private Tab drawDisplayTab(WinePrefixDTO winePrefixDTO) {
        final Tab displayTab = new Tab(translate("Display"));
        final VBox displayPane = new VBox();
        final Text title = new TextWithStyle(translate("Display settings"), TITLE_CSS_CLASS);

        displayPane.getStyleClass().add(CONFIGURATION_PANE_CSS_CLASS);
        displayPane.getChildren().add(title);

        final GridPane displayContentPane = new GridPane();
        displayContentPane.getStyleClass().add("grid");

        final ComboBox<GLSL> glslComboBox = new ComboBox<>();
        glslComboBox.setValue(winePrefixDTO.getGlslValue());
        addItems(glslComboBox, GLSL.class);
        displayContentPane.add(new TextWithStyle(translate("GLSL support"), CAPTION_TITLE_CSS_CLASS), 0, 0);
        displayContentPane.add(glslComboBox, 1, 0);

        final ComboBox<DirectDrawRenderer> directDrawRendererComboBox = new ComboBox<>();
        directDrawRendererComboBox.setValue(winePrefixDTO.getDirectDrawRenderer());
        addItems(directDrawRendererComboBox, DirectDrawRenderer.class);
        displayContentPane.add(new TextWithStyle(translate("Direct Draw Renderer"), CAPTION_TITLE_CSS_CLASS), 0, 1);
        displayContentPane.add(directDrawRendererComboBox, 1, 1);

        final ComboBox<VideoMemorySize> videoMemorySizeComboBox = new ComboBox<>();
        videoMemorySizeComboBox.setValue(winePrefixDTO.getVideoMemorySize());
        addItemsVideoMemorySize(videoMemorySizeComboBox);
        displayContentPane.add(new TextWithStyle(translate("Video memory size"), CAPTION_TITLE_CSS_CLASS), 0, 2);
        displayContentPane.add(videoMemorySizeComboBox, 1, 2);

        final ComboBox<OffscreenRenderingMode> offscreenRenderingModeComboBox = new ComboBox<>();
        offscreenRenderingModeComboBox.setValue(winePrefixDTO.getOffscreenRenderingMode());
        addItems(offscreenRenderingModeComboBox, OffscreenRenderingMode.class);
        displayContentPane.add(new TextWithStyle(translate("Offscreen rendering mode"), CAPTION_TITLE_CSS_CLASS), 0, 3);
        displayContentPane.add(offscreenRenderingModeComboBox, 1, 3);

        final ComboBox<RenderTargetModeLock> renderTargetModeLockComboBox = new ComboBox<>();
        renderTargetModeLockComboBox.setValue(winePrefixDTO.getRenderTargetModeLock());
        displayContentPane.add(new TextWithStyle(translate("Render target lock mode"), CAPTION_TITLE_CSS_CLASS), 0, 4);
        displayContentPane.add(renderTargetModeLockComboBox, 1, 4);

        final ComboBox<Multisampling> multisamplingComboBox = new ComboBox<>();
        multisamplingComboBox.setValue(winePrefixDTO.getMultisampling());
        addItems(multisamplingComboBox, Multisampling.class);
        displayContentPane.add(new TextWithStyle(translate("Multisampling"), CAPTION_TITLE_CSS_CLASS), 0, 5);
        displayContentPane.add(multisamplingComboBox, 1, 5);

        final ComboBox<StrictDrawOrdering> strictDrawOrderingComboBox  = new ComboBox<>();
        strictDrawOrderingComboBox.setValue(winePrefixDTO.getStrictDrawOrdering());
        addItems(strictDrawOrderingComboBox, StrictDrawOrdering.class);
        displayContentPane.add(new TextWithStyle(translate("Strict Draw Ordering"), CAPTION_TITLE_CSS_CLASS), 0, 6);
        displayContentPane.add(strictDrawOrderingComboBox, 1, 6);

        final ComboBox<AlwaysOffscreen> alwaysOffscreenComboBox  = new ComboBox<>();
        alwaysOffscreenComboBox.setValue(winePrefixDTO.getAlwaysOffscreen());
        addItems(alwaysOffscreenComboBox, AlwaysOffscreen.class);
        displayContentPane.add(new TextWithStyle(translate("Always Offscreen"), CAPTION_TITLE_CSS_CLASS), 0, 7);
        displayContentPane.add(alwaysOffscreenComboBox, 1, 7);

        displayContentPane.getRowConstraints().addAll(
                new RowConstraints(50.),
                new RowConstraints(50.),
                new RowConstraints(50.),
                new RowConstraints(50.),
                new RowConstraints(50.),
                new RowConstraints(50.),
                new RowConstraints(50.),
                new RowConstraints(50.),
                new RowConstraints(50.)
        );

        displayContentPane.getColumnConstraints().addAll(
                new ColumnConstraintsWithPercentage(30),
                new ColumnConstraintsWithPercentage(70)
        );

        displayPane.getChildren().addAll(displayContentPane);
        displayTab.setContent(displayPane);
        displayTab.setClosable(false);

        lockableElements.addAll(Arrays.asList(
                glslComboBox, directDrawRendererComboBox, offscreenRenderingModeComboBox,
                renderTargetModeLockComboBox, multisamplingComboBox, strictDrawOrderingComboBox,
                alwaysOffscreenComboBox, videoMemorySizeComboBox));

        return displayTab;
    }


    protected Tab drawInputTab(WinePrefixDTO containerEntity) {
        final Tab inputTab = new Tab(translate("Input"));
        final VBox inputPane = new VBox();
        final Text title = new TextWithStyle(translate("Input settings"), TITLE_CSS_CLASS);

        inputPane.getStyleClass().add(CONFIGURATION_PANE_CSS_CLASS);
        inputPane.getChildren().add(title);

        final GridPane inputContentPane = new GridPane();
        inputContentPane.getStyleClass().add("grid");

        final ComboBox<MouseWarpOverride> mouseWarpOverrideComboBox = new ComboBox<>();
        mouseWarpOverrideComboBox.setValue(containerEntity.getMouseWarpOverride());
        addItems(mouseWarpOverrideComboBox, MouseWarpOverride.class);
        inputContentPane.add(new TextWithStyle(translate("Mouse Warp Override"), CAPTION_TITLE_CSS_CLASS), 0, 0);
        inputContentPane.add(mouseWarpOverrideComboBox, 1, 0);

        inputContentPane.getRowConstraints().addAll(
                new RowConstraints(50.)
        );

        inputContentPane.getColumnConstraints().addAll(
                new ColumnConstraintsWithPercentage(30),
                new ColumnConstraintsWithPercentage(70)
        );

        inputPane.getChildren().addAll(inputContentPane);
        inputTab.setContent(inputPane);
        inputTab.setClosable(false);

        lockableElements.add(mouseWarpOverrideComboBox);
        return inputTab;
    }


    protected Tab drawToolsTab(WinePrefixDTO containerEntity) {
        final Tab toolsTab = new Tab(translate("Tools"));
        final VBox toolsPane = new VBox();
        final Text title = new TextWithStyle(translate("Wine tools"), TITLE_CSS_CLASS);

        toolsPane.getStyleClass().add(CONFIGURATION_PANE_CSS_CLASS);
        toolsPane.getChildren().add(title);

        final GridPane toolsContentPane = new GridPane();
        toolsContentPane.getStyleClass().add("grid");

        toolsContentPane.add(wineToolButton(translate("Configure Wine"), "winecfg.png",
                e -> {}), 0, 0);

        toolsContentPane.add(wineToolCaption(translate("Configure Wine")), 0, 1);

        toolsContentPane.add(wineToolButton(translate("Registry Editor"), "regedit.png", null), 1, 0);
        toolsContentPane.add(wineToolCaption(translate("Registry Editor")), 1, 1);

        toolsContentPane.add(wineToolButton(translate("Windows reboot"), "rebootPrefix.png", null), 2, 0);
        toolsContentPane.add(wineToolCaption(translate("Windows reboot")), 2, 1);

        toolsContentPane.add(wineToolButton(translate("Repair virtual drive"), "repair.png", null), 3, 0);
        toolsContentPane.add(wineToolCaption(translate("Repair virtual drive")), 3, 1);

        toolsContentPane.add(wineToolButton(translate("Command prompt"), "cmd.png", null), 0, 3);
        toolsContentPane.add(wineToolCaption(translate("Command prompt")), 0, 4);

        toolsContentPane.add(wineToolButton(translate("Task manager"), "taskmgr.png", null), 1, 3);
        toolsContentPane.add(wineToolCaption(translate("Task manager")), 1, 4);

        toolsContentPane.add(wineToolButton(translate("Kill processes"), "killProcesses.png", null), 2, 3);
        toolsContentPane.add(wineToolCaption(translate("Kill processes")), 2, 4);

        toolsContentPane.add(wineToolButton(translate("Wine uninstaller"), "uninstaller.png", null), 3, 3);
        toolsContentPane.add(wineToolCaption(translate("Wine uninstaller")), 3, 4);

        toolsPane.getChildren().addAll(toolsContentPane);

        toolsContentPane.getColumnConstraints().addAll(
                new ColumnConstraintsWithPercentage(25),
                new ColumnConstraintsWithPercentage(25),
                new ColumnConstraintsWithPercentage(25),
                new ColumnConstraintsWithPercentage(25)
        );

        toolsContentPane.getRowConstraints().addAll(
                new RowConstraints(96.),
                new RowConstraints(25.),
                new RowConstraints(30.),
                new RowConstraints(96.),
                new RowConstraints(25.)
        );


        toolsTab.setContent(toolsPane);
        toolsTab.setClosable(false);
        return toolsTab;
    }

    private Text wineToolCaption(String caption) {
        final Text text = new TextWithStyle(caption, "wineToolCaption");
        GridPane.setHalignment(text, HPos.CENTER);
        GridPane.setValignment(text, VPos.CENTER);
        lockableElements.add(text);
        return text;
    }

    private Button wineToolButton(String caption, String imageName, EventHandler<? super MouseEvent> eventHandler) {
        final Button button = new Button(caption,
                new ImageView(
                        new Image(this.getClass().getResourceAsStream(imageName), 48., 48., true, true)
                )
        );
        button.getStyleClass().addAll("wineToolButton");
        button.setOnMouseClicked(event -> {
            lockAll();
            eventHandler.handle(event);
        });
        lockableElements.add(button);
        GridPane.setHalignment(button, HPos.CENTER);

        return button;
    }

    private void unlockAll() {
        for(Node element: lockableElements) {
            element.setDisable(false);
        }
    }

    private void lockAll() {
        for(Node element: lockableElements) {
            element.setDisable(true);
        }
    }

    private void addItemsVideoMemorySize(ComboBox<VideoMemorySize> videoMemorySizeComboBox) {
        videoMemorySizeComboBox.setItems(new ImmutableObservableList<>(VideoMemorySize.possibleValues()));
    }

    private <T extends Enum> void addItems(ComboBox<T> comboBox , Class<T> clazz) {
        final List<T> possibleValues = new ArrayList<>(EnumSet.allOf(clazz));

        final ObservableList<T> possibleValuesObservable = new ObservableListWrapper<>(possibleValues);
        comboBox.setItems(possibleValuesObservable);
    }

}