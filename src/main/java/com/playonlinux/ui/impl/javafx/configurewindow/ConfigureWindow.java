/*
 * Copyright (C) 2015 PÂRIS Quentin
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.playonlinux.ui.impl.javafx.configurewindow;

import com.playonlinux.domain.PlayOnLinuxError;
import com.playonlinux.ui.api.InstalledVirtualDrives;
import com.playonlinux.ui.api.PlayOnLinuxWindow;
import com.playonlinux.ui.impl.javafx.JavaFXEventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ConfigureWindow extends Stage implements PlayOnLinuxWindow {
    private final PlayOnLinuxWindow parent;
    private static ConfigureWindow instance;
    private final VirtualDrivesWidget installedVirtualDrivesWidget;

    /**
     * Get the instance of the configure window.
     * The singleton pattern is only meant to avoid opening this window twice.
     * @param parent
     * @return the configureWindow instance
     */
    public static ConfigureWindow getInstance(PlayOnLinuxWindow parent) throws PlayOnLinuxError {
        if(instance == null) {
            instance = new ConfigureWindow(parent);
        } else {
            instance.toFront();
        }
        return instance;
    }

    private ConfigureWindow(PlayOnLinuxWindow parent) throws PlayOnLinuxError {
        super();
        this.parent = parent;

        this.installedVirtualDrivesWidget = new VirtualDrivesWidget();

        GridPane gridPane = new GridPane();
        gridPane.add(installedVirtualDrivesWidget, 0, 0);

        Scene scene = new Scene(gridPane, 620, 400);

        ColumnConstraints columnConstraint = new ColumnConstraints();
        columnConstraint.setPercentWidth(25);

        gridPane.getColumnConstraints().add(columnConstraint);

        this.setScene(scene);
        this.setUpEvents();
        this.show();
    }

    public void setUpEvents() throws PlayOnLinuxError {
        InstalledVirtualDrives installedVirtualDrives = getEventHandler().getInstalledVirtualDrives();
        installedVirtualDrives.addObserver(this.installedVirtualDrivesWidget);

    }
    @Override
    public JavaFXEventHandler getEventHandler() {
        return parent.getEventHandler();
    }
}
